package ui.history;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.component.History;
import ui.history.data.State;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class HistoryTreePanel extends JPanel implements MouseListener {

  static final String NAME = "History Tree";

  private Interface parent;

  private HistoryTree historyTree;


  public HistoryTreePanel(Interface parent, History root) {
    this.parent = parent;

    root.addMouseListener(this);
    this.historyTree = new HistoryTree(this, root);
    
    this.setLayout(null);
    this.alignHistoryTree();
  }


  @Override public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    this.historyTree.getRoot().drawEdgesToNext(g);
  }


  @Override public void mouseClicked(MouseEvent e) {
    History h = (History)e.getSource();
    this.historyTree.changeCurrent(h);

    this.parent.restoreHistory(h);

    this.repaint();
  }

  @Override public void mousePressed(MouseEvent e) { }
  @Override public void mouseReleased(MouseEvent e) { }
  @Override public void mouseEntered(MouseEvent e) { }
  @Override public void mouseExited(MouseEvent e) { }


  public void addHistory(History h) {
    h.addMouseListener(this);
    this.historyTree.addHistory(h);
    this.add(h);

    alignHistoryTree();
    this.repaint();
  }


  private void alignHistoryTree() {
    this.historyTree.getRoot().align(new Point(32, 32), 0, 0);
    this.repaint();
  }
}
