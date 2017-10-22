package org.lorenzoleonardini.naojava;

import java.util.HashMap;
import java.util.Map;

import com.aldebaran.qi.Application;

public class ApplicationCache
{
	private static Map<String, Application> applications = new HashMap<String, Application>();
	
	public static Application newApplication(String ip)
	{
		if(applications.get(ip) != null)
			return applications.get(ip);
		
		Application a = new Application(new String[0], "tcp://" + ip);
		applications.put(ip, a);
		return a;
	}
}
