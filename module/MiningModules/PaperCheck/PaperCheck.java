//
// Mining module for TETDM
// PaperCheck.java Version 0.30
// Copyright(C) 2011 - 2012 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//



package module.MiningModules.PaperCheck;

import source.*;
import source.TextData.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class PaperCheck extends MiningModule implements ActionListener {
  JButton bt1;

  String noises[];	//Noise/Stop words
  int noiseNumber;

  int score;

  String checkedText;

  public PaperCheck() {
    setModuleID(2);
    pairingVisualizationID = new int[] {3,5};
  }

  public void initializePanel() {
    bt1 = new JButton("checkresult");

    operationPanel.setBackground(Color.RED);

    bt1.setBackground(Color.RED);
    bt1.addActionListener(this);
    operationPanel.add(bt1);
  }

  public void initializeData() {
    noises = fileReadArray(myModulePath+"checklist");
    noiseNumber = noises.length;
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        setDataString(10000,myModulePath+"checklist");	//For FileDisplay

        checkedText = checkText();
        resetData();
        setDataString(checkedText); //For TextDisplayHtml
        setDataInteger(1,(100-score)/5);//For TextInfo***
        displayOperations(0);
        break;

      case 1:
        initializeData();

        checkedText = checkText();
        resetData();
        setDataString(checkedText); //For TextDisplayHtml
        setDataInteger(1,(100-score)/5);//For TextInfo***
        displayOperations(0);
        break;
    }
  }

  //Action listener
  public void actionPerformed(ActionEvent e) {
//		insideOfActionPerformed(e);

    if (e.getSource() == bt1) {
      displayOtherModule(5,991);
      miningOperations(1);
      executeOtherModule(19,2);
      executeOtherModule(19,1);
    }
  }

  public String checkText() {
    StringWriter sw1 = new StringWriter();
    BufferedWriter bw1 = new BufferedWriter(sw1);

    score = 100;

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
        bw1.write("<TD ALIGN=right>"+(i+1)+"</TD>");
        bw1.write("<TD>");

        for (int j=0; j<text.segment[i].sentenceNumber; j++)
          for (int k=0,x; k<text.segment[i].sentence[j].wordNumber; k++) {
            for (x=0; x<noiseNumber; x++)
              if (text.segment[i].sentence[j].wordEndForm[k].equals(noises[x])) {
                bw1.write("<span style=background-color:lime;>"+text.segment[i].sentence[j].word[k]+"</span>");
                score-=5;
                break;
              }

            if (x == noiseNumber) {
              bw1.write(text.segment[i].sentence[j].word[k]);
            }
          }
        bw1.write("</TD>");
        bw1.write("</TR>");
      }

      bw1.write("<TR><TD></TD><TD>");
      bw1.write("YOUR SCORE = "+score+"</TD></TR>");

      bw1.write("</TABLE>");
      bw1.write("</BODY></HTML>");

      bw1.flush();
    } catch (Exception e) {
      System.out.println("Error in PaperCheck");
    }
    return sw1.toString();
  }
}


