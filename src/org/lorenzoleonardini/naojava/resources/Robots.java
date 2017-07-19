package org.lorenzoleonardini.naojava.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lorenzoleonardini.naojava.NAO;
import org.lorenzoleonardini.naojava.NAOConnectionException;
import org.lorenzoleonardini.naojava.ui.SavedNAO;
import org.lorenzoleonardini.naojava.ui.Window;

public class Robots
{
	private List<SavedNAO> NAOs = new ArrayList<SavedNAO>();
	private File file;

	private Window window;

	private String fileVersion = "1";

	public Robots(Window window)
	{
		this.window = window;
		file = new File("robots.txt");
		if (!file.exists())
		{
			newFile();
			return;
		}

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line;

			String version = reader.readLine();
			if(version == null)
			{
				reader.close();
				newFile();
				return;
			}
			version = version.toLowerCase().trim();
			if (!version.startsWith("#version") && !version.startsWith("# version"))
			{
				reader.close();
				newFile();
				return;
			}
			String[] versionSplitted = version.split(" ");
			version = versionSplitted[versionSplitted.length - 1];
			if (!version.trim().equalsIgnoreCase(fileVersion))
			{
				reader.close();
				newFile();
				return;
			}

			SavedNAO nao = null;

			while ((line = reader.readLine()) != null)
			{
				line = line.trim();
				if (line.startsWith("#"))
				{
					String name = line.substring(1).trim();
					String ip = line = reader.readLine().trim();
					nao = new SavedNAO(window, name, ip);
					NAOs.add(nao);
				}
			}

			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void newFile()
	{
		try
		{
			file.delete();
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write("# VERSION " + fileVersion);
			writer.newLine();
			
			writer.close();
		}
		catch (IOException e)
		{
		}
	}

	public int robotCount()
	{
		return NAOs.size();
	}

	public NAO connect()
	{
		System.out.println("Trying connecting to one robot");
		for (SavedNAO nao : NAOs)
		{
			try
			{
				return nao.connect();
			}
			catch (Exception e)
			{
				System.out.println("\t" + nao.getIP() + " is not reachable");
			}
		}
		System.out.println("--- No NAO reachable");
		return null;
	}

	public NAO connect(int index) throws NAOConnectionException
	{
		return NAOs.get(index).connect();
	}

	public void addRobot(String name, String ip)
	{
		for (SavedNAO nao : NAOs)
			if (nao.getIP().equalsIgnoreCase(ip))
				return;

		NAOs.add(new SavedNAO(window, name, ip));
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

			writer.write("# " + name);
			writer.newLine();
			writer.write(ip);
			writer.newLine();

			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addRobot(NAO nao)
	{
		for (SavedNAO n : NAOs)
			if (n.getIP().equalsIgnoreCase(nao.getIP()))
				return;

		try
		{
			NAOs.add(new SavedNAO(window, nao));

			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

			writer.write("# " + nao.getName());
			writer.newLine();
			writer.write(nao.getIP());
			writer.newLine();

			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch(NAOConnectionException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public void removeRobot(SavedNAO nao)
	{
		NAOs.remove(nao);
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write("# VERSION " + fileVersion);
			writer.newLine();
			
			for(SavedNAO n : NAOs)
			{
				writer.write("# " + n.getName());
				writer.newLine();
				writer.write(n.getIP());
				writer.newLine();
			}

			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		nao = null;
	}
	
	public void updateTheme()
	{
		for(SavedNAO nao : NAOs)
		{
			nao.updateTheme();
		}
	}
	
	public void updateLanguage()
	{
		for(SavedNAO nao : NAOs)
			nao.updateLanguage();
	}
	
	public List<SavedNAO> getNAOS()
	{
		return NAOs;
	}
}
