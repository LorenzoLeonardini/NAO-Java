package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bar extends JPanel
{
	private static final long serialVersionUID = 1L;

	private int xMouse, yMouse;
	
	private Window window;
	
	private JLabel exit;
	private JLabel minimize;
	private JLabel title;
	
	public Bar(Window window)
	{
		this.window = window;
		
		setBounds(0, 0, 1000, 50);
		setLayout(null);
		setBackground(window.theme.getColor("barBG"));

		exit = new JLabel(window.theme.getIcon("exitIcon"));
		exit.setBackground(Color.red);
		exit.setBounds(950, 0, 50, 50);
		exit.setVerticalAlignment(JLabel.CENTER);
		exit.setHorizontalAlignment(JLabel.CENTER);
		exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				exit.setOpaque(true);
				exit.setIcon(window.theme.getIcon("exitHoverIcon"));
				window.updateUI();
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				exit.setOpaque(false);
				exit.setIcon(window.theme.getIcon("exitIcon"));
				window.updateUI();
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				window.dispose();
			}
		});
		add(exit);
		
		minimize = new JLabel(window.theme.getIcon("minimizeIcon"));
		minimize.setBackground(window.theme.getColor("minimizeBG"));
		minimize.setBounds(900, 0, 50, 50);
		minimize.setVerticalAlignment(JLabel.CENTER);
		minimize.setHorizontalAlignment(JLabel.CENTER);
		minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				minimize.setOpaque(true);
				window.updateUI();
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				minimize.setOpaque(false);
				window.updateUI();
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				window.setState(JFrame.ICONIFIED);
				window.updateUI();
			}
		});
		add(minimize);
		
		JLabel drag = new JLabel();
		drag.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				xMouse = e.getX();
				yMouse = e.getY();
				drag.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				drag.setCursor(Cursor.getDefaultCursor());
			}
		});
		drag.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				int x = e.getXOnScreen() - xMouse;
				int y = e.getYOnScreen() - yMouse;
				window.setLocation(x, y);
			}
		});
		drag.setBounds(0, 0, 900, 50);
		add(drag);
		
		title = new JLabel("NAO-Java");
		title.setBounds(20, 0, 900, 48);
		title.setFont(Const.FONT_BOLD_30);
		title.setForeground(window.theme.getColor("barTitle"));
		title.setVerticalAlignment(JLabel.CENTER);
		add(title);
	}
	
	public void updateLanguage()
	{
		
	}
	
	public void updateTheme()
	{
		setBackground(window.theme.getColor("barBG"));
		exit.setIcon(window.theme.getIcon("exitIcon"));
		minimize.setBackground(window.theme.getColor("minimizeBG"));
		minimize.setIcon(window.theme.getIcon("minimizeIcon"));
		title.setForeground(window.theme.getColor("barTitle"));
	}
	
	public void update(boolean connected)
	{
		
	}
}
