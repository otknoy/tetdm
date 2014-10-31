package ui.graph.event;

import ui.graph.ModuleSelectPanel;
import ui.graph.component.Node;
import ui.graph.component.VisualizationNode;
import ui.graph.component.Edge;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseEvent;


public class NodeSelectListener implements MouseListener, MouseMotionListener {

  public static final int DISTANCE_THRESHOLD = 128;
  
  private ModuleSelectPanel parent;

  
  public NodeSelectListener(ModuleSelectPanel parent) {
    this.parent = parent;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    if (!(e.getSource() instanceof Node)) return;
    Node node = (Node)e.getSource();

    if ((node instanceof VisualizationNode) &&
	((VisualizationNode)node).hasPathFromPreprocessNode()) {
      this.parent.setModulesToPanel(0, node.getPreviousNode().getID(), node.getID());
    }

    node.selected(!node.isSelected());
    node.repaint();
  }
  
  @Override
  public void mousePressed(MouseEvent e) {}
  
  @Override
  public void mouseReleased(MouseEvent e) {}
  
  @Override
  public void mouseEntered(MouseEvent e) {
    if (!(e.getSource() instanceof Node)) return;
    Node node = (Node)e.getSource();
    node.highlighted(true);

    List<Node> connectableNodes = new ArrayList<Node>();
    for (Node n : this.parent.getNodes()) {
      if (node.isConnectableTo(n)) connectableNodes.add(n);
    }

    for (Node n : connectableNodes) n.highlighted(true);
  }
  
  @Override
  public void mouseExited(MouseEvent e) {
    if (!(e.getSource() instanceof Node)) return;
    Node node = (Node)e.getSource();
    node.highlighted(false);

    List<Node> connectableNodes = new ArrayList<Node>();
    for (Node n : this.parent.getNodes()) {
      if (node.isConnectableTo(n)) connectableNodes.add(n);
    }

    for (Node n : connectableNodes) n.highlighted(false);
  }
  
  @Override
  public void mouseMoved(MouseEvent e) {}
  
  @Override
  public void mouseDragged(MouseEvent e) {
    if (!(e.getSource() instanceof Node)) return;
    Node node = (Node)e.getSource();

    List<Node> connectableNodes = new ArrayList<Node>();
    for (Node n : this.parent.getNodes()) {
      if (node.isConnectableTo(n)) connectableNodes.add(n);
    }

    for (Node n : connectableNodes) {
      if (node.isConnectedTo(n)) continue;
      if (!n.isSelected()) continue;
      if (node.distance(n) <= DISTANCE_THRESHOLD) {
	Node n1 = node;
	Node n2 = n;
	if (node.isConnectableToPrevious(n)) {
	  n1 = n;
	  n2 = node;
	}

	n1.addNextNode(n2);
	n2.setPreviousNode(n1);

	// System.out.println("connected");
	// System.out.println(n1);
	// System.out.println(n2);
	// System.out.println(node instanceof VisualizationNode);

	if ((node instanceof VisualizationNode) &&
	    ((VisualizationNode)node).hasPathFromPreprocessNode()) {
	  this.parent.setModulesToPanel(0, n1.getID(), n2.getID());
	}
      }
    }
    this.parent.repaint();
  }
}
