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

package idea.plugin.psiviewer.controller.application;

import com.intellij.openapi.options.BaseConfigurable;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.view.configuration.ConfigurationPanel;

import javax.swing.*;
import java.awt.*;

public class Configuration extends BaseConfigurable implements PsiViewerConstants {
    private ConfigurationPanel myPanel;

    private final PsiViewerApplicationSettings mySettings;

    public Configuration() {
        mySettings = PsiViewerApplicationSettings.getInstance();
    }

    @Override
    public String getDisplayName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        myPanel = new ConfigurationPanel();
        return myPanel;
    }

    @Override
    public boolean isModified() {
        if (!Helpers.encodeColor(myPanel.getHighlightColor()).equals(mySettings.HIGHLIGHT_COLOR))
            return true;

        if (!Helpers.encodeColor(myPanel.getReferenceHighlightColor()).equals(mySettings.REFERENCE_HIGHLIGHT_COLOR))
            return true;

        return false;
    }

    /**
     * Save the settings from the configuration panel
     */
    @Override
    public void apply() {
        mySettings.HIGHLIGHT_COLOR = Helpers.encodeColor(myPanel.getHighlightColor());
        mySettings.REFERENCE_HIGHLIGHT_COLOR = Helpers.encodeColor(myPanel.getReferenceHighlightColor());
        mySettings.getTextAttributes().setBackgroundColor(getHighlightColor());
    }

    /**
     * Load current settings into the configuration panel
     */
    @Override
    public void reset() {
        myPanel.setHighlightColor(getHighlightColor());
        myPanel.setReferenceHighlightColor(getReferenceHighlightColor());
    }

    @Override
    public void disposeUIResources() {
        myPanel = null;
    }

    private Color getHighlightColor() {
        return Helpers.parseColor(mySettings.HIGHLIGHT_COLOR);
    }


    public Color getReferenceHighlightColor() {
        return Helpers.parseColor(mySettings.REFERENCE_HIGHLIGHT_COLOR);
    }
}
