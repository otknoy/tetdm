package ui.graph.event;

import ui.Interface;
import ui.graph.component.Node;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class NodeConnectMouseListener implements MouseListener, MouseMotionListener {

  private final Interface parent;
  private final Node node;


  public NodeConnectMouseListener(Interface parent, Node node) {
    this.parent = parent;
    this.node = node;
  }

  
  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override  
  public void mousePressed(MouseEvent e) {
  }

  @Override  
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  
  @Override  
  public void mouseDragged(MouseEvent e) {
  }
  
  @Override
  public void mouseMoved(MouseEvent e) {}
}
