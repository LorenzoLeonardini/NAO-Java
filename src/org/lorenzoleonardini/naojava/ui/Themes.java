package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.Border;

import org.lorenzoleonardini.naojava.resources.Settings;
import org.lorenzoleonardini.naojava.resources.Theme;

public class Themes
{
	public String name;

	private Window window;
	private Settings settings;

	private Theme dark;
	private Theme white;
	
	public Themes(Window window, Settings settings)
	{
		this.window = window;
		this.settings = settings;
		name = settings.get("theme");
		dark = new Theme("dark");
		white = new Theme("white");
		load();
	}

	public void set(String string)
	{
		settings.set("theme", string);
		name = settings.get("theme");
		load();
	}

	private void load()
	{
		System.out.println("Changing to " + name + " theme...");

		window.getRootPane().setBorder(getBorder("windowBorder"));
		window.setBackground(getColor("windowBG"));

		window.updateTheme();
	}
	
	public Color getColor(String id)
	{
		return (name.equals("dark") ? dark : white).getColor(id);
	}
	
	public Border getBorder(String id)
	{
		return (name.equals("dark") ? dark : white).getBorder(id);
	}
	
	public ImageIcon getIcon(String id)
	{
		return (name.equals("dark") ? dark : white).getIcon(id);
	}
}
