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

package idea.plugin.psiviewer.model;

import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiWhiteSpace;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectComponent;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class PsiViewerTreeModel implements TreeModel
{
    private PsiElement _root;
    private final PsiViewerProjectComponent _projectComponent;

    public PsiViewerTreeModel(PsiViewerProjectComponent projectComponent)
    {
        _projectComponent = projectComponent;
    }

    public Object getRoot()
    {
        return _root;
    }

    public void setRoot(PsiElement root)
    {
        _root = root;
    }

    public Object getChild(Object parent, int index)
    {
        PsiElement psi = (PsiElement) parent;
        List<PsiElement> children = getFilteredChildren(psi);
        return children.get(index);
    }

    public int getChildCount(Object parent)
    {
        PsiElement psi = (PsiElement) parent;
        return getFilteredChildren(psi).size();
    }

    public boolean isLeaf(Object node)
    {
        PsiElement psi = (PsiElement) node;
        return psi.getChildren().length == 0;
    }

    private List<PsiElement> getFilteredChildren(PsiElement psi)
    {
        PsiElement[] children = psi.getChildren();
        List<PsiElement> filteredChildren = new ArrayList<PsiElement>(children.length);
        for (PsiElement child : children)
        {
            if (isValid(child))
            {
                if (psi instanceof PsiLanguageInjectionHost) {
                    List<Pair<PsiElement,TextRange>> injected = ((PsiLanguageInjectionHost) psi).getInjectedPsi();

                    if (injected != null) {
                        for(Pair<PsiElement,TextRange> pair : injected) {
                            PsiElement element = pair.getFirst();

                            if (isValid(element))
                                filteredChildren.add(element);
                        }

                        continue;
                    }
                }

                filteredChildren.add(child);
            }
        }
        return filteredChildren;
    }

    private boolean isValid(PsiElement psiElement)
    {
        return !_projectComponent.isFilterWhitespace() || !(psiElement instanceof PsiWhiteSpace);
    }

    public int getIndexOfChild(Object parent, Object child)
    {
        PsiElement psiParent = (PsiElement) parent;
        List<PsiElement> psiChildren = getFilteredChildren(psiParent);

        return psiChildren.indexOf(child);
    }

    public void valueForPathChanged(TreePath path, Object newValue)
    {
    }

    public synchronized void addTreeModelListener(TreeModelListener l)
    {
    }

    public synchronized void removeTreeModelListener(TreeModelListener l)
    {
    }
}
