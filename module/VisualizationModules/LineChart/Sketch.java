package module.VisualizationModules.Histogram;

import processing.core.*;

public class Sketch extends PApplet {

  private String[] labels;
  private double[] data;


  public Sketch(String[] labels, double[] data) {
    this.labels = labels;
    this.data = data;
  }

  @Override
  public void setup() {
    size(640, 480);
  }

  @Override
  public void draw() {
    ellipse(frameCount, frameCount, 32, 32);
  }
}