package ui.toolbox.event;

import ui.Interface;
import ui.toolbox.ToolSelectPanel;
import ui.graph.component.Node;
import ui.graph.component.ModuleNode;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class ToolSelectMouseListener implements MouseListener {

  private final Interface parent;
  private final ToolSelectPanel toolSelectPanel;
  private List<Node> connectableNodes;


  public ToolSelectMouseListener(Interface parent, ToolSelectPanel toolSelectPanel) {
    this.parent = parent;
    this.toolSelectPanel = toolSelectPanel;
  }


  @Override public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    ModuleNode cn = n.clone();

    this.parent.addNodeToGraphInterface(cn);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }

  @Override public void mouseEntered(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(true);

    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(this.toolSelectPanel.getNodes());
    nodes.addAll(this.parent.getNodesFromGraphInterface());

    this.connectableNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (node.isConnectableTo(n)) {
	n.highlighted(true);
	this.connectableNodes.add(n);
      }
   }

    this.parent.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(false);

    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = null;

    this.parent.repaint();
  }
}