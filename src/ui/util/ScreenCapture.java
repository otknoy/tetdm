package ui.util;

import java.awt.Graphics2D;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class ScreenCapture {

  public static BufferedImage getImage() throws AWTException {
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
    int width  = displayMode.getWidth();
    int height = displayMode.getHeight();

    Robot r = new Robot();
    BufferedImage screenCapture = r.createScreenCapture(new Rectangle(width, height));

    return screenCapture;
  }

  public static BufferedImage resize(BufferedImage img, int width, int height) {
    BufferedImage resized = new BufferedImage(width, height, img.getType());
    Graphics2D g2 = resized.createGraphics();
    g2.drawImage(img, 0, 0, width, height, null);
    g2.dispose();
    
    return resized;
  }
}