package idea.plugin.psiviewer.controller.application;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.util.xmlb.XmlSerializerUtil;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.util.Helpers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static idea.plugin.psiviewer.PsiViewerConstants.DEFAULT_HIGHLIGHT_COLOR;
import static idea.plugin.psiviewer.PsiViewerConstants.DEFAULT_REFERENCE_HIGHLIGHT_COLOR;

@State(name = PsiViewerConstants.CONFIGURATION_COMPONENT_NAME, storages = @Storage("other.xml"))
public class PsiViewerApplicationSettings implements PersistentStateComponent<PsiViewerApplicationSettings> {
    private final TextAttributes myTextAttributes = new TextAttributes();
    public String HIGHLIGHT_COLOR;
    public String REFERENCE_HIGHLIGHT_COLOR;
    private final TextAttributes myReferenceTextAttributes = new TextAttributes();

    public PsiViewerApplicationSettings() {
        HIGHLIGHT_COLOR = DEFAULT_HIGHLIGHT_COLOR;
        REFERENCE_HIGHLIGHT_COLOR = DEFAULT_REFERENCE_HIGHLIGHT_COLOR;
        getTextAttributes().setBackgroundColor(Helpers.parseColor(HIGHLIGHT_COLOR));
    }

    public static PsiViewerApplicationSettings getInstance() {
        return ServiceManager.getService(PsiViewerApplicationSettings.class);
    }

    @Nullable
    @Override
    public PsiViewerApplicationSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PsiViewerApplicationSettings state) {
        XmlSerializerUtil.copyBean(state, this);

        getTextAttributes().setBackgroundColor(Helpers.parseColor(HIGHLIGHT_COLOR));
        getReferenceTextAttributes().setBackgroundColor(Helpers.parseColor(REFERENCE_HIGHLIGHT_COLOR));
    }

    public TextAttributes getTextAttributes() {
        return myTextAttributes;
    }

    public TextAttributes getReferenceTextAttributes() {
        return myReferenceTextAttributes;
    }
}
