package ui.graph.component;

import java.awt.*;
import javax.swing.*;


public class CloseIcon implements Icon {

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Graphics2D g2 = (Graphics2D)g;

    BasicStroke wideStroke = new BasicStroke(1.0f);
    g2.setStroke(wideStroke);
    g.setColor(Color.BLACK);

    g.translate(x, y);
    g.drawLine(4,  4, 11, 11);
    g.drawLine(4,  5, 10, 11);
    g.drawLine(5,  4, 11, 10);
    g.drawLine(11, 4,  4, 11);
    g.drawLine(11, 5,  5, 11);
    g.drawLine(10, 4,  4, 10);
    g.translate(-x, -y);
  }

  @Override public int getIconWidth() {
    return 16;
  }

  @Override public int getIconHeight() {
    return 16;
  }
}
