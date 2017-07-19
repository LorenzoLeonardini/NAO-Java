package org.lorenzoleonardini.naojava.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings
{
	private Map<String, String> settings = new HashMap<String, String>();
	private File file;
	private String _default[] = 
	{
		"lang:english",
		"theme:dark"
	};
	
	public Settings()
	{
		file = new File("settings.txt");
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				
				for(String s : _default)
				{
					writer.write(s);
					writer.newLine();
				}
				
				writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String s;
			
			while((s = reader.readLine()) != null)
			{
				String[] data = s.split(":");
				if(data.length != 2)
					continue;
				settings.put(data[0], data[1]);
			}
			
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void set(String name, String value)
	{
		name = name.toLowerCase();
		value = value.toLowerCase();
		if(!settings.containsKey(name))
			settings.put(name, value);
		else
			settings.replace(name, value);
		try
		{
			file.delete();
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(String key : settings.keySet())
			{
				writer.write(key + ":" + settings.get(key));
				writer.newLine();
			}
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String get(String name)
	{
		return settings.get(name);
	}
}
