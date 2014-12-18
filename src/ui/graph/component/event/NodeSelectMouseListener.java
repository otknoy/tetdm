package ui.graph.component.event;

import ui.Interface;
import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.Node;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;


public class NodeSelectMouseListener implements MouseListener {

  private final Interface parent;
  private final Node node;

  private List<Node> connectableNodes;


  public NodeSelectMouseListener(Interface parent, Node node) {
    this.parent = parent;
    this.node = node;
  }

  @Override public void mouseClicked(MouseEvent e) {
    node.selected(!node.isSelected());

    node.repaint();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}

  @Override public void mouseEntered(MouseEvent e) {
    Toolbox toolbox = this.parent.getToolbox();
    GraphInterface graphInterface = this.parent.getGraphInterface();

    node.highlighted(true);


    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(toolbox.getNodes());
    nodes.addAll(graphInterface.getNodes());

    this.connectableNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (node.isConnectableTo(n)) {
    	n.highlighted(true);
    	this.connectableNodes.add(n);
      }
    }

    toolbox.repaint();
    graphInterface.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Toolbox toolbox = this.parent.getToolbox();
    GraphInterface graphInterface = this.parent.getGraphInterface();

    node.highlighted(false);

    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = null;

    toolbox.repaint();
    graphInterface.repaint();
  }
}