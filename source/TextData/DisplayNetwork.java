//
// Core Program for TETDM
// DisplayNetwork.java Version 0.50
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;
import java.util.*;

import java.awt.*;

public class DisplayNetwork {
  public DisplayObject object[];
  public int objectNumber;

  public double positionX, positionY;    //relative position for mouse move

  public boolean link[][];
  public double value[][];
  public double linkThreshold;

  public int linkNumber;
  public int activeNumber;

  public SpringModel springModel;

  public DisplayNetwork() {}

  public DisplayNetwork(String word[], int fontSize, Color color) {
    objectNumber = word.length;
    object = new DisplayObject[objectNumber];

    for (int i=0; i<objectNumber; i++) {
      object[i] = new DisplayObject(word[i],i,0,0,fontSize,color);
    }

    link = new boolean[objectNumber][objectNumber];
    value = new double[objectNumber][objectNumber];

    springModel = new SpringModel(object);
  }

  //Node
  public void setNodeValue(double value[]) {
    for (int i=0; i<objectNumber; i++) {
      object[i].value = value[i];
    }
  }

  //Relation
  public void setLink(boolean link[][]) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++) {
        this.link[i][j] = link[i][j];
      }
  }

  public void setLinkValue(double value[][]) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++) {
        this.value[i][j] = value[i][j];
      }
  }

  public void createLinksFromValue() {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (value[i][j] > linkThreshold && object[i].active && object[j].active) {
          link[i][j] = true;
        } else {
          link[i][j] = false;
        }

    calculateLinkNumber();
  }

  public void calculateLinkNumber() {
    linkNumber = 0;
    for (int i=0; i<objectNumber; i++) {
      object[i].linkNumber = 0;
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true) {
          object[i].linkNumber++;
          linkNumber++;
        }
    }
  }

  public void drawLink(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true && object[i].active && object[j].active)
          g2.drawLine((int)object[i].x+object[i].sizeX/2,(int)object[i].y+object[i].sizeY/2,
                      (int)object[j].x+object[j].sizeX/2,(int)object[j].y+object[j].sizeY/2);
  }

  public void drawLink(Graphics2D g2, int i, int j) {
    g2.drawLine((int)object[i].x+object[i].sizeX/2,(int)object[i].y+object[i].sizeY/2,
                (int)object[j].x+object[j].sizeX/2,(int)object[j].y+object[j].sizeY/2);
  }

  public void drawLinkAddLeft(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true && object[i].active && object[j].active)
          g2.drawLine((int)object[i].x-object[i].fontSize/2,(int)object[i].y+object[i].fontSize/2,
                      (int)object[j].x-object[j].fontSize/2,(int)object[j].y+object[i].fontSize/2);
  }

  public void drawLinkAddLeft(Graphics2D g2, int i, int j) {
    g2.drawLine((int)object[i].x-object[i].fontSize/2,(int)object[i].y+object[i].fontSize/2,
                (int)object[j].x-object[j].fontSize/2,(int)object[j].y+object[i].fontSize/2);
  }

  public void drawArrow(Graphics2D g2, int x0, int y0, int x1, int y1, int r) {
    double t;
    int x, y;
    double l = 30;	// length of arrow wings, default:30

    //Angle of end of an arrow
    double dt = Math.PI / 6.0;  // pi/6 = 30 degree

    t = Math.atan2((double)(y1 - y0), (double)(x1 - x0));	// arrow vector

    //FROM
    x0 = x0 + (int)(r * Math.cos(t));
    y0 = y0 + (int)(r * Math.sin(t));

    //TO
    x1 = x1 - (int)(r * Math.cos(t));
    y1 = y1 - (int)(r * Math.sin(t));

    //LINE
    g2.drawLine(x0, y0, x1, y1);

    //WING
    x = x1 - (int)(l * Math.cos(t - dt));
    y = y1 - (int)(l * Math.sin(t - dt));
    g2.drawLine(x1, y1, x, y);
    x = x1 - (int)(l * Math.cos(t + dt));
    y = y1 - (int)(l * Math.sin(t + dt));
    g2.drawLine(x1, y1, x, y);
  }

  public void drawLinkByArrow(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true && object[i].active && object[j].active) {
          drawArrow(g2,(int)object[i].x,(int)object[i].y,(int)object[j].x,(int)object[j].y,(int)(object[i].fontSize/2));
        }
  }

  public void drawLinkByArrow(Graphics2D g2, int i, int j) {
    drawArrow(g2,(int)object[i].x,(int)object[i].y,(int)object[j].x,(int)object[j].y,(int)(object[i].fontSize/2));
  }

  public void drawLinkAddLeftByArrow(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true && object[i].active && object[j].active)
          drawArrow(g2,(int)object[i].x-object[i].fontSize/2,(int)object[i].y+object[i].fontSize/2,
                    (int)object[j].x-object[j].fontSize/2,(int)object[j].y+object[i].fontSize/2,(int)(object[i].fontSize/2));
  }

  public void drawLinkAddLeftByArrow(Graphics2D g2, int i, int j) {
    drawArrow(g2,(int)object[i].x-object[i].fontSize/2,(int)object[i].y+object[i].fontSize/2,
              (int)object[j].x-object[j].fontSize/2,(int)object[j].y+object[i].fontSize/2,(int)(object[i].fontSize/2));
  }

  public void drawLinkValue(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      for (int j=0; j<objectNumber; j++)
        if (link[i][j] == true && object[i].active && object[j].active)
          g2.drawString(String.format("%.2f",value[i][j]),
                        (int)((object[i].x+object[j].x)/2),(int)((object[i].y+object[j].y)/2));
  }

  public void drawLinkValue(Graphics2D g2, int i, int j) {
    g2.drawString(String.format("%.2f",value[i][j]),
                  (int)((object[i].x+object[j].x)/2),(int)((object[i].y+object[j].y)/2));
  }

  //For Objects
  public void drawNodeValue(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active)
        g2.drawString(String.format("%.1f",object[i].value),
                      (int)(object[i].x),(int)(object[i].y + object[i].fontSize*2));
  }

  public void drawNodeValue(Graphics2D g2, int i) {
    g2.drawString(String.format("%.1f",object[i].value), (int)(object[i].x),(int)(object[i].y + object[i].fontSize*2));
  }

  public void centeringFromScreenSize(int screenSizeX, int screenSizeY) {
    for (int i=0; i<objectNumber; i++) {
      object[i].centeringFromScreenSize(screenSizeX, screenSizeY, objectNumber);
    }
  }

  public void reSizeByScreenSize(double changeX, double changeY) {
    for (int i=0; i<objectNumber; i++) {
      object[i].x *= changeX;
      object[i].y *= changeY;
    }
  }

  public void moveObjects(int diffX, int diffY) {
    for (int i=0; i<objectNumber; i++) {
      object[i].x += diffX;
      object[i].y += diffY;
    }
  }

  public int getOnMouseObject(int mouseX, int mouseY) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active)
        if (object[i].isOnMouse(mouseX,mouseY)) {
          positionX = mouseX - object[i].x;
          positionY = mouseY - object[i].y;
          return i;
        }

    return -1;
  }

  public int getOnMouseObjectAddLeft(int mouseX, int mouseY) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active)
        if (object[i].isOnMouseAddLeft(mouseX,mouseY)) {
          positionX = mouseX - object[i].x;
          positionY = mouseY - object[i].y;
          return i;
        }

    return -1;
  }

  public void moveObjectByMouse(int mouseX, int mouseY, int i) {
    object[i].x = mouseX - positionX;
    object[i].y = mouseY - positionY;
  }

  public void drawWord(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active) {
        object[i].drawWord(g2);
      }
  }

  public void drawWord(Graphics2D g2, int i) {
    object[i].drawWord(g2);
  }

  public void drawWord(Graphics2D g2, int i, Color color) {
    object[i].drawWord(g2, color);
  }

  public void drawRect(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active) {
        object[i].drawRect(g2);
      }
  }

  public void drawRect(Graphics2D g2, int i) {
    object[i].drawRect(g2);
  }

  public void fillRect(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active) {
        object[i].fillRect(g2);
      }
  }

  public void fillRect(Graphics2D g2, int i) {
    object[i].fillRect(g2);
  }

  public void drawOval(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active) {
        object[i].drawOval(g2);
      }
  }

  public void drawOval(Graphics2D g2, int i) {
    object[i].drawOval(g2);
  }

  public void fillOval(Graphics2D g2) {
    for (int i=0; i<objectNumber; i++)
      if (object[i].active) {
        object[i].fillOval(g2);
      }
  }

  public void fillOval(Graphics2D g2, int i) {
    object[i].fillOval(g2);
  }

  public void setActive(boolean active[]) {
    for (int i=0; i<objectNumber; i++) {
      object[i].active = active[i];
    }

    countActiveNumber();
  }

  public void setActiveByLinkInActive() {
    for (int i=0; i<objectNumber; i++)
      if (object[i].linkNumber > 1 && object[i].active) { //exclude self-link
        object[i].active = true;
      } else {
        object[i].active = false;
      }

    countActiveNumber();
  }

  public void countActiveNumber() {
    activeNumber = 0;
    for (int i=0; i<objectNumber; i++)
      if (object[i].active == true) {
        activeNumber++;
      }
  }

  //SPRING MODEL
  public void startSpringModel(int screenSizeX, int screenSizeY) {
    //requires value, link, object[i].(x,y,fontSize,linkNumber)
    springModel.startSpringModel(screenSizeX, screenSizeY, value, link);
  }

  public void stopSpringModel() {
    springModel.stopSpringModel();
  }
}
