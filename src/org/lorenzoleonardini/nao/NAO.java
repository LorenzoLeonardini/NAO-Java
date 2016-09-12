package org.lorenzoleonardini.nao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lorenzoleonardini.nao.Expression.AngryExpression;
import org.lorenzoleonardini.nao.Expression.DefaultExpression;
import org.lorenzoleonardini.nao.Expression.HappyExpression;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALBodyTemperature;
import com.aldebaran.qi.helper.proxies.ALDiagnosis;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALSonar;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTracker;

public class NAO
{
	private String robotURL;
	public boolean connected = true;
	protected Application application;
	private ALMemory memory;

	private ALSpeechRecognition speechRecognition;
	private ALTracker tracker;
	public ALMotion motion;
	private ALBattery battery;
	public ALBodyTemperature bodyTemperature;
	public ALLeds leds;
	public ALDiagnosis diagnosis;
	private ALSonar sonar;
	private ALTextToSpeech tts;

	public boolean canMove = true;
	private Map<String, Long> eventIDS = new HashMap<String, Long>();
	private Expression exp;
	public Posture posture;
	private Motion myMotion;

	public static DefaultExpression defaultExp = new DefaultExpression();
	public static AngryExpression angryExp = new AngryExpression();
	public static HappyExpression happyExp = new HappyExpression();

	public NAO(String IP)
	{
		robotURL = "tcp://" + IP + ":9559";
		application = new Application(new String[0], robotURL);
		try
		{
			application.start();
		}
		catch (Exception e)
		{
			connected = false;
			return;
		}
		myMotion = new Motion(this);
		posture = new Posture(this);
		log("Successfully connected to the robot!");
		initializeServices();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> unsubscribeFromAll()));
	}

	public Motion getMotion()
	{
		return myMotion;
	}

	public void unsubscribeFromAll()
	{
		try
		{
			log("Disabling speech recognition");
			speechRecognition.unsubscribe("WordRecognised");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			error("Error while disabling speech recognition!");
		}
		try
		{
			log("Disabling all sonars");
			sonar.unsubscribe("SonarLeftDetected");
			sonar.unsubscribe("SonarRightDetected");
			sonar.unsubscribe("SonarLeftNothingDetected");
			sonar.unsubscribe("SonarRightNothingDetected");
		}
		catch (Exception e)
		{
			error("Error while disabling sonars!");
		}
		log("Stopping application");
		stop();
	}

	private void initializeServices()
	{
		try
		{
			memory = new ALMemory(application.session());
			tts = new ALTextToSpeech(application.session());
			speechRecognition = new ALSpeechRecognition(application.session());
			tracker = new ALTracker(application.session());
			motion = new ALMotion(application.session());
			battery = new ALBattery(application.session());
			bodyTemperature = new ALBodyTemperature(application.session());
			bodyTemperature.setEnableNotifications(true);
			leds = new ALLeds(application.session());
			diagnosis = new ALDiagnosis(application.session());
			sonar = new ALSonar(application.session());
			sonar.subscribe("SonarLeftDetected");
			sonar.subscribe("SonarRightDetected");
			sonar.subscribe("SonarLeftNothingDetected");
			sonar.subscribe("SonarRightNothingDetected");
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
		if (this.exp == e)
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

	public void setVocabulary(Map<String[], RecognitionEvent> map)
	{
		NAO nao = this;
		List<String> strings = new ArrayList<String>();

		for (String[] s : map.keySet())
			for (String s1 : s)
				strings.add(s1);

		setVocabulary(strings);
		addEvent("WordRecognized", new EventCallback<ArrayList<String>>()
		{
			@Override
			public void onEvent(ArrayList<String> arr)
			{
				try
				{
					String word = arr.get(0).replace("<...>", "").trim();
					for (String[] s : map.keySet())
					{
						for (String s1 : s)
						{
							if (word.equalsIgnoreCase(s1))
							{
								map.get(s).onWordRecognized(nao);
								break;
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void setVocabulary(List<String> vocab)
	{
		try
		{
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

	public void addEvent(String eventName, @SuppressWarnings("rawtypes") EventCallback eventCallback)
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

	public void log(String message)
	{
		System.out.println("NAO > " + message);
	}

	private void error(String message)
	{
		System.err.println("NAO > " + message);
	}
}