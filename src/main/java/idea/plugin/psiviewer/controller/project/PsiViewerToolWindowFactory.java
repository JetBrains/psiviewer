package idea.plugin.psiviewer.controller.project;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import idea.plugin.psiviewer.controller.application.PsiViewerApplicationSettings;
import idea.plugin.psiviewer.view.PsiViewerPanel;
import org.jetbrains.annotations.NotNull;

public class PsiViewerToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        PsiViewerProjectService component = idea.plugin.psiviewer.controller.project.PsiViewerProjectService.getInstance(project);
        ContentManager contentManager = toolWindow.getContentManager();
        PsiViewerPanel panel = component.initToolWindow(toolWindow);
        Content content = contentManager.getFactory().createContent(panel, null, false);
        contentManager.addContent(content);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return !project.isDefault() && PsiViewerApplicationSettings.getInstance().PLUGIN_ENABLED;
    }
}
