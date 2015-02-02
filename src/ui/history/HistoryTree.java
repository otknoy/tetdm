package ui.history;

import ui.history.component.Node;
import ui.history.component.History;


import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;


public class HistoryTree {

  private final HistoryTreePanel parent;
  private final History root;
  private History current;


  public HistoryTree(HistoryTreePanel parent, History root) {
    this.parent = parent;
    this.root = root;

    this.current = this.root;
    this.current.selected(true);
    this.parent.add(this.current);
    this.parent.repaint();
  }


  public History getRoot() { return this.root; }
  public History getCurrent() { return this.current; }

  public void changeCurrent(History h) {
    this.current.selected(false);
    this.current = h;
    this.current.selected(true);
  }

  public void addHistory(History h) {
    this.current.addToNext(h);
    this.changeCurrent(h);
  }

  public Dimension getSize() {
    int maxWidth  = Integer.MIN_VALUE;
    int maxHeight = Integer.MIN_VALUE;

    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.root);
    for (int i = 0; i < nodes.size(); i++) {
      nodes.addAll(nodes.get(i).getNextNodes());
    }

    for (Node n : nodes) {
      int width  = n.getX() + n.getWidth();
      int height = n.getY() + n.getHeight();

      maxWidth  = Math.max(width,  maxWidth);
      maxHeight = Math.max(height, maxHeight);
    }

    return new Dimension(maxWidth, maxHeight);
  }
}
