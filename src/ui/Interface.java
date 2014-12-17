package ui;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.history.HistoryTree;

import java.awt.*;
import javax.swing.*;


public class Interface extends JFrame {

  private final Toolbox toolbox;
  private final GraphInterface graphInterface;

  
  public Interface() {
    this.setTitle("Tool Selector");
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.toolbox = new Toolbox();
    this.toolbox.setMaximumSize(new Dimension(Short.MAX_VALUE, (int)(0.3 * this.getHeight())));
    
    this.graphInterface = new GraphInterface();
    this.graphInterface.setMaximumSize(new Dimension(Short.MAX_VALUE, (int)(0.7 * this.getHeight())));


    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

    p.add(this.toolbox);
    p.add(this.graphInterface);

    this.add(p);
  }
}