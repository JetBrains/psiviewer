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

package idea.plugin.psiviewer.view;

import com.intellij.psi.*;
import com.intellij.psi.xml.*;
import idea.plugin.psiviewer.PsiViewerConstants;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

class PsiViewerTreeCellRenderer extends DefaultTreeCellRenderer implements PsiViewerConstants {
    private final ElementVisitor _elementVisitor = new ElementVisitor();
    private final XmlElementVisitor _elementVisitorXml = new ElementVisitorXml();

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded,
                                                  boolean isLeaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);
        setIcon(IconCache.DEFAULT_ICON);

        PsiElement psiElement = (PsiElement) value;

        psiElement.accept(_elementVisitor);
        psiElement.accept(_elementVisitorXml);

        return this;
    }

    public PsiViewerTreeCellRenderer() {
        setOpaque(false);
    }

    private class ElementVisitor extends PsiElementVisitor {

        private static final int MAX_TEXT_LENGTH = 80;


        public void visitBinaryFile(PsiBinaryFile psiElement) {
            setIcon(IconCache.getIcon(PsiBinaryFile.class));
            setText("PsiBinaryFile: " + psiElement.getName());
        }


        public void visitComment(PsiComment psiElement) {
            setIcon(IconCache.getIcon(PsiComment.class));
            setText("PsiComment: " + truncate(psiElement.getText()));
        }

        public void visitDirectory(PsiDirectory psiElement) {
            setIcon(IconCache.getIcon(PsiDirectory.class));
            setText("PsiDirectory: " + psiElement.getName());
        }

        public void visitElement(PsiElement psiElement) {
            setText(psiElement.toString());
        }


        public void visitFile(PsiFile psiElement) {
            setText("PsiFile: " + psiElement.getName());
        }


        public void visitPlainTextFile(PsiPlainTextFile psiElement) {
            setIcon(IconCache.getIcon(PsiPlainTextFile.class));
            setText("PsiPlainTextFile: " + psiElement.getName());
        }


        public void visitWhiteSpace(@NotNull PsiWhiteSpace psiElement) {
            setIcon(IconCache.getIcon(PsiWhiteSpace.class));
            setText("PsiWhiteSpace");
        }

        private String truncate(String text) {
            if (text.length() > MAX_TEXT_LENGTH)
                return text.substring(0, MAX_TEXT_LENGTH).trim() + "...";
            else
                return text;
        }

        private ElementVisitor() {
        }
    }



    private class ElementVisitorXml extends XmlElementVisitor {
        public void visitXmlAttribute(XmlAttribute psiElement) {
            setIcon(IconCache.getIcon(XmlAttribute.class));
            setText("XmlAttribute: " + psiElement.getName());
        }

        public void visitXmlAttributeValue(XmlAttributeValue psiElement) {
            setText("XmlAttributeValue");
        }

        public void visitXmlComment(XmlComment psiElement) {
            setIcon(IconCache.getIcon(XmlComment.class));
            setText("XmlComment");
        }

        public void visitXmlDecl(XmlDecl psiElement) {
            setText("XmlDecl");
        }

        public void visitXmlDoctype(XmlDoctype psiElement) {
            setText("XmlDoctype");
        }

        public void visitXmlDocument(XmlDocument psiElement) {
            setText("XmlDocument");
        }

        public void visitXmlFile(XmlFile psiElement) {
            setIcon(IconCache.getIcon(XmlFile.class));
            setText("XmlFile: " + psiElement.getName());
        }

        public void visitXmlProlog(XmlProlog psiElement) {
            setText("XmlProlog");
        }

        public void visitXmlTag(XmlTag psiElement) {
            setIcon(IconCache.getIcon(XmlTag.class));
            setText("XmlTag: " + psiElement.getName());
        }

        public void visitXmlToken(XmlToken psiElement) {
            setText("XmlToken: " + psiElement.getText());
        }
    }

}
