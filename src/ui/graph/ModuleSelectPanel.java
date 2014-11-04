package ui.graph;

import ui.graph.module.ModuleInfo;
import ui.graph.module.ModuleManager;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningNode;
import ui.graph.component.VisualizationNode;
import ui.graph.component.Edge;

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


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setPreferredSize(new Dimension(800, 800));
    this.setLayout(null);

    this.nodes = new ArrayList<Node>();

    // init preprocess node
    Node n = new PreprocessNode(this.moduleManager.getPreprocess(), new Point(32, 400));
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

  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.moduleManager.setModulesToPanel(panelIndex, miningModuleID, visualizationModuleID);
  }

  /**
   * get all nodes
   * @return all nodes as List
   */
  public List<Node> getNodes() {
    return this.nodes;
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