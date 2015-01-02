package ui.history.component;

import ui.history.data.State;


public class History extends Node {

  private State state;


  public History(State state, History prev) {
    super(prev);
    this.state = state;
  }

  public History(History prev) {
    this(null, prev);
  }


  public State getState() { return this.state; }
  public State setState(State s) { this.state = s; }
}
