import java.util.HashMap;
import java.util.Map;

import org.lorenzoleonardini.nao.NAO;
import org.lorenzoleonardini.nao.RecognitionEvent;
import org.lorenzoleonardini.nao.userInterface.Window;

import com.aldebaran.qi.helper.EventCallback;

public class Main
{
	private NAO nao;

	long sonarLeft = 0;
	long sonarRight = 0;

	public Main()
	{
		nao = new NAO("192.168.1.24");
		
		if(!nao.connected)
		{
			System.err.println("SYS > Unable to estabilish connection");
			System.err.println("SYS > Starting program with null nao (May be full of errors)");
			new Window(null);
			return;
		}
		
		new Window(nao);
		
		nao.changeLanguage("italian");
		
		nao.setExpression(NAO.defaultExp, 0);
		
		addSpeechRecognition();
		
		nao.posture.setBreathing(false);
		nao.posture.crouch();

		nao.trackFace();

		nao.addEvent("PassiveDiagnosisErrorChanged", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float f)
			{
				System.out.println(f);
			}
		});

		//TODO: Wait a few seconds before changing expression (also to prevent glitches)
		nao.addEvent("FrontTactilTouched", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float f)
			{
				if (f > 0)
				{
					nao.setExpression(NAO.happyExp, .1f);
				}
				else
				{
					nao.setExpression(NAO.defaultExp, .1f);
				}
			}
		});

		nao.addEvent("footContactChanged", new EventCallback<Boolean>()
		{
			@Override
			public void onEvent(Boolean b)
			{
				if (!b)
				{
					nao.setExpression(NAO.angryExp, 0);
//					nao.say("pòsami subito!");
				}
				else
				{
					nao.setExpression(NAO.defaultExp, 1);
				}
			}
		});

		nao.run();
	}
	
	private void addSpeechRecognition()
	{
		Map<String[], RecognitionEvent> vocab = new HashMap<String[], RecognitionEvent>();
		vocab.put(new String[] { "nao seduto", "nao siediti" } , new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				if (!nao.canMove)
					return;
				nao.posture.crouch();
			}
		});
		vocab.put(new String[] { "nao in piedi" }, new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				if(!nao.canMove)
					return;
				nao.posture.stand();
			}
		});
		vocab.put(new String[] { "nao cammina" }, new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				if(!nao.canMove)
					return;
				nao.posture.stand();
				nao.move(0.25f, 0, 0);
			}
		});
		vocab.put(new String[] { "ciao", "buongiorno" }, new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				if(!nao.canMove)
					return;
				nao.canMove = false;
				nao.getMotion().saluta();
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				nao.canMove = true;
			}
		});
		vocab.put(new String[] { "blocca movimenti" }, new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				nao.canMove = false;
			}
		});
		vocab.put(new String[] { "abilita movimenti" }, new RecognitionEvent()
		{
			@Override
			public void onWordRecognized(NAO nao)
			{
				nao.canMove = true;
			}
		});

		nao.setVocabulary(vocab);
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}