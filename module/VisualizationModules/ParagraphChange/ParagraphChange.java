//
// Visuzalization module for TETDM
// ParagraphChange.java Version 0.30
// Copyright(C) 2013 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.ParagraphChange;		// Replace ALL [VisualizationStyle] with your [module name]

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.*;

public class ParagraphChange extends VisualizationModule implements MouseMotionListener, MouseListener {
  String buttonNamesInJapanese[];

  int space = 20;
  int buttonSize = 30;
  int width;
  int height;
  BOX box[];

  class BOX {
    int x, y;
    int index;
    String assist;
  }

  int index[];
  int boxY[];

  int desirableIndex[];  //desirable index order;
  int advice[];

  //For objects operation
  int mouseX, mouseY;			//Coordinates for a mouse
  int posiX,posiY;

  boolean dragging;
  int oldTouchNumber;
  int touchNumber;				//Node Number of being touched

  int line;
  int length;


  public ParagraphChange() {
    setModuleID(18);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S
                             0,1,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(2);
  }

  public void initializePanel() {
    buttonNamesInJapanese = fileReadArray();
    addMouseListener(this);			//MouseListener
    addMouseMotionListener(this);	//MouseMotionListener
  }

  public void initializeData() {
    box = new BOX[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      box[i] = new BOX();
    }

    oldTouchNumber = -1;
    touchNumber = -1;
    dragging = false;

    index = new int[text.segmentNumber];
    boxY = new int[text.segmentNumber];
    advice = new int[text.segmentNumber];
    desirableIndex = new int[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      desirableIndex[i] = i;
    }

    calculateCoordinates();
  }

  public boolean setData(int dataID, int data[]) {
    switch (dataID) {
      case 0:
        if (data.length != text.segmentNumber) {
          return false;
        }

        for (int i=0; i<text.segmentNumber; i++)
          if (data[i] < 0 || data[i] >= text.segmentNumber) {
            return false;
          }

        desirableIndex = data;
        return true;
    }
    return false;
  }

  void calculateCoordinates() {
    width = (int)(sizeX * 0.9);
    height = (sizeY - space * (text.segmentNumber+1) - buttonSize)/text.segmentNumber;

    line = (int)(height/11);
    length = (int)((width-10)/10);

    for (int i=0; i<text.segmentNumber; i++) {
      box[i].x = (int)(sizeX * 0.05);
      box[i].y = space + (height+space)*i;
    }
  }

  void createAssist() {
    getCurrentIndex();

    int current[] = new int[text.segmentNumber];

    for (int i=0; i<text.segmentNumber; i++) {
      current[index[i]] = i;
    }

    for (int i=0; i<text.segmentNumber; i++) {
      advice[desirableIndex[i]] = current[desirableIndex[i]] - i;  // paragraphNumber - ranking
    }

    String set,ad;
    int loop;
    for (int i=0; i<text.segmentNumber; i++) {
      set="";
      ad = buttonNamesInJapanese[0];
      loop = advice[i];
      if (advice[i] < 0) {
        ad = buttonNamesInJapanese[1];
        loop = advice[i] * (-1);
      }

      for (int j=0; j<loop; j++) {
        set += ad;
      }

      box[i].assist = set;
    }

//        for(int i=0;i<text.segmentNumber;i++)
    //          System.out.println("ADVICE FOR SEG "+i+" = "+advice[i]);
  }

  void getCurrentIndex() {
    for (int i=0; i<text.segmentNumber; i++) {
      boxY[i] = -box[i].y;
    }

    Qsort.initializeIndex(index,text.segmentNumber);
    Qsort.quicksort(boxY,index,text.segmentNumber);
  }

