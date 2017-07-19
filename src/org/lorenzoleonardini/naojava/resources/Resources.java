package org.lorenzoleonardini.naojava.resources;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resources
{
	// TODO: make a real icon (and not a stolen one)
	public static Image icon()
	{
		try
		{
			return ImageIO.read(Resources.class.getResource("images/icon.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	public static ImageIcon getIcon(String id)
	{
		if (!icons.containsKey(id))
		{
			loadIcon(id);
			return getIcon(id);
		}
		return icons.get(id);
	}

	private static void loadIcon(String id)
	{
		try
		{
			icons.put(id, new ImageIcon(Resources.class.getResource("images/" + id)));
		}
		catch (Exception e)
		{
			System.err.println("Failed to load " + id + "!");
			icons.put(id, null);
		}
	}
}
