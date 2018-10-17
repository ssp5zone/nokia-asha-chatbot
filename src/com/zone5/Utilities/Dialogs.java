package com.zone5.Utilities;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;

import com.zone5.ChatBox.StartMidlet;

/**
 * A Dialog utility class. All the pop-ups you see while using the app are cming form here.
 */
public class Dialogs implements CommandListener 
{
	private static Alert alert;
	private static Timer timer;
    private static Image downloadImage;
    private static Image installImage;
    private static Image infoImage;
    
    private static Thread parentThread = null;
	
    private static final int TIMED = 0;
    private static final int TEXT_1_BUTTON = 1;
    private static final int TEXT_IMAGE_1_BUTTON = 2;
    private static final int TEXT_IMAGE_DETERMINATE_1_BUTTON = 3;
    private static final int TEXT_IMAGE_INDETERMINATE_1_BUTTON = 4;
    private static final int TEXT_2_BUTTONS = 5;
    private static final int TEXT_IMAGE_INDETERMINATE_3_BUTTONS = 6;
    private static final int ALERT_ERROR = 7;
    private static final int ALERT_WARNING = 8;
    private static final int ALERT_INFO = 9;
    private static final int ALERT_CONFIRMATION = 10;
    private static final int ALERT_ALARM = 11;
    
    private static final Command screenOkCommand = new Command(
            "OK", Command.SCREEN, 3);
    private static final Command screenHelpCommand = new Command(
            "Help", Command.SCREEN, 2);
    private static final Command screenCancelCommand = new Command(
            "Cancel", Command.SCREEN, 1);
    
    private static Displayable callerDisplay = null;
    
    public static void simpleAlert(Displayable caller, String message)
    {
    	callerDisplay=caller;
    	displayDialog("",message,TIMED);
    }
	
    public static void alertInfo(Displayable caller, String message)
    {
    	callerDisplay=caller;
    	displayDialog("Info",message,ALERT_INFO);
    }
    
    public static void waitInderminate(Displayable caller,Displayable next, String title,Thread t)
    {
    	System.out.println("Creating Load Alert");
    	callerDisplay=caller;
    	displayDialog(title, "", TEXT_IMAGE_INDETERMINATE_1_BUTTON);
    	
    	parentThread=t;
    	
    	final Thread innerThread = t;
    	final Displayable innerNext = next;
    	
    	System.out.println("isAlive: "+innerThread.isAlive());
    	
    	timer = new Timer();
        timer.schedule(new TimerTask() 
        				{
        					public void run() 
				            {
				              if(innerThread.isAlive())
				              {
				            	System.out.println("Waiting for 1 sec.");  
				              }
				              else
				              {
				            	  StartMidlet.getInstance().passControl(innerNext);
				            	  this.cancel();
				              }
				            }
        				}, 0, 1000);
    	
    }
    
    
    
	public static void displayDialog(String title,String message,int type)
	{
		alert = new Alert(title);
        alert.setString(message);
        
        final Gauge gauge;
        
        switch (type) 
        {
	        default:
	        case TIMED:
	            alert.setTimeout(3000);
	            break;
	        case TEXT_1_BUTTON:
	            alert.addCommand(Commands.ALERT_OK);
	            break;
	        case TEXT_IMAGE_1_BUTTON:
	            alert.setImage(infoImage);
	            alert.addCommand(Commands.ALERT_OK);
	            alert.setType(AlertType.INFO);
	            break;
	        case TEXT_IMAGE_DETERMINATE_1_BUTTON:
	            // Creates an alert with a determinate gauge
	            // The alert is dismissed when the gauge reaches finish 
	            // (10 seconds) or Cancel is pressed
	            gauge = new Gauge(null, false, 10, 0);
	            alert.setIndicator(gauge);
	            alert.setImage(downloadImage);
	            timer = new Timer();
	            // A timer handles the gauge
	            timer.schedule(new TimerTask() {
	
	                public void run() {
	                    if (gauge.getValue() == gauge.getMaxValue()) 
	                    {
	                        StartMidlet.getInstance().passControl(callerDisplay);
	                        this.cancel();
	                    }
	                    else 
	                    {
	                        gauge.setValue(gauge.getValue() + 1);
	                    }
	                }
	            }, 0, 1000);
	            alert.addCommand(Commands.ALERT_CANCEL);
	            alert.setType(AlertType.INFO);
	            break;
	        case TEXT_IMAGE_INDETERMINATE_1_BUTTON:
	            // Creates an alert with an indeterminate gauge
	            gauge = new Gauge(null, false, Gauge.INDEFINITE,
	                Gauge.CONTINUOUS_RUNNING);
	            alert.setIndicator(gauge);
	            //alert.setImage(downloadImage);
	            alert.addCommand(Commands.ALERT_CANCEL);
	            alert.setType(AlertType.INFO);
	            break;
	        case TEXT_2_BUTTONS:
	            alert.addCommand(Commands.ALERT_OK);
	            alert.addCommand(Commands.ALERT_CANCEL);
	            alert.setType(AlertType.CONFIRMATION);
	            break;
	        case TEXT_IMAGE_INDETERMINATE_3_BUTTONS:
	            // Creates an alert with an indeterminate gauge and
	            // three buttons
	            gauge = new Gauge(null, false, Gauge.INDEFINITE,
	                Gauge.CONTINUOUS_RUNNING);
	            alert.setIndicator(gauge);
	            alert.setImage(installImage);
	            alert.addCommand(screenOkCommand);
	            alert.addCommand(screenHelpCommand);
	            alert.addCommand(screenCancelCommand);
	            break;
	        case ALERT_ERROR:
	            alert.setType(AlertType.ERROR);
	            break;
	        case ALERT_WARNING:
	            alert.setType(AlertType.WARNING);
	            break;
	        case ALERT_INFO:
	            alert.setType(AlertType.INFO);
	            break;
	        case ALERT_CONFIRMATION:
	            alert.setType(AlertType.CONFIRMATION);
	            break;
	        case ALERT_ALARM:
	            alert.setType(AlertType.ALARM);
	            break;
	    }
	    alert.addCommand(Commands.BACK);

	    // For all other Alerts than TIMED and ALERT_ERROR, _WARNING, _INFO,
	    // _CONFIRMATION, _ALARM, set the automatic timeout to forever
	    if (type != TIMED && type < ALERT_ERROR) 
	    {
	        alert.setTimeout(Alert.FOREVER);
		}
	    
	    StartMidlet.getInstance().passControl(alert);
	}

	public void nsWaitInderminate(Displayable caller,Displayable next, String title,Thread t)
    {
    	waitInderminate(caller, next, title, t);
    	alert.setCommandListener(this);
    }
	
	public void commandAction(Command c, Displayable d) 
	{
		if (timer != null) 
		{
            timer.cancel();
            if(parentThread!=null)
            {
            	try {
					parentThread.interrupt();
				} catch (Exception e) {
					System.out.println("User Interrupt");;
				}
            }
            StartMidlet.getInstance().passControl(callerDisplay);
        }
		
	}

}
