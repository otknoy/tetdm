package ui.graph;

import ui.graph.module.ModuleInfo;
import ui.graph.module.ModuleManager;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningNode;
import ui.graph.component.VisualizationNode;
import ui.graph.component.Edge;
import ui.graph.event.NodeSelectListener;

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
  private final NodeSelectListener nodeSelectListener;

  private Node preprocessNode;
  private List<Node> miningNodes;
  private List<Node> visNodes;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.nodeSelectListener = new NodeSelectListener(this);
    this.setPreferredSize(new Dimension(800, 800));
    this.setLayout(null);


    this.preprocessNode = new PreprocessNode(this.moduleManager.getPreprocess(), new Point(32, 540));
    this.miningNodes = new ArrayList<Node>();
    for (ModuleInfo mi : this.moduleManager.getMinings()) {
      MiningNode n = new MiningNode(mi);
      miningNodes.add(n);
    }
    this.visNodes =  new ArrayList<Node>();
    for (ModuleInfo mi : this.moduleManager.getVisualizations()) {
      VisualizationNode n = new VisualizationNode(mi);
      miningNodes.add(n);
    }

    // align nodes
    List<Node> alignNodes = new ArrayList<Node>();
    alignNodes.addAll(this.miningNodes);
    alignNodes.addAll(this.visNodes);
    this.alignNodes(alignNodes);

    // add all nodes to this panel
    this.add(preprocessNode);
    preprocessNode.selected(true);
    for (Node n : this.miningNodes) this.add(n);
    for (Node n : this.miningNodes) this.addNodeSelectListenerTo(n);
    for (Node n : this.visNodes) this.add(n);
    for (Node n : this.visNodes) this.addNodeSelectListenerTo(n);
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // drag edges
    for (Node n : this.getNodes()) {
      n.drawEdgesToNextNode(g);
    }
  }

  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.moduleManager.setModulesToPanel(panelIndex, miningModuleID, visualizationModuleID);
  }

  // public ModuleManager getModuleManager() { return this.moduleManager; }

  /**
   * add listener to Node in order to select.
   * @param n Node you want to add NodeSelectListener.
   */
  private void addNodeSelectListenerTo(Node n) {
    NodeSelectListener nsl = new NodeSelectListener(this);
    n.addMouseListener(nsl);
    n.addMouseMotionListener(nsl);
  }

  /**
   * align nodes
   * @param nodes nodes you want to align
   */
  private void alignNodes(List<Node> nodes) {
    int i = 0;
    int padding = 16;
    int columnNum = 9;
    int w = 80;
    int h = 40;
    for (Node n : nodes) {
      int x = padding + w * (i%columnNum);
      int y = padding + h * (i/columnNum);
      n.setLocation(x, y);
      i++;
    }
  }

  /**
   * get all nodes
   * @return all nodes as List
   */
  public List<Node> getNodes() {
    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.preprocessNode);
    nodes.addAll(this.miningNodes);
    nodes.addAll(this.visNodes);
    return nodes;
  }

  // /**
  //  * search Nodes which contain ModuleInformation in miList
  //  * @param miList module information list
  //  * @return search results as List
  //  */
  // public List<Node> searchNodesBy(List<ModuleInformation> miList) {
  //   List<Node> result = new ArrayList<Node>();
  //   for (Node n : this.getNodes()) {
  //     for (ModuleInformation mi : miList) {
  // 	if (mi == n.getModuleInformation()) result.add(n);
  //     }
  //   }
  //   return result;
  // }
}
