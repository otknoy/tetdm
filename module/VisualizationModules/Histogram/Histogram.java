package module.VisualizationModules.Histogram;

import source.*;

import java.awt.Graphics;

public class Histogram extends VisualizationModule {

  private String[] terms;
  private int[] frequency;

  public Histogram() {
    setModuleID(1);
    dataNumbers = new int[] {0, 0, 0, 0,
			     0, 1, 0, 1,
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

  }


  @Override
  public boolean setData(int dataID, String[] terms) {
    this.terms = terms;
    return true;
  }

  @Override
  public boolean setData(int dataID, int[] frequency) {
    this.frequency = frequency;
    return true;    
  }


  @Override
  public void paintComponent(Graphics g) {
    getPanelSize();

    drawHistogram(g);
    // System.out.println(sizeX);
  }

  private void drawHistogram(Graphics g) {
    double[] normalizedFrequency = normalize(frequency);

    // for (int i = 0; i < normalizedFrequency.length; i++) {
      // System.out.println(terms[i]);
      // System.out.print(frequency[i]);
      // System.out.print("\t");
      // System.out.println(normalizedFrequency[i]);
    // }
    
    int width = sizeX;
    int height = sizeY;
    double w = width/(double)terms.length;

    for (int i = 0; i < terms.length; i++) {
      double h = height*normalizedFrequency[i];
      double x = i * w; 
      double y = height - h;
      
      g.drawString(terms[i], (int)x, (int)y+12);

      g.drawRect((int)x, (int)y, (int)w, (int)h);
    }
  }

  private double[] normalize(double[] data) {
    double max = Double.MIN_VALUE;    
    for (int i = 0; i< frequency.length; i++){
      max = Math.max(max, frequency[i]);
    }

    double[] temp = new double[data.length];
    for (int i = 0; i < temp.length; i++) {
      temp[i] = data[i]/max;
    }
    return temp;
  }

  private double[] normalize(int[] data) {
    double[] temp = new double[data.length];
    for (int i = 0; i < temp.length; i++) {
      temp[i] = (double)data[i];
    }
    return normalize(temp);
  }

}
