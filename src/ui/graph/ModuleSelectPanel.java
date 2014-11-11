package ui.graph;

import ui.graph.module.*;
import ui.graph.module.*;
import ui.graph.component.*;
import ui.graph.component.event.NodeEventListener;
import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.util.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;


public class ModuleSelectPanel extends JPanel {

  static final String NAME = "Module Selector";
  private final ModuleManager moduleManager;

  private List<Node> nodes;
  private Node preprocessNode;

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 800;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setPreferredSize(new Dimension(800, 800));
    this.setLayout(null);

    this.nodes = new ArrayList<Node>();

    // init preprocess node
    Node n = new PreprocessNode(new Point(this.WIDTH/10, this.HEIGHT/2));
    this.preprocessNode = n;
    this.addNode(n);

    // init mining module node
    List<Node> mmNodes = new ArrayList<Node>();
    List<ModuleData> mdl = moduleManager.getMiningModuleDataList();
    for (ModuleData md : mdl) {
      n = new MiningModuleNode(md);
      mmNodes.add(n);
    }
    this.addNodes(mmNodes);

    // init visualization module node
    List<Node> vmNodes = new ArrayList<Node>();
    List<ModuleData> vdl = moduleManager.getVisualizationModuleDataList();
    for (ModuleData md : vdl) {
      n = new VisualizationModuleNode(md);
      vmNodes.add(n);
    }
    this.addNodes(vmNodes);

    Nodes.alignNodes(new Rectangle(      0, 0, WIDTH/2, HEIGHT/2), mmNodes);
    Nodes.alignNodes(new Rectangle(WIDTH/2, 0, WIDTH/2, HEIGHT/2), vmNodes);
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    for (Node n : this.nodes) {
      n.drawEdgesToNextNodes(g);
    }
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
    // select event
    NodeEventListener nel = new NodeEventListener(this, node);
    node.addMouseListener(nel);
    node.addMouseMotionListener(nel);

    // drag & drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(node);
    node.addMouseListener(mddl);
    node.addMouseMotionListener(mddl);

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


  /**
   * Find connectable nodes from all nodes.
   * @param base node
   * @return nodes which are connectable to Node n1
   */
  public List<Node> findConnectableNodes(Node n1) {
    List<Node> connectableNodes = new ArrayList<Node>();
    for (Node n2 : this.nodes) {
      if (n1.isConnectableTo(n2)) {
	connectableNodes.add(n2);
      }
    }
    return connectableNodes;
  }

  /**
   * get combinations of preprocess node, mining module node, and visualization module node
   * @return combination list
   */
  public List<Node[]> getNodeCombinations() {
    List<Node[]> combinations = new ArrayList<Node[]>();
    Node n1 = this.preprocessNode;
    for (Node n2 : n1.getNextNodes()) {
      for (Node n3 : n2.getNextNodes()) {
	Node[] c = {n1, n2, n3};
	combinations.add(c);
      }
    }
    return combinations;
  }
}