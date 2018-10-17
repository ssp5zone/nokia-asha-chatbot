package com.zone5.ChatBox;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.zone5.Logo.SplashLoader;

/**
 * The starting point of App.
 */
public class StartMidlet extends MIDlet 
{
	private static StartMidlet instance=null;
	
	private BotList botList = null;
	
	public static StartMidlet getInstance()
	{
		return instance;
	}

	public StartMidlet() 
	{
		instance = this;
		botList = new BotList();
	}

	/**
	 * The over-ridden method. Called once the app starts.
	 */
	protected void startApp() throws MIDletStateChangeException 
	{
		// get the current display.
		Displayable current = Display.getDisplay(this).getCurrent();
        
		if (current == null && botList !=null) 
        {	
			// show logo screen
        	new SplashLoader(botList);
        }
	
	}
	
	// A generic utility that is used by all screens to move back and forth between themselves
	public void passControl(Displayable displayable)
	{
        Display.getDisplay(this).setCurrent(displayable);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {}

	protected void pauseApp() {	}



}
