package org.lorenzoleonardini.nao;

import com.aldebaran.qi.CallError;

public class Motion
{
	public void saluta(final NAO nao)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					float angle = nao.motion.getAngles("RShoulderPitch", true).get(0);
					nao.motion.angleInterpolation("RShoulderPitch", -1.5f, 1.5f, true);
					Thread.sleep(2200);
					nao.motion.angleInterpolation("RShoulderPitch", angle, 1.5f, true);
				} catch (CallError | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		new Thread()
		{
			public void run()
			{
				try
				{
					float angle = nao.motion.getAngles("RElbowYaw", true).get(0);
					nao.motion.angleInterpolation("RElbowYaw", 0f, 1.5f, true);
					Thread.sleep(2200);
					nao.motion.angleInterpolation("RElbowYaw", angle, 1.5f, true);
				} catch (CallError | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		new Thread()
		{
			public void run()
			{
				try
				{
					float angle = nao.motion.getAngles("RElbowRoll", true).get(0);
					nao.motion.angleInterpolation("RElbowRoll", 1f, 1.5f, true);
					Thread.sleep(100);
					nao.motion.angleInterpolation("RElbowRoll", 0f, .3f, true);
					Thread.sleep(100);
					nao.motion.angleInterpolation("RElbowRoll", 1f, .3f, true);
					Thread.sleep(100);
					nao.motion.angleInterpolation("RElbowRoll", 0f, .3f, true);
					Thread.sleep(100);
					nao.motion.angleInterpolation("RElbowRoll", 1f, .3f, true);
					Thread.sleep(600);
					nao.motion.angleInterpolation("RElbowRoll", angle, .3f, true);
				} catch (CallError | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(2000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				nao.say("ciao!");
			}
		}.start();
	}
}