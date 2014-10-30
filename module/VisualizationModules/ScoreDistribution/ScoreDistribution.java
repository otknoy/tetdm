//
// Visuzalization module for TETDM
// ScoreDistribution.java Version 0.36
// Copyright(C) 2011 - 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.ScoreDistribution;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class ScoreDistribution extends VisualizationModule {
  int width;
  double lightscore[];

  public ScoreDistribution() {
    setModuleID(4);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S
                             0,0,1,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(2);
  }

  public void initializePanel() {}

  public void initializeData() {
    lightscore = new double[0];
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        repaint();
        break;
    }
  }

  public boolean setData(int dataID, double score[]) {
    switch (dataID) {
      case 0:
        lightscore = score;
        repaint();
        return true;
    }

    return false;
  }

//	public void setFont(){}

  //background
  public void draw_background(Graphics2D g2) {
    if (lightscore.length == 0) {
      width = 0;
    } else {
      width = (sizeY-10)/lightscore.length;
      if (width == 0) {
        width = 1;
      }
    }

    g2.setColor(Color.black);
    g2.fillRect(0,0, sizeX, sizeY);
    g2.setColor(Color.white);
    g2.drawRect(10,10, sizeX-20, lightscore.length*width);
    g2.drawLine(sizeX*2/3,10,sizeX*2/3,10+lightscore.length*width);	//410
  }

  public void draw_distribution(Graphics2D g2) {
    double max = 1.0;

    for (int i=0; i<lightscore.length; i++)
      if (lightscore[i] > max) {
        max = lightscore[i];
      }

    g2.setColor(Color.yellow);
    for (int i=0; i<lightscore.length; i++) {
      g2.fillRect(10,10+width*i, (int)((sizeX-20)*lightscore[i]/max), width);
    }
  }

  //////////paint
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    getPanelSize();

    draw_background(g2);		//background
    draw_distribution(g2);
  }

  public void update(Graphics g) {	//avoid from blinking
    paintComponent(g);
  }
}