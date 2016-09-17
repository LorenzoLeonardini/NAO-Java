package org.lorenzoleonardini.nao.userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lorenzoleonardini.nao.Main;

public class IP extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextPane ip;
	
	public IP()
	{
		setSize(200, 100);
		setLocationRelativeTo(null);
		setTitle("Select IP");
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
		setContentPane(contentPane);
		
		JLabel label = new JLabel("Inserisci IP NAO");
		label.setBounds(0, 0, 185, 20);
		label.setHorizontalAlignment(SwingUtilities.CENTER);
		contentPane.add(label);
		
		ip = new JTextPane();
		ip.setBounds(0, 20, 185, 20);
		contentPane.add(ip);
		
		JButton confirm = new JButton("Conferma");
		confirm.setBounds(0, 40, 185, 20);
		confirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				new Main(ip.getText());
			}
		});
		contentPane.add(confirm);
		
		setVisible(true);
	}
}
