package com.zone5.ChatBox;

import java.util.Enumeration;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import com.zone5.Bot.ParentBot;
import com.zone5.Utilities.Dialogs;

/**
 * Each bot has there own settings and personalities.
 * This screen provides the selection for the same.
 */
public class BotMode extends List implements CommandListener 
{

	private Displayable parentDisplay = null;
	private ParentBot bot = null;
	
	private final Command BACK;
	private final Command LOAD;
	
	
	public BotMode(ParentBot bot, Displayable parentDisplay) 
	{
		super("Model", List.EXCLUSIVE);
		
		this.parentDisplay=parentDisplay;
		this.bot=bot;
		
		BACK = new Command("BACK", Command.BACK,2);
        this.addCommand(BACK);
        
        LOAD = new Command("LOAd", Command.OK, 1);
        this.addCommand(LOAD);
        
        this.setCommandListener(this);
        
        constructList();
        
	}
	
	private void constructList()
	{
		Enumeration models = bot.getModels().keys();
		
		while (models.hasMoreElements())
		{
			String element = (String)models.nextElement(); 
			
			if(element.equals("Generic"))
				this.setSelectedIndex(this.append(element, null), true);
			else
				this.append(element, null);
		}
	}
	
	/**
	 * This little guy is the one that awakes the dragon..
	 */
	private void loadBot()
	{
		// This is the screen where the user would be naviagted if load was successful	
		ChatForm chatForm = new ChatForm(bot, this);
		
		String modelName = this.getString(getSelectedIndex());
		bot.setModel(modelName);
		
		// A seperate thread so that the app doesn't hang while showing the spinner.
		Thread initiateThread = new Thread(new Runnable() 
								{
		     						public void run() 
		     						{
		     							System.out.println("Started Thread...");
		     							bot.initiate();
		     							System.out.println("Task Completed...");
		     						}
								});
		
		initiateThread.start();
		Dialogs dialog = new Dialogs();
		dialog.nsWaitInderminate(this,chatForm, "Loading...",initiateThread);
	}

	public void commandAction(Command c, Displayable d) 
	{
		if(c==BACK)
		{
			StartMidlet.getInstance().passControl(parentDisplay);
		}
		else if(c==LOAD)
		{	
			this.loadBot();			
		}
	}

}
