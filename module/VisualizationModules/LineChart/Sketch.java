package module.VisualizationModules.LineChart;

import processing.core.*;

public class Sketch extends PApplet {

  private String[] labels;
  private double[] data;


  public Sketch(int width, int height, String[] labels, double[] data) {
    this.width = width;
    this.height = height;
    this.labels = labels;
    this.data = data;
  }

  @Override
  public void setup() {
    // size(640, 480);
  }

  @Override
  public void draw() {
    background(255);

    stroke(0);
    fill(127, 127, 127);
    beginShape();
    vertex(0, height);
    for (int i = 0; i < labels.length; i++) {
      float x = map(i, 0, labels.length, 0, width);
      float y = map((float)data[i], 0, 64, height, 0);
      vertex(x, y);

      text(labels[i], x, y);
    }
    vertex(width, height);
    endShape();

    noLoop();
  }
}