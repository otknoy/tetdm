package ui.graph;

import java.awt.*;
import javax.swing.*;


public class GraphInterface extends JPanel {

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 650;


  public GraphInterface() {
    this.setPreferredSize(new Dimension(GraphInterface.WIDTH, GraphInterface.HEIGHT));
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}