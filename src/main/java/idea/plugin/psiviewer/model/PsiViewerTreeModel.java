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

import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectService;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class PsiViewerTreeModel implements TreeModel {
    private final PsiViewerProjectService myPsiViewerProjectService;
    private PsiElement myRootElement;

    public PsiViewerTreeModel(PsiViewerProjectService projectComponent) {
        myPsiViewerProjectService = projectComponent;
    }

    @Override
    public Object getRoot() {
        return myRootElement;
    }

    public void setRoot(PsiElement root) {
        myRootElement = root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        PsiElement psi = (PsiElement) parent;
        List<PsiElement> children = getFilteredChildren(psi);
        return children.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        PsiElement psi = (PsiElement) parent;
        return getFilteredChildren(psi).size();
    }

    @Override
    public boolean isLeaf(Object node) {
        PsiElement psi = (PsiElement) node;
        return getFilteredChildren(psi).isEmpty();
    }

    private List<PsiElement> getFilteredChildren(PsiElement psi) {
        return ReadAction.compute(() -> {
            final List<PsiElement> filteredChildren = new ArrayList<>();

            for (PsiElement e = psi.getFirstChild(); e != null; e = e.getNextSibling())
                if (isValid(e)) {
                    filteredChildren.add(e);
                }

            return filteredChildren;
        });
    }

    private boolean isValid(PsiElement psiElement) {
        return !myPsiViewerProjectService.isFilterWhitespace() || !(psiElement instanceof PsiWhiteSpace);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (!(child instanceof PsiElement)) return -1;
        PsiElement psiParent = (PsiElement) parent;
        List<PsiElement> psiChildren = getFilteredChildren(psiParent);

        return psiChildren.indexOf(child);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public synchronized void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public synchronized void removeTreeModelListener(TreeModelListener l) {
    }
}
