package org.lorenzoleonardini.naojava.ui.panels;

import org.lorenzoleonardini.naojava.ui.PagePanel;
import org.lorenzoleonardini.naojava.ui.PostureButton;
import org.lorenzoleonardini.naojava.ui.Window;

public class PosturesPanel extends PagePanel
{
	private static final long serialVersionUID = 1L;

	PostureButton stand;
	PostureButton standInit;
	PostureButton standZero;
	PostureButton crouch;
	PostureButton sit;
	PostureButton sitRelax;
	PostureButton lay;
	PostureButton layBelly;
	PostureButton hello;
	
	public PosturesPanel(Window window)
	{
		super(window);
		
		requiresRobot(true);
		
		stand = new PostureButton(window, 95, 24, "stand");
		stand.add(this);
		
		standInit = new PostureButton(window, 290, 24, "standInit");
		standInit.add(this);
		
		standZero = new PostureButton(window, 480, 24, "standZero");
		standZero.add(this);
		
		crouch = new PostureButton(window, 95, 219, "crouch");
		crouch.add(this);
		
		sit = new PostureButton(window, 290, 219, "sit");
		sit.add(this);
		
		sitRelax = new PostureButton(window, 480, 219, "sitRelax");
		sitRelax.add(this);
		
		lay = new PostureButton(window, 95, 414, "lyingBelly");
		lay.add(this);
		
		layBelly = new PostureButton(window, 290, 414, "lyingBack");
		layBelly.add(this);
		
		hello = new PostureButton(window, 480, 414, "hello");
		hello.add(this);
	}

	@Override
	public void updateLanguage(String lang)
	{
		
	}

	@Override
	public void updateTheme()
	{
		stand.updateTheme();
		standInit.updateTheme();
		standZero.updateTheme();
		crouch.updateTheme();
		sit.updateTheme();
		sitRelax.updateTheme();
		lay.updateTheme();
		layBelly.updateTheme();
		hello.updateTheme();
	}

	@Override
	public void update(boolean connected)
	{
		
	}

	@Override
	public void select()
	{
		
	}
}