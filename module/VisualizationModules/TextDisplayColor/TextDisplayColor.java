//
// Visuzalization module for TETDM
// TextDisplayColor.java Version 0.31
// Copyright(C) 2011 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the licenses.
// You Should read the README file.
//



package module.VisualizationModules.TextDisplayColor;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


public class TextDisplayColor extends VisualizationModule {
  //Text pane
  JTextPane text1;
  JScrollPane scroll1;

  //for DISPLAY
  String displayText;	//display text

  String outputInJapanese;


  //for text DISPLAY
  StyleContext sc;
  DefaultStyledDocument doc;
  MutableAttributeSet attr[];

  int sentenceAttribute[];
  int wordAttribute[];

  //WORD
  String colorWord;

  int fontsize;

  public TextDisplayColor() {
    setModuleID(2);
    dataNumbers = new int[] {0,0,0,2,   // b,i,d,S
                             0,2,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(3);
  }

  public void initializePanel() {
    text1 = new JTextPane();
    text1.setContentType("text/plain");
    scroll1 = new JScrollPane(text1);
    add(scroll1);

    outputInJapanese = fileRead();

    colorSettings();
  }

  public void initializeData() {
    fontsize = 14;

    if (isMenuInJapanese()) {
      displayText = outputInJapanese;
    } else {
      displayText = "Nothing";
    }

    colorWord = "K";
    sentenceAttribute = new int[text.sentenceNumber];
    wordAttribute = new int[text.wordNumber];
    for (int i=0; i<text.sentenceNumber; i++) {
      sentenceAttribute[i] = 0;
    }
    for (int i=0; i<text.wordNumber; i++) {
      wordAttribute[i] = 0;
    }
  }

  public void setFont() {
    fontsize = text.fontSize;
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        makeDocumentBySentence();

//				if(!text.focus.getMainFocusString().equals(""))
//				{
//					colorWord = text.focus.getMainFocusString();
//					setWordColor();
//				}

        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text1.setDocument(doc);
        text1.setCaretPosition(0);

        break;

      case 11:
        makeDocumentByWords();
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text1.setDocument(doc);
        text1.setCaretPosition(0);
        break;

      case 1:
        text.fileSave(text1.getText());
        break;

      case 9:
        fontsize--;
        displayOperations(0);
        break;

      case 10:
        fontsize++;
        displayOperations(0);
        break;

      case 25:
        displayText = text1.getText();
      case 24:
        setWordColor();
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text1.setDocument(doc);
        text1.setCaretPosition(0);
        break;
    }
  }
  /*
  	public void setData(int dataID, int data[])
  	{
  		switch(dataID)
  		{
  			case 1:
  			case 23:
  			    for(int i=0;i<data.length;i++)
  					sentenceAttribute[i] = data[i];
  			    break;

  			case 11:
  			case 12:
  			case 13:
  			    for(int i=0;i<data.length;i++)
  					wordAttribute[i] = data[i];
  			    break;
  		}
  	}
   */
  public boolean setData(int dataID, int data[]) {
    switch (dataID) {
      case 0:
        for (int i=0; i<data.length; i++)
          if (data[i]>=0 && data[i]<21) {
            sentenceAttribute[i] = data[i];
          }
        return true;

      case 1:
        for (int i=0; i<data.length; i++)
          if (data[i]>=0 && data[i]<21) {
            wordAttribute[i] = data[i];
          }
        return true;
    }

    return false;
  }

  /*
  	public void setData(int dataID, String data)
  	{
  		switch(dataID)
  		{
  			case 1:
  				displayText = data;
  				break;

  			case 24:
  			case 25:
  				colorWord = data;
  				break;
  		}
  	}
  */
  public boolean setData(int dataID, String t) {
    switch (dataID) {
      case 0:
        displayText = t;
        return true;

      case 1:
        colorWord = t;
        return true;
    }

    return false;
  }

  void colorSettings() {
    attr = new SimpleAttributeSet[21];
    for (int i=0; i<attr.length; i++) {
      attr[i] = new SimpleAttributeSet();
    }

    Color cl[] = new Color[attr.length];
    cl[0] = new Color(255, 255, 255);
    cl[1] = new Color(255, 255, 55);//4.0
    cl[2] = new Color(220, 220, 0);//3.5
    cl[3] = new Color(220, 220, 0);//3.0
    cl[4] = new Color(190, 190, 0);//2.5
    cl[5] = new Color(190, 190, 0);//2.0
    cl[6] = new Color(120, 120, 0);//1.5
    cl[7] = new Color(120, 120, 0);//1.0
    cl[8] = new Color(100, 100, 0);//0.5
    cl[9] = new Color(50, 50, 0);	//0
    cl[10] = new Color(255, 255, 255);//white
    cl[11] = new Color(0,0,0);		//black
    cl[12] = new Color(255, 255, 0);//yeloow
    cl[13] = new Color(255, 164, 0);//orange
    cl[14] = new Color(160, 82, 45);//brown
    cl[15] = new Color(128, 0, 0);//red-brown
    cl[16] = new Color(190, 190, 0);//2.5
    cl[17] = new Color(176,196,222);//lightsteelblue
    cl[18] = new Color(255, 192, 203);//pink
    cl[19] = new Color(102, 205, 170);//aquamarine
    cl[20] = new Color(50, 205, 50);//limegreen

    for (int i=0; i<attr.length; i++) {
      StyleConstants.setBackground(attr[i], cl[i]);
    }

    sc = new StyleContext();
  }

  void makeDocumentBySentence() {
    doc = new DefaultStyledDocument(sc);
    int offset = 0;
    try {
      for (int i=0; i<text.sentenceNumber; i++) {
        doc.insertString(offset, text.sentence[i].sentenceText, attr[sentenceAttribute[i]]);
        offset += text.sentence[i].sentenceText.length();
//				doc.insertString(offset++, "\n", attr[0]);
      }
    } catch (BadLocationException ble) {
      System.err.println("Accessed at illegal position out of the document.");
    }
  }
  void makeDocumentByWords() {
    doc = new DefaultStyledDocument(sc);
    int offset = 0;
    int wordCount = 0;
    try {
      for (int i=0; i<text.sentenceNumber; i++)
        for (int j=0; j<text.sentence[i].wordNumber; j++,wordCount++) {
          doc.insertString(offset, text.sentence[i].word[j], attr[wordAttribute[wordCount]]);
          offset += text.sentence[i].word[j].length();
        }
    } catch (BadLocationException ble) {
      System.err.println("Accessed at illegal position out of the document.");
    }
  }

  public void setWordColor() {
    doc = new DefaultStyledDocument(sc);
    int offset = 0, position = displayText.indexOf(colorWord), wordLength = colorWord.length();

    try {
      doc.insertString(offset, displayText, attr[10]);
    } catch (BadLocationException ble) {
      System.err.println("Accessed at illegal position out of the document.");
    }

    while (position != -1) {
      doc.setCharacterAttributes(position, wordLength, attr[1], true);
      position = displayText.indexOf(colorWord, position+wordLength);
    }
  }
}
