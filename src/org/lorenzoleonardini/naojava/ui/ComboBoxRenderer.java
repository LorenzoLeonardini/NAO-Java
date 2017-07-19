package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("hiding")
public class ComboBoxRenderer<String> extends JLabel implements ListCellRenderer<String>
{

	private static final long serialVersionUID = 1L;

	private Window window;
	
	public ComboBoxRenderer(Window window)
	{
		this.window = window;
		setOpaque(true);
		setFont(Const.FONT_20);
		setBackground(window.theme.getColor("fieldBG"));
		setForeground(window.theme.getColor("combo"));
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK), BorderFactory.createEmptyBorder(5, 0, 5, 0)));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus)
	{
		setText(value.toString());
		return this;
	}
	
	public void updateTheme()
	{
		setBackground(window.theme.getColor("fieldBG"));
		setForeground(window.theme.getColor("combo"));
	}
}