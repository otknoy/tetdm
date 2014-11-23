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
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;


public class GraphPanel extends JPanel implements Cloneable {

  static final String NAME = "Module Selector";
  private final ModuleManager moduleManager;

  private List<PreprocessNode> preprocessNodes;
  private List<MiningModuleNode> miningModuleNodes;
  private List<VisualizationModuleNode> visualizationModuleNodes;

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 800;


  public GraphPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setPreferredSize(new Dimension(GraphPanel.WIDTH, GraphPanel.HEIGHT));
    this.setLayout(null);

    this.preprocessNodes = new ArrayList<PreprocessNode>();
    this.miningModuleNodes = new ArrayList<MiningModuleNode>();
    this.visualizationModuleNodes = new ArrayList<VisualizationModuleNode>();

    List<PreprocessNode> pNodes = this.createPreprocessNodes();
    List<MiningModuleNode> mmNodes = this.createMiningModuleNodes(this.moduleManager);
    List<VisualizationModuleNode> vmNodes = this.createVisualizationModuleNodes(this.moduleManager);

    this.addNodes(pNodes);
    this.addNodes(mmNodes);
    this.addNodes(vmNodes);

    Nodes.alignNodes(new Rectangle(      0, 0, WIDTH/2, HEIGHT/2), mmNodes);
    Nodes.alignNodes(new Rectangle(WIDTH/2, 0, WIDTH/2, HEIGHT/2), vmNodes);


    // sticky experiment
    Sticky sticky = new Sticky("hogehogehoge おおつ");
    sticky.setLocation(200, 200);
    this.add(sticky);

    sticky = new Sticky("hogehogehoge おおつかなおや");
    sticky.setLocation(500, 500);
    this.add(sticky);
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    for (Node n : this.getNodes()) {
      n.drawEdgesToNextNodes(g);
    }
  }

  @Override
  public GraphPanel clone() {
    GraphPanel gp;
    try {
      gp = (GraphPanel) super.clone();
    } catch (CloneNotSupportedException ce) {
      throw new RuntimeException();/* 適切なエラー処理 */
    }

    return gp;
  }


  private List<PreprocessNode> createPreprocessNodes() {
    List<PreprocessNode> preprocessNodes = new ArrayList<PreprocessNode>();
    PreprocessNode n = new PreprocessNode(new Point(this.WIDTH/10, this.HEIGHT/2));
    preprocessNodes.add(n);
    return preprocessNodes;
  }

  private List<MiningModuleNode> createMiningModuleNodes(ModuleManager moduleManager) {
    List<MiningModuleNode> mmNodes = new ArrayList<MiningModuleNode>();
    List<ModuleData> mmdl = moduleManager.getMiningModuleDataList();
    for (ModuleData md : mmdl) {
      MiningModuleNode n = new MiningModuleNode(md);
      mmNodes.add(n);
    }
    return mmNodes;
  }

  private List<VisualizationModuleNode> createVisualizationModuleNodes(ModuleManager moduleManager) {
    List<VisualizationModuleNode> vmNodes = new ArrayList<VisualizationModuleNode>();
    List<ModuleData> vmdl = moduleManager.getVisualizationModuleDataList();
    for (ModuleData md : vmdl) {
      VisualizationModuleNode n = new VisualizationModuleNode(md);
      vmNodes.add(n);
    }
    return vmNodes;
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

  /**
   * Set mining module and visualization module to a panel
   * @param index of panel
   * @param mining module node
   * @param visualization module node
   */
  public void setModulesToPanel(int panelIndex, MiningModuleNode mmn, VisualizationModuleNode vmn) {
    this.setModulesToPanel(panelIndex, mmn.getId(), vmn.getId());
  }


  public List<Node> getNodes() {
    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(this.preprocessNodes);
    nodes.addAll(this.miningModuleNodes);
    nodes.addAll(this.visualizationModuleNodes);
    return nodes;
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

    if (node instanceof PreprocessNode) {
      this.preprocessNodes.add((PreprocessNode)node);
    } else if (node instanceof MiningModuleNode) {
      this.miningModuleNodes.add((MiningModuleNode)node);
    } else if (node instanceof VisualizationModuleNode) {
      this.visualizationModuleNodes.add((VisualizationModuleNode)node);
    }

    this.add(node);
  }

  public void addNodes(List<? extends Node> nodes) {
    for (Node n : nodes) {
      this.addNode(n);
    }
  }
  
  public void removeNode(Node node) {
    if (node instanceof PreprocessNode) {
      this.preprocessNodes.remove((PreprocessNode)node);
    } else if (node instanceof MiningModuleNode) {
      this.miningModuleNodes.remove((MiningModuleNode)node);
    } else if (node instanceof VisualizationModuleNode) {
      this.visualizationModuleNodes.remove((VisualizationModuleNode)node);
    }

    this.remove(node);
  }

  public void removeNodes(List<Node> nodes) {
    for (Node n : nodes) {
      this.removeNode(n);
    }
  }

  public void addSticky(Sticky s) { this.add(s); }
  public void removeSticky(Sticky s) { this.remove(s); }


  /**
   * Find connectable nodes from all nodes.
   * @param base node
   * @return nodes which are connectable to Node n1
   */
  public List<Node> findConnectableNodes(Node n1) {
    List<Node> connectableNodes = new ArrayList<Node>();
    for (Node n2 : this.getNodes()) {
      if (n1.isConnectableTo(n2)) {
	connectableNodes.add(n2);
      }
    }
    return connectableNodes;
  }

  /**
   * Get combinations of preprocess node, mining module node, and visualization module node
   * @return combination list
   */
  public List<Node[]> getNodeCombinations() {
    List<Node[]> combinations = new ArrayList<Node[]>();
    for (Node n1 : this.preprocessNodes) {
      for (Node n2 : n1.getNextNodes()) {
	for (Node n3 : n2.getNextNodes()) {
	  Node[] c = {n1, n2, n3};
	  combinations.add(c);
	}
      }
    }
    return combinations;
  }

  /**
   * Extract combinations which have connected all nodes
   * @param node combinations
   * @return filtered node combinations
   */
  public List<Node[]> extractConnectedCombinations(List<Node[]> combinations) {
    List<Node[]> filtered = new ArrayList<Node[]>();
    for (Node[] c : combinations) {
      Node n1 = c[0], n2 = c[1], n3 = c[2];
      if (!checkNodeTypes(n1, n2, n3)) {
	continue;
      }

      boolean n1n2 = n1.getNextNodes().contains(n2);
      boolean n2n3 = n2.getNextNodes().contains(n3);
      if (n1n2 && n2n3) {
	filtered.add(c);
      }
    }
    return filtered;
  }

  private boolean checkNodeTypes(Node n1, Node n2, Node n3) {
    return ((n1 instanceof PreprocessNode) &&
	    (n2 instanceof MiningModuleNode) &&
	    (n3 instanceof VisualizationModuleNode));
  }
}