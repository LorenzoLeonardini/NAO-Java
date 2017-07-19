package org.lorenzoleonardini.naojava.ui.panels;

import java.awt.Color;

import org.lorenzoleonardini.naojava.ui.ColorPicker;
import org.lorenzoleonardini.naojava.ui.ColorPickerEvent;
import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.Window;

public class LEDsPanel extends PagePanel
{
	private static final long serialVersionUID = 1L;
	
	private ColorPicker brain;
	private ColorPicker eyes;
	private ColorPicker ears;
	private ColorPicker chest;
	private ColorPicker feet;

	public LEDsPanel(Window window)
	{
		super(window);
		
		requiresRobot(true);
		
		System.out.println("\tCreating brain...");
		brain = new ColorPicker(window, window.lang.getString("brain"), 200, 10);
		brain.add(this);
		brain.addEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("BrainLeds", color);
			}
		});
		
		System.out.println("\tCreating eyes...");
		eyes = new ColorPicker(window, window.lang.getString("eyes"), window.lang.getString("rEye"), window.lang.getString("lEye"), 12, 130);
		eyes.add(this);
		eyes.addEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("FaceLeds", color);
			}
		});
		eyes.addLeftEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("LeftFaceLeds", color);
			}
		});
		eyes.addRightEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("RightFaceLeds", color);
			}
		});
		
		System.out.println("\tCreating ears...");
		ears = new ColorPicker(window, window.lang.getString("ears"), 200, 250);
		ears.add(this);
		ears.addEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("EarLeds", color);
			}
		});
		
		System.out.println("\tCreating chest...");
		chest = new ColorPicker(window, window.lang.getString("chest"), 200, 370);
		chest.add(this);
		chest.addEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("ChestLeds", color);
			}
		});
		
		System.out.println("\tCreating feet...");
		feet = new ColorPicker(window, window.lang.getString("feet"), window.lang.getString("rFoot"), window.lang.getString("lFoot"), 12, 490);
		feet.add(this);
		feet.addEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("FeetLeds", color);
			}
		});
		feet.addLeftEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("LeftFootLeds", color);
			}
		});
		feet.addRightEvent(new ColorPickerEvent()
		{
			@Override
			public void onColorChange(Color color)
			{
				if(window.nao != null)
					window.nao.changeLedColor("RightFootLeds", color);
			}
		});
	}

	@Override
	public void updateLanguage(String lang)
	{
		brain.setText(window.lang.getString("brain"));
		eyes.setTexts(window.lang.getString("eyes"), window.lang.getString("rEye"), window.lang.getString("lEye"));
		ears.setText(window.lang.getString("ears"));
		chest.setText(window.lang.getString("chest"));
		feet.setTexts(window.lang.getString("feet"), window.lang.getString("rFoot"), window.lang.getString("lFoot"));
	}

	@Override
	public void updateTheme()
	{
		brain.updateTheme();
		eyes.updateTheme();
		ears.updateTheme();
		chest.updateTheme();
		feet.updateTheme();
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
