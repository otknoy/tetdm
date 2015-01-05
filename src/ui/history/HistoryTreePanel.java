package ui.history;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.component.History;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;


public class HistoryTreePanel extends JPanel {

  static final String NAME = "History Tree";

  private Interface parent;

  private HistoryTree historyTree;


  public HistoryTreePanel(Interface parent) {
    this.parent = parent;

    History root = new History(this.parent.cloneGraphInterface(), History.RATE_NORMAL);
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

  public void addHistory(History h) {
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
