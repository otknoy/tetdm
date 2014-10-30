//
// Mining module for TETDM
// RelationCheck.java Version 0.30
// Copyright(C) 2011 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//



package module.MiningModules.RelationCheck;

import source.*;
import source.TextData.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class RelationCheck extends MiningModule {
  StringWriter sw1;
  BufferedWriter bw1;

  String checkedText;

  public RelationCheck() {
    setModuleID(17);
    pairingVisualizationID = new int[] {3};
    setToolType(2);
    defaultSetPanelModuleID = new int[] {0,0,0,17};
    defaultSetPanelVisualizationID = new int[] {1013,1016,1016,3};
    focusTouchExecute = true;
  }

  public void initializePanel() {}

  public void initializeData() {}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 4501:
      case 0:
        if (text.focus.mainFocusSegment != -1 && text.focus.subFocusSegment != -1) {
          checkedText = checkText();
        } else {
          checkedText = "Not exist focused texts.";
        }
        resetData();
        setDataString(checkedText);
        break;

      case 1:
        checkedText = "Please touch nodes in the left end Panel.";
        resetData();
        setDataString("<HTML><BODY>"+checkedText+"</BODY></HTML>");
        displayOperations(0);

        break;
    }
  }

  public void additionalPanelSet() {
    displayModuleFirst(1016, 21);
    displayModule(1016, 22);
    executeModule(17,1);
  }

  public String checkText() {
    sw1 = new StringWriter();
    bw1 = new BufferedWriter(sw1);

    int seg1 = text.focus.mainFocusSegment;
    int seg2 = text.focus.subFocusSegment;

    try {
      //header
      bw1.write("<HTML><HEAD><TITLE>BBS ANALYSIS</TITLE></HEAD><BODY>");
      bw1.write("<H1>RELATION BETWEEN [<font color=red>");
      bw1.write(Integer.toString(seg1+1));
      bw1.write("</font>] and [<font color=red>");
      bw1.write(Integer.toString(seg2+1));
      bw1.write("</font>] in ");
      bw1.write(text.sourceFilename);
      bw1.write("</H1>");

      int pa = (int)(text.segmentRelation.cond[seg1][seg2] * 100);
      int pb = (int)(text.segmentRelation.cond[seg2][seg1] * 100);
      bw1.write(Integer.toString(pa));
      bw1.write("% of ");
      bw1.write(Integer.toString(seg1+1));
      bw1.write(" is included in ");
      bw1.write(Integer.toString(seg2+1));
      bw1.write(", ");
      bw1.write(Integer.toString(pb));
      bw1.write("% of ");
      bw1.write(Integer.toString(seg2+1));
      bw1.write(" is included in ");
      bw1.write(Integer.toString(seg1+1));

      bw1.write("<H2><font color=black>Number of Comments = ");
      bw1.write(Integer.toString(text.segmentNumber));
      bw1.write("</font></H2>");

      // each segment evaluation
      bw1.write("<TABLE BORDER=2 BORDERCOLOR=green>");
      bw1.write("<TR>");
      bw1.write("<TD ALIGN=right>No.</TD>");
      bw1.write("<TD ALIGN=right>Comment</TD>");
      bw1.write("<TD ALIGN=right>Kind Num</TD>");
//			bw1.write("<TD ALIGN=right>ALL Num</TD>");
      bw1.write("</TR>");

      for (int i=0; i<text.segmentNumber; i++)
        if (i == seg1 || i == seg2) {
          bw1.write("<TR>");
          bw1.write("<TD ALIGN=right>");
          bw1.write(Integer.toString(text.segment[i].segmentID+1));
          bw1.write("</TD>");
          bw1.write("<TD>");

          for (int j=0; j<text.segment[i].sentenceNumber; j++)
            for (int k=0,x=0; k<text.segment[i].sentence[j].wordNumber; k++)
              if (text.segment[i].sentence[j].wordIDList[k] >= 0) {
                if (text.keyword[text.segment[i].sentence[j].keywordIDList[x]].appearingSegmentTable[seg1] == 0 ||
                    text.keyword[text.segment[i].sentence[j].keywordIDList[x]].appearingSegmentTable[seg2] == 0) {
                  bw1.write("<span style=background-color:aqua;>");
                  bw1.write(text.segment[i].sentence[j].word[k]);
                  bw1.write("</span>");
                } else {
                  bw1.write(text.segment[i].sentence[j].word[k]);
                }
                x++;
              } else {
                bw1.write(text.segment[i].sentence[j].word[k]);
              }

          bw1.write("</TD>");
          bw1.write("<TD ALIGN=right>");
          bw1.write(Integer.toString(text.segment[i].kindKeywordNumber - text.segmentRelation.coFrequency[seg1][seg2]));
          bw1.write("/");
          bw1.write(Integer.toString(text.segment[i].kindKeywordNumber));
          bw1.write("</TD>");
//					bw1.write("<TD ALIGN=right>"+text.segment[i].wordNumber+"</TD>");
          bw1.write("</TR>");
        }

      for (int i=0; i<text.segmentNumber; i++)
        if (i == seg1 || i == seg2) {
          bw1.write("<TR>");
          bw1.write("<TD ALIGN=right>");
          bw1.write(Integer.toString(text.segment[i].segmentID+1));
          bw1.write("</TD>");
          bw1.write("<TD>");

          for (int j=0; j<text.segment[i].sentenceNumber; j++)
            for (int k=0,x=0; k<text.segment[i].sentence[j].wordNumber; k++)
              if (text.segment[i].sentence[j].wordIDList[k] >= 0) {
                if (!(text.keyword[text.segment[i].sentence[j].keywordIDList[x]].appearingSegmentTable[seg1] == 0 ||
                      text.keyword[text.segment[i].sentence[j].keywordIDList[x]].appearingSegmentTable[seg2] == 0)) {
                  bw1.write("<span style=background-color:fuchsia;>");
                  bw1.write(text.segment[i].sentence[j].word[k]);
                  bw1.write("</span>");
                } else {
                  bw1.write(text.segment[i].sentence[j].word[k]);
                }
                x++;
              } else {
                bw1.write(text.segment[i].sentence[j].word[k]);
              }

          bw1.write("</TD>");
          bw1.write("<TD ALIGN=right>");
          bw1.write(Integer.toString(text.segmentRelation.coFrequency[seg1][seg2]));
          bw1.write("/");
          bw1.write(Integer.toString(text.segment[i].kindKeywordNumber));
          bw1.write("</TD>");
//					bw1.write("<TD ALIGN=right>"+text.segment[i].wordNumber+"</TD>");
          bw1.write("</TR>");
        }

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=right>No.</TD>");
      bw1.write("<TD ALIGN=right>Comment</TD>");
      bw1.write("<TD ALIGN=right>Kind Num</TD>");
//			bw1.write("<TD ALIGN=right>ALL Num</TD>");
      bw1.write("</TR>");

      bw1.write("</TABLE>");
      bw1.write("</BODY></HTML>");
      bw1.flush();
    } catch (Exception e) {
      System.out.println("Error in output_summary");
    }

    return sw1.toString();
  }
}


