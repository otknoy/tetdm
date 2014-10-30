package ui.graph.event;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;


public class MouseDragAndDropListener implements MouseListener, MouseMotionListener {

  private JComponent component;
  private Point dragStartLocation = null;
  private Point dragStartMousePoint = null;

  public MouseDragAndDropListener(JComponent component) {
    this.component = component;
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override  
  public void mousePressed(MouseEvent e) {
    // System.out.println("mousePressed");
    dragStartLocation = new Point(component.getX(), component.getY());
    dragStartMousePoint = e.getLocationOnScreen();
  }

  @Override  
  public void mouseReleased(MouseEvent e) {
    // System.out.println("mouseReleased");
    dragStartLocation = null;
    dragStartMousePoint = null;
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override  
  public void mouseDragged(MouseEvent e) {
    int dx = (int)(e.getLocationOnScreen().getX() - dragStartMousePoint.getX());
    int dy = (int)(e.getLocationOnScreen().getY() - dragStartMousePoint.getY());
    int x = (int)dragStartLocation.getX() + dx;
    int y = (int)dragStartLocation.getY() + dy;

    Container parent = component.getParent();
    if (x < 0)
      x = 0;
    if (parent.getWidth()-component.getWidth() <= x)
      x = parent.getWidth()-component.getWidth();
    if (y < 0)
      y = 0;
    if (parent.getHeight()-component.getHeight() <= y)
      y = parent.getHeight()-component.getHeight();
    component.setLocation(x, y);
  }
  
  @Override
  public void mouseMoved(MouseEvent e) {}
}
