package org.lorenzoleonardini.naojava.ui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ComboBoxArrow extends BasicComboBoxUI
{

	public static ComboBoxUI createUI(JComponent c)
	{
		return new ComboBoxArrow();
	}

	@Override
	protected JButton createArrowButton()
	{
		return new JButton()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getWidth()
			{
				return 0;
			}
		};
	}
}