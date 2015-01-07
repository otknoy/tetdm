package ui.history.component;

import ui.util.ScreenCapture;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JToolTip;


class ToolTip extends JToolTip {

  private final BufferedImage img;


  ToolTip(BufferedImage img) {
    int width  = img.getWidth()  / 2;
    int height = img.getHeight() / 2;
    this.setPreferredSize(new Dimension(width, height));
    this.img = ScreenCapture.resize(img, width, height);
  }

  @Override public void paint(Graphics g) {
    // super.paint(g);
    g.drawImage(this.img, 0, 0, this);
  }
}