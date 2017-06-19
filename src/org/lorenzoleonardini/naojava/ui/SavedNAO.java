package org.lorenzoleonardini.naojava.ui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lorenzoleonardini.naojava.NAO;
import org.lorenzoleonardini.naojava.NAOConnectionException;

public class SavedNAO extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Window window;
	private String name;
	private String ip;
	
	public NAO nao;
	
	private JLabel nameLabel;
	private JLabel naoImage;
	private JLabel naoIp;
	private JLabel disconnect;
	
	private boolean connected = false;
	
	private SavedNAO _this = this;
	
	public SavedNAO(Window window, String name, String ip)
	{
		this.window = window;
		this.name = name;
		this.ip = ip;
		init();
	}
	
	public SavedNAO(Window window, NAO nao) throws NAOConnectionException
	{
		if(nao == null)
			throw new NAOConnectionException(window.lang.getString("notConnected"));
		this.window = window;
		this.name = nao.getName();
		this.ip = nao.getIP();
		init();
		connected = true;
	}
	
	private void init()
	{	
		setBounds(0, 0, 750, 610);
		setLayout(null);
		setBackground(null);
		
		nameLabel = new JLabel(name.toUpperCase());
		nameLabel.setBounds(0, 90, 750, 50);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setFont(Const.FONT_30);
		nameLabel.setForeground(window.theme.getColor("foregroundSelected"));
		add(nameLabel);
		
		naoImage = new JLabel(window.theme.getIcon("naoBig"));
		naoImage.setBounds(0, 140, 750, 265);
		add(naoImage);
		
		naoIp = new JLabel("Connected at: " + ip);
		naoIp.setBounds(0, 415, 750, 40);
		naoIp.setForeground(window.theme.getColor("foregroundText"));
		naoIp.setFont(Const.FONT_20);
		naoIp.setVerticalAlignment(JLabel.CENTER);
		naoIp.setHorizontalAlignment(JLabel.CENTER);
		add(naoIp);
		
		disconnect = new JLabel("DISCONNECT");
		disconnect.setOpaque(true);
		disconnect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		disconnect.setForeground(window.theme.getColor("foregroundHover"));
		disconnect.setFont(Const.FONT_BOLD_20);
		disconnect.setBackground(window.theme.getColor("pickerBG"));
		disconnect.setBorder(window.theme.getBorder("pickerBorder"));
		disconnect.setHorizontalAlignment(JLabel.CENTER);
		disconnect.setBounds(200, 460, 350, 40);
		disconnect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				nao = null;
				connected = false;
				window.robots.removeRobot(_this);
				window.nao = null;
				window.update(false);
			}
		});
		add(disconnect);
	}
	
	public NAO connect() throws NAOConnectionException
	{
		nao = new NAO(window, ip);
		if(nao == null)
			return null;
		connected = true;
		nameLabel.setText(nao.getName().toUpperCase());
		window.update(true);
		return nao;
	}
	
	public String getIP()
	{
		return ip;
	}
	
	public void updateTheme()
	{
		nameLabel.setForeground(window.theme.getColor("foregroundSelected"));
	}
	
	public boolean isConnected()
	{
		return connected;
	}
}
