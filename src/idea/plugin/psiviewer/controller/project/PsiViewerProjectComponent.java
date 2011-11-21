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
package idea.plugin.psiviewer.controller.project;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import idea.plugin.psiviewer.PsiViewerConstants;
import idea.plugin.psiviewer.controller.actions.PropertyToggleAction;
import idea.plugin.psiviewer.controller.application.Configuration;
import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.view.PsiViewerPanel;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PsiViewerProjectComponent implements ProjectComponent, JDOMExternalizable, PsiViewerConstants
{

    private static final Logger LOG = Logger.getInstance("idea.plugin.psiviewer.controller.project.PsiViewerProjectComponent");
    public boolean HIGHLIGHT = false;
    public boolean FILTER_WHITESPACE = false;
    public boolean SHOW_PROPERTIES = true;
    public int SPLIT_DIVIDER_POSITION = 300;
    public boolean AUTOSCROLL_TO_SOURCE = false;
    public boolean AUTOSCROLL_FROM_SOURCE = false;

    private final Project _project;
    private EditorListener _editorListener;
    private PsiViewerPanel _viewerPanel;

    public PsiViewerProjectComponent(Project project)
    {
        _project = project;
    }

    public void projectOpened()
    {
        if (Configuration.getInstance().isPluginEnabled())
        {
            initToolWindow();
        }
    }

    public void projectClosed()
    {
        unregisterToolWindow();
    }

    public void initComponent()
    {
    }

    public void disposeComponent()
    {
    }

    @NotNull
    public String getComponentName()
    {
        return PLUGIN_NAME + '.' + PROJECT_COMPONENT_NAME;
    }

    public void initToolWindow()
    {
        _viewerPanel = new PsiViewerPanel(this);

        _viewerPanel.addPropertyChangeListener("ancestor", new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        handleCurrentState();
                    }
                });
        ActionManager actionManager = ActionManager.getInstance();

        DefaultActionGroup actionGroup = new DefaultActionGroup(ID_ACTION_GROUP, false);
        actionGroup.add(new PropertyToggleAction("Filter Whitespace",
                                                 "Remove whitespace elements",
                                                 Helpers.getIcon(ICON_FILTER_WHITESPACE),
                                                 this,
                                                 "filterWhitespace"));
        actionGroup.add(new PropertyToggleAction("Highlight",
                                                 "Highlight selected PSI element",
                                                 Helpers.getIcon(ICON_TOGGLE_HIGHLIGHT),
                                                 this,
                                                 "highlighted"));
        actionGroup.add(new PropertyToggleAction("Properties",
                                                 "Show PSI element properties",
                                                 Helpers.getIcon(ICON_SHOW_PROPERTIES),
                                                 this,
                                                 "showProperties"));
        actionGroup.add(new PropertyToggleAction("Autoscroll to Source",
                                                 "Autoscroll to Source",
                                                 Helpers.getIcon("/general/autoscrollToSource.png"),
                                                 this,
                                                 "autoScrollToSource"));
        actionGroup.add(new PropertyToggleAction("Autoscroll from Source",
                                                 "Autoscroll from Source",
                                                 Helpers.getIcon("/general/autoscrollFromSource.png"),
                                                 this,
                                                 "autoScrollFromSource"));

        ActionToolbar toolBar = actionManager.createActionToolbar(ID_ACTION_TOOLBAR, actionGroup, true);

        _viewerPanel.add(toolBar.getComponent(), BorderLayout.NORTH);

        ToolWindow toolWindow = getToolWindow();
        toolWindow.setIcon(Helpers.getIcon(ICON_TOOL_WINDOW));
        _viewerPanel.setToolWindow(toolWindow);

        _editorListener = new EditorListener(_viewerPanel, _project);
    }

    private void handleCurrentState() {
        if (_viewerPanel == null)
            return;
        
        if (_viewerPanel.isDisplayable())
        {
            _editorListener.start();
            _viewerPanel.selectElementAtCaret();
        }
        else
        {
            _editorListener.stop();
            _viewerPanel.removeHighlighting();
        }
    }

    public void unregisterToolWindow()
    {
        _viewerPanel.removeHighlighting();
        _viewerPanel = null;

        if (_editorListener != null)
            _editorListener.stop();

        if (isToolWindowRegistered())
            ToolWindowManager.getInstance(_project).unregisterToolWindow(ID_TOOL_WINDOW);
    }

    private ToolWindow getToolWindow()
    {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(_project);
        if (isToolWindowRegistered())
            return toolWindowManager.getToolWindow(ID_TOOL_WINDOW);
        else
            return toolWindowManager.registerToolWindow(ID_TOOL_WINDOW,
                                                        _viewerPanel,
                                                        ToolWindowAnchor.RIGHT);
    }

    private boolean isToolWindowRegistered()
    {
        return ToolWindowManager.getInstance(_project).getToolWindow(ID_TOOL_WINDOW) != null;
    }

    public void readExternal(Element element) throws InvalidDataException
    {
        DefaultJDOMExternalizer.readExternal(this, element);
    }

    public void writeExternal(Element element) throws WriteExternalException
    {
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    public PsiViewerPanel getViewerPanel()
    {
        return _viewerPanel;
    }

    public boolean isHighlighted()
    {
        return HIGHLIGHT;
    }

    public void setHighlighted(boolean highlight)
    {
        debug("set highlight to " + highlight);
        HIGHLIGHT = highlight;
        _viewerPanel.applyHighlighting();

    }

    public boolean isFilterWhitespace()
    {
        return FILTER_WHITESPACE;
    }

    public void setFilterWhitespace(boolean filterWhitespace)
    {
        FILTER_WHITESPACE = filterWhitespace;
        getViewerPanel().applyWhitespaceFilter();
    }

    public boolean isShowProperties()
    {
        return SHOW_PROPERTIES;
    }

    public void setShowProperties(boolean showProperties)
    {
        SHOW_PROPERTIES = showProperties;
        getViewerPanel().showProperties(showProperties);
    }

    public int getSplitDividerLocation()
    {
        return SPLIT_DIVIDER_POSITION;
    }

    public void setSplitDividerLocation(int location)
    {
        SPLIT_DIVIDER_POSITION = location;
    }

    public boolean isAutoScrollToSource()
    {
        return AUTOSCROLL_TO_SOURCE;
    }

    public void setAutoScrollToSource(boolean isAutoScrollToSource)
    {
        debug("autoscrolltosource=" + isAutoScrollToSource);
        AUTOSCROLL_TO_SOURCE = isAutoScrollToSource;
    }

    public boolean isAutoScrollFromSource()
    {
        return AUTOSCROLL_FROM_SOURCE;
    }

    public void setAutoScrollFromSource(boolean isAutoScrollFromSource)
    {
        debug("autoscrollfromsource=" + isAutoScrollFromSource);
        AUTOSCROLL_FROM_SOURCE = isAutoScrollFromSource;
    }

    public Project getProject()
    {
        return _project;
    }

    public static PsiViewerProjectComponent getInstance(Project project)
    {
        return project.getComponent(PsiViewerProjectComponent.class);
    }

    private static void debug(String message)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug(message);
        }
    }

}
