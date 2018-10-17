package com.zone5.Utilities;

import javax.microedition.lcdui.Command;

/**
 * A list of button that helps user navigate between selections and screen on mobile.
 */
public class Commands 
{

	    public static final Command SELECT =
	        new Command("Select", Command.ITEM, 1);
	    public static final Command LIST_SELECT =
	        new Command("Select", Command.OK, 1);
	    public static final Command BACK =
	        new Command("Back", Command.BACK, 1);
	    public static final Command EXIT =
	        new Command("Back", Command.EXIT, 1);
	    public static final Command INFORMATION =
	        new Command("Information", Command.SCREEN, 3);
	    public static final Command INFORMATION_BACK =
	        new Command("Back", Command.BACK, 1);
	    public static final Command OK = new Command("OK", Command.OK, 1);
	    public static final Command DONE = new Command("Done", Command.OK, 1);
	    public static final Command ALERT_HELP =
	        new Command("Help", Command.HELP, 1);
	    public static final Command CANCEL =
	        new Command("Cancel", Command.CANCEL, 1);
	    public static final Command ALERT_OK = new Command("OK", Command.OK, 1);
	    public static final Command ALERT_CANCEL =
	        new Command("Cancel", Command.CANCEL, 1);
	    public static final Command ALERT_CONTINUE =
	        new Command("Continue", Command.OK, 1);
	    public static final Command ALERT_SAVE_YES =
	        new Command("Yes", Command.OK, 1);
	    public static final Command ALERT_SAVE_NO =
	        new Command("No", Command.HELP, 1);
	    public static final Command ALERT_SAVE_BACK =
	        new Command("Back", Command.CANCEL, 1);
	    public static final Command EDIT =
	        new Command("Edit", Command.SCREEN, 1);
	    public static final Command OK_SCREEN =
	        new Command("OK", Command.SCREEN, 2);
	    public static final Command DELETE_SCREEN =
	        new Command("Delete", Command.SCREEN, 2);

	


}
