/*
    IDEA PsiViewer Plugin
    Copyright (C) 2002 Andrew J. Armstrong

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

	Author:
	Andrew J. Armstrong <andrew_armstrong@bigpond.com>
*/
package idea.plugin.psiviewer.controller.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import idea.plugin.psiviewer.util.ActionEventUtil;

public class ViewElementAtCaretAction extends BaseGlobalAction
{

    protected PsiElement getTargetElement(AnActionEvent event)
    {
        Editor editor = ActionEventUtil.getEditor(event);
        if (editor == null)
            return null;
        final PsiFile psiFile = ActionEventUtil.getPsiFile(event);
        if (psiFile == null)
            return null;
        return psiFile.findElementAt(editor.getCaretModel().getOffset());
    }

    public String getToolWindowTitle()
    {
        return "Element at Caret";
    }

}
