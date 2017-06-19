package org.lorenzoleonardini.naojava.resources;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class Theme
{
	private Map<String, Color> colors = new HashMap<String, Color>();
	private Map<String, Border> borders = new HashMap<String, Border>();
	private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	
	public Theme(String name)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Theme.class.getResource(name + "Theme.txt").openStream()));
			
			String mode = "";
			String s;
			
			while((s = reader.readLine()) != null)
			{
				if(s.startsWith("#"))
				{
					s = s.replaceAll("#", "").trim();
					mode = s;
					continue;
				}
				
				String data[];
				String id;
				
				switch(mode.toUpperCase())
				{
					case "COLORS":
						data = s.split(":");
						id = data[0];
						data = data[1].split(";");
						Color color;
						if(data.length != 4)
							color = new Color(0, 0, 0, 0);
						else
							color = new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
						colors.put(id, color);
						break;
					case "BORDERS":
						data = s.split(":");
						id = data[0];
						data = data[1].split(";");
						Border border;
						if(data.length != 4)
							border = BorderFactory.createEmptyBorder(1, 1, 1, 1);
						else
							border = BorderFactory.createLineBorder(new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])), 1);
						borders.put(id, border);
						break;
					case "IMAGES":
						data = s.split(":");
						id = data[0];
						String path = data[1].split(";")[0];
						icons.put(id, Resources.getIcon(path));
						break;
					default:
						break;
				}
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Color getColor(String id)
	{
		return colors.get(id);
	}
	
	public Border getBorder(String id)
	{
		return borders.get(id);
	}
	
	public ImageIcon getIcon(String id)
	{
		return icons.get(id);
	}
}
