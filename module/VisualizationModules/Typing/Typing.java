//
// Visuzalization module for TETDM
// Typing.java Version 0.30
// Copyright(C) 2011 System Interface Laboratory (Hiroshima City University) ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//


package module.VisualizationModules.Typing;

import source.*;
import source.TextData.*;
import source.Utility.*;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;



public class Typing extends VisualizationModule implements KeyListener {
  boolean typing,result;

  int maxQuestion;
  int questionNumber;

  char inputChar, nextChar;

  String answers[];
  int seed[];
  String inputWord, lastInputWord;

  long startTime, endTime;
  double lapTime, lastLapTime;

  int totalLength, missed;


  public Typing() {
    setModuleID(20001);
    setToolType(1);
  }

  public void initializePanel() {
    addKeyListener(this);
    answers = fileReadArray(myModulePath+"answer.txt");
  }

  public void initializeData() {
    typing = result = false;
    maxQuestion = 10;
    seed = new int[maxQuestion];
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        repaint();
        break;

      case 1:
        requestFocus();
        startTyping();
        repaint();
        break;

      case 2:
        if (typing == false) {
          return;
        }

        requestFocus();
        if (questionNumber >= maxQuestion) {
          return;
        }

        inputWord = answers[seed[questionNumber]];
        nextTyping();

        if (questionNumber == maxQuestion) {
          endTyping();
          return;
        }

        nextChar = answers[seed[questionNumber]].charAt(0);
        repaint();
        break;

      case 3:
        typing = false;
        repaint();
        break;
    }
  }

  public void setStartTime() {
    startTime = System.nanoTime();
  }

  public double getDiffTime() {
    endTime = System.nanoTime();
    return (double)(endTime - startTime)/1000000000.0;
  }

  public void createSeed() {
    totalLength = 0;
    for (int i=0; i<maxQuestion; i++) {
      seed[i] = (int)(Math.random() * answers.length);
      totalLength += answers[seed[i]].length();
    }

  }

  public String createQuestionWord(String input, String question) {
    String word = input;

    for (int i=input.length(); i<question.length(); i++)
      if (question.charAt(i) == ' ') {
        word += " ";
      } else {
        word += "*";
      }

    return word;
  }

  public void startTyping() {
    typing = true;
    result = false;

    createSeed();

    questionNumber = 0;
    missed = 0;
    lastInputWord = inputWord = "";

    nextChar = answers[seed[0]].charAt(0);

    setStartTime();
    lapTime = lastLapTime = (double)startTime;
  }

  public void nextTyping() {
    lastInputWord = inputWord;
    inputWord = "";
    questionNumber++;

    text.focus.setMainFocusDouble(getDiffTime());
    repaintOthersByTiming();
  }

  public void endTyping() {
    lapTime = getDiffTime();
    writeActionLog("Typing ResultTime = "+lapTime);
    typing = false;
    result = true;
    repaint();
  }

  //////////DRAWING
  public void drawBackground(Graphics2D g2) {
    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, sizeX, sizeY);
    ImageIcon icon1 = new ImageIcon(myModulePath+"tree2.png");
    Image image1 = icon1.getImage();

    ImageIcon icon2 = new ImageIcon(myModulePath+"tet1208moto.gif");
    Image image2 = icon2.getImage();

    if (typing) {
      g2.setColor(Color.white);
      g2.drawLine(20,sizeY/2,sizeX-20,sizeY/2);
    }

    g2.drawImage(image1,sizeX/2+sizeX/4,sizeY/2+sizeY/4,sizeX/4,sizeY/4,this);
    g2.drawImage(image2,sizeX/4,sizeY/2+sizeY/4,sizeX/4,sizeY/4,this);
  }

  public void drawQuestion(Graphics g2) {
    g2.setFont(new Font("Dialog", Font.BOLD, 80));
    g2.setColor(Color.yellow);
    g2.drawString(""+(seed[questionNumber]+1),sizeX/2-40,sizeY/4);

    g2.setFont(new Font("Dialog", Font.BOLD, 40));
    g2.setColor(Color.white);
    g2.drawString(createQuestionWord(inputWord, answers[seed[questionNumber]]),20,sizeY/2);
  }

  public void drawInputKeywords(Graphics g2) {
    g2.setFont(new Font("Dialog", Font.BOLD, 40));
    g2.setColor(Color.yellow);
    g2.drawString(inputWord,20,sizeY/2);
    g2.setColor(Color.gray);
    g2.drawString(lastInputWord,20,3*sizeY/4);

    g2.drawString(" "+nextChar,0,30);
  }

  public void drawTime(Graphics g2) {
    g2.setFont(new Font("Dialog", Font.BOLD, 30));
    g2.setColor(Color.yellow);
    g2.drawString("SCORE = "+((int)(totalLength*60/lapTime - missed*10))+"[points]",20,sizeY/8);
    g2.setColor(Color.green);
    g2.drawString("Speed = "+String.format("%.2f",totalLength*60/lapTime)+"[chars/min]",20,sizeY/4);
    g2.drawString("Missed = "+missed+"[times]",20,3*sizeY/8);
    g2.drawString("Words = "+totalLength+"[words]",20,sizeY/2);
    g2.drawString("Time = "+String.format("%.2f",lapTime)+"[sec]",20,5*sizeY/8);
  }

  public void paintComponent(Graphics g) {

    Graphics2D g2 = (Graphics2D)g;

    boolean change = getPanelSize();

//		if(change)
//			calcu_coordinate();


    drawBackground(g2);	//Set background*

    if (typing) {
      drawQuestion(g2);
      drawInputKeywords(g2);
    }

    if (result) {
      drawTime(g2);
    }

  }



  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }



  public void keyPressed(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {
    if (typing == false) {
      return;
    }

    inputChar = e.getKeyChar();

//        System.out.println("INPUT "+inputChar+" NEXT "+nextChar);

    if (inputChar != nextChar) {
      missed++;
      return;
    }

    inputWord += inputChar;

    if (answers[seed[questionNumber]].length() == inputWord.length()) {
      nextTyping();
      if (questionNumber == maxQuestion) {
        endTyping();
        return;
      }
    }

    nextChar = answers[seed[questionNumber]].charAt(inputWord.length());

    repaint();
  }

}
