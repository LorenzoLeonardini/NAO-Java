package org.lorenzoleonardini.naojava.ui;

import java.awt.Font;

public class Const
{
	//TODO: change font
	public static Font FONT_30 = new Font("Raleway", Font.PLAIN, 30);
	public static Font FONT_BOLD_30 = new Font("Raleway", Font.BOLD, 30);
	public static Font FONT_20 = new Font("Raleway", Font.PLAIN, 20);
	public static Font FONT_BOLD_20 = new Font("Raleway", Font.BOLD, 20);
	public static Font FONT_17 = new Font("Raleway", Font.PLAIN, 17);
	public static Font FONT_BOLD_17 = new Font("Raleway", Font.BOLD, 17);
	/* ---- END CONSTANTS  ---- */
	
	private static Window window;
	
	public static void init(Window window)
	{
		Const.window = window;
	}
	
	public static void updateUI()
	{
		window.updateUI();
	}
}
