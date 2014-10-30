//
// Mining module for TETDM
// FocusWord.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

package module.MiningModules.FocusCheck;

import source.*;
import source.TextData.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class FocusCheck extends MiningModule implements ActionListener {
  String checkedText;

  JToggleButton button[];
  ButtonGroup bg;
  String buttonNamesInJapanese[];

  int type;

  public FocusCheck() {
    setModuleID(18);
    pairingVisualizationID = new int[] {3};
    setToolType(2);
    focusTouchExecute = true;
    focusClickExecute = true;
  }

  public void initializePanel() {
    button = new JToggleButton[6];
    bg = new ButtonGroup();

    type = 0;

    for (int i=0; i<6; i++) {
      button[i] = new JToggleButton();
      button[i].addActionListener(this);
      operationPanel.add(button[i]);
      bg.add(button[i]);
    }
    buttonNamesInJapanese = fileReadArray();
    button[type].setSelected(true);
  }

  public void initializeData() {
    String buttonNames[] = {"Keyword","Keywords","Sentence","Sentences","Segment","Segments"};

    if (isMenuInJapanese())
      for (int i=0; i<6; i++) {
        button[i].setText(buttonNamesInJapanese[i]);
      }
    else
      for (int i=0; i<6; i++) {
        button[i].setText(buttonNames[i]);
      }
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {

      case 0:
        checkedText = check_text();
        resetData();
        setDataString(checkedText);
        break;

      case 4501:
      case 4502:
        checkedText = check_text();
        resetData();
        setDataString(checkedText);
        displayOperations(0);
        break;

      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
        type = optionNumber - 1;
        break;


    }
  }

  public void checkMainSub(BufferedWriter bw1, int i, int mainK, int subK) {
    try {
      switch (type) {
        case 0:
          for (int j=0; j<text.segment[i].sentenceNumber; j++)
            for (int k=0; k<text.segment[i].sentence[j].wordNumber; k++)
              if (text.segment[i].sentence[j].wordIDList[k] == mainK && mainK >= 0) {
                bw1.write("<span style=background-color:red;>"+text.segment[i].sentence[j].word[k]+"</span>");
              } else if (text.segment[i].sentence[j].wordIDList[k] == subK && subK >= 0) {
                bw1.write("<span style=background-color:aqua;>"+text.segment[i].sentence[j].word[k]+"</span>");
              } else {
                bw1.write(text.segment[i].sentence[j].word[k]);
              }
          break;

        case 2:
          for (int j=0; j<text.segment[i].sentenceNumber; j++)
            if (text.segment[i].positionOfFirstSentence + j == mainK) {
              bw1.write("<span style=background-color:red;>"+text.segment[i].sentence[j].sentenceText+"</span>");
            } else if (text.segment[i].positionOfFirstSentence + j == subK) {
              bw1.write("<span style=background-color:aqua;>"+text.segment[i].sentence[j].sentenceText+"</span>");
            } else {
              bw1.write(text.segment[i].sentence[j].sentenceText);
            }
          break;

        case 4:
          if (i == mainK) {
            bw1.write("<span style=background-color:red;>"+text.segment[i].segmentText+"</span>");
          } else if (i == subK) {
            bw1.write("<span style=background-color:aqua;>"+text.segment[i].segmentText+"</span>");
          } else {
            bw1.write(""+text.segment[i].segmentText);
          }
          break;
      }
    } catch (Exception e) {
      System.out.println("Error in FocusWord1");
    }

  }

  public void checkWords(BufferedWriter bw1, int i) {
    try {
      for (int j=0; j<text.segment[i].sentenceNumber; j++)
        for (int k=0; k<text.segment[i].sentence[j].wordNumber; k++)
          if (text.segment[i].sentence[j].wordIDList[k] >= 0) {
            if (text.focus.focusKeywords[text.segment[i].sentence[j].wordIDList[k]]) {
              bw1.write("<span style=background-color:red;>"+text.segment[i].sentence[j].word[k]+"</span>");
            }
          } else {
            bw1.write(text.segment[i].sentence[j].word[k]);
          }
    } catch (Exception e) {
      System.out.println("Error in FocusWord4");
    }
  }

  public void checkSet(BufferedWriter bw1, int i) {
    try {
      switch (type) {
        case 3:
          for (int j=0; j<text.segment[i].sentenceNumber; j++)
            if (text.focus.focusSentences[ text.segment[i].positionOfFirstSentence + j ]) {
              bw1.write("<span style=background-color:red;>"+text.segment[i].sentence[j].sentenceText+"</span>");
            } else {
              bw1.write(text.segment[i].sentence[j].sentenceText);
            }
          break;

        case 5:
          if (text.focus.focusSegments[i]) {
            bw1.write("<span style=background-color:red;>"+text.segment[i].segmentText+"</span>");
          } else {
            bw1.write(""+text.segment[i].segmentText);
          }
          break;
      }
    } catch (Exception e) {
      System.out.println("Error in FocusWord3");
    }

  }

  public String check_text() {
    int mainK=-1, subK=-1;

    switch (type) {
      case 0:
        mainK = text.focus.mainFocusKeyword;
        subK = text.focus.subFocusKeyword;
        break;
      case 2:
        mainK = text.focus.mainFocusSentence;
        subK = text.focus.subFocusSentence;
        break;
      case 4:
        mainK = text.focus.mainFocusSegment;
        subK = text.focus.subFocusSegment;
        break;
    }

    StringWriter sw1 = new StringWriter();
    BufferedWriter bw1 = new BufferedWriter(sw1);

    try {
      //header
      bw1.write("<HTML><HEAD><TITLE>ANALYSIS</TITLE></HEAD><BODY>");
      bw1.write("<H1>STATUS OF TEXT</H1>");

      // each segment evaluation
      bw1.write("<TABLE BORDER=2 BORDERCOLOR=black>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=right>No.</TD>");
      bw1.write("<TD ALIGN=right>Comment</TD>");
      bw1.write("</TR>");


      for (int i=0; i<text.segmentNumber; i++) {
        bw1.write("<TR>");
        bw1.write("<TD ALIGN=right>"+(text.segment[i].segmentID+1)+"</TD>");
        bw1.write("<TD>");

        if ( type == 0 || type == 2 || type == 4) {
          checkMainSub(bw1,i,mainK,subK);
        }
        if ( type == 1 ) {
          checkWords(bw1, i);
        }
        if ( type == 3 || type == 5) {
          checkSet(bw1,i);
        }

        bw1.write("</TD>");
        bw1.write("</TR>");
      }

      bw1.write("</TABLE>");
      bw1.write("</BODY></HTML>");
      bw1.flush();
    } catch (Exception e) {
      System.out.println("Error in FocusWord2");
    }

    return sw1.toString();
  }

  public void actionPerformed(ActionEvent e) {
    for (int i=0; i<6; i++)
      if (e.getSource() == button[i]) {
        miningOperations(i+1);
        return;
      }
  }
}


