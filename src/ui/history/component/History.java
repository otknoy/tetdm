package ui.history.component;

import ui.history.data.State;


public class History extends Node {

  private final State state;


  public History(State state, History prev) {
    super(prev);
    this.state = state;
  }


  public State getState() { return this.state; }
}
