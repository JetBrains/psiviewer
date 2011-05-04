/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package idea.plugin.psiviewer.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class ActionEventUtil
{
    public static Project getProject(AnActionEvent event)
    {
        return (Project) event.getDataContext().getData(PlatformDataKeys.PROJECT.getName());
    }

    public static PsiElement getPsiElement(AnActionEvent event)
    {
        return (PsiElement) event.getDataContext().getData("psi.Element");
    }

    public static Editor getEditor(AnActionEvent event)
    {
        return (Editor) event.getDataContext().getData(PlatformDataKeys.EDITOR.getName());
    }

    public static PsiFile getPsiFile(AnActionEvent event)
    {
        return (PsiFile) event.getDataContext().getData("psi.File");
    }

    public static VirtualFile getVirtualFile(AnActionEvent event)
    {
        return (VirtualFile) event.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE.getName());
    }
}
