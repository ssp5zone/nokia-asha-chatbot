package com.zone5.Bot;

import java.util.Hashtable;

/**
 * A contract that all bots impliment.
 * Using this you can get a generic set of functionalities like start or stop the bot etc.
 */
public interface ParentBot 
{
	
	public Hashtable getModels();
	public void setModel(String modelName);
	public void initiate();
	public String think(String message);
	public void think(PostMan postMan);
	public void closeConnection();
	/*public boolean isLoading();
	public boolean isThinking();*/
}
