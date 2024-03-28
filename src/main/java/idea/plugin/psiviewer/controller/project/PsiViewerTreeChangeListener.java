package idea.plugin.psiviewer.controller.project;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiTreeUtil;
import idea.plugin.psiviewer.view.PsiViewerPanel;
import org.jetbrains.annotations.NotNull;

public class PsiViewerTreeChangeListener extends PsiTreeChangeAdapter {
    private static final Logger LOG = Logger.getInstance(PsiViewerTreeChangeListener.class);
    private final @NotNull Project myProject;

    public PsiViewerTreeChangeListener(@NotNull Project project) {
        myProject = project;
    }

    public void childrenChanged(@NotNull final PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        updateTreeFromPsiTreeChange(event);
    }

    private void updateTreeFromPsiTreeChange(final PsiTreeChangeEvent event) {
        if (isElementChangedUnderViewerRoot(event)) {
            LOG.debug("PSI Change, starting update timer");
            ApplicationManager.getApplication().runWriteAction(() -> getViewerPanel().refreshRootElement());
        }
    }

    private boolean isElementChangedUnderViewerRoot(final PsiTreeChangeEvent event) {
        PsiElement elementChangedByPsi = event.getParent();
        PsiElement viewerRootElement = getViewerPanel().getRootElement();
        boolean isAncestor = false;
        try {
            isAncestor = PsiTreeUtil.isAncestor(viewerRootElement, elementChangedByPsi, false);
        } catch (Throwable ignored) {
        }

        return isAncestor;
    }

    private @NotNull PsiViewerPanel getViewerPanel() {
        return PsiViewerProjectService.getViewerPanel(myProject);
    }

}
