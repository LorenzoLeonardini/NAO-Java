package org.lorenzoleonardini.nao.userInterface;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lorenzoleonardini.nao.NAO;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private NAO nao;
	private LEDColors ledColors;
	private Postures postures;

	public Window(NAO nao)
	{
		this.nao = nao;
		ledColors = new LEDColors(nao);
		postures = new Postures(nao);
		setSize(800, 600);
		setTitle("NAO-Java | Lorenzo Leonardini");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);
		
		JButton leds = new JButton("Control Leds");
		leds.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leds.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ledColors.setVisible(true);
			}
		});
		leds.setBounds(0, 0, 150, 25);
		contentPane.add(leds);
		
		JButton saluta = new JButton("Saluta");
		saluta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saluta.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nao.getMotion().saluta();
			}
		});
		saluta.setBounds(400, 0, 150, 25);
		contentPane.add(saluta);
		
		JButton posture = new JButton("Postures");
		posture.setCursor(new Cursor(Cursor.HAND_CURSOR));
		posture.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				postures.setVisible(true);
			}
		});
		posture.setBounds(200, 0, 150, 25);
		contentPane.add(posture);
		
		setVisible(true);
	}
}
