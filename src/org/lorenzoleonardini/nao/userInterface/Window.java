package org.lorenzoleonardini.nao.userInterface;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lorenzoleonardini.nao.NAO;

import com.aldebaran.qi.CallError;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public Window(final NAO nao)
	{
		setSize(800, 600);
		setTitle("NAO-Java | Lorenzo Leonardini");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);

		JColorChooser cc = new JColorChooser();
		AbstractColorChooserPanel[] panels = cc.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels)
		{
			if (accp.getDisplayName().equals("HSV"))
			{
				accp.setBounds(0, 0, 300, 130);
				accp.getColorSelectionModel().addChangeListener(new ChangeListener()
				{
					public void stateChanged(ChangeEvent e)
					{
						try
						{
							nao.leds.fadeRGB("FaceLeds", cc.getColor().getRGB(), 0f);
						}
						catch (CallError | InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
				});
				contentPane.add(accp);
			}
		}
		
		JColorChooser cc2 = new JColorChooser();
		AbstractColorChooserPanel[] panels2 = cc2.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels2)
		{
			if (accp.getDisplayName().equals("HSV"))
			{
				accp.setBounds(490, 0, 300, 130);
				accp.getColorSelectionModel().addChangeListener(new ChangeListener()
				{
					public void stateChanged(ChangeEvent e)
					{
						try
						{
							nao.leds.fadeRGB("FeetLeds", cc2.getColor().getRGB(), 0f);
						}
						catch (CallError | InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}
				});
				contentPane.add(accp);
			}
		}

		setVisible(true);
	}
}
