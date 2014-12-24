package ui.graph.event;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.graph.component.util.Nodes;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;


public class NodeSelectMouseListener implements MouseListener {

  private final Interface parent;
  private final GraphInterface graphInterface;
  private List<Node> connectableNodes;


  public NodeSelectMouseListener(Interface parent, GraphInterface graphInterface) {
    this.parent = parent;
    this.graphInterface = graphInterface;
  }

  @Override public void mouseClicked(MouseEvent e) {
    Node n = (Node)e.getSource();
    if (!n.isSelected()) {
      List<Node> nodes = Nodes.searchSameTypeNodes(this.graphInterface.getNodes(), n);
      Nodes.selectedAll(nodes, false);
    }

    n.selected(!n.isSelected());
    this.graphInterface.repaint();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}

  @Override public void mouseEntered(MouseEvent e) {
    Node n = (Node)e.getSource();
    n.highlighted(true);

    Node n1 = n;
    List<Node> nodes = this.graphInterface.getNodes();
    this.connectableNodes = new ArrayList<Node>();
    for (Node n2 : nodes) {
      if (n1.isConnectableTo(n2)) {
    	n2.highlighted(true);
    	this.connectableNodes.add(n2);
      }
    }

    // highlight connectable nodes on toolbox
    for (Node n2 : this.parent.getToolbox().getNodesFromToolSelectPanel()) {
      if (n1.isConnectableTo(n2)) {
	System.out.println(n2);
	n2.highlighted(true);
      }
    }

    this.parent.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Node n = (Node)e.getSource();
    n.highlighted(false);

    for (Node cn : this.connectableNodes) {
      cn.highlighted(false);
    }
    this.connectableNodes = null;

    // unhighlight all nodes on toolbox
    for (Node cn : this.parent.getToolbox().getNodesFromToolSelectPanel()) {
      cn.highlighted(false);
    }

    this.parent.repaint();
  }
}