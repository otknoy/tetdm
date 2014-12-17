package ui.toolbox.event;

import ui.Interface;
import ui.graph.component.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class NodeMouseListener implements MouseListener {

  private final Interface parent;


  public NodeMouseListener(Interface parent) {
    this.parent = parent;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    parent.graphInterface.addNode(n.clone());

    System.out.println(n);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }
  @Override public void mouseEntered(MouseEvent e) { }
  @Override public void mouseExited(MouseEvent e) { }
}
