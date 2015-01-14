package ui.graph.event;

import tetdm.PanelState;
import ui.Interface;
import ui.graph.GraphInterface;
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

  private final Interface parent;
  private final GraphInterface graphInterface;
  private List<Node> connectableNodes;


  public NodeConnectMouseListener(Interface parent, GraphInterface graphInterface) {
    this.parent = parent;
    this.graphInterface = graphInterface;
  }

  
  @Override public void mouseClicked(MouseEvent e) {
    this.changeTools();
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

  @Override public void mouseExited(MouseEvent e) {}

  @Override public void mouseDragged(MouseEvent e) {
    Object o = e.getSource();
    if (!(o instanceof Node)) {
      return;
    }

    Node n = (Node)o;
    Nodes.connect(n, this.connectableNodes, this.threshold);

    this.graphInterface.repaint();

    this.changeTools();
  }

  @Override public void mouseMoved(MouseEvent e) {}


  private boolean changeTools() {
    List<Node[]> combinations = this.graphInterface.getNodeCombinations();
    combinations = this.extractSelectedCombinations(combinations);
    if (combinations.size() <= 0) {
      return false;
    }

    if (combinations.size() <= 1) {
      Node[] c = combinations.get(0);

      int mId  = c[1].getId();
      int vId  = c[2].getId();
      int pNum = ((ToolPanelNode)c[3]).getPanelNumber();
      PanelState ps = new PanelState(pNum, mId, vId);

      this.parent.setToolsToPanel(ps);
    } else {
      //
      System.out.println(this.parent);
    }

    return true;
  }

  private List<Node[]> extractSelectedCombinations(List<Node[]> combinations) {
    List<Node[]> selected = new ArrayList<Node[]>();
    for (Node[] c : combinations) {
      if (Nodes.checkAllSelected(Arrays.asList(c))) {
	selected.add(c);
      }
    }
    return selected;
  }
}
