//
// Mining module for TETDM
// SubjectExtraction Version 0.10 2012.2.9
// Copyright(C) 2012
//

// You should read the README file.
//

package module.MiningModules.SubjectExtraction;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

public class SubjectExtraction extends MiningModule implements ActionListener {
  int sentenceHighlightNumber[];
  int wordHighlightNumber[];

  JButton bt1,bt2,bt3,bt4;
  String buttonNamesInJapanese[];

  public SubjectExtraction() {
    setModuleID(5);						// Set your module ID after you have got it
    pairingVisualizationID = new int[] {2};
  }

  public void initializePanel() {
    buttonNamesInJapanese = fileReadArray();

    bt1 = new JButton("No Subject");
    bt2 = new JButton("All S-Words");
    bt3 = new JButton("S-Words(key)");
    bt4 = new JButton("S-Words(ex)");

    operationPanel.setBackground(Color.RED);

    bt1.setBackground(Color.RED);
    bt1.addActionListener(this);
    operationPanel.add(bt1);
    bt2.setBackground(Color.RED);
    bt2.addActionListener(this);
    operationPanel.add(bt2);
    bt3.setBackground(Color.RED);
    bt3.addActionListener(this);
    operationPanel.add(bt3);
    bt4.setBackground(Color.RED);
    bt4.addActionListener(this);
    operationPanel.add(bt4);

    if (isMenuInJapanese()) {
      bt1.setText(buttonNamesInJapanese[0]);
      bt2.setText(buttonNamesInJapanese[1]);
      bt3.setText(buttonNamesInJapanese[2]);
      bt4.setText(buttonNamesInJapanese[3]);
    }

  }

  public void initializeData() {
    sentenceHighlightNumber = new int[text.sentenceNumber];
    wordHighlightNumber = new int[text.wordNumber];
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        ColorBySubject();
        setDataIntegerArray(0,sentenceHighlightNumber);
        break;

      case 1:
        initializeData();
        break;

      case 11:
        ColorBySubject();
        setDataIntegerArray(0,sentenceHighlightNumber);
        displayOperations(0);
        break;
      case 12:
        ColorByPartOfSpeech();
        setDataIntegerArray(1,wordHighlightNumber);
        displayOperations(11);
        break;
      case 13:
        ColorByPartOfSpeech2();
        setDataIntegerArray(1,wordHighlightNumber);
        displayOperations(11);
        break;
      case 14:
        ColorByPartOfSpeech3();
        setDataIntegerArray(1,wordHighlightNumber);
        displayOperations(11);
        break;
    }
  }

  void ColorBySubject() {
    for (int i=0; i<text.sentenceNumber; i++)
      if (text.sentence[i].hasSubject == false) {
        sentenceHighlightNumber[i] = 1;
      } else {
        sentenceHighlightNumber[i] = 10;
      }
  }

  void ColorByPartOfSpeech() {
    int wordCount = 0;
    for (int i=0; i<text.sentenceNumber; i++)
      for (int j=0; j < text.sentence[i].wordNumber; j++, wordCount++) {
        wordHighlightNumber[wordCount] = 10;

        if (text.sentence[i].subjectCheck[j] == true) {
          wordHighlightNumber[wordCount] = 1;
        }
      }
  }

  void ColorByPartOfSpeech2() {
    int wordCount = 0;
    for (int i=0; i<text.sentenceNumber; i++)
      for (int j=0; j < text.sentence[i].wordNumber; j++, wordCount++) {
        wordHighlightNumber[wordCount] = 10;

        if (text.sentence[i].subjectCheck[j] == true && text.sentence[i].wordIDList[j] >= 0) {
          wordHighlightNumber[wordCount] = 1;
        }
      }
  }

  void ColorByPartOfSpeech3() {
    int wordCount = 0;
    for (int i=0; i<text.sentenceNumber; i++)
      for (int j=0; j < text.sentence[i].wordNumber; j++, wordCount++) {
        wordHighlightNumber[wordCount] = 10;

        if (text.sentence[i].subjectCheck[j] == true && text.sentence[i].wordIDList[j] < 0) {
          wordHighlightNumber[wordCount] = 1;
        }
      }
  }

  //Action listener
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bt1) {
      miningOperations(11);
    }

    if (e.getSource() == bt2) {
      miningOperations(12);
    }

    if (e.getSource() == bt3) {
      miningOperations(13);
    }

    if (e.getSource() == bt4) {
      miningOperations(14);
    }
  }
}
