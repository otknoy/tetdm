//
// Visuzalization module for TETDM
// Paragraph.Panel.java Version 0.30
// Copyright(C) 2011 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.ParagraphPanel;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class ParagraphPanel extends VisualizationModule {
  public ParagraphPanel() {
    setModuleID(1005);			// Set your module ID after you have got it
    setToolType(1);
  }

  public void initializePanel() {}

  public void initializeData() {}

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        repaint();
        break;
    }
  }

  //background
  public void draw_background(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void draw_Paragraph(Graphics2D g2) {
    int y;
    double w;

    y = sizeY/(text.segmentNumber*2+1);

    for (int i=0; i<text.segmentNumber; i++) {
      if ((i+1) != text.segmentNumber) {
        w = ((int)(text.segmentRelation.cos[i][i+1]*10/2)+1)*3-2;
      } else {
        w=0;
      }
      g2.setColor(Color.BLACK);
      g2.setStroke(new BasicStroke(2.0f));
      g2.drawRect(sizeX/2-sizeX/6,y+2*y*i,sizeX/3,y);


      g2.drawString("seg"+(i+1),sizeX/2-10,y+2*y*i+y/2);

      if ((i+1) != text.segmentNumber) {
        g2.drawString(String.format("%1.3f",text.segmentRelation.cos[i][i+1]),sizeX/2+sizeX/6+10,y+2*y*i+3*y/2);
        g2.setStroke(new BasicStroke((float)w));
        g2.drawLine(sizeX/2,y+2*y*i+y +(int)(w/2),sizeX/2,y+2*y*i+2*y -(int)(w/2));

      }
    }
  }

  //////////paint
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    getPanelSize();

    draw_background(g2);		//background
    draw_Paragraph(g2);
  }

  public void update(Graphics g) {	//avoid from blinking
    paintComponent(g);
  }
}
