package ui.toolbox.event;

import ui.graph.component.*;
import ui.graph.GraphInterface;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class NodeMouseListener implements MouseListener {

  private final GraphInterface graphInterface;


  public NodeMouseListener(GraphInterface graphInterface) {
    this.graphInterface = graphInterface;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    ModuleNode c = n.clone();
    this.graphInterface.addNode(c);

    System.out.println(n);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  
  @Override public void mouseReleased(MouseEvent e) { }
  
  @Override public void mouseEntered(MouseEvent e) { }
  
  @Override public void mouseExited(MouseEvent e) { }
}
