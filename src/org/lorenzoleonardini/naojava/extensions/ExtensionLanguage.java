package org.lorenzoleonardini.naojava.extensions;

import java.util.HashMap;
import java.util.Map;

public class ExtensionLanguage
{
	private Map<String, String> strings = new HashMap<String, String>();
	private String lang = "en"; 
	
	public ExtensionLanguage()
	{
		
	}
	
	public void setStrings(Map<String, String> strings)
	{
		this.strings = strings;
	}

	public String get(String key)
	{
		if(lang.equalsIgnoreCase("en"))
			return strings.get(key.toLowerCase());
		
		if(strings.containsKey(key.toLowerCase() + "_" + lang.toLowerCase()))
			return strings.get(key.toLowerCase() + "_" + lang.toLowerCase());
		else
			return strings.get(key.toLowerCase());
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}
}
