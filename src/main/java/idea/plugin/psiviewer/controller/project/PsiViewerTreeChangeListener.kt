package idea.plugin.psiviewer.controller.project

import com.intellij.openapi.application.EDT
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiTreeChangeAdapter
import com.intellij.psi.PsiTreeChangeEvent
import com.intellij.psi.util.PsiTreeUtil
import idea.plugin.psiviewer.view.PsiViewerPanel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

private val REFRESH_DEBOUNCE = 100.milliseconds

class PsiViewerTreeChangeListener(
    private val project: Project,
    coroutineScope: CoroutineScope,
) : PsiTreeChangeAdapter() {
    private val refreshRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        coroutineScope.launch { collectRefreshRequests() }
    }

    @OptIn(FlowPreview::class)
    private suspend fun collectRefreshRequests() {
        refreshRequests
            .debounce(REFRESH_DEBOUNCE)
            .collect {
                withContext(Dispatchers.EDT) {
                    viewerPanel.refreshRootElement()
                }
            }
    }

    override fun childrenChanged(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childAdded(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childMoved(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childRemoved(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childReplaced(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun propertyChanged(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    private fun updateTreeFromPsiTreeChange(event: PsiTreeChangeEvent) {
        if (!viewerPanel.isVisible) {
            return
        }

        if (isElementChangedUnderViewerRoot(event)) {
            refreshRequests.tryEmit(Unit)
        }
    }

    private fun isElementChangedUnderViewerRoot(event: PsiTreeChangeEvent): Boolean {
        val viewerRootElement = viewerPanel.rootElement
        var isAncestor = false
        try {
            isAncestor = PsiTreeUtil.isAncestor(viewerRootElement, event.parent, false)
        } catch (ignored: Throwable) {
        }

        return isAncestor
    }

    private val viewerPanel: PsiViewerPanel
        get() = PsiViewerProjectService.getViewerPanel(project)
}
