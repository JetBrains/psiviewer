package idea.plugin.psiviewer.controller.application;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import static idea.plugin.psiviewer.PsiViewerConstants.DEFAULT_HIGHLIGHT_COLOR;

/**
 * Created by Jon on 10/7/2016.
 */
public class PsiViewerApplicationSettings implements PersistentStateComponent<PsiViewerApplicationSettings> {
    public String HIGHLIGHT_COLOR = DEFAULT_HIGHLIGHT_COLOR;
    public boolean PLUGIN_ENABLED = true;

    private final TextAttributes _textAttributes = new TextAttributes();

    @Nullable
    @Override
    public PsiViewerApplicationSettings getState() {
        return this;
    }

    @Override
    public void loadState(PsiViewerApplicationSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static PsiViewerApplicationSettings getInstance() {
        return ServiceManager.getService(PsiViewerApplicationSettings.class);
    }

    public TextAttributes getTextAttributes()
    {
        return _textAttributes;
    }
}
