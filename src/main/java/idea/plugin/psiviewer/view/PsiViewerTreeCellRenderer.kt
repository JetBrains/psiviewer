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
package idea.plugin.psiviewer.view

import com.intellij.openapi.application.runReadActionBlocking
import com.intellij.psi.*
import com.intellij.psi.xml.*
import idea.plugin.psiviewer.PsiViewerConstants
import java.awt.Component
import javax.swing.JTree
import javax.swing.tree.DefaultTreeCellRenderer

private const val MAX_TEXT_LENGTH = 80

internal class PsiViewerTreeCellRenderer : DefaultTreeCellRenderer(), PsiViewerConstants {
    private val _elementVisitor = ElementVisitor()
    private val _elementVisitorXml = ElementVisitorXml()

    override fun getTreeCellRendererComponent(
        tree: JTree?, value: Any?, isSelected: Boolean, isExpanded: Boolean,
        isLeaf: Boolean, row: Int, hasFocus: Boolean
    ): Component {
        runReadActionBlocking {
            super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus)
            setIcon(IconCache.DEFAULT_ICON)

            val psiElement = value as PsiElement
            psiElement.accept(_elementVisitor)
            psiElement.accept(_elementVisitorXml)
        }
        return this
    }

    init {
        isOpaque = false
    }

    private inner class ElementVisitor : PsiElementVisitor() {
        override fun visitBinaryFile(psiElement: PsiBinaryFile) {
            setIcon(IconCache.getIcon(PsiBinaryFile::class.java))
            text = "PsiBinaryFile: " + psiElement.name
        }

        override fun visitComment(psiElement: PsiComment) {
            setIcon(IconCache.getIcon(PsiComment::class.java))
            text = "PsiComment: " + truncate(psiElement.text)
        }

        override fun visitDirectory(psiElement: PsiDirectory) {
            setIcon(IconCache.getIcon(PsiDirectory::class.java))
            text = "PsiDirectory: " + psiElement.name
        }

        override fun visitElement(psiElement: PsiElement) {
            text = psiElement.toString()
        }

        override fun visitFile(psiElement: PsiFile) {
            text = "PsiFile: " + psiElement.name
        }

        override fun visitPlainTextFile(psiElement: PsiPlainTextFile) {
            setIcon(IconCache.getIcon(PsiPlainTextFile::class.java))
            text = "PsiPlainTextFile: " + psiElement.name
        }

        override fun visitWhiteSpace(psiElement: PsiWhiteSpace) {
            setIcon(IconCache.getIcon(PsiWhiteSpace::class.java))
            text = "PsiWhiteSpace"
        }

        private fun truncate(text: String): String {
            return if (text.length > MAX_TEXT_LENGTH) text.substring(0, MAX_TEXT_LENGTH).trim { it <= ' ' } + "..."
            else text
        }
    }

    private inner class ElementVisitorXml : XmlElementVisitor() {
        override fun visitXmlAttribute(psiElement: XmlAttribute) {
            setIcon(IconCache.getIcon(XmlAttribute::class.java))
            text = "XmlAttribute: " + psiElement.name
        }

        override fun visitXmlAttributeValue(psiElement: XmlAttributeValue) {
            text = "XmlAttributeValue"
        }

        override fun visitXmlComment(psiElement: XmlComment) {
            setIcon(IconCache.getIcon(XmlComment::class.java))
            text = "XmlComment"
        }

        override fun visitXmlDecl(psiElement: XmlDecl) {
            text = "XmlDecl"
        }

        override fun visitXmlDoctype(psiElement: XmlDoctype) {
            text = "XmlDoctype"
        }

        override fun visitXmlDocument(psiElement: XmlDocument) {
            text = "XmlDocument"
        }

        override fun visitXmlFile(psiElement: XmlFile) {
            setIcon(IconCache.getIcon(XmlFile::class.java))
            text = "XmlFile: " + psiElement.name
        }

        override fun visitXmlProlog(psiElement: XmlProlog) {
            text = "XmlProlog"
        }

        override fun visitXmlTag(psiElement: XmlTag) {
            setIcon(IconCache.getIcon(XmlTag::class.java))
            text = "XmlTag: " + psiElement.name
        }

        override fun visitXmlToken(psiElement: XmlToken) {
            text = "XmlToken: " + psiElement.text
        }
    }
}
