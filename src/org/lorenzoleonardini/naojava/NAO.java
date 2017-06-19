package org.lorenzoleonardini.naojava;

import java.awt.Color;
import java.util.List;

import com.aldebaran.qi.helper.proxies.ALVideoDevice;

public class NAO
{
	public String cameraID;

	public ALVideoDevice camera;

	public void changeLedColor(String colorID, Color color)
	{
	}

	public void posture(String pst)
	{
	}

	public String getName()
	{
		return null;
	}

	public String getIP()
	{
		return null;
	}

	public String toString()
	{
		return null;
	}

	public String getHeadVersion()
	{
		return null;
	}

	public String getBodyVersion()
	{
		return null;
	}
	
	public void motionSetPosition(String chainName, int space, List<Float> position, int axisMask, List<Float> time)
	{
	}
	
	public void openHand(String hand)
	{
	}
	
	public void closeHand(String hand)
	{
	}
}
