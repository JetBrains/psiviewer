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
package idea.plugin.psiviewer;

import com.intellij.openapi.editor.markup.HighlighterLayer;

public interface PsiViewerConstants
{
    // Plugin names
    public static final String PLUGIN_NAME = "PsiViewer";
    public static final String PROJECT_COMPONENT_NAME = "ProjectComponent";
    public static final String CONFIGURATION_COMPONENT_NAME = "Settings";

    // Icons
    public static final String ICON_CONFIGURATION = "/PsiViewer/images/psi.png";
    public static final String ICON_TOOL_WINDOW = "/PsiViewer/images/psiToolWindow.png";
    public static final String ICON_PSI = "/PsiViewer/images/psi18x18.png";
    public static final String ICON_WHITESPACE = "/PsiViewer/images/whitespace.png";
    public static final String ICON_FILTER_WHITESPACE = "/PsiViewer/images/filterWhitespace.png";
    public static final String ICON_TOGGLE_HIGHLIGHT = "/PsiViewer/images/highlighter.png";
    public static final String ICON_SHOW_PROPERTIES = "/objectBrowser/showStructure.png";
    public static final String ICON_COMMENT = "/PsiViewer/images/comment.png";
    public static final String ICON_XML_TAG = "/PsiViewer/images/xmlTag.png";
    public static final String ICON_XML_ATTRIBUTE = "/PsiViewer/images/xmlAttribute.png";
    public static final String ICON_XML_COMMENT = "/PsiViewer/images/xmlComment.png";

    // Ids
    public static final String ID_TOOL_WINDOW = "PsiViewer";
    public static final String ID_ACTION_GROUP = "PsiActionGroup";
    public static final String ID_ACTION_TOOLBAR = "PsiActionToolbar";

    // Miscellaneous
    public static final String URL_ABOUT = "/PsiViewer/html/About.html";
    public static final int PSIVIEWER_HIGHLIGHT_LAYER = HighlighterLayer.SELECTION - 100;
    public static final String DEFAULT_HIGHLIGHT_COLOR = "162 3 229 32"; // ThighMaster Accident
    public static final String TITLE_PREFIX_CURRENT_FILE = "Current File";
}
