package ui.history;

import ui.history.component.History;


public class HistoryTree {

  private final HistoryTreePanel parent;
  private final History root;
  private History current;


  public HistoryTree(HistoryTreePanel parent, History root) {
    this.parent = parent;
    this.root = root;
    this.current = this.root;
  }

  public HistoryTree(HistoryTreePanel parent) {
    this(parent, null);
  }


  public History getRoot() { return this.root; }
  public History getCurrent() { return this.current; }

  public void changeCurrent(History h) {
    this.current = h;
    this.parent.repaint();
  }

  public void addHistory(History h) {
    current.addToNext(h);
    current = h;

    this.parent.add(h);
    this.parent.repaint();
  }
}