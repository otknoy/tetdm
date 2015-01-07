package ui.util;

import java.awt.Graphics2D;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class ScreenCapture {

  /**
   * Get full screen capture image
   * @return full screen capture image
   */
  public static BufferedImage getImage(){
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
    int width  = displayMode.getWidth();
    int height = displayMode.getHeight();

    Robot r = null;
    try {
      r = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }

    BufferedImage screenCapture = r.createScreenCapture(new Rectangle(width, height));
    return screenCapture;
  }

  /**
   * Resize Bufferd Image
   * @param resize target image
   * @param target width
   * @param target height
   * @return resized image
   */
  public static BufferedImage resize(BufferedImage img, int width, int height) {
    BufferedImage resized = new BufferedImage(width, height, img.getType());
    Graphics2D g2 = resized.createGraphics();
    g2.drawImage(img, 0, 0, width, height, null);
    g2.dispose();
    
    return resized;
  }
}