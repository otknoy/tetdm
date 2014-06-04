package module.VisualizationModules.Histogram;

import source.*;

import java.awt.Graphics;

import processing.core.*;


public class LineChart extends VisualizationModule {

  private String[] labels;
  private double[] data;

  
  public LineChart() {
    setModuleID(2);
    dataNumbers = new int[] {0, 0, 0, 0,
			     0, 1, 1, 1,
			     0, 0, 0};
    setToolType(2);
  }

  @Override
  public void initializePanel() {
    PApplet app = new Sketch(labels, data);
    add(app);
  }
  
  @Override
  public void initializeData() {

  }

  @Override
  public void displayOperations(int optionNumber) {
  }


  @Override
  public boolean setData(int dataID, String[] labels) {
    this.labels = labels;
    return true;
  }

  @Override
  public boolean setData(int dataID, int[] data) {
    double[] temp = new double[data.length];
    for (int i = 0; i < temp.length; i++) {
      temp[i] = data[i];
    }
    this.data = temp;
    return true;    
  }

  @Override
  public boolean setData(int dataID, double[] data) {
    this.data = data;
    return true;    
  }

  @Override
  public void paintComponent(Graphics g) {
    getPanelSize();

    // System.out.println(sizeX);
  }
}