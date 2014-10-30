//
// Visuzalization module for TETDM
// Graph.java Version 0.36
// Copyright(C) 2011 - 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.Graph;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class Graph extends VisualizationModule {
  double value[];
  int dataNumber;

  int leftMargin;
  int rightMargin;
  int topMargin;
  int bottomMargin;

  double max,min;
  int width,height;

  int focusNumber;

  public Graph() {
    setModuleID(17);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S
                             0,0,1,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(2);
  }

  public void initializePanel() {}

  public void initializeData() {
    dataNumber = 10;

    value = new double[dataNumber];
    focusNumber = 0;

    leftMargin = 50;
    rightMargin = 20;
    topMargin = 20;
    bottomMargin = 50;
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 4503:
        if (focusNumber >= dataNumber) {
          focusNumber = 0;
        }
        value[focusNumber] = text.focus.getMainFocusDouble();
        focusNumber++;
        repaint();
        break;

      case 0:
        repaint();
        break;

      case 1:
        focusNumber = 0;
        break;
    }
  }

  public boolean setData(int dataID, double score[]) {
    switch (dataID) {
      case 0:
        value = score;
        dataNumber = value.length;
        repaint();
        return true;
    }

    return false;
  }

//	public void setFont(){}

  //background
  public void drawBackground(Graphics2D g2) {
    g2.setColor(Color.black);
    g2.fillRect(0,0, sizeX, sizeY);
    g2.setColor(Color.white);
    g2.drawLine(leftMargin, topMargin, leftMargin, sizeY-bottomMargin);
  }

  public void drawDistribution(Graphics2D g2) {
    if (value.length == 0) {
      return;
    }

    max = min = value[0];
    width = sizeX - leftMargin - rightMargin;
    height = sizeY - topMargin - bottomMargin;

    for (int i=1; i<value.length; i++) {
      if (value[i] > max) {
        max = value[i];
      }
      if (value[i] < min) {
        min = value[i];
      }
    }

    if (max == min) {
      return;
    }

    g2.setColor(Color.cyan);
    for (int i=1; i<value.length; i++)
      g2.drawLine(leftMargin +  width * (i-1)/(value.length-1) ,(int)(topMargin + height * (max - value[i-1]) / (max - min)),
                  leftMargin +  width * i/(value.length-1) ,(int)(topMargin + height * (max - value[i]) / (max - min)));

    g2.setColor(Color.yellow);
    for (int i=0; i<value.length; i++) {
      g2.fillOval(leftMargin +  width * i/(value.length-1)-5 ,(int)(topMargin + height * (max - value[i]) / (max - min))-5, 10,10);
    }
  }

  public void drawGuide(Graphics2D g2) {
    int n=5;

    g2.setColor(Color.white);

    for (int i=0; i<5; i++) {
      g2.drawString(""+String.format("%.1f",min+(max-min)*(n-1-i)/(n-1)),2,topMargin+height*i/(n-1));
      g2.drawLine(leftMargin-10,topMargin+height*i/(n-1),leftMargin+10,topMargin+height*i/(n-1));
    }
  }

  //////////paint
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    getPanelSize();

    drawBackground(g2);		//background
    drawDistribution(g2);
    drawGuide(g2);
  }

  public void update(Graphics g) {	//avoid from blinking
    paintComponent(g);
  }
}