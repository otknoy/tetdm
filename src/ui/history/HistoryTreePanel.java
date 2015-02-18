package ui.history;

import ui.MainPanel;
import ui.history.component.History;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class HistoryTreePanel extends JPanel implements MouseListener {

  static final String NAME = "History Tree";

  private MainPanel mainPanel;

  private HistoryTree historyTree;


  public HistoryTreePanel(MainPanel mainPanel, History root) {
    this.mainPanel = mainPanel;

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

    this.mainPanel.restoreHistory(h);

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

    // resize
    this.setPreferredSize(this.historyTree.getSize());
    this.revalidate();
  }


  private void alignHistoryTree() {
    this.historyTree.getRoot().align(new Point(16, 16), 0, 0);
    this.repaint();
  }
}
