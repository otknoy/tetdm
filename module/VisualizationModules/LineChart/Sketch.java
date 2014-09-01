package module.VisualizationModules.LineChart;

import processing.core.*;

public class Sketch extends PApplet {

  private String[] labels;
  private float[] data;


  public Sketch(int width, int height, String[] labels, double[] data) {
    this.width = width;
    this.height = height;
    this.labels = labels;
    this.data = new float[data.length];
    for (int i = 0; i < data.length; i++) {
      this.data[i] = (float)data[i];
    }
  }

  @Override
  public void setup() {
    // size(640, 480);
  }

  @Override
  public void draw() {
    background(255);

    int padding = 32;
    drawLineChart(padding, padding, width-2*padding, height-2*padding);

    noLoop();
  }


  private void drawLineChart(int x, int y, int width, int height) {
    translate(x, y);

    float max = max(data);

    stroke(0);
    fill(127, 127, 127);
    beginShape();
    vertex(0, height);
    for (int i = 0; i < labels.length; i++) {
      float px = map(i, 0, labels.length, 0, width);
      float py = map((float)data[i], 0, max, height, 0);
      vertex(px, py);

      float lx = px - textWidth(labels[i])/2;
      float ly = height + textAscent();
      text(labels[i], lx, ly);
    }
    vertex(width, height);
    endShape();
  }
}