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
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlComment;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.util.Helpers;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

class IconCache implements PsiViewerConstants
{
    public static final Icon DEFAULT_ICON = Helpers.getIcon(ICON_PSI);
    public static final Map<Class, Icon> _iconCache = new HashMap<>();

    static
    {
        _iconCache.put(PsiBinaryFile.class, Helpers.getIcon("/fileTypes/unknown.png"));
        _iconCache.put(PsiPlainTextFile.class, Helpers.getIcon("/fileTypes/text.png"));

        _iconCache.put(PsiWhiteSpace.class, Helpers.getIcon(ICON_WHITESPACE));
        _iconCache.put(PsiComment.class, Helpers.getIcon(ICON_COMMENT));

        _iconCache.put(PsiDirectory.class, Helpers.getIcon("/nodes/folder.png"));

//        try {
//            _iconCache.put(PsiField.class, Helpers.getIcon("/nodes/field.png"));
//            _iconCache.put(PsiMethod.class, Helpers.getIcon("/nodes/method.png"));
//            _iconCache.put(PsiVariable.class, Helpers.getIcon("/images/variable.png"));
//            _iconCache.put(PsiClass.class, Helpers.getIcon("/nodes/class.png"));
//        } catch (Exception e) {
//        }

        _iconCache.put(XmlFile.class, Helpers.getIcon("/fileTypes/xml.png"));
        _iconCache.put(XmlAttribute.class, Helpers.getIcon(ICON_XML_ATTRIBUTE));
        _iconCache.put(XmlComment.class, Helpers.getIcon(ICON_XML_COMMENT));
        _iconCache.put(XmlTag.class, Helpers.getIcon(ICON_XML_TAG));
    }

    public static Icon getIcon(Class clazz)
    {
        return _iconCache.get(clazz);
    }

}
