package org.lorenzoleonardini.naojava.ui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PostureButton
{
	private Window window;
	
	private JLabel bg;
	
	private String texture;
	
	public PostureButton(Window window, int x, int y, String texture)
	{
		this.window = window;
		this.texture = texture;
		bg = new JLabel(window.theme.getIcon(texture));
		bg.setBounds(x, y, 170, 170);
		bg.setOpaque(true);
		bg.setBackground(window.theme.getColor("pickerBG"));
		bg.setBorder(window.theme.getBorder("pickerBorder"));
		bg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bg.setHorizontalAlignment(JLabel.CENTER);
		bg.setVerticalAlignment(JLabel.CENTER);
		bg.setToolTipText(texture.substring(0, 1).toUpperCase() + texture.substring(1));
		bg.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(window.nao == null)
					return;
				window.nao.posture(texture.substring(0, 1).toUpperCase() + texture.substring(1), 3.5f);
			}
		});
	}
	
	public void add(JPanel panel)
	{
		panel.add(bg);
	}
	
	public void updateTheme()
	{
		bg.setBackground(window.theme.getColor("pickerBG"));
		bg.setBorder(window.theme.getBorder("pickerBorder"));
		bg.setIcon(window.theme.getIcon(texture));
	}
}
