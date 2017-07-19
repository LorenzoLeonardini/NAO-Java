package org.lorenzoleonardini.naojava.extensions;

import java.util.HashMap;
import java.util.Map;

import org.lorenzoleonardini.naojava.ui.PagePanel;

public class ExtensionLanguage
{
	private static Map<PagePanel, Map<String, Map<String, String>>> strings = new HashMap<PagePanel, Map<String, Map<String, String>>>();

	private static String lang = "en";

	public static void add(PagePanel panel, String lang, String key, String message)
	{
		key = key.toLowerCase();
		lang = lang.toLowerCase().substring(0, 2);
		
		if (strings.get(panel) == null)
			strings.put(panel, new HashMap<String, Map<String, String>>());

		if (strings.get(panel).get(key) == null)
			strings.get(panel).put(key, new HashMap<String, String>());

		strings.get(panel).get(key).put(lang, message);
	}

	public static String getText(PagePanel panel, String key)
	{
		key = key.toLowerCase();
		
		if (strings.get(panel) == null)
			return "NO PANEL";

		if (strings.get(panel).get(key) == null)
			return "NO KEY";

		if (strings.get(panel).get(key).get(lang) == null)
			if (strings.get(panel).get(key).get("en") == null)
				if (strings.get(panel).get(key).get(strings.get(panel).get(key).keySet().iterator().next()) == null)
					return "NO STRING AT ALL";
				else
					return strings.get(panel).get(key).get(strings.get(panel).get(key).keySet().iterator().next());
			else
				return strings.get(panel).get(key).get("en");
		else
			return strings.get(panel).get(key).get(lang);
	}

	public static void setLang(String lang)
	{
		ExtensionLanguage.lang = lang;
	}
}
