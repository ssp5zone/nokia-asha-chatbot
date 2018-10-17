package com.zone5.Logo;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Displayable;

import com.zone5.ChatBox.StartMidlet;

/**
 * A Logo screen, once the app starts.
 */
public class SplashLoader 
{
	private static final String SPLASH_SCREEN_IMAGE_URI = "/splash/logo1.1.png";
    private static final int SPLASH_SCREEN_BG_COLOR = 0x000000;
    private static final long SPLASH_SCREEN_DURATION = 3000; // Milliseconds


    private Displayable nextView = null;
    private Timer splashScreenTimer;
    private TimerTask splashScreenTimerTask;
    
    public SplashLoader(Displayable nextDisplay)
    {
    	this.nextView=nextDisplay;
    	
    	SplashScreen splashScreen = new SplashScreen(SPLASH_SCREEN_IMAGE_URI, SPLASH_SCREEN_BG_COLOR);
        StartMidlet.getInstance().passControl(splashScreen);
        
        // Set the timer to show the main view
        splashScreenTimer = new Timer();
        splashScreenTimerTask = new SplashScreenTimerTask();
        splashScreenTimer.schedule(splashScreenTimerTask, SPLASH_SCREEN_DURATION);
    	
    }
    
    
    
    private class SplashScreenTimerTask extends TimerTask {
        public void run() {
            System.out.println("SplashScreenTimerTask.run()");
            
            // After the timer completes, move to next screen - Showing list of bots
            if (nextView != null) {
            	StartMidlet.getInstance().passControl(nextView);
                splashScreenTimer = null;
                splashScreenTimerTask = null;
            }
        }
    }

}
