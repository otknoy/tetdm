package ui.graph.component;

import java.awt.*;
import javax.swing.*;


public class CloseIcon implements Icon {

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

    BasicStroke wideStroke = new BasicStroke(1.25f);
    g2.setStroke(wideStroke);
    g2.setColor(Color.BLACK);

    g2.translate(x, y);
    g2.drawLine(4,  4, 11, 11);
    g2.drawLine(11, 4,  4, 11);
    g2.translate(-x, -y);
  }

  @Override public int getIconWidth() {
    return 16;
  }

  @Override public int getIconHeight() {
    return 16;
  }
}
