package org.lorenzoleonardini.naojava.extensions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.Window;

public class ExtensionInstance
{
	public PagePanel panel;
	private Class<?> clazzPanel;
	public Extension info;
	
	public ExtensionInstance(File file) throws ExtensionLoadException
	{
		try
		{
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + file.getAbsolutePath() + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			while (e.hasMoreElements())
			{
				JarEntry je = e.nextElement();
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
			
			jarFile.close();
			
			info = clazzPanel.getAnnotation(Extension.class);
			
			if(clazzPanel == null || info == null)
			{
				System.err.println("\tUnable to load " + file.getName() + " extension");
				throw new ExtensionLoadException("Unable to load " + file.getName() + " extension");
			}
			
			System.out.println("\t" + info.name() + " > Successufully loaded" + (info.version().length() > 0 ? (", version " + info.version() + ", by ") : ", by ") + info.author());
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
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		return panel;
	}
}
