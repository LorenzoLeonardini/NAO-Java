package org.lorenzoleonardini.naojava.ui;

import javax.swing.JPanel;

import org.lorenzoleonardini.naojava.extensions.ExtensionLanguage;

public abstract class PagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public ExtensionLanguage STRINGS;

	protected Window window;

	public PagePanel(Window window)
	{
		this.window = window;
	}

	protected void requiresRobot(boolean bool)
	{
	}
	
	public abstract void updateLanguage(String lang);

	public abstract void updateTheme();

	public abstract void update(boolean connected);
	
	public abstract void select();
}
