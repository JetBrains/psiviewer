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
package idea.plugin.psiviewer.util;

import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public final class Helpers
{
    private static final Logger LOG = Logger.getInstance(Helpers.class);
    private static final Icon DEFAULT_ICON = getDefaultIcon();

    /**
     * Gets an icon either via the class loader, or from a url (maybe a file). <p> To keep the peace, it will always
     * return <i>some</i> sort of icon even if it has to build one on-the-fly.
     *
     * @param path
     * @return An Icon almost guaranteed to be usable.
     */
    public static Icon getIcon(String path)
    {
        URL url = Helpers.class.getResource(path);      // pull icon from jar first
        if (url == null)
        {
            try
            {
                url = new URL(path);    // now try from a url
            }
            catch (MalformedURLException e)
            {
                LOG.debug("Could not find icon " + path);
                return DEFAULT_ICON;
            }
        }

        Icon icon = new ImageIcon(url);
        if (icon.getIconWidth() < 0 || icon.getIconHeight() < 0)
        {
            LOG.debug("Bad icon data " + path);
            return DEFAULT_ICON;
        }

        return icon;
    }

    private static Icon getDefaultIcon()
    {
        BufferedImage bi = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2 = bi.createGraphics();
        g2.setBackground(Color.red);
        g2.clearRect(0, 0, bi.getWidth(), bi.getHeight());
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        GeneralPath x = new GeneralPath();
        x.moveTo(0, 0);
        x.lineTo(bi.getWidth() - 1, bi.getHeight() - 1);
        x.moveTo(0, bi.getHeight() - 1);
        x.lineTo(bi.getWidth() - 1, 0);
        g2.draw(x);
        return new ImageIcon(bi);
    }

    public static Color parseColor(String rgba)
    {
        int red = 0, green = 0, blue = 0, alpha = 128;
        String token[] = rgba.split(" ");
        if (token.length > 0) red = getSample(token[0]);
        if (token.length > 1) green = getSample(token[1]);
        if (token.length > 2) blue = getSample(token[2]);
        if (token.length > 3) alpha = getSample(token[3]);
        return new Color(red, green, blue, alpha);
    }

    private static int getSample(String sample)
    {
        int s;
        try
        {
            s = Math.min(Math.abs(Integer.valueOf(sample).intValue()), 255);
        }
        catch (NumberFormatException e)
        {
            s = 0;
        }
        return s;
    }

    public static String encodeColor(Color color)
    {
        return color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + color.getAlpha();
    }

}
