package ui.graph.component.event;

import ui.graph.StickyManager;
import ui.graph.component.Sticky;
import ui.graph.component.StickyEditor;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class StickyEventListener implements MouseListener{

  private StickyManager stickyManager;


  public StickyEventListener(StickyManager stickyManager) {
    this.stickyManager = stickyManager;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    Object o = e.getSource();
    if (!(o instanceof Sticky)) return;

    Sticky s = (Sticky)o;

    StickyEditor se = new StickyEditor(this.stickyManager, s);

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