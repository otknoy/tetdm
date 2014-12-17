package ui.toolbox.event;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.ModuleNode;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class NodeMouseListener implements MouseListener {

  private final Toolbox toolbox;
  private final GraphInterface graphInterface;


  public NodeMouseListener(Toolbox toolbox, GraphInterface graphInterface) {
    this.toolbox = toolbox;
    this.graphInterface = graphInterface;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    ModuleNode n = (ModuleNode)e.getSource();
    this.graphInterface.addNode(n.clone());

    System.out.println(n);
  }
  
  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }
  @Override public void mouseEntered(MouseEvent e) { }
  @Override public void mouseExited(MouseEvent e) { }
}
