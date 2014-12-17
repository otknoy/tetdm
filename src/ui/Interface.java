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

    this.setLayout(new BorderLayout());


    // menu bar
    // this.add(hoge, BorderLayout.NORTH);


    // toolbox and graph interface
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

    this.graphInterface = new GraphInterface(this);
    this.toolbox = new Toolbox(this);
    p.add(this.toolbox);
    p.add(this.graphInterface);
    this.add(p, BorderLayout.CENTER);


    // manipulation bar
    // this.add(hoge, BorderLayout.SOUTH);


    this.pack();
  }
}