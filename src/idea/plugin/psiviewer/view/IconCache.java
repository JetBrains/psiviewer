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
import com.intellij.psi.jsp.JspFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlComment;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.PsiViewerConstants;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

class IconCache implements PsiViewerConstants
{
    public static final Icon DEFAULT_ICON = Helpers.getIcon(ICON_PSI);
    public static final Map<Class,Icon> _iconCache = new HashMap<Class, Icon>();

    static
    {
//        _iconCache.put(PsiAnnotation.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAnnotationMemberValue.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAnnotationMethod.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAnnotationParameterList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAnonymousClass.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiArrayAccessExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiArrayInitializerExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiArrayInitializerMemberValue.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAssertStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiAssignmentExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiBinaryExpression.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiBinaryFile.class, Helpers.getIcon("/fileTypes/unknown.png"));
        _iconCache.put(JspFile.class, Helpers.getIcon("/fileTypes/jsp.png"));
        _iconCache.put(XmlFile.class, Helpers.getIcon("/fileTypes/xml.png"));
        _iconCache.put(PsiJavaFile.class, Helpers.getIcon("/fileTypes/java.png"));
        _iconCache.put(PsiPlainTextFile.class, Helpers.getIcon("/fileTypes/text.png"));

//        _iconCache.put(PsiBlockStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiBreakStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiCall.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiCallExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiCatchSection.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiClass.class, Helpers.getIcon("/nodes/class.png"));

//        _iconCache.put(PsiClassInitializer.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiClassObjectAccessExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiCodeBlock.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiCodeFragment.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiComment.class, Helpers.getIcon(ICON_COMMENT));

//        _iconCache.put(PsiCompiledElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiConditionalExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiConstructorCall.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiContinueStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiDeclarationStatement.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiDirectory.class, Helpers.getIcon("/nodes/folder.png"));

//        _iconCache.put(PsiDocCommentOwner.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiDoWhileStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiEmptyStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiEnumConstant.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiEnumConstantInitializer.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiErrorElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiExpressionCodeFragment.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiExpressionList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiExpressionListStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiExpressionStatement.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiField.class, Helpers.getIcon("/nodes/field.png"));

//        _iconCache.put(PsiFile.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiFileSystemItem.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiForeachStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiForStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiIdentifier.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiIfStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiImportList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiImportStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiImportStatementBase.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiImportStaticReferenceElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiImportStaticStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiInlineDocTag.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiInstanceOfExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiJavaCodeReferenceCodeFragment.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiJavaCodeReferenceElement.class, Helpers.getIcon(ICON_PSI));

//        _iconCache.put(PsiJavaToken.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiKeyword.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiLabeledStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiLiteralExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiLocalVariable.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiMethod.class, Helpers.getIcon("/nodes/method.png"));

//        _iconCache.put(PsiMethodCallExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiModifierList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiModifierListOwner.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiNamedElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiNameValuePair.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiNewExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiPackage.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiPackageStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiParameter.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiParameterList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiParenthesizedExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiPlainText.class, Helpers.getIcon(ICON_PSI));

//        _iconCache.put(PsiPostfixExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiPrefixExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiReferenceExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiReferenceList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiReferenceParameterList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiReturnStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiSuperExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiSwitchLabelStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiSwitchStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiSynchronizedStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiThisExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiThrowStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTryStatement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiType.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeCastExpression.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeCodeFragment.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeParameter.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeParameterList.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(PsiTypeParameterListOwner.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiVariable.class, Helpers.getIcon("/images/variable.png"));

//        _iconCache.put(PsiWhileStatement.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(PsiWhiteSpace.class, Helpers.getIcon(ICON_WHITESPACE));




//        _iconCache.put(XmlAttlistDecl.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(XmlAttribute.class, Helpers.getIcon(ICON_XML_ATTRIBUTE));

//        _iconCache.put(XmlAttributeDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlAttributeValue.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(XmlComment.class, Helpers.getIcon(ICON_XML_COMMENT));

//        _iconCache.put(XmlDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlDoctype.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlDocument.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlElement.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlElementContentSpec.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlElementDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlEntityDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlEntityRef.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlEnumeratedType.class, Helpers.getIcon(ICON_PSI));

//        _iconCache.put(XmlFile.class, Helpers.getIcon("/nodes/xml.png"));

//        _iconCache.put(XmlMarkupDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlNotationDecl.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlProcessingInstruction.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlProlog.class, Helpers.getIcon(ICON_PSI));

        _iconCache.put(XmlTag.class, Helpers.getIcon(ICON_XML_TAG));

//        _iconCache.put(XmlTagChild.class, Helpers.getIcon(ICON_PSI));
//        _iconCache.put(XmlToken.class, Helpers.getIcon(ICON_PSI));
    }

    public static Icon getIcon(Class clazz)
    {
        return _iconCache.get(clazz);
    }

}
