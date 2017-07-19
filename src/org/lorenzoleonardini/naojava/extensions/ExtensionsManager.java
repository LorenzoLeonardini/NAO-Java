package org.lorenzoleonardini.naojava.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtensionsManager
{
	private File folder;

	private List<ExtensionInstance> extensions = new ArrayList<ExtensionInstance>();

	public ExtensionsManager()
	{
		folder = new File("extensions/");
		folder.mkdirs();

		System.out.println("Loading extensions...");
		for (File file : folder.listFiles())
		{
			try
			{
				ExtensionInstance e = new ExtensionInstance(file);
				if(e != null)
					extensions.add(e);
			}
			catch (ExtensionLoadException e)
			{
			}
		}
	}

	public List<ExtensionInstance> getExtensions()
	{
		return extensions;
	}
}