  void saveText() {
    getCurrentIndex();

    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    try {
      for (int i=0; i<text.segmentNumber; i++) {
        bw.write(text.segment[index[i]].segmentText);
        if (i != text.segmentNumber -1) {
          bw.write(text.control.sp1.segment_tag);
        }
        bw.newLine();
      }
      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in TextDisplay");
    }
    text.fileSave(sw.toString());
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        repaint();
        break;
      case 1:
        String LOG = "SAVE EXCHANGE";
        for (int i=0; i<text.segmentNumber; i++) {
          LOG = LOG+" "+index[i];
        }
        writeActionLog(LOG);
        saveText();
        break;
      case 2:
        for (int i=0; i<text.segmentNumber; i++) {
          index[i] = desirableIndex[i];
        }
        calculateCoordinates2();
        repaint();
        break;
    }
  }



  //background
  public void draw_background(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillRect(0,0, sizeX, sizeY);

    g2.setColor(Color.yellow);
    g2.fillRect(0,sizeY-buttonSize, sizeX/2, buttonSize);
    g2.setColor(Color.green);
    g2.fillRect(sizeX/2,sizeY-buttonSize, sizeX/2, buttonSize);

    g2.setFont(new Font("Dialog", Font.BOLD, 20));
    g2.setColor(Color.black);
    g2.drawString(buttonNamesInJapanese[3],sizeX/4-buttonNamesInJapanese[3].length()*20/2, sizeY-5);
    g2.drawString(buttonNamesInJapanese[4],3*sizeX/4-buttonNamesInJapanese[4].length()*20/2, sizeY-5);

    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(2.0f));
    if (touchNumber == -3) {
      g2.drawRect(0,sizeY-buttonSize, sizeX/2-1, buttonSize-2);
    }
    if (touchNumber == -2) {
      g2.drawRect(sizeX/2,sizeY-buttonSize, sizeX/2-1, buttonSize-2);
    }
  }

  public void draw_Paragraph(Graphics2D g2, int segNumber) {
    g2.setColor(new Color(0xFF,0xEF,0xD5));
    g2.setStroke(new BasicStroke(2.0f));

    g2.setColor(new Color(0xFF,0xEF,0xD5));
    g2.fillRect(box[segNumber].x,box[segNumber].y,width,height);

    g2.setColor(new Color(165,42,42));
    g2.drawRect(box[segNumber].x,box[segNumber].y,width,height);

    g2.setFont(new Font("Dialog", Font.BOLD, 10));
    g2.setColor(Color.black);
    for (int j=0; j<line; j++)
      if ((j+1)*length > text.segment[segNumber].segmentText.length()) {
        g2.drawString(text.segment[segNumber].segmentText.substring(j*length,text.segment[segNumber].segmentText.length()),box[segNumber].x+5, box[segNumber].y+11+j*11);
        break;
      } else {
        g2.drawString(text.segment[segNumber].segmentText.substring(j*length,(j+1)*length),box[segNumber].x+5, box[segNumber].y+11+j*11);
      }
  }

  public void draw_Paragraph(Graphics2D g2) {
    g2.setColor(new Color(0xFF,0xEF,0xD5));
    g2.setStroke(new BasicStroke(2.0f));

    for (int i=text.segmentNumber-1; i>=0; i--) {
      g2.setColor(new Color(0xFF,0xEF,0xD5));
      g2.fillRect(box[i].x,box[i].y,width,height);

      g2.setColor(new Color(165,42,42));
      g2.drawRect(box[i].x,box[i].y,width,height);

      g2.setFont(new Font("Dialog", Font.BOLD, 10));
      g2.setColor(Color.black);
      for (int j=0; j<line; j++)
        if ((j+1)*length > text.segment[i].segmentText.length()) {
          g2.drawString(text.segment[i].segmentText.substring(j*length,text.segment[i].segmentText.length()),box[i].x+5, box[i].y+11+j*11);
          break;
        } else {
          g2.drawString(text.segment[i].segmentText.substring(j*length,(j+1)*length),box[i].x+5, box[i].y+11+j*11);
        }
    }
  }

  public void draw_assist(Graphics2D g2) {
    g2.setColor(Color.blue);
    g2.setFont(new Font("Dialog", Font.BOLD, 16));
    for (int i=0; i<text.segmentNumber; i++) {
      g2.drawString(buttonNamesInJapanese[2]+" "+(i+1)+" "+box[i].assist, box[i].x, box[i].y - 1);
    }
  }



  void calculateCoordinates2() {
    for (int i=0; i<text.segmentNumber; i++) {
      box[index[i]].x = (int)(sizeX * 0.05);
      box[index[i]].y = space + (height+space)*i;
    }
  }

  //////////paint
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    boolean change = getPanelSize();

    if (change) {
      calculateCoordinates();
    }

    createAssist();

    draw_background(g2);		//background
    draw_Paragraph(g2);
    draw_assist(g2);

    if (touchNumber >= 0) {
      draw_Paragraph(g2,touchNumber);
      g2.setColor(Color.green);
      g2.drawRect(box[touchNumber].x,box[touchNumber].y,width,height);
    }
  }

  public void update(Graphics g) {	//avoid from blinking
    paintComponent(g);
  }

  //MouseListener
  public void mousePressed(MouseEvent me) {	//Press
    if (touchNumber >= 0) {
      dragging = true;
      return;
    }
  }

  public void mouseReleased(MouseEvent me) {	//Release
    if (dragging == true)
      if (touchNumber >= 0) {
        getCurrentIndex();
        calculateCoordinates2();
      }

    dragging = false;

    repaint();
  }

  public void mouseEntered(MouseEvent me) {}	//Enter an area
  public void mouseExited(MouseEvent me) {}	//Exit an area

  public void mouseClicked(MouseEvent me) {	//Click
//        System.out.println("CLICKED");

    if (touchNumber == -2) {
      displayOperations(1);
    }

    if (touchNumber == -3) {
      displayOperations(2);
    }
  }



  //MouseMotionListener
  public void mouseDragged(MouseEvent me) {
    if (touchNumber >= 0) {
      try {
        mouseX = me.getX();
        mouseY = me.getY();

        box[touchNumber].x = mouseX-posiX;
        box[touchNumber].y = mouseY-posiY;

        repaint();
      } catch (StackOverflowError e) {
        System.out.println("Stack OVERFLOW");
      }
    }
  }

  public void mouseMoved(MouseEvent me) {
    mouseX = me.getX();
    mouseY = me.getY();
    touchNumber = CatchNode(mouseX,mouseY);

    if (touchNumber != oldTouchNumber) {
      repaint();
      oldTouchNumber = touchNumber;
    }
  }

  public int CatchNode(int mouseX, int mouseY) {	//Investigate number of selected node
    for (int i=0; i<text.segmentNumber; i++)
      if (mouseX > box[i].x && mouseX < box[i].x+width && mouseY > box[i].y && mouseY < box[i].y + height) {
        posiX = mouseX - box[i].x;
        posiY = mouseY - box[i].y;
        return i;
      }

    if (mouseY > sizeY-buttonSize) {
      if (mouseX > sizeX/2) {
        return -2;
      } else {
        return -3;
      }
    }

    return -1;
  }
}
