package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lorenzoleonardini.naojava.NAO;
import org.lorenzoleonardini.naojava.extensions.ExtensionsManager;
import org.lorenzoleonardini.naojava.resources.Language;
import org.lorenzoleonardini.naojava.resources.Languages;
import org.lorenzoleonardini.naojava.resources.Resources;
import org.lorenzoleonardini.naojava.resources.Robots;
import org.lorenzoleonardini.naojava.resources.Settings;
import org.lorenzoleonardini.naojava.ui.panels.LoadingPanel;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;

	protected JPanel panel = new JPanel();

	private MenuElement selected;
	private PagePanel pagePanel;

	private Bar bar;
	private Menu menu;

	private Settings settings;
	public Languages langs;
	public Language lang;
	public Themes theme;
	public ExtensionsManager extManager;

	public NAO nao;
	public Robots robots;
	
	public Window()
	{
		setTitle("NAO-Java");
		setSize(1000, 660);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Resources.icon());

		setUndecorated(true);

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e)
		{
			e.printStackTrace();
		}

		panel.setLayout(null);
		setContentPane(panel);

		setBackground(new Color(42, 46, 68, 200));

		Const.init(this);
		settings = new Settings();
		langs = new Languages(settings);
		lang = langs.selected;
		theme = new Themes(this, settings);
		extManager = new ExtensionsManager();

		pagePanel = new LoadingPanel(this);

		menu = new Menu(this);
		panel.add(menu);

		bar = new Bar(this);
		panel.add(bar);

		panel.add(pagePanel);

		System.out.println("\n");
		System.out.println("-----------------------------------");
		System.out.println("               READY");
		System.out.println("-----------------------------------");
		System.out.println("\n");

		update(false);
		
		menu.homePanelLoading(true);
		robots = new Robots(this);
		new Thread(() -> {
			if (robots.robotCount() > 0)
				setNAO(robots.connect());
			menu.homePanelLoading(false);
		}).start();
		
		

		setVisible(true);
	}

	protected void select(MenuElement e)
	{
		if (selected == e)
			return;
		if (selected != null)
			selected.unselect();
		selected = e;
		selected.select();
		panel.remove(pagePanel);
		pagePanel = selected.panel;
		panel.add(pagePanel);
	}

	protected void updateUI()
	{
		if (bar != null)
			bar.updateUI();
		if (menu != null)
			menu.updateUI();
		panel.updateUI();
	}

	public void update(boolean connected)
	{
		if (bar != null)
			bar.update(connected);
		if (menu != null)
			menu.update(connected);
		updateUI();
	}

	public void changeLanguage(String language)
	{
		settings.set("lang", language);
		langs.select(language);
		lang = langs.selected;
		bar.updateLanguage();
		menu.updateLanguage();
		if (robots != null)
			robots.updateLanguage();
		updateUI();
	}

	public void updateTheme()
	{
		if (bar != null)
			bar.updateTheme();
		if (menu != null)
			menu.updateTheme();
		if (robots != null)
			robots.updateTheme();
		updateUI();
	}
	
	public void setNAO(NAO nao)
	{
		this.nao = nao;
		selected.select();
	}
}
