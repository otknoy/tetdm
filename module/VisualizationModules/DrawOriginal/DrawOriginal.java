//
// Visuzalization module for TETDM
// DrawOriginal.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//


package module.VisualizationModules.DrawOriginal;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class DrawOriginal extends VisualizationModule implements MouseListener, MouseMotionListener {
  //For objects operation
  int mouseX, mouseY;			//Coordinates for a mouse
  int oldMouseX, oldMouseY;	//Last Coordinates for a mosue

  int touchNumber;				//Node Number of being touched
  int oldTouchNumber;

  boolean moving;			//Flag for node moving
  boolean fieldMove;		//Flag for field moving

  DisplayNetwork network;


  public DrawOriginal() {
    setModuleID(1013);
    setToolType(1);
  }

  public void initializePanel() {
    addMouseListener(this);			//MouseListener
    addMouseMotionListener(this);	//MouseMotionListener
  }

  public void initializeData() {
    touchNumber = -1;

    moving = false;
    fieldMove = false;
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 4501:
      case 0:

        String names[] = new String[text.segmentNumber];
        for (int i=0; i<names.length; i++) {
          names[i] = "SEG"+(i+1);
        }

        network = new DisplayNetwork(names, 20, Color.black);

        text.segmentRelation.createStrongestLink(text.segmentRelation.cos);
        //		text.segmentRelation.optimize_link(text.segmentRelation.cos, 0.5);


        for (int i=0; i<network.objectNumber; i++)
          for (int j=0; j<network.objectNumber; j++) {
            network.link[i][j] = text.segmentRelation.link[i][j];
            network.value[i][j] = text.segmentRelation.value[i];
          }

        for (int i=0; i<network.objectNumber; i++) {
          network.object[i].x = (int)(sizeX/2 + (sizeX/2-50) * Math.cos(i));
          network.object[i].y = (int)(network.value[i][0] * sizeY);
        }

//				saveData();
        repaint();
        break;

      case 1:
        saveData();
        break;
    }
  }

  public void saveData() {
    String datafile = "OriginalData";
    String saveFile = myModulePath+datafile;
    String saveData[];

    saveData = new String[text.segmentRelation.number];

    for (int i=0; i<text.segmentRelation.number; i++)
//			saveData[i] = network.object[i].word+" "+network.value[i][0];
    {
      saveData[i] = text.segmentRelation.name[i]+" "+network.value[i][0];
    }

    fileWriteArray(saveFile, saveData, saveData.length);
  }

  //////////DRAWING

  void drawBackground(Graphics2D g2) {
    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, sizeX, (int)(sizeY*0.5));

    g2.setColor(Color.yellow);
    g2.fillRect(0,(int)(sizeY*0.5), sizeX, (int)(sizeY*0.75));

    g2.setColor(Color.red);
    g2.fillRect(0,(int)(sizeY*0.75), sizeX, sizeY);

  }

  //Display links
  public void drawLinks(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    network.drawLinkAddLeftByArrow(g2);
  }

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
    network.drawLinkAddLeftByArrow(g2,i,j);

    network.drawWord(g2,i,Color.green);
    network.drawWord(g2,j);

    g2.setColor(Color.green);
    network.drawLinkValue(g2,i,j);

    g2.setColor(Color.green);
    network.fillOval(g2,i);

    g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
    g2.setColor(Color.black);
    network.drawOval(g2,i);
  }

  public void paintComponent(Graphics g) {

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
