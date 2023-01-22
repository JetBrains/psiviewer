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

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class AlphaChooserPanel extends AbstractColorChooserPanel
{

    private CompoundSlider _alphaSlider;

    /**
     * Invoked automatically when the model's state changes.
     * It is also called by <code>installChooserPanel</code> to allow
     * you to set up the initial state of your chooser.
     * Override this method to update your <code>ChooserPanel</code>.
     */
    public void updateChooser()
    {
        getAlphaSlider().setValue(getColorFromModel().getAlpha());
    }

    /**
     * Builds a new chooser panel.
     */
    protected void buildChooser()
    {
        setAlphaSlider(new CompoundSlider("Alpha", getColorSelectionModel()));
        add(getAlphaSlider());
        getColorSelectionModel().addChangeListener(new ChangeListener()
        {
            /**
             * When the user chooses a color, make sure that the current
             * alpha slider value is applied to it - otherwise it defaults
             * to alpha=255 (opaque).
             */
            public void stateChanged(ChangeEvent e)
            {
                ColorSelectionModel model = (ColorSelectionModel) e.getSource();
                Color selectedColor = model.getSelectedColor();

                if (selectedColor.getAlpha() != getAlphaSlider().getValue())
                {
                    Color colorWithAlphaApplied = new Color(selectedColor.getRed(),
                                                            selectedColor.getGreen(),
                                                            selectedColor.getBlue(),
                                                            getAlphaSlider().getValue());
                    model.setSelectedColor(colorWithAlphaApplied);
                }
            }
        }
        );
    }

    /**
     * Returns a string containing the display name of the panel.
     * @return the name of the display panel
     */
    public String getDisplayName()
    {
        return "Alpha";
    }

    /**
     * Returns the small display icon for the panel.
     * @return the small display icon
     */
    public Icon getSmallDisplayIcon()
    {
        return null;
    }

    /**
     * Returns the large display icon for the panel.
     * @return the large display icon
     */
    public Icon getLargeDisplayIcon()
    {
        return null;
    }

    private CompoundSlider getAlphaSlider()
    {
        return _alphaSlider;
    }

    private void setAlphaSlider(CompoundSlider alphaSlider)
    {
        _alphaSlider = alphaSlider;
    }

    public void setAlpha(int alpha)
    {
        _alphaSlider.setValue(alpha);
    }

}
