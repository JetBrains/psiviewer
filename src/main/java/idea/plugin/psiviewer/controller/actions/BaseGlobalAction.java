/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectService;
import idea.plugin.psiviewer.util.ActionEventUtil;
import idea.plugin.psiviewer.view.PsiViewerPanel;
import org.jetbrains.annotations.NotNull;

abstract class BaseGlobalAction extends AnAction
{

    public void update(AnActionEvent event)
    {
        Presentation presentation = event.getPresentation();
        Project project = ActionEventUtil.getProject(event);
        if (project == null) {
            presentation.setEnabled(false);
            presentation.setVisible(false);
            return;
        }
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(PsiViewerConstants.ID_TOOL_WINDOW);
        if (toolWindow == null)
        { // tool window isn't registered
            presentation.setEnabled(false);
            presentation.setVisible(false);
            return;
        }
        VirtualFile file = ActionEventUtil.getVirtualFile(event);

        if (file == null)
        {
            presentation.setEnabled(false);
            presentation.setVisible(false);
            return;
        }
        presentation.setEnabled(toolWindow.isAvailable());
        presentation.setVisible(true);
    }

    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = ActionEventUtil.getProject(event);
        PsiDocumentManager.getInstance(project).commitAllDocuments();
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(PsiViewerConstants.ID_TOOL_WINDOW);
        if (toolWindow != null) {
            toolWindow.activate(null);
            PsiViewerPanel viewer = PsiViewerProjectService.getInstance(project).getViewerPanel();
            if (getTargetElement(event) == null)
                return;

            viewer.selectRootElement(getTargetElement(event), getToolWindowTitle());
        }
    }

    protected abstract String getToolWindowTitle();

    protected abstract PsiElement getTargetElement(AnActionEvent event);

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
