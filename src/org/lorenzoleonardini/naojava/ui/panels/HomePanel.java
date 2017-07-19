package org.lorenzoleonardini.naojava.ui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.lorenzoleonardini.naojava.NAO;
import org.lorenzoleonardini.naojava.NAOConnectionException;
import org.lorenzoleonardini.naojava.ui.Const;
import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.SavedNAO;
import org.lorenzoleonardini.naojava.ui.Window;

public class HomePanel extends PagePanel
{
	private static final long serialVersionUID = 1L;

	private JLabel title;
	private JTextField IP = new JTextField();
	private JTextField PORT = new JTextField("9559");
	private JLabel ip;
	private JLabel port;
	private JLabel connect;
	private JLabel background = new JLabel();
	private JLabel error;
	private JLabel loading;

	private SavedNAO saved;

	private boolean connecting = false;

	public HomePanel(Window window)
	{
		super(window);

		title = new JLabel(window.lang.getString("connect") + " NAO");
		title.setForeground(window.theme.getColor("foregroundSelected"));
		title.setFont(Const.FONT_30);
		title.setBounds(210, 152, 330, 40);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title);

		IP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(3, 10, 3, 10)));
		IP.setBackground(window.theme.getColor("fieldBG"));
		IP.setForeground(Color.black);
		IP.setFont(Const.FONT_20);
		IP.setHorizontalAlignment(JLabel.CENTER);
		IP.setBounds(250, 267, 250, 30);
		IP.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				estabilishConnection();
			}
		});
		add(IP);

		PORT.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(3, 10, 3, 10)));
		PORT.setBackground(window.theme.getColor("fieldBG"));
		PORT.setForeground(Color.black);
		PORT.setFont(Const.FONT_20);
		PORT.setHorizontalAlignment(JLabel.CENTER);
		PORT.setBounds(250, 345, 250, 30);
		PORT.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				estabilishConnection();
			}
		});
		add(PORT);

		ip = new JLabel(window.lang.getString("ip"));
		ip.setForeground(window.theme.getColor("foregroundHover"));
		ip.setFont(Const.FONT_20);
		ip.setHorizontalAlignment(JLabel.CENTER);
		ip.setBounds(250, 232, 250, 30);
		add(ip);

		port = new JLabel(window.lang.getString("port"));
		port.setForeground(window.theme.getColor("foregroundHover"));
		port.setFont(Const.FONT_20);
		port.setBounds(250, 310, 250, 30);
		port.setHorizontalAlignment(JLabel.CENTER);
		add(port);

		connect = new JLabel(window.lang.getString("connect"));
		connect.setOpaque(true);
		connect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connect.setForeground(window.theme.getColor("foregroundHover"));
		connect.setFont(Const.FONT_BOLD_20);
		connect.setBackground(window.theme.getColor("pickerBG"));
		connect.setBorder(window.theme.getBorder("pickerBorder"));
		connect.setHorizontalAlignment(JLabel.CENTER);
		connect.setBounds(200, 406, 350, 40);
		connect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				estabilishConnection();
			}
		});
		add(connect);

		error = new JLabel();
		error.setForeground(Color.red);
		error.setFont(Const.FONT_17);
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setVerticalAlignment(JLabel.CENTER);
		error.setBounds(200, 446, 350, 30);
		error.setVisible(false);
		add(error);

		background.setBorder(window.theme.getBorder("pickerBorder"));
		background.setOpaque(true);
		background.setBackground(window.theme.getColor("pickerBG"));
		background.setBounds(200, 207, 350, 200);
		add(background);

		loading = new JLabel(window.theme.getIcon("loading"));
		loading.setBounds(0, 0, 750, 610);
		loading.setVerticalAlignment(JLabel.CENTER);
		loading.setHorizontalAlignment(JLabel.CENTER);
		loading.setVisible(false);
		add(loading);
	}

	private void estabilishConnection()
	{
		if (connecting)
			return;
		connecting = true;
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		loading(true);
		error.setVisible(false);
		new Thread(() -> {
			try
			{
				window.setNAO(new NAO(window, IP.getText() + ":" + PORT.getText()));
				if (window.nao == null)
				{
					throw new NAOConnectionException(window.lang.getString("cantConnect"));
				}
				window.robots.addRobot(window.nao);
				window.update(true);
			}
			catch (Exception e)
			{
				error.setText(e.getMessage());
				error.setVisible(true);
				window.update(false);
			}
			connecting = false;
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			loading(false);
		}).start();
	}

	@Override
	public void updateLanguage(String lang)
	{
		title.setText(window.lang.getString("connect") + " NAO");
		ip.setText(window.lang.getString("ip"));
		port.setText(window.lang.getString("port"));
		connect.setText(window.lang.getString("connect"));
	}

	@Override
	public void updateTheme()
	{
		title.setForeground(window.theme.getColor("foregroundSelected"));

		IP.setBackground(window.theme.getColor("fieldBG"));

		PORT.setBackground(window.theme.getColor("fieldBG"));

		ip.setForeground(window.theme.getColor("foregroundHover"));

		port.setForeground(window.theme.getColor("foregroundHover"));

		connect.setForeground(window.theme.getColor("foregroundHover"));
		connect.setBackground(window.theme.getColor("pickerBG"));
		connect.setBorder(window.theme.getBorder("pickerBorder"));

		background.setBorder(window.theme.getBorder("pickerBorder"));
		background.setBackground(window.theme.getColor("pickerBG"));

		loading.setIcon(window.theme.getIcon("loading"));
	}

	@Override
	public void update(boolean connected)
	{
		title.setVisible(!connected);
		IP.setVisible(!connected);
		PORT.setVisible(!connected);
		ip.setVisible(!connected);
		port.setVisible(!connected);
		connect.setVisible(!connected);
		background.setVisible(!connected);
		if (window.robots != null)
		{
			for (SavedNAO nao : window.robots.getNAOS())
			{
				if (nao.isConnected())
				{
					add(nao);
					saved = nao;
				}
			}
		}
		if (!connected && saved != null)
		{
			remove(saved);
			saved = null;
		}
	}

	public void loading(boolean loading)
	{
		title.setVisible(!loading && saved == null);
		IP.setVisible(!loading && saved == null);
		PORT.setVisible(!loading && saved == null);
		ip.setVisible(!loading && saved == null);
		port.setVisible(!loading && saved == null);
		connect.setVisible(!loading && saved == null);
		background.setVisible(!loading && saved == null);
		error.setVisible(!loading && saved == null);
		this.loading.setVisible(loading);
	}

	@Override
	public void select()
	{

	}
}
