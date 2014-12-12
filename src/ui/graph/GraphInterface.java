package ui.graph;

import java.awt.*;
import javax.swing.*;


public class GraphInterface extends JPanel {

  public GraphInterface() {

  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}