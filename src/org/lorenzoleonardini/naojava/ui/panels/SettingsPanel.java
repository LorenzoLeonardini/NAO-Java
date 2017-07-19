package org.lorenzoleonardini.naojava.ui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.lorenzoleonardini.naojava.ui.ComboBoxArrow;
import org.lorenzoleonardini.naojava.ui.ComboBoxEditor;
import org.lorenzoleonardini.naojava.ui.ComboBoxRenderer;
import org.lorenzoleonardini.naojava.ui.Const;
import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.Switch;
import org.lorenzoleonardini.naojava.ui.Switch.STATE;
import org.lorenzoleonardini.naojava.ui.SwitchEvent;
import org.lorenzoleonardini.naojava.ui.Window;

public class SettingsPanel extends PagePanel
{
	private static final long serialVersionUID = 1L;

	private JLabel language;
	private JComboBox<String> languages;
	private ComboBoxRenderer<String> comboRenderer;
	private ComboBoxEditor comboEditor;
	private JLabel theme;
	private Switch themes;
	private JLabel about;
	private JLabel developed;
	private JLabel me;
	private JLabel source;
	private JLabel link;
	private JLabel version;

	public SettingsPanel(Window window)
	{
		super(window);
		language = new JLabel(window.lang.getString("language"));
		language.setBounds(0, 69, 750, 40);
		language.setForeground(window.theme.getColor("foregroundSelected"));
		language.setFont(Const.FONT_30);
		language.setHorizontalAlignment(JLabel.CENTER);
		language.setVerticalAlignment(JLabel.CENTER);
		add(language);

		languages = new JComboBox<String>();
		for(String lang : window.langs.getLanguages())
			languages.addItem(lang);
		languages.setSelectedItem(window.langs.name);
		languages.setBounds(225, 119, 300, 40);
		languages.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		languages.setBackground(Color.WHITE);
		languages.setEditable(true);
		comboRenderer = new ComboBoxRenderer<String>(window);
		languages.setRenderer(comboRenderer);
		comboEditor = new ComboBoxEditor(window);
		languages.setEditor(comboEditor);
		languages.setUI(ComboBoxArrow.createUI(languages));
		languages.remove(languages.getComponent(0));
		languages.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
					window.changeLanguage((String) languages.getSelectedItem());
			}
		});
		add(languages);

		// ----------------

		theme = new JLabel(window.lang.getString("theme"));
		theme.setBounds(0, 204, 750, 40);
		theme.setForeground(window.theme.getColor("foregroundSelected"));
		theme.setFont(Const.FONT_30);
		theme.setHorizontalAlignment(JLabel.CENTER);
		theme.setVerticalAlignment(JLabel.CENTER);
		add(theme);

		themes = new Switch(window, window.lang.getString("darkTheme"), window.lang.getString("whiteTheme"), 254, this);
		if(window.theme.name.equalsIgnoreCase("white"))
			themes.change();
		themes.add(this);
		themes.addEvent(new SwitchEvent()
		{
			@Override
			public void switchChanged(STATE state)
			{
				if(state == STATE.LEFT)
					window.theme.set("dark");
				else
					window.theme.set("white");
			}
		});

		about = new JLabel(window.lang.getString("about"));
		about.setBounds(0, 334, 750, 40);
		about.setForeground(window.theme.getColor("foregroundSelected"));
		about.setFont(Const.FONT_30);
		about.setHorizontalAlignment(JLabel.CENTER);
		about.setVerticalAlignment(JLabel.CENTER);
		add(about);

		me = new JLabel("<html><u>LORENZO LEONARDINI</u></html>");
		me.setForeground(window.theme.getColor("foregroundText"));
		me.setFont(Const.FONT_17);
		me.setHorizontalAlignment(JLabel.CENTER);
		me.setVerticalAlignment(JLabel.CENTER);
		me.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		me.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("http://" + window.lang + ".lorenzoleonardini.altervista.org"));
				}
				catch (IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		add(me);

		developed = new JLabel("<html>" + window.lang.getString("developed") + "</html>");
		developed.setForeground(window.theme.getColor("foregroundText"));
		developed.setFont(Const.FONT_17);
		developed.setHorizontalAlignment(JLabel.CENTER);
		developed.setVerticalAlignment(JLabel.CENTER);
		add(developed);
		
		int w = (int) developed.getPreferredSize().getWidth() + 5 + 187;
		developed.setBounds((750 - w) / 2, 379, (int) developed.getPreferredSize().getWidth(), 30);
		me.setBounds((750 - w) / 2 + (int) developed.getPreferredSize().getWidth(), 379, 198, 30);

		source = new JLabel("<html>" + window.lang.getString("source") + "</html>");
		source.setBounds(0, 414, 750, 30);
		source.setForeground(window.theme.getColor("foregroundText"));
		source.setFont(Const.FONT_17);
		source.setHorizontalAlignment(JLabel.CENTER);
		source.setVerticalAlignment(JLabel.CENTER);
		add(source);

		link = new JLabel("<html><u>https://github.com/LorenzoLeonardini/NAO-Java</u></html>");
		link.setBounds(0, 439, 750, 30);
		link.setForeground(window.theme.getColor("foregroundText"));
		link.setFont(Const.FONT_17);
		link.setHorizontalAlignment(JLabel.CENTER);
		link.setVerticalAlignment(JLabel.CENTER);
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://github.com/LorenzoLeonardini/NAO-Java"));
				}
				catch (IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		add(link);

		version = new JLabel("V 0.1 - Interface only");
		version.setBounds(0, 509, 750, 30);
		version.setForeground(window.theme.getColor("foregroundText"));
		version.setFont(Const.FONT_20);
		version.setHorizontalAlignment(JLabel.CENTER);
		version.setVerticalAlignment(JLabel.CENTER);
		add(version);
	}

	@Override
	public void updateLanguage(String lang)
	{
		language.setText(window.lang.getString("language"));
		theme.setText(window.lang.getString("theme"));
		themes.setLabels(window.lang.getString("darkTheme"), window.lang.getString("whiteTheme"));
		about.setText(window.lang.getString("about"));
		developed.setText("<html>" + window.lang.getString("developed") + "</html>");
		source.setText("<html>" + window.lang.getString("source") + "</html>");
		
		int w = (int) developed.getPreferredSize().getWidth() + 5 + 187;
		developed.setBounds((750 - w) / 2, 379, (int) developed.getPreferredSize().getWidth(), 30);
		me.setBounds((750 - w) / 2 + (int) developed.getPreferredSize().getWidth(), 379, 198, 30);
	}

	@Override
	public void updateTheme()
	{
		language.setForeground(window.theme.getColor("foregroundSelected"));
		comboRenderer.updateTheme();
		comboEditor.updateTheme();
		theme.setForeground(window.theme.getColor("foregroundSelected"));
		themes.updateTheme();
		about.setForeground(window.theme.getColor("foregroundSelected"));
		me.setForeground(window.theme.getColor("foregroundText"));
		developed.setForeground(window.theme.getColor("foregroundText"));
		source.setForeground(window.theme.getColor("foregroundText"));
		link.setForeground(window.theme.getColor("foregroundText"));
		version.setForeground(window.theme.getColor("foregroundText"));
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
