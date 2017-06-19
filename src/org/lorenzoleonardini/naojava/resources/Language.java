package org.lorenzoleonardini.naojava.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Language
{
	private String name;
	
	private Map<String, String> strings = new HashMap<String, String>();
		
	public Language(String file)
	{
		name = file.substring(0, 2);
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Language.class.getResource(file).openStream()));
			
			String line;
			while((line = reader.readLine()) != null)
			{
				String parts[] = line.split(":");
				strings.put(parts[0].toLowerCase(), parts[1]);
			}
			
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getString(String id)
	{
		String ret = strings.get(id.toLowerCase());
		if(ret == null)
			return id;
		return ret;
	}
	
	public String toString()
	{
		return name;
	}
}
