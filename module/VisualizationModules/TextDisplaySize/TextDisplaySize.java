//
// Visuzalization module for TETDM
// TextDisplaySize.java Version 0.36
// Copyright(C) 2011 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You Should read the README file.
//

package module.VisualizationModules.TextDisplaySize;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class TextDisplaySize extends TextDisplay {
  Sizepanel sizepanel;

  public TextDisplaySize() {
    setModuleID(11);
    dataNumbers = new int[] {0,0,0,1,0,0,0,0,0,0,0};
    setToolType(2);
  }

  public void initializePanel() {
    text1 = new JTextPane();
    text1.setContentType("text/plain");
    scroll1 = new JScrollPane(text1);
    add(scroll1);

    sizepanel = new Sizepanel();
    sizepanel.setPreferredSize(new Dimension(0,30));
    add("South",sizepanel);
  }

  class Sizepanel extends JPanel implements MouseListener {
    int sizex;
    int sizey;
    int mousex,mousey;

    String down = "Smaller Size";
    String up = "Larger Size";

    //constructer
    Sizepanel() {
      addMouseListener(this);
    }

    //background
    public void draw_background(Graphics2D g2) {
      sizex = getWidth();
      sizey = getHeight();

      g2.setColor(new Color(0xff, 0xd7, 0x72));
      g2.fillRect(0,0,sizex/2,sizey);
      g2.setColor(new Color(0xfa, 0x80, 0x72));
      g2.fillRect(sizex/2,0,sizex/2,sizey);

      g2.setFont(new Font("Dialog", Font.BOLD, 20));
      g2.setColor(Color.black);
      int downx = sizex/4 - down.length()*5;
      int upx = 3*sizex/4 - up.length()*5;
      if (downx < 5) {
        downx = 5;
      }
      if (upx < sizex/2+5) {
        upx = sizex/2+5;
      }
      g2.drawString(down, downx ,sizey/2+10);
      g2.drawString(up, upx ,sizey/2+10);
    }

    //////////paint
    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;

      draw_background(g2);		//background
    }

    public void update(Graphics g) {	//avoid from blinking
      paintComponent(g);
    }

    //MouseListener
    public void mousePressed(MouseEvent me) {}	//Press
    public void mouseReleased(MouseEvent me) {}	//Release
    public void mouseEntered(MouseEvent me) {}	//Enter an area
    public void mouseExited(MouseEvent me) {}	//Exit an area

    public void mouseClicked(MouseEvent me) {	//Click
      mousex = me.getX();
      mousey = me.getY();

      if (mousex < sizex/2) {
        fontsize = 14 * mousex * 2 /sizex;
      } else {
        fontsize = 14 + (mousex - sizex/2)/10;
      }
      displayOperations(0);
    }
  }
}

