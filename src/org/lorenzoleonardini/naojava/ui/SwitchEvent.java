package org.lorenzoleonardini.naojava.ui;

import org.lorenzoleonardini.naojava.ui.Switch.STATE;

public abstract class SwitchEvent
{
	public abstract void switchChanged(STATE state);
}
