package ui.history;

import source.MainFrame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;


public class HistoryTreePanel<T> extends JPanel implements MouseListener {

  static final String NAME = "History Tree";

  private MainFrame mainFrame;

  private History<T> root;
  private History<T> current;


  public HistoryTreePanel(MainFrame mainFrame, T o) {
    this.mainFrame = mainFrame;
    
    this.setLayout(null);

    History<T> root = new History<T>(o, null);
    root.addMouseListener(this);
    root.selected(true);
    
    this.root = root;
    this.add(this.root);
    this.current = this.root;

    this.alignHistoryTree(this.root);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    History h = (History)e.getSource();
    this.current.selected(false);
    this.current = h;
    this.current.selected(true);
    this.repaint();

    // this.mainFrame.changeModuleSelectPanel(this.current.getData());
  }
  
  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    this.root.drawEdge(g);
  }
  
  
  private void alignHistoryTree(History root) {
    // int x = 32;
    // int y = 32;
    // root.moveTo(x, y);
    root.align(new Point(32, 32), 0, 0);
    this.repaint();
  }

  public void saveHistory(T o) {
    History<T> h = new History<T>(o, this.current);
    h.addMouseListener(this);

    this.current.addToNext(h);
    this.add(h);

    this.current.selected(false);
    h.selected(true);

    this.current = h;

    this.alignHistoryTree(this.root);

    // this.mainFrame.changeModuleSelectPanel(this.current.getData());
  }

  public T getCurrent() { return this.current.getData(); }
}
