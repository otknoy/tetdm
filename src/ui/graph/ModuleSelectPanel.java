package ui.graph;

import ui.graph.module.*;
import ui.graph.module.*;
import ui.graph.component.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Component;
import java.util.Arrays;
import java.awt.Dimension;


public class ModuleSelectPanel extends JPanel {

  static final String NAME = "Module Selector";
  private final ModuleManager moduleManager;

  private List<Node> nodes;

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 800;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setPreferredSize(new Dimension(800, 800));
    this.setLayout(null);

    this.nodes = new ArrayList<Node>();

    // init preprocess node
    Node n = new PreprocessNode(new Point(this.WIDTH/10, this.HEIGHT/2));
    this.addNode(n);
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // drag edges
    // for (Node n : this.nodes) {
    //   n.drawEdgesToNextNode(g);
    // }
  }

  /**
   * Set mining module and visualization module to a panel
   * @param index of panel
   * @param mining module ID
   * @param visualization module ID
   */
  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.moduleManager.setModulesToPanel(panelIndex, miningModuleID, visualizationModuleID);
  }


  public void addNode(Node node) {
    this.nodes.add(node);
    this.add(node);
  }

  public void addNodes(List<Node> nodes) {
    for (Node n : nodes) {
      this.addNode(n);
    }
  }
  
  public void removeNode(Node node) {
    this.nodes.remove(node);
    this.remove(node);
  }

  public void removeNodes(List<Node> nodes) {
    for (Node n : nodes) {
      this.removeNode(n);
    }
  }
}