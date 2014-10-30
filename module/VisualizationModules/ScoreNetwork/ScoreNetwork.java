//
// Visuzalization module for TETDM
// ScoreNetwork.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//


//setData, setScoreData: differences between this and drawOriginal

package module.VisualizationModules.ScoreNetwork;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class ScoreNetwork extends VisualizationModule implements MouseListener, MouseMotionListener {
  //For objects operation
  int mouseX, mouseY;			//Coordinates for a mouse
  int oldMouseX, oldMouseY;	//Last Coordinates for a mosue

  int touchNumber;				//Node Number of being touched
  int oldTouchNumber;

  boolean moving;			//Flag for node moving
  boolean fieldMove;		//Flag for field moving

  DisplayNetwork network;

  double svalue[];
  double maxSvalue;
  double minSvalue;

  public ScoreNetwork() {
    setModuleID(6);
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S
                             0,0,1,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {
    addMouseListener(this);			//MouseListener
    addMouseMotionListener(this);	//MouseMotionListener
  }

  public void initializeData() {
    touchNumber = -1;

    moving = false;
    fieldMove = false;

    initializeNetwork();
  }

  public void initializeNetwork() {
    String names[] = new String[text.segmentNumber];
    for (int i=0; i<names.length; i++) {
      names[i] = "SEG"+(i+1);
    }

    network = new DisplayNetwork(names, 20, Color.black);

    svalue = new double[network.objectNumber];/**/

    text.segmentRelation.createStrongestLink(text.segmentRelation.cos);
    //		text.segmentRelation.optimize_link(text.segmentRelation.cos, 0.5);

    for (int i=0; i<network.objectNumber; i++)
      for (int j=0; j<network.objectNumber; j++) {
        network.link[i][j] = text.segmentRelation.link[i][j];
        network.value[i][j] = text.segmentRelation.value[i];
      }

    for (int i=0; i<network.objectNumber; i++) {
      network.object[i].x = (int)(sizeX/2 + (sizeX/2-50) * Math.cos(i));
      network.object[i].y = (int)(network.value[i][0] * sizeY);/**/
    }
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 4501:
      case 0:
//				boolean active[] = new boolean[text.segmentNumber];
//				for(int i=0;i<text.segmentNumber;i++)
//					active[i] = true;

//saveData();
        repaint();
        break;
        /*
        	case 1:
        		text.segmentRelation.createStrongestLink(text.segmentRelation.cos);
                    for(int i=0;i<network.objectNumber;i++)
                        for(int j=0;j<network.objectNumber;j++)
                        {
                            network.link[i][j] = text.segmentRelation.link[i][j];
                            network.value[i][j] = text.segmentRelation.value[i];
                        }

                    for(int i=0;i<network.objectNumber;i++)
                    {
                        network.object[i].x = (int)(sizeX/2 + (sizeX/2-50) * Math.cos(i));
                        network.object[i].y = (int)(network.value[i][0] * sizeY);//
                    }
                 */
        /*

        for(int i=0;i<text.segmentNumber;i++)
        map.network.object[i].value = map.relation[i][0].value;
        map.calculateSvalue();
        normalColor=true;


        break;
        */
      case 11:
        saveData();
        break;
    }
  }

  public boolean setData(int dataID, double score[]) {
    if (score.length != text.segmentNumber) {
      return false;
    }

    switch (dataID) {
      case 0:
        network.setNodeValue(score);
        calculateSvalue();
        return true;
    }
    return false;
  }

  //svalue

  void calculateSvalue() { // This is not used if all values are the same (might be occurred when a word appears all segments and used conditional probs)
    double max = network.object[0].value;

    for (int i=1; i<network.objectNumber; i++)
      if (network.object[i].value > max) {
        max = network.object[i].value;
      }



    double average,average2;
    double std;

    average = average2 = 0.0;

    for (int i=0; i<network.objectNumber; i++) {
      average += network.object[i].value;
      average2 += network.object[i].value * network.object[i].value;
    }
    average /= network.objectNumber;
    average2 /= network.objectNumber;

    std = Math.sqrt(average2 - average*average);

    for (int i=0; i<network.objectNumber; i++) {
      svalue[i] = 50 + (network.object[i].value - average) * 10 / std;
    }


    minSvalue = svalue[0];
    maxSvalue = svalue[0];

    for (int i=1; i<network.objectNumber; i++) {
      if (svalue[i] > maxSvalue) {
        maxSvalue = svalue[i];
      }
      if (svalue[i] < minSvalue) {
        minSvalue = svalue[i];
      }
    }

    for (int i=0; i<network.objectNumber; i++) {
      network.object[i].y =  (int)(((svalue[i] - minSvalue) / (maxSvalue - minSvalue)) * (sizeY - 100)) + 50;
    }

    //			System.out.println("min , max svalues are "+minSvalue+" : "+maxSvalue);

  }


  public void saveData() {
    String datafile = "ScoreData";
    String saveFile = myModulePath+datafile;
    String saveData[];

    saveData = new String[text.segmentNumber];

    for (int i=0; i<text.segmentNumber; i++) {
      saveData[i] = network.object[i].word+" "+network.object[i].value;
    }

    fileWriteArray(saveFile, saveData, saveData.length);
  }

  //////////DRAWING

  void drawBackground(Graphics2D g2) {
    int y40 = (int)(((40 - minSvalue) / (maxSvalue - minSvalue)) * (sizeY - 100)) + 50;
    int y60 = (int)(((60 - minSvalue) / (maxSvalue - minSvalue)) * (sizeY - 100)) + 50;

//			System.out.println(" y40, y60 = "+y40+" : "+y60);

    /*		if(normalColor)
    //		{
    			g2.setColor(new Color(0,128,255));
    			g2.fillRect(0,0, sizeX, y40);

    			g2.setColor(Color.yellow);
    			g2.fillRect(0,y40, sizeX, y60);

    			g2.setColor(Color.red);
    			g2.fillRect(0,y60, sizeX, sizeY);
    		}
    		else
    		{*/
    g2.setColor(Color.red);
    g2.fillRect(0,0, sizeX, y40);

    g2.setColor(Color.yellow);
    g2.fillRect(0,y40, sizeX, y60);

    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,y60, sizeX, sizeY);
//		}
  }

  //Display links
  public void drawLinks(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    network.drawLinkAddLeft(g2);
  }

  //Display nodes
  public void drawNode(Graphics2D g2) {
    g2.setColor(new Color(0xff,0x66,0x00));
    network.fillOval(g2);

    g2.setColor(Color.black);
    network.drawOval(g2);

    network.drawWord(g2);
  }

  public void drawTouchNode(Graphics2D g2) {
    if (text.focus.mainFocusSegment < 0) {
      return;
    }

    int i=text.focus.mainFocusSegment, j=text.segmentRelation.rank[text.focus.mainFocusSegment][1];

    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    network.drawLinkAddLeft(g2,i,j);

    network.drawWord(g2,i,Color.green);
    network.drawWord(g2,j);

    g2.setColor(Color.green);
    network.drawNodeValue(g2,i);

    g2.setColor(Color.green);
    network.fillOval(g2,i);

    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.setColor(Color.black);
    network.drawOval(g2,i);
  }

  public void paintComponent(Graphics g) {
    if (checkDataNumbers() == false) {
      return;
    }

    Graphics2D g2 = (Graphics2D)g;

    boolean change = getPanelSize();

    if (change) {
      network.reSizeByScreenSize(changeX, changeY);
    }

    drawBackground(g2);	//Set background
    drawLinks(g2);			//Display links
    drawNode(g2);
    drawTouchNode(g2);	//Display nodes touched by a mouse
  }

  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }

  //MouseListener
  public void mousePressed(MouseEvent me) {	//Press
    mouseX = me.getX();
    mouseY = me.getY();

    if (touchNumber < 0) {
      oldMouseX = mouseX;
      oldMouseY = mouseY;
      fieldMove = true;
      return;
    }
    moving = true;
  }

  public void mouseReleased(MouseEvent me) {	//Release
    moving = false;
    fieldMove = false;
  }

  public void mouseEntered(MouseEvent me) {}	//Enter an area
  public void mouseExited(MouseEvent me) {}	//Exit an area
  public void mouseClicked(MouseEvent me) {}	//Click


  //MouseMotionListener
  public void mouseDragged(MouseEvent me) {	//fieldMove
    mouseX = me.getX();
    mouseY = me.getY();

    if (moving == true) {	//Move a node
      network.moveObjectByMouse(mouseX, mouseY, touchNumber);
      repaint();
    }

    if (fieldMove == true) {	//Move Field
      network.moveObjects(mouseX - oldMouseX, mouseY - oldMouseY);
      oldMouseX = mouseX;
      oldMouseY = mouseY;
      repaint();
    }
  }

  public void mouseMoved(MouseEvent me) {
    mouseX = me.getX();
    mouseY = me.getY();
    touchNumber = network.getOnMouseObjectAddLeft(mouseX, mouseY);

    if (touchNumber >= 0 && text.segmentNumber > 1) {
      text.focus.mainFocusSegment = touchNumber;
      text.focus.subFocusSegment = text.segmentRelation.rank[touchNumber][1];//

      if (touchNumber != oldTouchNumber) {
        repaintOthersByTouch();
        executeAllByTouch();
        oldTouchNumber = touchNumber;
      }
    }
    repaint();
  }
}
