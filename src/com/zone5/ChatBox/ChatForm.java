package com.zone5.ChatBox;


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

import com.zone5.Bot.ParentBot;
import com.zone5.Bot.PostMan;

/**
 * A very simple Chat Screen, looks simpilar to any messaging app.
 * The only difference - you are talking to a bot here.
 */
public class ChatForm extends Form implements CommandListener,PostMan,ItemStateListener 
{

	private Displayable parentDisplay = null;
	private ParentBot bot = null;
	
	private TextField textField;
	private int MAX_CHARS = 100;
	private int lastItem;
	private String message;
	
	private final Command BACK;
	private final Command SEND;
		
	public ChatForm(ParentBot bot, Displayable parentDisplay) 
	{
		super("");
		
		this.bot=bot;
		this.parentDisplay=parentDisplay;
		
		BACK = new Command("BACK", Command.BACK,2);
        this.addCommand(BACK);
        
        SEND = new Command("SEND", Command.OK,1);
        this.addCommand(SEND);
        
        this.setCommandListener(this);
        this.setItemStateListener(this);
        
        constructForm();
	}
	
	private void constructForm()
	{
		textField = new TextField("", "", MAX_CHARS, TextField.ANY);
		this.append(textField);
		lastItem=this.append("");		
	}
	
	private void sendMessage(String message)
	{			
		this.message = message;
				
		this.insert(lastItem,new StringItem("", "U:> "+getMessage()));
					
		startThinking();
		
	}
	
	private void startThinking()
	{
		final PostMan postMan = this;
		
		Thread thinkThread = new Thread(new Runnable() 
		{
				public void run() 
				{
					System.out.println("Started Think Thread...");
					bot.think(postMan);
					System.out.println("Thinking Completed...");
				}
		});

		thinkThread.start();	
	}
	
	public void commandAction(Command c, Displayable d) 
	{
	
		if(c==BACK)
		{	
			bot.closeConnection();
			StartMidlet.getInstance().passControl(parentDisplay);
		}
		else if(c==SEND)
		{
			String str = textField.getString();
			textField.setString("");
    		int strlen = str.length()-1;
    		if(strlen>0)
    			sendMessage(str);
		}
		
	}

	public void post(String reply) 
	{
		if(reply==null)
			this.insert(lastItem,new StringItem("", "no reply recieved..."));
		else
			this.insert(lastItem,new StringItem("", "B:> "+reply));
	}
	
	public String getMessage() {
		return message;
	}

	public void itemStateChanged(Item item) 
	{
		if(item==textField)
    	{
    		
    		String str = textField.getString();
    		int strlen = str.length()-1;
    		if(strlen==-1)
    			return;
    		char entered = str.charAt(strlen);
  		
    		if(entered=='\n')
    		{
    			textField.setString("");
    			
    			if(strlen>0)
    			{
	    			sendMessage(str.substring(0, strlen)); 
    			}
    		}
    	}
	}

}
