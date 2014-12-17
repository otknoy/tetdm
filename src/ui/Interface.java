package ui;

import ui.toolbox.Toolbox;
import ui.toolbox.event.NodeMouseListener;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.history.HistoryTree;
import ui.graph.module.ModuleManager;

import java.awt.*;
import javax.swing.*;


public class Interface extends JFrame {

  public static final String NAME = "Tool Selector";

  private final ModuleManager moduleManager;

  private final Toolbox toolbox;
  private final GraphInterface graphInterface;

  
  public Interface(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setTitle(Interface.NAME);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());


    // menu bar
    // this.add(hoge, BorderLayout.NORTH);


    // toolbox and graph interface
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

    this.graphInterface = new GraphInterface(this);

    this.toolbox = new Toolbox(this.moduleManager);
    NodeMouseListener tml = new NodeMouseListener(this.toolbox, this.graphInterface);
    for (Node n : this.toolbox.getNodes()) {
      n.addMouseListener(tml);
    }

    p.add(this.toolbox);
    p.add(this.graphInterface);
    this.add(p, BorderLayout.CENTER);


    // manipulation bar
    // this.add(hoge, BorderLayout.SOUTH);


    this.pack();
  }

  
  public Toolbox getToolbox() { return this.toolbox; }
  public GraphInterface getGraphInterface() { return this.graphInterface; }
}