package ui.graph.component.event;

import ui.graph.GraphInterface;
import ui.graph.component.Node;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class NodeRemoveListener implements MouseListener {

  private final GraphInterface graphInterface;
  private final Node node;
  

  public NodeRemoveListener(GraphInterface graphInterface, Node node) {
    this.graphInterface = graphInterface;
    this.node = node;
  }


  @Override public void mouseClicked(MouseEvent e) {
    this.graphInterface.removeNode(this.node);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }
  @Override public void mouseEntered(MouseEvent e) { }
  @Override public void mouseExited(MouseEvent e) { }
}