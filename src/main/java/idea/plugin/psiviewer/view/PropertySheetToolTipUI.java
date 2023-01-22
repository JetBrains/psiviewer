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

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;
import java.awt.*;

class PropertySheetToolTipUI extends BasicToolTipUI
{
    private static final PropertySheetToolTipUI INSTANCE = new PropertySheetToolTipUI();

    private static final int MAX_WIDTH_IN_PIXELS = 600;

    public static ComponentUI getInstance()
    {
        return INSTANCE;
    }

    public void paint(Graphics g, JComponent c)
    {
        JToolTip toolTip = (JToolTip) c;

        String tipText = toolTip.getTipText();
        if (tipText == null)
            return;

        Graphics2D g2 = (Graphics2D) g;

        JComponent subject = toolTip.getComponent();
        g2.setPaint(subject.getBackground());
        g2.fill(g2.getClip());

        g2.setPaint(subject.getForeground());
        g2.drawString(tipText, 1, g2.getFontMetrics().getAscent());
    }

    public Dimension getPreferredSize(JComponent toolTip)
    {
        Dimension dim = super.getPreferredSize(toolTip);

        if (dim.width > MAX_WIDTH_IN_PIXELS)
            dim.width = MAX_WIDTH_IN_PIXELS;

        return dim;
    }

}

