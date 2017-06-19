package org.lorenzoleonardini.naojava.ui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Switch
{
	private JLabel lbl1 = new JLabel();
	private JLabel lbl2 = new JLabel();
	private JLabel _switch;
	private JLabel switchBall;
	
	float x = 325;
	float currentX = 325;
	
	private List<SwitchEvent> evts = new ArrayList<SwitchEvent>();
	
	public enum STATE
	{
		LEFT,
		RIGHT
	}
	
	private STATE state = STATE.LEFT;

	private Window window;
	
	public Switch(Window window, String opt1, String opt2, int y, JPanel panel)
	{
		this.window = window;
		lbl1.setText(opt1);
		lbl1.setFont(Const.FONT_20);
		lbl1.setForeground(window.theme.getColor("foregroundText"));
		lbl1.setBounds(0, y, 300, 40);
		lbl1.setHorizontalAlignment(JLabel.RIGHT);
		lbl1.setVerticalAlignment(JLabel.CENTER);
		
		lbl2.setText(opt2);
		lbl2.setFont(Const.FONT_20);
		lbl2.setForeground(window.theme.getColor("foregroundText"));
		lbl2.setBounds(450, y, 300, 40);
		lbl2.setVerticalAlignment(JLabel.CENTER);
		
		_switch = new JLabel(window.theme.getIcon("switchBG"));
		_switch.setBounds(325, y, 100, 40);
		_switch.setVerticalAlignment(JLabel.CENTER);
		_switch.setHorizontalAlignment(JLabel.CENTER);
		_switch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		_switch.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				change();
				for(SwitchEvent evt : evts)
					evt.switchChanged(state);
			}
		});
		
		switchBall = new JLabel(window.theme.getIcon("switchBall"));
		switchBall.setBounds(325, y, 40, 40);
		switchBall.setVerticalAlignment(JLabel.CENTER);
		switchBall.setVerticalAlignment(JLabel.CENTER);
		switchBall.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchBall.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				change();
				for(SwitchEvent evt : evts)
					evt.switchChanged(state);
			}
		});
	}
	
	public void add(JPanel panel)
	{
		panel.add(lbl1);
		panel.add(lbl2);
		panel.add(switchBall);
		panel.add(_switch);
	}

	public void setLabels(String lbl1, String lbl2)
	{
		this.lbl1.setText(lbl1);
		this.lbl2.setText(lbl2);
	}
	
	public void addEvent(SwitchEvent evt)
	{
		evts.add(evt);
	}
	
	public void removeEvent(SwitchEvent evt)
	{
		evts.remove(evt);
	}
	
	public void change()
	{
		x = x == 325 ? 385 : 325;
		if(state == STATE.LEFT)
			state = STATE.RIGHT;
		else
			state = STATE.LEFT;
		switchBall.setLocation((int) x, (int) switchBall.getLocation().getY());
	}
	
	public void updateTheme()
	{
		_switch.setIcon(window.theme.getIcon("switchBG"));
		switchBall.setIcon(window.theme.getIcon("switchBall"));
		lbl1.setForeground(window.theme.getColor("foregroundText"));
		lbl2.setForeground(window.theme.getColor("foregroundText"));
	}
}
