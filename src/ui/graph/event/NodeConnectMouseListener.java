package ui.graph.event;

import ui.Interface;
import ui.graph.component.Node;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class NodeConnectMouseListener implements MouseListener, MouseMotionListener {

  private static final int threshold = 192;

  private final Interface parent;
  private final Node node;

  private List<Node> connectableNodes;


  public NodeConnectMouseListener(Interface parent, Node node) {
    this.parent = parent;
    this.node = node;
  }

  
  @Override public void mouseClicked(MouseEvent e) {}
  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}

  @Override public void mouseEntered(MouseEvent e) {
    List<Node> nodes = this.parent.getGraphInterface().getNodes();

    this.connectableNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (this.node.isConnectableTo(n)) {
	this.connectableNodes.add(n);
      }
    }
  }

  @Override public void mouseExited(MouseEvent e) {}

  @Override public void mouseDragged(MouseEvent e) {
    for (Node n : this.connectableNodes) {
      if (this.node.distance(n) <= this.threshold) {
	if (this.node.isConnectedTo(n)) continue;

	this.node.connectsTo(n);
      } else {
	if (!this.node.isConnectedTo(n)) continue;

	this.node.disconnectsTo(n);
      }
    }

    this.parent.getGraphInterface().repaint();
  }

  @Override public void mouseMoved(MouseEvent e) { }
}
