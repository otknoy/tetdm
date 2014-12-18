package ui.toolbox.event;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.graph.component.ModuleNode;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class NodeMouseListener implements MouseListener {

  private final Toolbox toolbox;
  private final GraphInterface graphInterface;

  private List<Node> connectableNodes;


  public NodeMouseListener(Toolbox toolbox, GraphInterface graphInterface) {
    this.toolbox = toolbox;
    this.graphInterface = graphInterface;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    this.graphInterface.addNode(n.clone());
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }


  @Override public void mouseEntered(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(true);

    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(this.toolbox.getNodes());
    nodes.addAll(this.graphInterface.getNodes());

    this.connectableNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (node.isConnectableTo(n)) {
	n.highlighted(true);
	this.connectableNodes.add(n);
      }
    }

    this.toolbox.repaint();
    this.graphInterface.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(false);

    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = null;

    this.toolbox.repaint();
    this.graphInterface.repaint();
  }
}
