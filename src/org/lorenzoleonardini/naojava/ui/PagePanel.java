package org.lorenzoleonardini.naojava.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lorenzoleonardini.naojava.extensions.ExtensionLanguage;

public abstract class PagePanel extends JPanel
{
	public ExtensionLanguage STRINGS = new ExtensionLanguage();
	
	private static final long serialVersionUID = 1L;

	protected Window window;

	private boolean requiresRobot = false;

	private boolean connected = false;

	private JLabel robotRequired;

	public PagePanel(Window window)
	{
		this.window = window;
		setBounds(250, 50, 750, 610);
		setBackground(window.theme.getColor("panelBG"));
		setLayout(null);

		robotRequired = new JLabel("<html>" + window.lang.getString("connectionRequired") + "<br /><br /><br /></html>");
		robotRequired.setBounds(0, 1, 750, 608);
		robotRequired.setBackground(window.theme.getColor("disabledBG"));
		robotRequired.setForeground(window.theme.getColor("foregroundSelected"));
		robotRequired.setFont(Const.FONT_BOLD_30);
		robotRequired.setHorizontalAlignment(JLabel.CENTER);
		robotRequired.setVerticalAlignment(JLabel.CENTER);
		robotRequired.setOpaque(true);
		add(robotRequired);
		robotRequired.setVisible(false);
	}

	protected void requiresRobot(boolean bool)
	{
		requiresRobot = bool;
		if (requiresRobot && !connected)
			robotRequired.setVisible(true);
	}
	
	public void superUpdateLanguage()
	{
		robotRequired.setText("<html>" + window.lang.getString("connectionRequired") + "<br /><br /><br /></html>");
		updateLanguage(window.lang.toString());
	}

	public abstract void updateLanguage(String lang);

	public void superUpdateTheme()
	{
		setBackground(window.theme.getColor("panelBG"));
		robotRequired.setBackground(window.theme.getColor("disabledBG"));
		robotRequired.setForeground(window.theme.getColor("foregroundSelected"));
		updateTheme();
	}

	public abstract void updateTheme();

	public void superUpdate(boolean connected)
	{
		this.connected = connected;

		if (requiresRobot)
		{
			if (connected)
				robotRequired.setVisible(false);
			else
				robotRequired.setVisible(true);
		}
		update(connected);
	}

	public abstract void update(boolean connected);
	
	public abstract void select();
}
