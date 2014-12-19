package ui;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.history.HistoryTree;
import ui.graph.module.ModuleManager;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;


public class Interface extends JFrame implements MouseListener {

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
    this.toolbox = new Toolbox(this);

    p.add(this.toolbox);
    p.add(this.graphInterface);
    this.add(p, BorderLayout.CENTER);


    // manipulation panel
    JPanel mp = new JPanel();
    JButton addStickyButton = new JButton("Add sticky");
    addStickyButton.addMouseListener(this);
    mp.add(addStickyButton);
    this.add(mp, BorderLayout.SOUTH);


    this.pack();
  }


  public ModuleManager getModuleManager() { return this.moduleManager; }
  public Toolbox getToolbox() { return this.toolbox; }
  public GraphInterface getGraphInterface() { return this.graphInterface; }


  @Override
  public void mouseClicked(MouseEvent e) {
    Object src = e.getSource();
    if (src instanceof JButton) {
      JButton b = (JButton)src;
      String text = b.getText();
      if (text == "Add sticky") {
	this.graphInterface.getStickyManager().add();
	this.graphInterface.repaint();
      }
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}