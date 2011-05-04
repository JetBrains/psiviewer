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

package idea.plugin.psiviewer.view.configuration;

import idea.plugin.psiviewer.util.Helpers;
import idea.plugin.psiviewer.PsiViewerConstants;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel
{
    private JCheckBox _pluginEnabled;
    private JColorChooser _colorChooser;
    private AlphaChooserPanel _alphaChooser;

    public ConfigurationPanel()
    {
        buildGUI();
    }

    private void buildGUI()
    {
        _alphaChooser = new AlphaChooserPanel();
        _colorChooser = new JColorChooser();
        _colorChooser.addChooserPanel(_alphaChooser);

        _pluginEnabled = new JCheckBox("Enable PsiViewer Plugin");
        _pluginEnabled.setToolTipText("Enable/disable the PsiViewer tool window");

        setLayout(new BorderLayout());

        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
        topPane.setBorder(BorderFactory.createEtchedBorder());
        topPane.add(_pluginEnabled);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setPreferredSize(new Dimension(400, 600));
        tabbedPane.insertTab("Highlighter",
                             Helpers.getIcon(PsiViewerConstants.ICON_TOGGLE_HIGHLIGHT),
                             _colorChooser,
                             "Set highlighter color",
                             0);

        add(topPane, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public boolean isPluginEnabled()
    {
        return _pluginEnabled.isSelected();
    }

    public void setPluginEnabled(boolean enabled)
    {
        _pluginEnabled.setSelected(enabled);
    }

    public Color getHighlightColor()
    {
        return _colorChooser.getSelectionModel().getSelectedColor();
    }

    public void setHighlightColor(Color color)
    {
        _alphaChooser.setAlpha(color.getAlpha());
        _colorChooser.setColor(color);
    }

}
