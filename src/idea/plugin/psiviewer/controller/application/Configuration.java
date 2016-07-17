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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.controller.project.PsiViewerProjectComponent;
import idea.plugin.psiviewer.view.configuration.ConfigurationPanel;
import org.jdom.Element;

import javax.swing.*;
import java.awt.*;

public class Configuration implements ApplicationComponent, JDOMExternalizable, PsiViewerConstants, Configurable
{
    public String HIGHLIGHT_COLOR = DEFAULT_HIGHLIGHT_COLOR;
    public boolean PLUGIN_ENABLED = true;

    private final TextAttributes _textAttributes = new TextAttributes();

    private ConfigurationPanel _panel;

    public Configuration()
    {
    }

    public void initComponent()
    {
        getTextAttributes().setBackgroundColor(getHighlightColor());
    }

    public void disposeComponent()
    {
    }

    public String getComponentName()
    {
        return PLUGIN_NAME + "." + CONFIGURATION_COMPONENT_NAME;
    }

    public void readExternal(Element element) throws InvalidDataException
    {
        DefaultJDOMExternalizer.readExternal(this, element);
    }

    public void writeExternal(Element element) throws WriteExternalException
    {
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    public String getDisplayName()
    {
        return PLUGIN_NAME;
    }

    public Icon getIcon()
    {
        return Helpers.getIcon(ICON_CONFIGURATION);
    }

    public String getHelpTopic()
    {
        return null;
    }

    public JComponent createComponent()
    {
        _panel = new ConfigurationPanel();
        return _panel;
    }

    public boolean isModified()
    {
        if (_panel.isPluginEnabled() ^ isPluginEnabled())
            return true;

        if (!Helpers.encodeColor(_panel.getHighlightColor()).equals(HIGHLIGHT_COLOR))
            return true;

        return false;
    }

    /**
     * Save the settings from the configuration panel
     */
    public void apply() throws ConfigurationException
    {
        if (PLUGIN_ENABLED ^ _panel.isPluginEnabled())  // If plugin-enabled state has changed...
            enableToolWindows(_panel.isPluginEnabled());

        PLUGIN_ENABLED = _panel.isPluginEnabled();
        HIGHLIGHT_COLOR = Helpers.encodeColor(_panel.getHighlightColor());
        getTextAttributes().setBackgroundColor(getHighlightColor());
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
    public void reset()
    {
        _panel.setPluginEnabled(isPluginEnabled());
        _panel.setHighlightColor(getHighlightColor());
    }

    public void disposeUIResources()
    {
        _panel = null;
    }

    public boolean isPluginEnabled()
    {
        return PLUGIN_ENABLED;
    }

    private Color getHighlightColor()
    {
        return Helpers.parseColor(HIGHLIGHT_COLOR);
    }

    public TextAttributes getTextAttributes()
    {
        return _textAttributes;
    }

    public static Configuration getInstance()
    {
        return ApplicationManager.getApplication().getComponent(Configuration.class);
    }

}
