package ui.graph.component.event;

import ui.graph.component.Node;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;


public class NodeSelectMouseListener implements MouseListener {

  private Node node;

  
  public NodeSelectMouseListener(Node node) {
    this.node = node;
  }
  

  @Override
  public void mouseClicked(MouseEvent e) {
    this.node.selected(!this.node.isSelected());

    node.repaint();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    node.highlighted(true);

    node.repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    node.highlighted(false);    

    node.repaint();
  }
}