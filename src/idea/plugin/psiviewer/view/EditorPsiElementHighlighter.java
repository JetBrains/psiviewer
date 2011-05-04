/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.view;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.controller.application.Configuration;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectComponent;

class EditorPsiElementHighlighter
{
    private static final Logger LOG = Logger.getInstance("idea.plugin.psiviewer.view.Highlighter");

    private final Project _project;
    private final TextAttributes _textAttributes;
    private RangeHighlighter _highlighter;
    private Editor _editor;

    public EditorPsiElementHighlighter(Project project)
    {
        _project = project;
        _textAttributes = Configuration.getInstance().getTextAttributes();
    }

    public void highlightElement(final PsiElement psiElement)
    {
        ApplicationManager.getApplication().runReadAction(new Runnable()
        {
            public void run()
            {
                apply(psiElement);
            }
        });
    }


    public void removeHilight()
    {
        ApplicationManager.getApplication().runReadAction(new Runnable()
        {
            public void run()
            {
                remove();
            }
        });
    }

    private void apply(PsiElement element)
    {
        remove();

        _editor = FileEditorManager.getInstance(_project).getSelectedTextEditor();
        if (_editor == null)
        {
            debug("no editor => no need to highlight");
            return;
        }

        if (element instanceof PsiWhiteSpace && isWhiteSpaceFiltered())
            return;

        if (isHighlightOn() && isElementInEditor(_editor, element))
        {
            TextRange textRange = element.getTextRange();
            debug("Adding highlighting for " + textRange);
            _highlighter = _editor.getMarkupModel().addRangeHighlighter(textRange.getStartOffset(),
                                                                        textRange.getEndOffset(),
                                                                        PsiViewerConstants.PSIVIEWER_HIGHLIGHT_LAYER,
                                                                        _textAttributes,
                                                                        HighlighterTargetArea.EXACT_RANGE);
        }
    }

    private void remove() {
        if (_highlighter != null && _highlighter.isValid())
        {
            debug("Removing highlighter for " + _highlighter);
            _editor.getMarkupModel().removeHighlighter(_highlighter);
            _highlighter = null;
        }
    }

    private boolean isWhiteSpaceFiltered()
    {
        return PsiViewerProjectComponent.getInstance(_project).isFilterWhitespace();
    }

    private boolean isHighlightOn()
    {
        return PsiViewerProjectComponent.getInstance(_project).isHighlighted();
    }

    private boolean isElementInEditor(Editor editor, PsiElement psiElement)
    {
        if (psiElement == null || psiElement.getContainingFile() == null) return false;
        VirtualFile elementFile = psiElement.getContainingFile().getVirtualFile();
        if (elementFile == null)
            return false;   // 20050826
        VirtualFile editorFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        return elementFile.equals(editorFile);
    }

    private static void debug(String message)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug(message);
        }
    }
}
