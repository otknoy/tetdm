package ui.graph;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.ToolPanelNode;
import ui.graph.component.event.MouseDragAndDropListener;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JComponent;


public class NodeManager {

  private GraphInterface graphInterface;
  private List<Node> nodes;

  
  public NodeManager(GraphInterface graphInterface) {
    this.graphInterface = graphInterface;
    this.nodes = new ArrayList<Node>();
  }

  public List<Node> getNodes() { return this.nodes; }

  public void add(Node n) {
    this.nodes.add(n);
    this.graphInterface.add(n);

    // drag and drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(n);
    n.addMouseListener(mddl);
    n.addMouseMotionListener(mddl);

    this.graphInterface.repaint();
  }

  public Node remove(Node n) {
    this.graphInterface.remove(n);
    this.nodes.remove(n);

    this.graphInterface.repaint();
    return n;
  }

  public List<Node[]> getNodeCombinations() {
    List<Node[]> combinations = new ArrayList<Node[]>();

    Node pNode = null;
    for (Node n : this.nodes) {
      if (n instanceof PreprocessNode) {
	pNode = n;
      }
    }
    if (pNode == null) {
      return combinations;
    }

    for (Node mmNode : pNode.getNextNodes()) {
      for (Node vmNode : mmNode.getNextNodes()) {
	for (Node tpNode : vmNode.getNextNodes()) {
	  Node[] combination = {pNode, mmNode, vmNode, tpNode};
	  combinations.add(combination);
	}
      }
    }

    return combinations;
  }
}