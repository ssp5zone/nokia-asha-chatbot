package com.zone5.Bot;

import java.util.Vector;

/**
 * The factory class that intializes all our bots.
 */
public class BotFactory 
{
	
	private static Vector botList;
	
	// A list of bots that we support
	private static final String PANDORA = "NN-1";
	private static final String CHATTER = "NN-2";
	private static final String JABBERWACKY = "NN-3";

	static
	{
		botList = new Vector();
		botList.addElement(PANDORA);
		botList.addElement(CHATTER);
		botList.addElement(JABBERWACKY);
	}
	
	public static ParentBot create(String botName)
	{
		
		if(botName.equals(PANDORA))
				return new PandoraBot();
		else if(botName.equals(CHATTER))
				return new CleverBot("http://www.cleverbot.com", "http://www.cleverbot.com/webservicemin", 35);
		else if(botName.equals(JABBERWACKY))
				return null;
		else
				return null;
	}

	
	public static Vector getBotList() 
	{
		return botList;
	}
	
}
