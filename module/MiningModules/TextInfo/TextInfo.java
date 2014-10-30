//
// Mining module for TETDM
// TextInfo.java Version 0.39
// Copyright(C) 2011 WATARU SUNAYAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

package module.MiningModules.TextInfo;

import source.*;
import source.TextData.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class TextInfo extends MiningModule {
  String checkedText;

  String titles[];
  String titlesInJapanese[];

  int mainCharacterID;

  int subjectList[];
  String keySentence;
  double scoreDist[];
  int relationIDs[];
  int noiseNumber;
  int veryLongSentenceNumber;

  int score;

  public TextInfo() {
    setModuleID(19);
    pairingVisualizationID = new int[] {3};
    setToolType(2);
    focusClickExecute = true;
    defaultSetPanelModuleID = new int[] {19,11,12,5,13,2,16};
    defaultSetPanelVisualizationID = new int[] {3,8,2,2,12,3,3};
  }

  public void initializePanel() {}

  public void initializeData() {
    titlesInJapanese = fileReadArray();
    String titleLists[] = {"Items","Results","Segments","Sentences","Subjects","Key Sentence","Lighting Rate","Coherence Rate","","","","","","","",""};

    if (isMenuInJapanese()) {
      titles = titlesInJapanese;
    } else {
      titles = titleLists;
    }
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 2:
      case 4502:/*
				subjectList = getDataIntegerArray(11,0);
				keySentence = getDataString(11,1);
				scoreDist = getDataDoubleArray(12,0);
				relationIDs = getDataIntegerArray(13,2);
				noiseNumber = getDataInteger(2,1);
				veryLongSentenceNumber = getDataInteger(16,1);

				checkedText = check_text();
				resetData();
				setDataString(checkedText);
				break;*/

      case 0:
        subjectList = getNewDataIntegerArray(11,0);
        keySentence = getNewDataString(11,1);
        scoreDist = getNewDataDoubleArray(12,0);
        relationIDs = getNewDataIntegerArray(13,2);
        noiseNumber = getNewDataInteger(2,1);
        veryLongSentenceNumber = getNewDataInteger(16,1);

        checkedText = check_text();
        resetData();
        setDataString(checkedText);
        break;

      case 1:
        displayOperations(0);
        break;
    }
  }
  public String check_text() {
    StringWriter sw1 = new StringWriter();
    BufferedWriter bw1 = new BufferedWriter(sw1);

    try {
      //header
      bw1.write("<HTML><HEAD><TITLE>ANALYSIS</TITLE></HEAD><BODY>");
      bw1.write("<H1>"+titles[11]+" ["+(new File(text.sourceFilename).getName())+"] </H1>");

      // each segment evaluation
      bw1.write("<TABLE BORDER=2 BORDERCOLOR=black>");

      mainCharacterID = 0;
      int max = text.keyword[0].frequencyAsSubject;
      for (int i=0; i<text.keywordNumber; i++)
        if (text.keyword[i].frequencyAsSubject > max) {
          max = text.keyword[i].frequencyAsSubject;
          mainCharacterID = i;
        }

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[14]+"</TD>");
      bw1.write("<TD ALIGN=center><span style=font-size:20px;>");
      bw1.write(text.keyword[mainCharacterID].word);
      bw1.write("</span>("+max+")</TD>");
      bw1.write("</TR>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[4]+"</TD>");
      bw1.write("<TD ALIGN=center><span style=font-size:20px;>");
      for (int i=0; i<subjectList.length; i++) {
        bw1.write(text.keyword[subjectList[i]].word+" ");
      }
      bw1.write("</span></TD>");
      bw1.write("</TR>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[5]+"</TD>");
      bw1.write("<TD ALIGN=center>"+keySentence+"</TD>");
      bw1.write("</TR>");

      bw1.write("</TABLE>");


      //EVALUATION TABLE
      bw1.write("<H1>"+titles[1]+"</H1>");

      bw1.write("<TABLE BORDER=2 BORDERCOLOR=black>");
      score = 100;
      int point;

      int count=0;
      for (int i=0; i<text.sentenceNumber; i++)
        if (scoreDist[i] > 0) {
          count++;
        }

      int a = 0;
      point = 0;
      if (text.sentenceNumber > 0) {
        a = count*100/text.sentenceNumber;
      }
      if (a < 80) {
        point = 80-a;
        score -= point;
      }


      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[6]+"</TD>");
      if (text.sentenceNumber > 0) {
        bw1.write("<TD ALIGN=center><span style=font-size:20px;color:blue;>"+(count*100/text.sentenceNumber)+"%</span> ("+count+"/"+text.sentenceNumber+")</TD>");
      } else {
        bw1.write("<TD ALIGN=center> 0% ("+count+"/"+text.sentenceNumber+")</TD>");
      }
      bw1.write("<TD ALIGN=center><span style=color:red;>"+(-point)+"</span></TD>");
      bw1.write("</TR>");

      a=0;
      point = 0;
      if (text.keywordNumber > 0) {
        a = relationIDs.length*100/text.keywordNumber;
      }
      if (a < 80) {
        point = 80-a;
        score -= point;
      }


      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[7]+"</TD>");
      if (text.keywordNumber > 0) {
        bw1.write("<TD ALIGN=center><span style=font-size:20px;color:blue;>"+(relationIDs.length*100/text.keywordNumber)+"%</span> ("+relationIDs.length+"/"+text.keywordNumber+")</TD>");
      } else {
        bw1.write("<TD ALIGN=center> 0% ("+relationIDs.length+"/"+text.keywordNumber+")</TD>");
      }
      bw1.write("<TD ALIGN=center><span style=color:red;>"+(-point)+"</span></TD>");
      bw1.write("</TR>");


      int subjectCount = 0;
      for (int i=0; i<text.sentenceNumber; i++)
        if (text.sentence[i].hasSubject) {
          subjectCount++;
        }

      point = text.sentenceNumber - subjectCount;
      score -= point;

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[15]+"</TD>");
      if (text.sentenceNumber > 0) {
        bw1.write("<TD ALIGN=center><span style=font-size:20px;color:blue;>"+(subjectCount*100/text.sentenceNumber)+"%</span> ("+subjectCount+"/"+text.sentenceNumber+")</TD>");
      } else {
        bw1.write("<TD ALIGN=center> 0% ("+subjectCount+"/"+text.sentenceNumber+")</TD>");
      }
      bw1.write("<TD ALIGN=center><span style=color:red;>"+(-point)+"</span></TD>");
      bw1.write("</TR>");


      point = noiseNumber;
      score -= point;

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[8]+"</TD>");
      bw1.write("<TD ALIGN=center><span style=font-size:20px;color:blue;>"+noiseNumber+"</span></TD>");
      bw1.write("<TD ALIGN=center><span style=color:red;>"+(-point)+"</span></TD>");
      bw1.write("</TR>");


      point = veryLongSentenceNumber * 5;
      score -= point;

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[9]+"</TD>");
      bw1.write("<TD ALIGN=center><span style=font-size:20px;color:blue;>"+veryLongSentenceNumber+"</span></TD>");
      bw1.write("<TD ALIGN=center><span style=color:red;>"+(-point)+"</span></TD>");
      bw1.write("</TR>");







      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[10]+"</TD>");
      bw1.write("<TD ALIGN=center></TD>");
      bw1.write("<TD ALIGN=center><span style=font-size:20px;color:red;>"+score+" "+titles[12]+"</span></TD>");
      bw1.write("</TR>");

      bw1.write("</TABLE>");


      //BASIC TABLE
      bw1.write("<H1>"+titles[0]+"</H1>");
      bw1.write("<TABLE BORDER=2 BORDERCOLOR=black>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[2]+"</TD>");
      bw1.write("<TD ALIGN=center>"+text.segmentNumber+"</TD>");
      bw1.write("</TR>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[3]+"</TD>");
      bw1.write("<TD ALIGN=center>"+text.sentenceNumber+"</TD>");
      bw1.write("</TR>");

      bw1.write("<TR>");
      bw1.write("<TD ALIGN=center>"+titles[13]+"</TD>");
      bw1.write("<TD ALIGN=center>"+text.keywordNumber+"</TD>");
      bw1.write("</TR>");

      bw1.write("</TABLE>");

      bw1.write("</BODY></HTML>");
      bw1.flush();
    } catch (Exception e) {
      System.out.println("Error in TextInfo");
      e.printStackTrace();
    }

    return sw1.toString();
  }
}


