package ui;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.history.HistoryTree;
import ui.graph.module.ModuleManager;

import java.awt.*;
import javax.swing.*;


public class Interface extends JFrame {

  public static final String NAME = "Tool Selector";

  public final ModuleManager moduleManager;

  public final Toolbox toolbox;
  public final GraphInterface graphInterface;

  
  public Interface(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setTitle(Interface.NAME);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.graphInterface = new GraphInterface(this);
    this.toolbox = new Toolbox(this);

    
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

    p.add(this.toolbox);
    p.add(this.graphInterface);

    this.add(p);
    this.pack();
  }
}