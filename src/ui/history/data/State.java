package ui.history.data;

import ui.graph.GraphInterface;


public class State {

  private GraphInterface gi;
  private int rate;

  
  public State(GraphInterface gi, int rate) {
    this.gi = gi;
    this.rate = rate;
  }


  public GraphInterface getGraphInterface() { return this.gi; }
  public int getRate() { return this.rate; }
}