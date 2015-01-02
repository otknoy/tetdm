package ui.history;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.component.History;
import ui.history.data.State;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class HistoryTreePanel extends JPanel {

  static final String NAME = "History Tree";

  private Interface parent;

  private HistoryTree historyTree;


  public HistoryTreePanel(Interface parent) {
    this.parent = parent;

    State s = new State(this.parent.cloneGraphInterface(), State.RATE_NORMAL);
    History root = new History(s, null);
    this.historyTree = new HistoryTree(this, root);
    
    this.setLayout(null);
  }


  @Override public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    // this.root.drawEdgesToNextNodes(g);
  }

  public void addHistory(History h) { this.historyTree.addHistory(h); }
}
