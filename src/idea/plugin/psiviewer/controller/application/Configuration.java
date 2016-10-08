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
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectComponent;
import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.view.configuration.ConfigurationPanel;

import javax.swing.*;
import java.awt.*;

public class Configuration extends BaseConfigurable implements PsiViewerConstants
{
    private ConfigurationPanel _panel;

    private final PsiViewerApplicationSettings settings;

    public Configuration()
    {
        settings = PsiViewerApplicationSettings.getInstance();
    }

    @Override
    public String getDisplayName()
    {
        return PLUGIN_NAME;
    }

    @Override
    public String getHelpTopic()
    {
        return null;
    }

    @Override
    public JComponent createComponent()
    {
        _panel = new ConfigurationPanel();
        return _panel;
    }

    @Override
    public boolean isModified()
    {
        if (_panel.isPluginEnabled() ^ isPluginEnabled())
            return true;

        if (!Helpers.encodeColor(_panel.getHighlightColor()).equals(settings.HIGHLIGHT_COLOR))
            return true;

        return false;
    }

    /**
     * Save the settings from the configuration panel
     */
    @Override
    public void apply() throws ConfigurationException
    {
        if (settings.PLUGIN_ENABLED ^ _panel.isPluginEnabled())  // If plugin-enabled state has changed...
            enableToolWindows(_panel.isPluginEnabled());

        settings.PLUGIN_ENABLED = _panel.isPluginEnabled();
        settings.HIGHLIGHT_COLOR = Helpers.encodeColor(_panel.getHighlightColor());
        settings.getTextAttributes().setBackgroundColor(getHighlightColor());
    }

    private static void enableToolWindows(boolean enableToolWindows)
    {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects)
        {
            PsiViewerProjectComponent pc = PsiViewerProjectComponent.getInstance(project);
            if (enableToolWindows) pc.initToolWindow();
            else
                pc.unregisterToolWindow();
        }
    }

    /**
     * Load current settings into the configuration panel
     */
    @Override
    public void reset()
    {
        _panel.setPluginEnabled(isPluginEnabled());
        _panel.setHighlightColor(getHighlightColor());
    }

    @Override
    public void disposeUIResources()
    {
        _panel = null;
    }

    public boolean isPluginEnabled()
    {
        return settings.PLUGIN_ENABLED;
    }

    private Color getHighlightColor()
    {
        return Helpers.parseColor(settings.HIGHLIGHT_COLOR);
    }


}
