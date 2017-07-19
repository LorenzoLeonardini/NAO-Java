package org.lorenzoleonardini.naojava.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class ColorPicker
{
	private JLabel bg = new JLabel();
	private JLabel color = new JLabel();
	private JLabel title = new JLabel();
	private JLabel redBar = new JLabel();
	private JLabel redBarIndicator = new JLabel();
	private JLabel greenBarIndicator = new JLabel();
	private JLabel blueBarIndicator = new JLabel();
	private JLabel greenBar = new JLabel();
	private JLabel blueBar = new JLabel();

	private JTextField hex = new JTextField();

	private int x;

	private boolean listener = true;

	private ColorPicker both, sn, dx;
	private boolean dual = false, single = true;
	private JLabel extend1;
	private JLabel extend2;
	private JLabel reduce;

	private JPanel panel;

	private Window window;

	private List<ColorPickerEvent> evts = new ArrayList<ColorPickerEvent>();

	public ColorPicker(Window window, String name, int x, int y)
	{
		this.window = window;
		this.x = x;

		bg.setBounds(x, y, 350, 110);
		bg.setOpaque(true);
		bg.setBackground(window.theme.getColor("pickerBG"));
		bg.setBorder(window.theme.getBorder("pickerBorder"));

		color.setBounds(x + 20, y + 20, 70, 70);
		color.setOpaque(true);
		color.setBackground(new Color(0xBC0000));
		color.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		title.setText(name);
		title.setFont(new Font("Raleway", Font.BOLD, 15));
		title.setBounds(x + 110, y + 19, 220, 30);
		title.setVerticalAlignment(JLabel.TOP);
		title.setForeground(window.theme.getColor("foregroundHover"));

		redBar.setBounds(x + 110, y + 51, 220, 5);
		redBar.setOpaque(true);
		redBar.setBackground(new Color(0xE54444));
		redBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		greenBar.setBounds(x + 110, y + 68, 220, 5);
		greenBar.setOpaque(true);
		greenBar.setBackground(new Color(0x44E544));
		greenBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		blueBar.setBounds(x + 110, y + 85, 220, 5);
		blueBar.setOpaque(true);
		blueBar.setBackground(new Color(0x4444E5));
		blueBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		redBarIndicator.setBounds(x + 130, y + 45, 6, 17);
		redBarIndicator.setBackground(new Color(0x14182E));
		redBarIndicator.setOpaque(true);

		greenBarIndicator.setBounds(x + 180, y + 62, 6, 17);
		greenBarIndicator.setBackground(new Color(0x14182E));
		greenBarIndicator.setOpaque(true);

		blueBarIndicator.setBounds(x + 150, y + 79, 6, 17);
		blueBarIndicator.setBackground(new Color(0x14182E));
		blueBarIndicator.setOpaque(true);

		hex.setDocument(new PlainDocument()
		{
			private static final long serialVersionUID = 1L;

			private String text = "";

			@Override
			public void insertString(int offset, String txt, AttributeSet a)
			{
				try
				{
					text = getText(0, getLength());
					if ((text + txt).matches("#[0-9a-fA-F]{0,7}"))
					{
						super.insertString(offset, txt.toUpperCase(), a);
					}
				}
				catch (Exception ex)
				{
					Logger.getLogger(PlainDocument.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		});

		hex.setBounds(x + 240, y + 20, 90, 20);
		hex.setBackground(window.theme.getColor("fieldBG"));
		hex.setForeground(Color.BLACK);
		hex.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		hex.setFont(new Font("Helvetica", Font.PLAIN, 15));
		hex.setHorizontalAlignment(JLabel.CENTER);
		hex.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				listener();
			}

			public void removeUpdate(DocumentEvent e)
			{
				listener();
			}

			public void insertUpdate(DocumentEvent e)
			{
				listener();
			}

			private void listener()
			{
				Runnable doHighlight = new Runnable()
				{
					@Override
					public void run()
					{
						if (!hex.getText().startsWith("#"))
							hex.setText("#" + hex.getText());
						if (hex.getText().length() > 7)
							hex.setText(hex.getText().substring(0, 7));
					}
				};
				SwingUtilities.invokeLater(doHighlight);
				if (!listener)
					return;
				calculateColor(hex.getText());
			}
		});
		hex.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(window.nao == null)
					bg.requestFocus();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				
			}
		});

		addControl(redBar, redBarIndicator);
		addControl(greenBar, greenBarIndicator);
		addControl(blueBar, blueBarIndicator);

		Random rand = new Random();

		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);

		calculateColor(r, g, b);

		changeHEX(r, g, b);
	}

	public ColorPicker(Window window, String name, String name1, String name2, int x, int y)
	{
		this.window = window;

		both = new ColorPicker(window, name, x + 188, y);
		sn = new ColorPicker(window, name1, x, y);
		dx = new ColorPicker(window, name2, x + 376, y);
		dual = true;

		MouseAdapter expandReduce = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(window.nao == null)
					return;
				expandReduce();
			}
		};

		reduce = new JLabel(window.theme.getIcon("pickerReduce"));
		reduce.setBounds(x + 349, y, 28, 110);
		reduce.setOpaque(true);
		reduce.setBackground(window.theme.getColor("pickerBG"));
		reduce.setHorizontalAlignment(JLabel.CENTER);
		reduce.setVerticalAlignment(JLabel.CENTER);
		reduce.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		reduce.addMouseListener(expandReduce);
		reduce.setBorder(window.theme.getBorder("pickerBorder"));

		extend1 = new JLabel(window.theme.getIcon("pickerExtSn"));
		extend1.setBounds(x + 171, y, 18, 110);
		extend1.setOpaque(true);
		extend1.setBackground(window.theme.getColor("pickerBG"));
		extend1.setHorizontalAlignment(JLabel.CENTER);
		extend1.setVerticalAlignment(JLabel.CENTER);
		extend1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		extend1.addMouseListener(expandReduce);
		extend1.setBorder(window.theme.getBorder("pickerBorder"));

		extend2 = new JLabel(window.theme.getIcon("pickerExtDx"));
		extend2.setBounds(x + 537, y, 18, 110);
		extend2.setOpaque(true);
		extend2.setBackground(window.theme.getColor("pickerBG"));
		extend2.setHorizontalAlignment(JLabel.CENTER);
		extend2.setVerticalAlignment(JLabel.CENTER);
		extend2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		extend2.addMouseListener(expandReduce);
		extend2.setBorder(window.theme.getBorder("pickerBorder"));
	}

	private void addControl(JLabel bar, JLabel indicator)
	{
		bar.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(window.nao == null)
					return;
				int posX = e.getX() - 3 + x + 110;
				moveIndicator(indicator, posX);
			}
		});
		bar.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(window.nao == null)
					return;
				int posX = e.getX() - 3 + x + 110;
				moveIndicator(indicator, posX);
			}
		});
		indicator.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(window.nao == null)
					return;
				int posX = e.getX() - 3 + indicator.getX();
				moveIndicator(indicator, posX);
			}
		});
	}

	private void moveIndicator(JLabel indicator, int posX)
	{
		posX = posX >= x + 107 ? posX : (x + 107);
		posX = posX <= x + 327 ? posX : (x + 327);
		indicator.setLocation(posX, indicator.getY());
		calculateColor();
	}

	private void calculateColor()
	{
		int r = (int) ((float) (redBarIndicator.getX() + 3 - redBar.getX()) / 220f * 255f);
		int g = (int) ((float) (greenBarIndicator.getX() + 3 - greenBar.getX()) / 220f * 255f);
		int b = (int) ((float) (blueBarIndicator.getX() + 3 - blueBar.getX()) / 220f * 255f);

		changeColor(r, g, b);
		changeHEX(r, g, b);
	}

	private void calculateColor(String hex)
	{
		if (hex.length() <= 0)
			return;
		if (hex.startsWith("#"))
			hex = hex.substring(1);
		hex = hex.substring(0, hex.length() > 6 ? 6 : hex.length());
		if (hex.length() <= 0)
			return;

		int rgb = Integer.parseInt(hex, 16);
		int r = (rgb & 0xFF0000) >> 16;
		int g = (rgb & 0x00FF00) >> 8;
		int b = (rgb & 0x0000FF);

		redBarIndicator.setLocation((int) ((float) r * 220f / 255f) + redBar.getX() - 3, redBarIndicator.getY());
		greenBarIndicator.setLocation((int) ((float) g * 220f / 255f) + greenBar.getX() - 3, greenBarIndicator.getY());
		blueBarIndicator.setLocation((int) ((float) b * 220f / 255f) + blueBar.getX() - 3, blueBarIndicator.getY());

		calculateColor(r, g, b);
	}

	private void calculateColor(int r, int g, int b)
	{
		changeColor(r, g, b);
	}

	private void changeColor(int r, int g, int b)
	{
		Color bg = new Color(r, g, b);
		color.setBackground(bg);
		for (ColorPickerEvent evt : evts)
			evt.onColorChange(new Color(r, g, b));
	}

	private String getColor()
	{
		int r = color.getBackground().getRed();
		int g = color.getBackground().getGreen();
		int b = color.getBackground().getBlue();
		String rS = Integer.toHexString(r);
		if (rS.length() == 1)
			rS = "0" + rS;
		String gS = Integer.toHexString(g);
		if (gS.length() == 1)
			gS = "0" + gS;
		String bS = Integer.toHexString(b);
		if (bS.length() == 1)
			bS = "0" + bS;
		return "#" + rS + gS + bS;
	}

	private void changeHEX(int r, int g, int b)
	{
		String rS = Integer.toHexString(r);
		if (rS.length() == 1)
			rS = "0" + rS;
		String gS = Integer.toHexString(g);
		if (gS.length() == 1)
			gS = "0" + gS;
		String bS = Integer.toHexString(b);
		if (bS.length() == 1)
			bS = "0" + bS;
		listener = false;
		hex.setText(("#" + rS + gS + bS).toUpperCase());
		listener = true;
	}

	private void expandReduce()
	{
		if (!single)
		{
			String color = sn.getColor();
			both.hex.setText(color);
		}
		else
		{
			String color = both.getColor();
			sn.hex.setText(color);
			dx.hex.setText(color);
		}
		single = !single;
		add(panel);
	}

	private void remove(JPanel panel)
	{
		panel.remove(hex);
		panel.remove(blueBarIndicator);
		panel.remove(greenBarIndicator);
		panel.remove(redBarIndicator);
		panel.remove(redBar);
		panel.remove(greenBar);
		panel.remove(blueBar);
		panel.remove(title);
		panel.remove(color);
		panel.remove(bg);
	}

	public void add(JPanel panel)
	{
		this.panel = panel;
		if (!dual)
		{
			panel.add(hex);
			panel.add(blueBarIndicator);
			panel.add(greenBarIndicator);
			panel.add(redBarIndicator);
			panel.add(redBar);
			panel.add(greenBar);
			panel.add(blueBar);
			panel.add(title);
			panel.add(color);
			panel.add(bg);
		}
		else
		{
			if (single)
			{
				sn.remove(panel);
				dx.remove(panel);
				panel.remove(reduce);

				both.add(panel);
				panel.add(extend1);
				panel.add(extend2);
			}
			else
			{
				both.remove(panel);
				panel.remove(extend1);
				panel.remove(extend2);

				sn.add(panel);
				dx.add(panel);
				panel.add(reduce);
			}
		}
		panel.updateUI();
	}

	public void setText(String text)
	{
		title.setText(text);
	}

	public void setTexts(String text1, String text2, String text3)
	{
		if (!dual)
		{
			setText(text1);
			return;
		}
		both.setText(text1);
		sn.setText(text2);
		dx.setText(text3);
	}

	public void updateTheme()
	{
		if (dual)
		{
			reduce.setBackground(window.theme.getColor("pickerBG"));
			extend1.setBackground(window.theme.getColor("pickerBG"));
			extend2.setBackground(window.theme.getColor("pickerBG"));
			reduce.setBorder(window.theme.getBorder("pickerBorder"));
			extend1.setBorder(window.theme.getBorder("pickerBorder"));
			extend2.setBorder(window.theme.getBorder("pickerBorder"));
			reduce.setIcon(window.theme.getIcon("pickerReduce"));
			extend1.setIcon(window.theme.getIcon("pickerExtSn"));
			extend2.setIcon(window.theme.getIcon("pickerExtDx"));
			both.updateTheme();
			sn.updateTheme();
			dx.updateTheme();
			return;
		}

		title.setForeground(window.theme.getColor("foregroundHover"));
		bg.setBackground(window.theme.getColor("pickerBG"));
		bg.setBorder(window.theme.getBorder("pickerBorder"));
		hex.setBackground(window.theme.getColor("fieldBG"));
	}

	public void addEvent(ColorPickerEvent e)
	{
		if (!dual)
			evts.add(e);
		else
			both.addEvent(e);
	}

	public void removeEvent(ColorPickerEvent e)
	{
		if (!dual)
			evts.remove(e);
		else
			both.removeEvent(e);
	}
	
	public void addRightEvent(ColorPickerEvent e)
	{
		if (dual)
			dx.addEvent(e);
	}

	public void removeRightEvent(ColorPickerEvent e)
	{
		if (dual)
			dx.removeEvent(e);
	}
	
	public void addLeftEvent(ColorPickerEvent e)
	{
		if (dual)
			sn.addEvent(e);
	}

	public void removeLeftEvent(ColorPickerEvent e)
	{
		if (dual)
			sn.removeEvent(e);
	}
}
