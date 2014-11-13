package ui.graph.component.event;

import ui.graph.component.Sticky;
import ui.graph.component.StickyEditor;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class StickyEventListener implements MouseListener{

  private Sticky sticky;


  public StickyEventListener(Sticky sticky) {
    this.sticky = sticky;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    StickyEditor se = new StickyEditor(this.sticky);

    Point mp = e.getLocationOnScreen();
    int x = (int)mp.getX() - se.getWidth() /2;
    int y = (int)mp.getY() - se.getHeight()/2;
    se.setLocation(x, y);

    se.setVisible(true);
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}