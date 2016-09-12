package org.lorenzoleonardini.nao.userInterface;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lorenzoleonardini.nao.NAO;

import com.aldebaran.qi.CallError;

public class LEDColors extends JFrame
{
	private static final long serialVersionUID = 1L;

	private NAO nao;
	private JPanel contentPane;
	
	public LEDColors(NAO nao)
	{
		this.nao = nao;
		setSize(615, 490);
		setTitle("LED Colors | NAO-Java | Lorenzo Leonardini");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
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
		
		addColorPicker(0, 0, "FaceLeds");
		addColorPicker(300, 0, "BrainLeds");
		addColorPicker(0, 150, "EarLeds");
		addColorPicker(300, 150, "ChestLeds");
		addColorPicker(150, 300, "FeetLeds");
	}
	
	private void addColorPicker(int x, int y, String LED)
	{
		JLabel label = new JLabel(LED);
		label.setBounds(x, y, 300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.green);
		contentPane.add(label);
		
		JColorChooser cc = new JColorChooser();
		AbstractColorChooserPanel[] panels = cc.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels)
		{
			if (accp.getDisplayName().equals("HSV"))
			{
				accp.setBounds(x, y + 20, 300, 130);
				accp.getColorSelectionModel().addChangeListener(new ChangeListener()
				{
					public void stateChanged(ChangeEvent e)
					{
						try
						{
							nao.leds.fadeRGB(LED, cc.getColor().getRGB(), 0f);
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
	}
}
