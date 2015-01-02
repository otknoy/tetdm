package ui.history;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.component.History;

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
    this.historyTree = new HistoryTree(this, );
    
    this.setLayout(null);
  }


  @Override public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    // this.root.drawEdgesToNextNodes(g);
  }
}
