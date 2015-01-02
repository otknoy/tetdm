package ui.history.data;

import ui.graph.GraphInterface;


public class State {

  private GraphInterface gi;
  private int rate;

  public static final int RATE_BAD    = 0;
  public static final int RATE_NORMAL = 1;
  public static final int RATE_GOOD   = 2;

  
  public State(GraphInterface gi, int rate) {
    this.gi = gi;
    this.rate = rate;
  }


  public GraphInterface getGraphInterface() { return this.gi; }
  public int getRate() { return this.rate; }
}