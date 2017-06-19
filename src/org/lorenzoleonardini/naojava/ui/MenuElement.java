package org.lorenzoleonardini.naojava.ui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

public class MenuElement
{
	private JLabel text;

	private Menu parentPanel;

	private boolean selected = false, hovered = false;

	protected PagePanel panel;

	private Window window;

	private Map<String, String> langs = new HashMap<String, String>();
	private String defaultLang;

	public MenuElement(Window window, String name, int id, PagePanel panel, Menu parentPanel)
	{
		System.out.println("Initializing " + name.toLowerCase() + " panel...");

		this.window = window;
		this.parentPanel = parentPanel;
		this.panel = panel;

		if (!name.contains(":"))
		{
			langs.put("en", name);
			text = new JLabel(name);
		}
		else
		{
			String[] lngs = name.split(";");
			for (String l : lngs)
			{
				String[] lng = l.split(":");
				langs.put(lng[0].toLowerCase(), lng[1]);
				if(defaultLang == null)
					defaultLang = lng[1];
			}
			String l = langs.get(window.lang.toString());
			if (l == null)
				l = langs.get("en");
			if (l == null)
				l = defaultLang;
			text = new JLabel(l);
		}

		text.setFont(Const.FONT_30);
		text.setBounds(0, id * 50, 250, 50);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		text.setForeground(window.theme.getColor("foreground"));
		text.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		text.setOpaque(true);
		text.setBackground(window.theme.getColor("bg"));
		parentPanel.add(text);

		if (id == 0)
			selectThis();

		text.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				if (!selected)
				{
					hover();
					hovered = true;
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				hovered = false;
				if (!selected)
					unselect();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				selectThis();
			}
		});
	}

	private void selectThis()
	{
		parentPanel.select(this);
		window.updateUI();
	}

	public void select()
	{
		text.setForeground(window.theme.getColor("foregroundSelected"));
		text.setFont(Const.FONT_BOLD_30);
		text.setBackground(window.theme.getColor("bgSelected"));
		selected = true;
		panel.select();
		window.updateUI();
	}

	public void unselect()
	{
		text.setForeground(window.theme.getColor("foreground"));
		text.setBackground(window.theme.getColor("bg"));
		text.setFont(Const.FONT_30);
		selected = false;
		window.updateUI();
	}

	public void hover()
	{
		text.setForeground(window.theme.getColor("foregroundHover"));
		text.setBackground(window.theme.getColor("bgHover"));
		window.updateUI();
	}

	public void setText(String text)
	{
		this.text.setText(text);
	}

	public void updateTheme()
	{
		if (selected)
		{
			text.setForeground(window.theme.getColor("foregroundSelected"));
			text.setBackground(window.theme.getColor("bgSelected"));
		}
		else if (hovered)
		{
			text.setForeground(window.theme.getColor("foreground"));
			text.setBackground(window.theme.getColor("bgHover"));
		}
		else
		{
			text.setForeground(window.theme.getColor("foreground"));
			text.setBackground(window.theme.getColor("bg"));
		}
	}

	public void updateLang(String ln)
	{
		String l = langs.get(window.lang.toString());
		if (l == null)
			l = langs.get("en");
		if (l == null)
			l = defaultLang;
		text.setText(l);
	}
}
