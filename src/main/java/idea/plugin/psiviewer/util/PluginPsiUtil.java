/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.util;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

public class PluginPsiUtil
{
    @Nullable
    private static VirtualFile getVirtualFile(PsiElement psiElement) {
        if (psiElement == null || !psiElement.isValid() || psiElement.getContainingFile() == null) {
            return null;
        }
        return psiElement.getContainingFile().getVirtualFile();
    }

    @Nullable
    public static PsiFile getContainingFile(PsiElement psiElement)
    {
        if (psiElement == null || !psiElement.isValid())
        {
            return null;
        }

        return psiElement.getContainingFile();
    }

    public static boolean isElementInSelectedFile(Project project, PsiElement psiElement)
    {
        VirtualFile elementFile = getVirtualFile(psiElement);
        if (elementFile == null)
        {
            return false;
        }

        VirtualFile[] currentEditedFiles = FileEditorManager.getInstance(project).getSelectedFiles();

        for (VirtualFile file : currentEditedFiles)
        {
            if (elementFile.equals(file))
            {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Editor getEditorIfSelected(Project project, PsiElement psiElement)
    {
        VirtualFile elementFile = getVirtualFile(psiElement);
        if (elementFile == null)
        {
            return null;
        }

        FileEditor fileEditor = FileEditorManager.getInstance(project).getSelectedEditor(elementFile);

        Editor editor = null;

        if (fileEditor instanceof TextEditor) {
            editor = ((TextEditor) fileEditor).getEditor();
        }

        return editor;
    }
}
