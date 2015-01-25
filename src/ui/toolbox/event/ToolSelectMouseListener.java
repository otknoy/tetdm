package ui.toolbox.event;

import ui.MainPanel;
import ui.toolbox.ToolSelectPanel;
import ui.graph.component.Node;
import ui.graph.component.ModuleNode;
import ui.graph.component.util.Nodes;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class ToolSelectMouseListener implements MouseListener {

  private final MainPanel mainPanel;
  private final ToolSelectPanel toolSelectPanel;
  private List<Node> connectableNodes;


  public ToolSelectMouseListener(MainPanel mainPanel, ToolSelectPanel toolSelectPanel) {
    this.mainPanel = mainPanel;
    this.toolSelectPanel = toolSelectPanel;
  }


  @Override public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    ModuleNode cn = n.clone();

    // selected exclusively
    List<Node> nodes = Nodes.selectSameTypeNodes(this.mainPanel.getGraphInterface().getNodes(), cn);
    Nodes.selectedAll(nodes, false);
    cn.selected(true);

    cn.highlighted(false);

    this.mainPanel.getGraphInterface().addNode(cn);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }

  @Override public void mouseEntered(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(true);

    List<Node> nodes = new ArrayList<Node>();
    nodes.addAll(this.toolSelectPanel.getNodes());
    nodes.addAll(this.mainPanel.getGraphInterface().getNodes());

    this.connectableNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (node.isConnectableTo(n)) {
	n.highlighted(true);
	this.connectableNodes.add(n);
      }
   }

    this.mainPanel.repaint();
  }

  @Override public void mouseExited(MouseEvent e) {
    Node node = (Node)e.getSource();
    node.highlighted(false);

    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = null;

    this.mainPanel.repaint();
  }
}
