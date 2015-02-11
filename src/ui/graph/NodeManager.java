package ui.graph;

import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.event.MouseDragAndDropListener;

import java.util.List;
import java.util.ArrayList;


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
    for (Node pn : n.getPrevNodes()) {
      pn.removeNextNode(n);
    }
    for (Node nn : n.getNextNodes()) {
      nn.removePrevNode(n);
    }

    this.graphInterface.remove(n);
    this.nodes.remove(n);

    this.graphInterface.repaint();
    return n;
  }

  public List<NodeCombination> getNodeCombinations() {
    List<NodeCombination> combinations = new ArrayList<NodeCombination>();

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
	  NodeCombination c = new NodeCombination(pNode, mmNode, vmNode, tpNode);
	  combinations.add(c);
	}
      }
    }

    return combinations;
  }
}
