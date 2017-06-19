package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.lorenzoleonardini.naojava.extensions.ExtensionInstance;
import org.lorenzoleonardini.naojava.ui.panels.HomePanel;
import org.lorenzoleonardini.naojava.ui.panels.LEDsPanel;
import org.lorenzoleonardini.naojava.ui.panels.PosturesPanel;
import org.lorenzoleonardini.naojava.ui.panels.SettingsPanel;

public class Menu extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private int elements = 0;
	
	protected Window window;
	
	private MenuElement home;
	private MenuElement postures;
	private MenuElement leds;
	private MenuElement settings;
	
	private PagePanel homePanel;
	private PagePanel posturesPanel;
	private PagePanel ledsPanel;
	private PagePanel settingsPanel;
	
	private List<MenuElement> extElmnts = new ArrayList<MenuElement>();
	private List<PagePanel> extPanels = new ArrayList<PagePanel>();
	
	public Menu(Window window)
	{
		this.window = window;
		setBounds(0, 50, 250, 610);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		homePanel =  new HomePanel(window);
		home = newMenuElement(window.lang.getString("home"), homePanel);
		
		posturesPanel = new PosturesPanel(window);
		postures = newMenuElement(window.lang.getString("postures"), posturesPanel);
		
		ledsPanel = new LEDsPanel(window);
		leds = newMenuElement(window.lang.getString("leds"), ledsPanel);
		
		for(ExtensionInstance e : window.extManager.getExtensions())
		{
			PagePanel panel = e.initPanel(window);
			extPanels.add(panel);
			extElmnts.add(newMenuElement(e.info.menuString(), panel));
		}
		
		settingsPanel = new SettingsPanel(window);
		settings = newMenuElement(window.lang.getString("settings"), settingsPanel);
	}
	
	private MenuElement newMenuElement(String text, PagePanel panel)
	{
		return new MenuElement(window, text, elements++, panel, this);
	}
	
	protected void select(MenuElement e)
	{
		window.select(e);
	}
	
	public void updateLanguage()
	{
		home.setText(window.lang.getString("home"));
		postures.setText(window.lang.getString("postures"));
		leds.setText(window.lang.getString("leds"));
		settings.setText(window.lang.getString("settings"));
		
		for(MenuElement el : extElmnts)
			el.updateLang(window.lang.toString());
		
		homePanel.superUpdateLanguage();
		posturesPanel.superUpdateLanguage();
		ledsPanel.superUpdateLanguage();
		settingsPanel.superUpdateLanguage();
		
		for(PagePanel panel : extPanels)
			panel.superUpdateLanguage();
	}
	
	public void updateTheme()
	{
		home.updateTheme();
		postures.updateTheme();
		leds.updateTheme();
		settings.updateTheme();
		
		for(MenuElement el : extElmnts)
			el.updateTheme();
		
		homePanel.superUpdateTheme();
		posturesPanel.superUpdateTheme();
		ledsPanel.superUpdateTheme();
		settingsPanel.superUpdateTheme();
		
		for(PagePanel panel : extPanels)
			panel.superUpdateTheme();
	}
	
	public void update(boolean connected)
	{
		homePanel.superUpdate(connected);
		posturesPanel.superUpdate(connected);
		ledsPanel.superUpdate(connected);
		settingsPanel.superUpdate(connected);
		
		for(PagePanel panel : extPanels)
			panel.superUpdate(connected);
	}
}
