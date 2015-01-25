package ui.graph.event;

import ui.MainPanel;
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

  private final MainPanel mainPanel;
  private final GraphInterface graphInterface;
  private List<Node> connectableNodes;


  public NodeSelectMouseListener(MainPanel mainPanel, GraphInterface graphInterface) {
    this.mainPanel = mainPanel;
    this.graphInterface = graphInterface;
  }

  @Override public void mouseClicked(MouseEvent e) {
    Node n = (Node)e.getSource();
    if (!n.isSelected()) {
      List<Node> nodes = Nodes.selectSameTypeNodes(this.graphInterface.getNodes(), n);
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
    for (Node n2 : this.mainPanel.getToolbox().getNodes()) {
      if (n1.isConnectableTo(n2)) {
	n2.highlighted(true);
      }
    }

    this.mainPanel.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Node n = (Node)e.getSource();
    n.highlighted(false);

    for (Node cn : this.connectableNodes) {
      cn.highlighted(false);
    }
    this.connectableNodes = null;

    // unhighlight all nodes on toolbox
    for (Node cn : this.mainPanel.getToolbox().getNodes()) {
      cn.highlighted(false);
    }

    this.mainPanel.repaint();
  }
}
