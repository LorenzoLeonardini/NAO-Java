package org.lorenzoleonardini.naojava.ui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lorenzoleonardini.naojava.resources.Resources;

public class UpdateWindow extends JDialog
{
	private static final long serialVersionUID = 1L;

	protected JPanel panel = new JPanel();
	private int xMouse, yMouse;
	
	public UpdateWindow(Window window, String name, String date)
	{
		setTitle("New Version");
		setSize(350, 200);
		setLocationRelativeTo(null);
		setIconImage(Resources.icon());

		setUndecorated(true);
		setModal (true);
		setModalityType (ModalityType.APPLICATION_MODAL);
		
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

		panel.setBackground(window.theme.getColor("panelBG"));
		
		JLabel string = new JLabel("NEW VERSION AVAILABLE!");
		string.setForeground(window.theme.getColor("foregroundHover"));
		string.setFont(Const.FONT_BOLD_20);
		string.setVerticalAlignment(JLabel.CENTER);
		string.setHorizontalAlignment(JLabel.CENTER);
		string.setBounds(0, 10, 350, 30);
		panel.add(string);
		
		JLabel nameL = new JLabel(name);
		nameL.setForeground(window.theme.getColor("foregroundHover"));
		nameL.setFont(Const.FONT_BOLD_17);
		nameL.setVerticalAlignment(JLabel.CENTER);
		nameL.setHorizontalAlignment(JLabel.CENTER);
		nameL.setBounds(10, 50, 330, 30);
		panel.add(nameL);
		
		JLabel dateL = new JLabel("published in date " + date);
		dateL.setForeground(window.theme.getColor("foregroundHover"));
		dateL.setFont(Const.FONT_17);
		dateL.setVerticalAlignment(JLabel.CENTER);
		dateL.setHorizontalAlignment(JLabel.CENTER);
		dateL.setBounds(10, 70, 330, 30);
		panel.add(dateL);
		
		JLabel download = new JLabel("DOWNLOAD");
		download.setOpaque(true);
		download.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		download.setForeground(window.theme.getColor("foregroundHover"));
		download.setFont(Const.FONT_BOLD_17);
		download.setBackground(window.theme.getColor("pickerBG"));
		download.setBorder(window.theme.getBorder("pickerBorder"));
		download.setHorizontalAlignment(JLabel.CENTER);
		download.setBounds(27, 150, 147, 40);
		download.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://lorenzoleonardini.altervista.org/nao-java#download"));
				}
				catch (IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
				dispose();
			}
		});
		add(download);

		JLabel close = new JLabel("CLOSE");
		close.setOpaque(true);
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.setForeground(window.theme.getColor("foregroundHover"));
		close.setFont(Const.FONT_BOLD_17);
		close.setBackground(window.theme.getColor("pickerBG"));
		close.setBorder(window.theme.getBorder("pickerBorder"));
		close.setHorizontalAlignment(JLabel.CENTER);
		close.setBounds(176, 150, 147, 40);
		close.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				dispose();
			}
		});
		add(close);
		
		
		
		JDialog _this = this;
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
				_this.setLocation(x, y);
			}
		});
		drag.setBounds(0, 0, 300, 150);
		panel.add(drag);

		setVisible(true);
	}
}
