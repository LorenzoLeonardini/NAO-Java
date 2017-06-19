package org.lorenzoleonardini.naojava.ui.panels;

import javax.swing.JLabel;

import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.Window;

public class LoadingPanel extends PagePanel
{
	private static final long serialVersionUID = 1L;

	JLabel label;
	
	public LoadingPanel(Window window)
	{
		super(window);
		label = new JLabel(window.theme.getIcon("loading"));
		label.setBounds(0, 0, 750, 550);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		add(label);
	}

	@Override
	public void updateLanguage(String lang)
	{
		
	}

	@Override
	public void updateTheme()
	{
		label.setIcon(window.theme.getIcon("loading"));
	}

	@Override
	public void update(boolean connected)
	{
	}

	@Override
	public void select()
	{
		
	}
}
