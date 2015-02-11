package ui.graph.event;

import tetdm.TETDM;
import tetdm.PanelState;
import ui.graph.GraphInterface;
import ui.graph.NodeCombination;
import ui.graph.component.Node;
import ui.graph.component.ToolPanelNode;
import ui.graph.component.util.Nodes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class NodeConnectMouseListener implements MouseListener, MouseMotionListener {

  private static final int threshold = 192;

  private final TETDM tetdm;
  private final GraphInterface graphInterface;
  private List<Node> connectableNodes;


  public NodeConnectMouseListener(TETDM tetdm, GraphInterface graphInterface) {
    this.tetdm = tetdm;
    this.graphInterface = graphInterface;
    this.connectableNodes = new ArrayList<Node>();
  }

  
  @Override public void mouseClicked(MouseEvent e) {
    Object o = e.getSource();
    if (!(o instanceof Node)) {
      return;
    }

    Node n = (Node)o;
    this.changeTools(n);
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}

  @Override public void mouseEntered(MouseEvent e) {
    Object o = e.getSource();
    if (!(o instanceof Node)) {
      return;
    }

    Node n = (Node)o;
    n.highlighted(true);

    this.connectableNodes = Nodes.selectConnectableNodes(n, this.graphInterface.getNodes());
  }

  @Override public void mouseExited(MouseEvent e) {
    this.connectableNodes = new ArrayList<Node>();
  }

  @Override public void mouseDragged(MouseEvent e) {
    Object o = e.getSource();
    if (!(o instanceof Node)) {
      return;
    }

    Node n = (Node)o;
    Nodes.connect(n, this.connectableNodes, this.threshold);
    this.graphInterface.repaint();

    this.changeTools(n);
  }

  @Override public void mouseMoved(MouseEvent e) {}


  private boolean changeTools(Node n) {
    List<NodeCombination> combinations = this.graphInterface.getNodeCombinations();

    NodeCombination c = predictUserIntendedNodeCombination(n, combinations);
    if (c == null) {
      return false;
    }

    int mId  = c.mining.getId();
    int vId  = c.visualization.getId();
    int pNum = c.toolPanel.getPanelNumber();
    PanelState ps = new PanelState(pNum, mId, vId);

    this.tetdm.setToolsToPanel(ps);

    return true;
  }

  private NodeCombination predictUserIntendedNodeCombination(Node n, List<NodeCombination> combinations) {
    combinations = this.extractNodeCombinationsContainsNode(n, combinations);

    NodeCombination predicted = null;
    int maxScore = Integer.MIN_VALUE;
    for (NodeCombination c : combinations) {
      int score = c.countSelectedNode();
      if (maxScore < score) {
	maxScore = score;
	predicted = c;
      }
    }

    return predicted;
  }

  private List<NodeCombination> extractNodeCombinationsContainsNode(Node n, List<NodeCombination> combinations) {
    List<NodeCombination> extracted = new ArrayList<NodeCombination>();

    for (NodeCombination c : combinations) {
      if (c.contains(n)) {
	extracted.add(c);
      }
    }

    return extracted;
  }
}


