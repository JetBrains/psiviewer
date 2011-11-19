/*
    IDEA Plugin
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

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class PropertySheetHeaderRenderer extends JLabel implements TableCellRenderer, PsiViewerConstants {
    private final ElementVisitor _elementVisitor = new ElementVisitor();
    private final XmlElementVisitor _elementVisitorXml = new ElementVisitorXml();

    public PropertySheetHeaderRenderer() {
        super();
    }

    public PropertySheetHeaderRenderer(Icon image) {
        super(image);
    }

    public PropertySheetHeaderRenderer(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public PropertySheetHeaderRenderer(Icon image, int horizontalAlignment, Border border) {
        super(image, horizontalAlignment);
        setBorder(border);
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        setText(value.toString());
        return this;
    }

    public void setIconForElement(PsiElement psiElement) {
        setIcon(IconCache.DEFAULT_ICON);
        if (psiElement == null)
            return;
        psiElement.accept(_elementVisitor);
        psiElement.accept(_elementVisitorXml);

//        try {
//            psiElement.accept(new PropertySheetJavaElementVisitor(this));
//        } catch (Exception e) {
//        }
    }

    private class ElementVisitor extends PsiElementVisitor {
        public void visitBinaryFile(PsiBinaryFile psiElement) {
            setIcon(IconCache.getIcon(PsiBinaryFile.class));
        }

        public void visitComment(PsiComment psiElement) {
            setIcon(IconCache.getIcon(PsiComment.class));
        }

        public void visitDirectory(PsiDirectory psiElement) {
            setIcon(IconCache.getIcon(PsiDirectory.class));
        }

        public void visitPlainTextFile(PsiPlainTextFile psiElement) {
            setIcon(IconCache.getIcon(PsiPlainTextFile.class));
        }

        public void visitWhiteSpace(PsiWhiteSpace psiElement) {
            setIcon(IconCache.getIcon(PsiWhiteSpace.class));
        }
    }

    private class ElementVisitorXml extends XmlElementVisitor {
        public void visitXmlAttribute(XmlAttribute psiElement) {
            setIcon(IconCache.getIcon(XmlAttribute.class));
        }

        public void visitXmlComment(XmlComment psiElement) {
            setIcon(IconCache.getIcon(XmlComment.class));
        }

        public void visitXmlFile(XmlFile psiElement) {
            setIcon(IconCache.getIcon(XmlFile.class));
        }

        public void visitXmlTag(XmlTag psiElement) {
            setIcon(IconCache.getIcon(XmlTag.class));
        }

        public void visitXmlAttributeValue(XmlAttributeValue psiElement) {
        }

        public void visitXmlDecl(XmlDecl psiElement) {
        }

        public void visitXmlDoctype(XmlDoctype psiElement) {
        }

        public void visitXmlDocument(XmlDocument psiElement) {
        }
        
        public void visitXmlProlog(XmlProlog psiElement) {
        }

        public void visitXmlToken(XmlToken psiElement) {
        }
    }

}

