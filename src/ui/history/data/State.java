package ui.history.data;

import ui.util.ScreenCapture;
import ui.graph.GraphInterface;
import ui.history.component.History;

import java.awt.image.BufferedImage;


public class State {

  private final GraphInterface gi;
  private final int rate;
  private final BufferedImage img;
  private final BufferedImage thumbnail;

  public static final int RATE_BAD    = 0;
  public static final int RATE_NORMAL = 1;
  public static final int RATE_GOOD   = 2;


  public State(GraphInterface gi, int rate, BufferedImage img) {
    this.gi = gi;
    this.rate = rate;
    this.img = img;

    this.thumbnail = ScreenCapture.resize(this.img, History.WIDTH, History.HEIGHT);
  }

  public State(GraphInterface gi, int rate) {
    this(gi, rate, ScreenCapture.getImage());
  }


  public GraphInterface getGraphInterface() { return this.gi; }
  public int getRate() { return this.rate; }
  public BufferedImage getImage() { return this.img; }
  public BufferedImage getThumbnail() { return this.thumbnail; }  
}