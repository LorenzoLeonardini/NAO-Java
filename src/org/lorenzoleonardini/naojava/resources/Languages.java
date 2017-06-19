package org.lorenzoleonardini.naojava.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Languages
{
	private Map<String, Language> langs = new HashMap<String, Language>();
	public Language selected;

	public String name;

	public Languages(Settings settings)
	{
		langs.put("ENGLISH", new Language("en.txt"));
		langs.put("ITALIANO", new Language("it.txt"));
		select(settings.get("lang"));
	}

	public void select(String language)
	{
		if (langs.containsKey(language.toUpperCase()))
		{
			name = language.toUpperCase();
			selected = langs.get(language.toUpperCase());
		}
		else
		{
			name = "ENGLISH";
			selected = langs.get("ENGLISH");
		}
	}
	
	public Set<String> getLanguages()
	{
		return langs.keySet();
	}
}
