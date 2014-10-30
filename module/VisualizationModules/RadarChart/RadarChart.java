//
// Visuzalization module for TETDM
// RadarChart.java Version 0.1 2012.11.7
// Copyright(C) 2012 Tomoki Kajinami ALL RIGHTS RESERVED.
// This program is provided under the license.
// This module draws a radar chart.
// You should read the README file.
//

package module.VisualizationModules.RadarChart;

import source.*;
import source.TextData.*;
import java.math.BigDecimal;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class RadarChart extends VisualizationModule implements ActionListener,ChangeListener {

  private String inputData;
  private int numberOfElements;
  private String[] row;
  private String[] elements;
  private String[] elementName;
  private double[] elementValue;
  private int[] pointX;
  private int[] pointY;
  private JPanel operationPanel;
  private JButton valueDisplay;
  private JButton fontSizeSwitch;
  private JButton colorChange;
  private JSlider colorSlider;
  private String[] buttonNamesInJapanese;
  private boolean valueDisplayFlg;
  private int fontSizeFlg;
  private int colorChangeFlg;
  private int alphaValue;
  private int panelHeight;

  public RadarChart() {
    setModuleID(16);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,0,0,1,   // b,i,d,S
                             0,0,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {
    buttonNamesInJapanese = fileReadArray();

    operationPanel=new JPanel();
    operationPanel.setLayout(new GridLayout(0,4));
    add("South",operationPanel);

    valueDisplay=new JButton("Value ON/OFF");
    valueDisplay.addActionListener(this);
    operationPanel.add(valueDisplay);

    fontSizeSwitch=new JButton("Font S/M/L");
    fontSizeSwitch.addActionListener(this);
    operationPanel.add(fontSizeSwitch);

    colorChange=new JButton("Color R/G/B");
    colorChange.addActionListener(this);
    operationPanel.add(colorChange);

    colorSlider=new JSlider(0,255,127);
    colorSlider.addChangeListener(this);
    operationPanel.add(colorSlider);

    if (isMenuInJapanese()) {
      valueDisplay.setText(buttonNamesInJapanese[0]);
      fontSizeSwitch.setText(buttonNamesInJapanese[1]);
      colorChange.setText(buttonNamesInJapanese[2]);
    }
  }

  public void initializeData() {
    alphaValue=127;
    inputData = ",0.0"+System.getProperty("line.separator");
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        dataSetting();
        repaint();
        break;
    }
  }

  private void dataSetting() {
    row=inputData.split(System.getProperty("line.separator"));
    numberOfElements=row.length;
    elementName=new String[numberOfElements];
    elementValue=new double[numberOfElements];

    for (int i=0; i<numberOfElements; i++) {
      elements=row[i].split(",");
      elementName[i]=elements[0];
      elementValue[i]=Double.parseDouble(elements[1]);
    }
  }

  public boolean setData(int dataID, String textData) {
    switch (dataID) {
      case 98:	//check a data
        inputData=textData;
        return true;
    }
    return false;
  }

  public void drawBackground(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void drawData(Graphics2D g2) {
    panelHeight=valueDisplay.getHeight();
    int fontSize=20;
    if (fontSizeFlg==0) {
      fontSize=20;
    }
    if (fontSizeFlg==1) {
      fontSize=30;
    }
    if (fontSizeFlg==2) {
      fontSize=10;
    }
    int maxLengthOfElementName=0;

    for (int i=0; i<numberOfElements; i++) {
      if (maxLengthOfElementName<elementName[i].getBytes().length) {
        maxLengthOfElementName=elementName[i].getBytes().length;
      }
    }

    int centerX=sizeX/2;
    int centerY=(sizeY-panelHeight)/2;
    double maxLengthOfElementValue;

    if (sizeX-maxLengthOfElementName*fontSize>=sizeY-panelHeight*2-fontSize*4) {
      maxLengthOfElementValue=centerY-fontSize*2-panelHeight*2-10;
    } else {
      maxLengthOfElementValue=centerX-maxLengthOfElementName*fontSize/2-10;
    }

    double theta=2*Math.PI/numberOfElements;

    pointX=new int[numberOfElements];
    pointY=new int[numberOfElements];

    for (int i=0; i<numberOfElements; i++) {
      pointX[i]=(int)(centerX+maxLengthOfElementValue*elementValue[i]*Math.cos(theta*i));
      pointY[i]=(int)(centerY+maxLengthOfElementValue*elementValue[i]*Math.sin(theta*i));
    }
    Polygon radarChart=new Polygon(pointX,pointY,numberOfElements);
    if (colorChangeFlg==0) {
      g2.setColor(Color.green);
      g2.draw(radarChart);
      g2.setPaint(new Color(0,255,0,alphaValue));
    }
    if (colorChangeFlg==1) {
      g2.setColor(Color.blue);
      g2.draw(radarChart);
      g2.setPaint(new Color(0,0,255,alphaValue));
    }
    if (colorChangeFlg==2) {
      g2.setColor(Color.red);
      g2.draw(radarChart);
      g2.setPaint(new Color(255,0,0,alphaValue));
    }
    g2.fill(radarChart);

    double scale=maxLengthOfElementValue/10;

    for (int i=1; i<=10; i++) {
      for (int j=0; j<numberOfElements; j++) {
        pointX[j]=(int)(centerX+scale*i*Math.cos(theta*j));
        pointY[j]=(int)(centerY+scale*i*Math.sin(theta*j));
      }
      Polygon scaleLine=new Polygon(pointX,pointY,numberOfElements);
      if (i==5 || i==10) {
        g2.setColor(Color.black);
      } else {
        g2.setColor(Color.gray);
      }
      g2.draw(scaleLine);
    }

    for (int i=0; i<numberOfElements; i++) {
      g2.setColor(Color.black);
      g2.drawLine(centerX,centerY,(int)(centerX+maxLengthOfElementValue*Math.cos(theta*i)),(int)(centerY+maxLengthOfElementValue*Math.sin(theta*i)));

      BigDecimal bd=new BigDecimal(elementValue[i]);
      BigDecimal valueForDisplay=bd.setScale(2,BigDecimal.ROUND_HALF_UP);
      g2.setFont(new Font("Dialog", Font.PLAIN, fontSize));

      if (valueDisplayFlg) {
        g2.drawString(valueForDisplay.toString(),(int)(centerX+maxLengthOfElementValue*elementValue[i]*Math.cos(theta*i)-fontSize),
                      (int)(centerY+maxLengthOfElementValue*elementValue[i]*Math.sin(theta*i)+fontSize/2));
      }

      g2.drawString(elementName[i],(int)(centerX+(maxLengthOfElementValue+elementName[i].getBytes().length*fontSize/4+2)*Math.cos(theta*i)-elementName[i].getBytes().length*fontSize/4),
                    (int)(centerY+(maxLengthOfElementValue+elementName[i].getBytes().length*fontSize/4+2)*Math.sin(theta*i)+(fontSize/2)));
    }
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    getPanelSize();
    drawBackground(g2);
    drawData(g2);
  }

  public void update(Graphics g) {
    paintComponent(g);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==valueDisplay) {
      if (valueDisplayFlg) {
        valueDisplayFlg=false;
        repaint();
      } else {
        valueDisplayFlg=true;
        repaint();
      }
    } else if (e.getSource()==fontSizeSwitch) {
      if (fontSizeFlg==2) {
        fontSizeFlg=0;
        repaint();
      } else {
        fontSizeFlg++;
        repaint();
      }
    } else if (e.getSource()==colorChange) {
      if (colorChangeFlg==2) {
        colorChangeFlg=0;
        repaint();
      } else {
        colorChangeFlg++;
        repaint();
      }
    }
  }

  public void stateChanged(ChangeEvent e) {
    alphaValue=colorSlider.getValue();
    repaint();
  }
}