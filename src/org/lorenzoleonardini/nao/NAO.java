package org.lorenzoleonardini.nao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALAutonomousLife;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALBodyTemperature;
import com.aldebaran.qi.helper.proxies.ALDiagnosis;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTracker;

public class NAO
{
	public static String CROUCH = "Crouch";
	public static String LYING_BACK = "LyingBack";
	public static String LYING_BELLY = "LyingBelly";
	public static String SIT = "Sit";
	public static String SIT_RELAX = "SitRelax";
	public static String STAND = "Stand";
	public static String STAND_INIT = "StandInit";
	public static String STAND_ZERO = "StandZero";

	private String robotURL;
	private Application application;
	private ALAutonomousLife autonomousLife;
	private ALMemory memory;
	private ALAutonomousMoves autoMove;
	private ALRobotPosture posture;
	private ALSpeechRecognition speechRecognition;
	private ALTracker tracker;
	public ALMotion motion;
	private ALFaceDetection faceDetection;
	private ALBattery battery;
	private ALBodyTemperature bodyTemperature;
	private ALLeds leds;
	public ALDiagnosis diagnosis;

	private Map<String, Long> eventIDS = new HashMap<String, Long>();

	private ALTextToSpeech tts;
	
	private Expression exp;

	public NAO(String IP)
	{
		robotURL = "tcp://" + IP + ":9559";
		application = new Application(new String[0], robotURL);
		application.start();
		log("Successfully connected to the robot!");
		initializeServices();
	}

	private void initializeServices()
	{
		try
		{
			autonomousLife = new ALAutonomousLife(application.session());
			memory = new ALMemory(application.session());
			tts = new ALTextToSpeech(application.session());
			autoMove = new ALAutonomousMoves(application.session());
			posture = new ALRobotPosture(application.session());
			speechRecognition = new ALSpeechRecognition(application.session());
			speechRecognition.unsubscribe("WordRecognized");
			faceDetection = new ALFaceDetection(application.session());
			tracker = new ALTracker(application.session());
			motion = new ALMotion(application.session());
			battery = new ALBattery(application.session());
			bodyTemperature = new ALBodyTemperature(application.session());
			bodyTemperature.setEnableNotifications(true);
			leds = new ALLeds(application.session());
			diagnosis = new ALDiagnosis(application.session());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String currentExpression()
	{
		return (exp == null) ? "Default" : exp.getName();
	}
	
	public void setExpression(Expression e, float fade)
	{
		if(this.exp == e)
			return;
		this.exp = e;
		try
		{
			leds.fadeRGB("BrainLeds", e.getBrainColor(), fade);
			leds.fadeRGB("ChestLeds", e.getChestColor(), fade);
			leds.fadeRGB("EarLeds", e.getEarColor(), fade);
			leds.fadeRGB("FaceLeds", e.getFaceColor(), fade);
		}
		catch (CallError | InterruptedException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public String getPowerLevel()
	{
		try
		{
			return battery.getBatteryCharge().toString();
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void trackFace()
	{
		try
		{
			tracker.addTarget("Face", 0.02f);
			tracker.track("Face");
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void move(float x, float y, float theta)
	{
		try
		{
			motion.moveTo(x, y, theta);
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

	public void say(String message)
	{
		try
		{
			tts.say(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void changeLanguage(String lang)
	{
		lang = lang.substring(0, 1).toUpperCase() + lang.substring(1).toLowerCase();
		try
		{
			tts.setLanguage(lang);
		}
		catch (Exception e)
		{
			System.err.println("NAO > Language not supported!");
		}
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

	public void setVocabulary(List<String> vocab)
	{
		try
		{
			autoMove.setExpressiveListeningEnabled(false);
			speechRecognition.setAudioExpression(false);
			speechRecognition.setVisualExpression(false);
			speechRecognition.setVocabulary(vocab, true);
			speechRecognition.subscribe("WordRecognized");
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void addEvent(String eventName, EventCallback eventCallback)
	{
		try
		{
			long l = memory.subscribeToEvent(eventName, eventCallback);
			eventIDS.put(eventName, l);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		log("Running application");
		application.run();
	}

	public void stop()
	{
		for (long l : eventIDS.values())
		{
			try
			{
				memory.unsubscribeToEvent(l);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		application.stop();
	}

	private void log(String message)
	{
		System.out.println("NAO > " + message);
	}
}