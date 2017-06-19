package org.lorenzoleonardini.naojava.ui;

import java.awt.Component;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class ComboBoxEditor extends BasicComboBoxEditor
{
	private JLabel label = new JLabel();
	private JPanel panel = new JPanel();
	private Object selectedItem;
	
	private Window window;

	public ComboBoxEditor(Window window)
	{
		this.window = window;
		label.setFont(Const.FONT_BOLD_20);
		label.setForeground(window.theme.getColor("comboSelected"));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		label.setSize(300, 40);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel btn = new JLabel(window.theme.getIcon("btn"));
		btn.setBounds(260, 0, 40, 40);
		btn.setVerticalAlignment(JLabel.CENTER);
		btn.setHorizontalAlignment(JLabel.CENTER);

		panel.setLayout(null);
		panel.add(label);
		panel.add(btn);
		panel.setBackground(window.theme.getColor("fieldBG"));
		panel.setSize(300, 40);
	}

	public Component getEditorComponent()
	{
		return this.panel;
	}

	public Object getItem()
	{
		return this.selectedItem.toString();
	}

	public void setItem(Object item)
	{
		this.selectedItem = item;
		label.setText(item.toString());
	}
	
	public void updateTheme()
	{
		panel.setBackground(window.theme.getColor("fieldBG"));
		label.setForeground(window.theme.getColor("comboSelected"));
	}

}