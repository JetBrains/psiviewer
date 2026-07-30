package idea.plugin.psiviewer.controller.project

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiTreeChangeAdapter
import com.intellij.psi.PsiTreeChangeEvent
import com.intellij.psi.util.PsiTreeUtil
import idea.plugin.psiviewer.view.PsiViewerPanel

private val log = logger<PsiViewerTreeChangeListener>()

class PsiViewerTreeChangeListener(private val myProject: Project) : PsiTreeChangeAdapter() {
    override fun childrenChanged(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childAdded(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childMoved(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childRemoved(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun childReplaced(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    override fun propertyChanged(event: PsiTreeChangeEvent) = updateTreeFromPsiTreeChange(event)

    private fun updateTreeFromPsiTreeChange(event: PsiTreeChangeEvent) {
        if (!this.viewerPanel.isVisible) {
            return
        }

        if (isElementChangedUnderViewerRoot(event)) {
            log.debug("PSI Change, starting update timer")
            ApplicationManager.getApplication().runWriteAction(Runnable { this.viewerPanel.refreshRootElement() })
        }
    }

    private fun isElementChangedUnderViewerRoot(event: PsiTreeChangeEvent): Boolean {
        val elementChangedByPsi = event.parent
        val viewerRootElement = this.viewerPanel.rootElement
        var isAncestor = false
        try {
            isAncestor = PsiTreeUtil.isAncestor(viewerRootElement, elementChangedByPsi, false)
        } catch (ignored: Throwable) {
        }

        return isAncestor
    }

    private val viewerPanel: PsiViewerPanel
        get() = PsiViewerProjectService.getViewerPanel(myProject)
}
