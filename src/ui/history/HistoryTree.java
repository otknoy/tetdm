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
}