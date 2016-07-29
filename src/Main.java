import java.util.ArrayList;
import java.util.List;

import org.lorenzoleonardini.nao.AngryExpression;
import org.lorenzoleonardini.nao.DefaultExpression;
import org.lorenzoleonardini.nao.HappyExpression;
import org.lorenzoleonardini.nao.Motion;
import org.lorenzoleonardini.nao.NAO;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;

public class Main
{
	private NAO nao;
	private Motion motion;
	private boolean canMove = true;

	public Main()
	{
		motion = new Motion();

		nao = new NAO("192.168.1.47");
		nao.changeLanguage("italian");

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

		nao.setBreathing(false);
		nao.changePosture(NAO.CROUCH);

		nao.trackFace();

		nao.addEvent("FrontTactilTouched", new EventCallback<Float>()
		{
			@Override
			public void onEvent(Float f)
			{
				if (f > 0)
				{
					nao.setExpression(happyExp, .1f);
					try
					{
						System.out.println(nao.diagnosis.getActiveDiagnosis());
						System.out.println(nao.diagnosis.getPassiveDiagnosis());
					}
					catch (CallError | InterruptedException e)
					{
						e.printStackTrace();
					}
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
						nao.changePosture(NAO.STAND);
						break;
					// case "ciao":
					// case "buongiorno":
					// if (!canMove)
					// break;
					// canMove = false;
					// motion.saluta(nao);
					// Thread.sleep(3000);
					// canMove = true;
					// break;
					case "nao seduto":
					case "nao siediti":
						if (!canMove)
							break;
						nao.changePosture(NAO.CROUCH);
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
						nao.changePosture(NAO.STAND);
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

		nao.say(nao.getPowerLevel() + "%");
		
		nao.run();
	}

	public static void main(String[] args)
	{
		new Main();
	}
}