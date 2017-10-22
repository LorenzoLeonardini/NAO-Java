package org.lorenzoleonardini.naojava.extensions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.lorenzoleonardini.naojava.ui.MenuElement;
import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.Window;

public class ExtensionInstance
{
	public PagePanel panel;
	private Class<?> clazzPanel;
	private MenuElement elmnt;
	public ExtensionInfo info;
	private Map<String, String> strings = new HashMap<String, String>();
	
	public ExtensionInstance(File file) throws ExtensionLoadException
	{
		try
		{
			//READ FILE
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + file.getAbsolutePath() + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);
			
			List<JarEntry> jarFiles = new ArrayList<JarEntry>();

			//SAVE EVERY FILE INSIDE JAR INTO THE jarFiles LIST
			while (e.hasMoreElements())
			{
				JarEntry je = e.nextElement();
				jarFiles.add(je);
			}
			
			Map<String, String> infoValues = new HashMap<String, String>();
			
			//LOAD EVERY INFO FROM extension.info FILE
			boolean hasInfo = false;
			for(JarEntry je : jarFiles)
			{
				if(je.getName().equalsIgnoreCase("extension.info"))
				{
					URL url = new URL("jar:file:" + file.getAbsolutePath() + "!/extension.info");
					InputStream is = url.openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					String line;
					while((line = reader.readLine()) != null)
					{
						if(!line.contains(":"))
							continue;
						String[] splitted = line.split(":");
						infoValues.put(splitted[0].toLowerCase().trim(), splitted[1].trim());
					}
					
					reader.close();
					is.close();
					
					if(	!infoValues.containsKey("name") ||
						!infoValues.containsKey("ver") ||
						!infoValues.containsKey("author") ||
						!infoValues.containsKey("menu"))
					{
						System.err.println("\tUnable to load " + file.getName() + ": extension.info file miss information");
						jarFile.close();
						throw new ExtensionLoadException("Unable to load " + file.getName() + ": extension.info file miss information");
					}
					
					hasInfo = true;
					break;
				}
			}
			
			//IF THIS FILE DOES NOT EXIST, ERROR
			if(!hasInfo)
			{
				System.err.println("\tUnable to load " + file.getName() + ": no extension.info file");
				jarFile.close();
				throw new ExtensionLoadException("Unable to load " + file.getName() + ": no extension.info file");
			}
			
			info = new ExtensionInfo(infoValues);
			
			//TODO
			//LOAD MAIN CLASS
			for(JarEntry je : jarFiles)
			{
				if (je.isDirectory() || !je.getName().endsWith(".class"))
				{
					continue;
				}
				// -6 because of .class
				String className = je.getName().substring(0, je.getName().length() - 6);
				className = className.replace('/', '.');
				Class<?> c = cl.loadClass(className);
				if(c.getSuperclass() == PagePanel.class)
					clazzPanel = c;
			}
			
			if(clazzPanel == null || infoValues == null)
			{
				jarFile.close();
				System.err.println("\tUnable to load " + file.getName() + " extension");
				throw new ExtensionLoadException("Unable to load " + file.getName() + " extension");
			}
			
			//LOAD strings.data FILE
			for(JarEntry je : jarFiles)
			{
				if(je.getName().equalsIgnoreCase("strings.data"))
				{
					URL url = new URL("jar:file:" + file.getAbsolutePath() + "!/strings.data");
					InputStream is = url.openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					String line;
					while((line = reader.readLine()) != null)
					{
						if(!line.contains(":"))
							continue;
						String[] splitted = line.split(":");
						strings.put(splitted[0].toLowerCase().trim(), splitted[1].trim());
					}
					
					reader.close();
					is.close();
					break;
				}
			}
			
			jarFile.close();
			
			System.out.println("\t" + infoValues.get("name") + " > Successufully loaded version " + infoValues.get("ver") + ", by " + infoValues.get("author"));
		}
		catch (Exception e)
		{
			throw new ExtensionLoadException("Unable to load " + file.getName() + " extension");
		}
	}
	
	public PagePanel initPanel(Window window)
	{
		try
		{
			panel = (PagePanel) clazzPanel.getDeclaredConstructor(Window.class).newInstance(window);
			panel.STRINGS.setStrings(strings);
			updateLanguage(window.lang.toString());
			panel.updateLanguage(window.lang.toString());
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		return panel;
	}
	
	public void setMenuElement(MenuElement elmnt)
	{
		this.elmnt = elmnt;
	}
	
	public void updateLanguage(String lang)
	{
		info.setLanguage(lang);
		if(elmnt != null)
			elmnt.setText(info.menuString());
		if(panel != null)
			panel.STRINGS.setLang(lang);
	}
}
