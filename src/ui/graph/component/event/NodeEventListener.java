package ui.graph.component.event;

import ui.graph.ModuleSelectPanel;
import ui.graph.module.ModuleData;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningModuleNode;
import ui.graph.component.VisualizationModuleNode;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;


public class NodeEventListener implements MouseListener, MouseMotionListener {

  private ModuleSelectPanel parent;

  private Node node;
  private List<Node> connectableNodes;

  
  public NodeEventListener(ModuleSelectPanel parent, Node node) {
    this.parent = parent;
    this.node = node;
    this.connectableNodes = new ArrayList<Node>();
  }
  

  @Override
  public void mouseClicked(MouseEvent e) {
    node.selected(!node.isSelected());
    node.repaint();
  }

  @Override  
  public void mousePressed(MouseEvent e) {}

  @Override  
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    if (node.isSelected()) return;
    
    node.highlighted(true);
    node.repaint();

    // highlight connectable nodes
    this.connectableNodes = parent.findConnectableNodes(this.node);
    for (Node n : this.connectableNodes) {
      n.highlighted(true);
    }

    this.parent.repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (node.isSelected()) return;

    node.highlighted(false);    
    node.repaint();

    // unhighlight connectable nodes
    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = new ArrayList<Node>();

    this.parent.repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (!this.node.isHighlighted()) {
      return;
    }
    System.out.println(e);

    // If the distance between connectable nodes becomes closer than threshold,
    // these nodes are connected.
    final int threshold = 128;
    for (Node n : this.connectableNodes) {
      if (this.node.isConnectedTo(n)) {
	continue;
      }

      if (threshold < this.node.distance(n)) {
	continue;
      }

      if (this.node.isConnectableToPrev(n)) {
	this.node.addPrevNodes(n);
	n.addNextNodes(n);
      } else if (this.node.isConnectableToNext(n) ){
	this.node.addNextNodes(n);
	n.addPrevNodes(n);
      }
    }

    parent.repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {}
}