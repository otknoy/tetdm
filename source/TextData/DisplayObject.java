//
// Core Program for TETDM
// DisplayObject.java Version 0.50
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;
import java.util.*;

import java.awt.*;

public class DisplayObject {
  public String word;
  public int id;
  public double x,y;
  public int sizeX,sizeY;
  public int fontSize;
  public Color color;

  public boolean active;
  public int linkNumber;

  public double value;

  public DisplayObject() {}

  public DisplayObject(String word, int id, int x, int y, int fontSize, Color color) {
    this.word = word;
    this.id = id;
    this.x = x;
    this.y = y;
    this.fontSize = fontSize;
    this.color = color;

    sizeX = word.length()*fontSize;
    sizeY = fontSize+4;

    active = true;
  }

  public void centeringFromScreenSize(int screenSizeX, int screenSizeY, int allNumber) {
    x = (screenSizeX - sizeX)/2;
    y = (id+1)*(screenSizeY-sizeY)/(allNumber+1);
  }

  public boolean isOnMouse(int mouseX, int mouseY) {
    if (mouseX > x && mouseX < x+sizeX && mouseY > y && mouseY < y + sizeY) {
      return true;
    }

    return false;
  }

  public boolean isOnMouseAddLeft(int mouseX, int mouseY) {
    if (mouseX > x-fontSize && mouseX < x+sizeX && mouseY > y && mouseY < y + sizeY) {
      return true;
    }

    return false;
  }

  public void drawWord(Graphics2D g2) {
    g2.setFont(new Font("Dialog", Font.BOLD, fontSize));
    g2.setColor(color);
    g2.drawString(word,(int)x,(int)(y+fontSize));
  }

  public void drawWord(Graphics2D g2, Color color) {
    g2.setColor(color);
    g2.drawString(word,(int)x,(int)(y+fontSize));
  }

  public void drawRect(Graphics2D g2) {
    g2.drawRect((int)x,(int)y,sizeX,sizeY);
  }

  public void fillRect(Graphics2D g2) {
    g2.fillRect((int)x,(int)y,sizeX,sizeY);
  }

  public void drawOval(Graphics2D g2) {
    g2.drawOval((int)(x-fontSize),(int)y,fontSize,fontSize);
  }

  public void fillOval(Graphics2D g2) {
    g2.fillOval((int)(x-fontSize),(int)y,fontSize,fontSize);
  }
}
