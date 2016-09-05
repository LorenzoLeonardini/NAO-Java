package org.lorenzoleonardini.nao;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;

public class Posture
{
	public static String LYING_BACK = "LyingBack";
	public static String LYING_BELLY = "LyingBelly";
	public static String SIT = "Sit";
	public static String SIT_RELAX = "SitRelax";
	public static String CROUCH = "Crouch";
	public static String STAND = "Stand";
	public static String STAND_INIT = "StandInit";
	public static String STAND_ZERO = "StandZero";
	
	private ALRobotPosture posture;
	private ALAutonomousMoves autoMove;
	
	public Posture(NAO nao)
	{
		try
		{
			posture = new ALRobotPosture(nao.application.session());
			autoMove = new ALAutonomousMoves(nao.application.session());
			autoMove.setExpressiveListeningEnabled(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void crouch()
	{
		crouch(1f);
	}
	
	public void crouch(float speed)
	{
		changePosture("Crouch", speed);
	}
	
	public void stand()
	{
		stand(1f);
	}
	
	public void stand(float speed)
	{
		changePosture("Stand", speed);
	}
	
	public void changePosture(String posture)
	{
		changePosture(posture, 1.0f);
	}

	public void changePosture(String posture, float speed)
	{
		try
		{
			this.posture.goToPosture(posture, speed);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setBreathing(boolean breathing)
	{
		try
		{
			if (breathing)
				autoMove.setBackgroundStrategy("backToNeutral");
			else
				autoMove.setBackgroundStrategy("none");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}