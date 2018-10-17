package com.zone5.ChatBox;

import java.util.Enumeration;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import com.zone5.Bot.BotFactory;
import com.zone5.Bot.ParentBot;
import com.zone5.Utilities.Dialogs;

/**
 * Creates a touch selectable list of all the chatbots we have.
 */
public class BotList extends List implements CommandListener 
{

	private final Command EXIT;
	private final Command ACTIVATE;
	
	public ParentBot bot = null;
	
	public BotList() 
	{
		super("Network", List.EXCLUSIVE);
				
		EXIT = new Command("Exit", Command.EXIT, 2);
        this.addCommand(EXIT);

        ACTIVATE = new Command("Activate", Command.OK, 1);
        this.addCommand(ACTIVATE);
        
        this.setCommandListener(this);
        
        constructList();
        
	}
	
	// Gets a list of active bots.
	private void constructList()
	{
		Enumeration bots = BotFactory.getBotList().elements();
		
		while (bots.hasMoreElements())
			this.append((String)bots.nextElement(), null);
	
	}

	public void commandAction(Command c, Displayable d) 
	{
		if(c==EXIT)
		{
			StartMidlet.getInstance().notifyDestroyed();
			System.out.println("Existing App...");
		}
		else if(c==ACTIVATE)
		{
			// try connecting to the bot
			bot = BotFactory.create(this.getString(getSelectedIndex()));
			
			if(bot!=null)
			{
				// If connection was established, move the user to that screen
				BotMode botMode = new BotMode(bot, this);
				StartMidlet.getInstance().passControl(botMode);
			}
			else
				Dialogs.alertInfo(this, "Not yet ready");
		}
	}

}
