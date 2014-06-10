package module.VisualizationModules.LineChart;

import source.*;

import processing.core.*;

import javax.swing.JPanel;
import java.awt.Graphics;


public class LineChart extends VisualizationModule {

  private Sketch sketch;

  private String[] labels;
  private double[] data;

  
  public LineChart() {
    setModuleID(90002);
    dataNumbers = new int[] {0, 0, 0, 0,
			     0, 1, 1, 1,
			     0, 0, 0};
    setToolType(2);
  }

  @Override
  public void initializePanel() {
  }
  
  @Override
  public void initializeData() {
  }

  @Override
  public void displayOperations(int optionNumber) {
    System.out.println("draw");

    switch(optionNumber) {
    case 0:
      if (sketch != null) remove(sketch);
      sketch = new Sketch(getWidth(), getHeight(), labels, data);
      add(sketch);
      sketch.init();
      break;
    default:
      break;
    }
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
    return setData(dataID, temp);
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