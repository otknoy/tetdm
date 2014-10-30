package ui.graph;

import ui.graph.module.ModuleInformation;
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
  private List<Node> visualizationNodes;


  public ModuleSelectPanel(ModuleManager moduleManager,
			   Node preprocessNode,
			   List<Node> miningNodes,
			   List<Node> visualizationNodes,
			   boolean align) {
    this.moduleManager = moduleManager;
    this.preprocessNode     = preprocessNode;
    this.miningNodes        = miningNodes;
    this.visualizationNodes = visualizationNodes;

    this.nodeSelectListener = new NodeSelectListener(this);
    this.setPreferredSize(new Dimension(800, 800));
    this.setLayout(null);

    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(this.miningNodes);
    nodes.addAll(this.visualizationNodes);

    // align nodes
    if (align) {
      this.alignNodes(nodes);
    }

    // add all nodes to this panel
    nodes.add(this.preprocessNode);
    for (Node n : nodes) this.add(n);    
    for (Node n : nodes) this.addNodeSelectListenerTo(n);

    this.preprocessNode.selected(true);
  }

  public ModuleSelectPanel(ModuleManager moduleManager) {
    this(moduleManager, new PreprocessNode(moduleManager.getPreprocess()),
	 ModuleSelectPanel.createNodesBasedOn(moduleManager.getMinings()),
	 ModuleSelectPanel.createNodesBasedOn(moduleManager.getVisualizations()), true);
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

  @Override
  public ModuleSelectPanel clone() {
    Node preprocessNode = (PreprocessNode)this.preprocessNode.clone();
    List<Node> miningNodes = new ArrayList<Node>();
    for (Node n : this.miningNodes) miningNodes.add((MiningNode)n.clone());
    List<Node> visualizationNodes = new ArrayList<Node>();
    for (Node n : this.visualizationNodes) visualizationNodes.add((VisualizationNode)n.clone());

    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.preprocessNode);
    nodes.addAll(this.miningNodes);
    nodes.addAll(this.visualizationNodes);
    List<Node> clones = new ArrayList<Node>();
    clones.add(preprocessNode);
    clones.addAll(miningNodes);
    clones.addAll(visualizationNodes);

    for (int i = 0; i < nodes.size(); i++) {
      Node node = nodes.get(i);
      Node clone = clones.get(i);

      Node previous = node.getPreviousNode();
      if (previous != null) {
    	int j = nodes.indexOf(previous);
    	clone.setPreviousNode(clones.get(j));
      }

      for (Node n : node.getNextNodes()) {
      	int j = nodes.indexOf(n);
      	clone.addNextNode(clones.get(j));
      }
    }

    return new ModuleSelectPanel(this.moduleManager, preprocessNode,
				 miningNodes, visualizationNodes, false);
  }
    

  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.moduleManager.setModulesToPanel(panelIndex, miningModuleID, visualizationModuleID);
  }

  public ModuleManager getModuleManager() { return this.moduleManager; }

  /**
   * create nodes based on module information
   * @param miList module information list
   * @return created nodes as List
   */
  static List<Node> createNodesBasedOn(List<ModuleInformation> miList) {
    List<Node> nodes = new ArrayList();
    for (ModuleInformation mi : miList) {
      Node node = null;
      if (mi.getType() == ModuleInformation.TYPE_MINING) {
	node = new MiningNode(mi);
      } else if (mi.getType() == ModuleInformation.TYPE_VISUALIZATION) {
	node = new VisualizationNode(mi);
      }

      nodes.add(node);
    }
    return nodes;
  }

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
   * get preprocess, mining and visualization nodes
   * @return all nodes as List
   */
  public List<Node> getNodes() {
    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.preprocessNode);
    nodes.addAll(this.miningNodes);
    nodes.addAll(this.visualizationNodes);
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
