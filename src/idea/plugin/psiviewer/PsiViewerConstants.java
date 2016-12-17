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
    String PLUGIN_NAME = "PsiViewer";
    String PROJECT_COMPONENT_NAME = "ProjectComponent";
    String CONFIGURATION_COMPONENT_NAME = "PsiViewerPluginSettings";

    // Icons
    String ICON_CONFIGURATION = "/images/psi.png";
    String ICON_TOOL_WINDOW = "/images/psiToolWindow.png";
    String ICON_PSI = "/images/psi18x18.png";
    String ICON_WHITESPACE = "/images/whitespace.png";
    String ICON_FILTER_WHITESPACE = "/images/filterWhitespace.png";
    String ICON_TOGGLE_HIGHLIGHT = "/images/highlighter.png";
    String ICON_SHOW_PROPERTIES = "/objectBrowser/showStructure.png";
    String ICON_COMMENT = "/images/comment.png";
    String ICON_XML_TAG = "/images/xmlTag.png";
    String ICON_XML_ATTRIBUTE = "/images/xmlAttribute.png";
    String ICON_XML_COMMENT = "/images/xmlComment.png";

    // Ids
    String ID_TOOL_WINDOW = "PsiViewer";
    String ID_ACTION_GROUP = "PsiActionGroup";
    String ID_ACTION_TOOLBAR = "PsiActionToolbar";

    // Miscellaneous
    int PSIVIEWER_HIGHLIGHT_LAYER = HighlighterLayer.SELECTION - 100;
    int PSIVIEWER_REFERENCE_HIGHLIGHT_LAYER = HighlighterLayer.SELECTION - 99;
    String DEFAULT_HIGHLIGHT_COLOR = "162 3 229 32"; // ThighMaster Accident
    String DEFAULT_REFERENCE_HIGHLIGHT_COLOR = "162 229 3 32"; // ThighMaster Accident
    String TITLE_PREFIX_CURRENT_FILE = "Current File";
}
