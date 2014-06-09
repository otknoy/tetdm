package module.VisualizationModules.LineChart;

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
    background(255, 0, 0);
    ellipse(frameCount, frameCount, 32, 32);
    text(labels.length + ", " + data.length, width/2, height/2);
  }
}