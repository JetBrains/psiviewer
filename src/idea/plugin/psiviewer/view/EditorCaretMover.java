/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.view;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import idea.plugin.psiviewer.util.PluginPsiUtil;

class EditorCaretMover
{
    private final Project _project;
    private boolean _shouldMoveCaret = true;

    public EditorCaretMover(Project project)
    {
        _project = project;
    }

    private void disableMovementOneTime()
    {
        _shouldMoveCaret = false;
    }

    public void moveEditorCaret(PsiElement element)
    {
        if (element == null) return;
        if (shouldMoveCaret(element))
        {
            Editor editor = getEditor(element);
            if (editor == null) return;

            int textOffset = element.getTextOffset();
            if (textOffset < editor.getDocument().getTextLength())
            {
                editor.getCaretModel().moveToOffset(textOffset);
                editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
            }
        }
        _shouldMoveCaret = true;
    }

    private boolean shouldMoveCaret(PsiElement element)
    {
        return _shouldMoveCaret && PluginPsiUtil.isElementInSelectedFile(_project, element);
    }

    private Editor getEditor(PsiElement element)
    {
        return PluginPsiUtil.getEditorIfSelected(_project, element);
    }

    public Editor openInEditor(PsiElement element)
    {
        PsiFile psiFile;
        int i;
        if (element instanceof PsiFile)
        {
            psiFile = (PsiFile) element;
            i = -1;
        }
        else
        {
            psiFile =  PluginPsiUtil.getContainingFile(element);
            i = element.getTextOffset();
        }
        
        if (psiFile == null) return null;

        final VirtualFile virtualFile = psiFile.getVirtualFile();

        if (virtualFile == null) return null;

        OpenFileDescriptor fileDesc = new OpenFileDescriptor(_project, virtualFile, i);    // 20050826 IDEA 5.0.1 #3461
        disableMovementOneTime();
        return FileEditorManager.getInstance(_project).openTextEditor(fileDesc, false);
    }

}
