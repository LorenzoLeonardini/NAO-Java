package org.lorenzoleonardini.naojava.extensions;

import java.util.HashMap;
import java.util.Map;

public class ExtensionInfo
{
	public Map<String, String> values = new HashMap<String, String>();
	
	private String menuString;
	
	public ExtensionInfo(Map<String, String> values)
	{
		this.values = values;
		
		menuString = values.get("menu");
	}
	
	public String menuString()
	{
		return menuString.toUpperCase();
	}
	
	public void setLanguage(String lang)
	{
		if(values.containsKey("menu_" + lang.toLowerCase()))
			menuString = values.get("menu_" + lang.toLowerCase());
		else
			menuString = values.get("menu");
	}
}
