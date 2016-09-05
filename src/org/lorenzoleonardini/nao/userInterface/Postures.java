package org.lorenzoleonardini.nao.userInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lorenzoleonardini.nao.NAO;
import org.lorenzoleonardini.nao.Posture;

public class Postures extends JFrame
{
	private NAO nao;
	private Posture posture;
	
	private JPanel contentPane;

	public Postures(NAO nao)
	{
		this.nao = nao;
		posture = new Posture(nao);
		setSize(155, 228);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		addButton(0, Posture.SIT_RELAX);
		addButton(1, Posture.SIT);
		addButton(2, Posture.CROUCH);
		addButton(3, Posture.STAND);
		addButton(4, Posture.STAND_INIT);
		addButton(5, Posture.STAND_ZERO);
		addButton(6, Posture.LYING_BACK);
		addButton(7, Posture.LYING_BELLY);
	}
	
	private void addButton(int pos, String postureName)
	{
		JButton button = new JButton(postureName);
		button.setBounds(0, pos * 25, 150, 25);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				posture.changePosture(postureName);
			}
		});
		contentPane.add(button);
	}
}
