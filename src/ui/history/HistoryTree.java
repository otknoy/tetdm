package ui.history;

import ui.history.component.History;

import java.util.List;
import java.util.ArrayList;


public class HistoryTree {

  private final HistoryTreePanel parent;
  private final History root;
  private final List<History> historyList;
  private History current;


  public HistoryTree(HistoryTreePanel parent, History root) {
    this.parent = parent;
    this.root = root;
    this.historyList = new ArrayList<History>();
    this.current = this.root;
  }

  public HistoryTree(HistoryTreePanel parent) {
    this(parent, null);
  }


  public History getCurrent() { return this.current; }

  public void changeCurrent(History h) {
    this.current = h;
    this.parent.repaint();
  }

  public void addHistory(History h) {
    historyList.add(h);
    current.addToNext(h);
    current = h;

    this.parent.add(h);
    this.parent.repaint();
  }
}