package com.zone5.Logo;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A Canvas based class for displaying the splash screen image.
 */
public class SplashScreen extends Canvas {
    // Members
    private Image splashScreenImage;
    private int backgroundColor;
    private int centerX;
    private int centerY;

    /**
     * Constructor.
     * 
     * @param imageUri The splash screen image URI.
     * @param backgroundColor The background color to fill the parts of the
     * screen that are not covered by the image.
     */
    
    
    public SplashScreen(final String imageUri, final int backgroundColor) {
        super();
        this.setFullScreenMode(true);
        this.backgroundColor = backgroundColor;
        
        try {
            splashScreenImage = Image.createImage(imageUri);
        }
        catch (IOException e) {
            System.out.println("Failed to load the splash screen image: " + e.toString());
        }
        
        if (splashScreenImage != null) {
            // Calculate the center position for the image
            centerX = (getWidth() - splashScreenImage.getWidth()) / 2;
            centerY = (getHeight() - splashScreenImage.getHeight()) / 2;
        }
    }

    /**
     * Paints the splash screen.
     * 
     * @see javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
     */
    protected void paint(Graphics graphics) {
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        if (splashScreenImage != null) {
            graphics.drawImage(splashScreenImage, centerX, centerY,
                    Graphics.TOP | Graphics.LEFT);
        }
    }
}
