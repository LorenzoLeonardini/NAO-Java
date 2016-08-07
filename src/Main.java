import java.util.ArrayList;
import java.util.List;

import org.lorenzoleonardini.nao.Expression.AngryExpression;
import org.lorenzoleonardini.nao.Expression.DefaultExpression;
import org.lorenzoleonardini.nao.Expression.HappyExpression;
import org.lorenzoleonardini.nao.Motion;
import org.lorenzoleonardini.nao.NAO;
import org.lorenzoleonardini.nao.Posture;
import org.lorenzoleonardini.nao.userInterface.Window;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;

public class Main
{
	private NAO nao;
	private Motion motion;
	private Posture posture;
	private boolean canMove = true;

	long sonarLeft = 0;
	long sonarRight = 0;

	public Main()
	{
		nao = new NAO("192.168.1.24");
		nao.changeLanguage("italian");

		new Window(nao);

		motion = new Motion();
		posture = new Posture(nao);

		final DefaultExpression defaultExp = new DefaultExpression();
		final AngryExpression angryExp = new AngryExpression();
		final HappyExpression happyExp = new HappyExpression();

		nao.setExpression(defaultExp, 0);

		List<String> vocab = new ArrayList<String>();
		vocab.add("nao seduto");
		vocab.add("nao siediti");
		vocab.add("nao in piedi");
		vocab.add("nao cammina");
		vocab.add("ciao");
		vocab.add("buongiorno");
		vocab.add("blocca movimenti");
		vocab.add("abilita movimenti");

		nao.setVocabulary(vocab);

		posture.setBreathing(false);
		posture.stand();

		nao.trackFace();

		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					// System.out.println((System.currentTimeMillis() <=
					// sonarLeft + 500) && (System.currentTimeMillis() <=
					// sonarRight + 500));
					// try
					// {
					// System.out.println(nao.bodyTemperature.getTemperatureDiagnosis());
					// }
					// catch (CallError e)
					// {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// catch (InterruptedException e)
					// {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			}
		}.start();

		nao.addEvent("PassiveDiagnosisErrorChanged", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float f)
			{
				System.out.println(f);
			}
		});

		nao.addEvent("FrontTactilTouched", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float f)
			{
				if (f > 0)
				{
					nao.setExpression(happyExp, .1f);
				}
				else
				{
					nao.setExpression(defaultExp, .1f);
				}
			}
		});

		nao.addEvent("WordRecognized", new EventCallback<ArrayList<String>>()
		{
			@Override
			public void onEvent(ArrayList<String> arr)
			{
				try
				{
					String word = arr.get(0).replace("<...>", "").trim();
					System.out.println(word);
					switch (word)
					{
					case "nao in piedi":
						if (!canMove)
							break;
						posture.stand();
						break;
//					case "ciao":
//					case "buongiorno":
//						if (!canMove)
//							break;
//						canMove = false;
//						motion.saluta(nao);
//						Thread.sleep(3000);
//						canMove = true;
//						break;
					case "nao seduto":
					case "nao siediti":
						if (!canMove)
							break;
						posture.crouch();
						break;
					case "blocca movimenti":
						canMove = false;
						break;
					case "abilita movimenti":
						canMove = true;
						break;
					case "nao cammina":
						if (!canMove)
							break;
						posture.stand();
						nao.move(0.25f, 0, 0);
						break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
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
					nao.setExpression(angryExp, 0);
					nao.say("pòsami subito!");
				}
				else
				{
					nao.setExpression(defaultExp, 1);
				}
			}
		});

		nao.addEvent("SonarLeftNothingDetected", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float l)
			{
				sonarLeft = System.currentTimeMillis();
			}
		});

		nao.addEvent("SonarRightNothingDetected", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float l)
			{
				sonarRight = System.currentTimeMillis();
			}
		});

		try
		{
			nao.leds.fadeRGB("FeetLeds", 0x00ff00, .3f);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}

		nao.say(nao.getPowerLevel() + "%");

		nao.run();
	}

	public static void main(String[] args)
	{
		new Main();
	}
}