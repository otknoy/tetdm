//
// Visuzalization module for TETDM
// SentenceSelect.java Version 0.45
// Copyright(C) 2012-2013 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.SentenceSelect;

import source.*;
import source.TextData.*;
import source.Utility.*;
import module.VisualizationModules.KeywordSelect.FocusCheckBox;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

//executeOthersByClick();
//repaintOthersByClick();

public class SentenceSelect extends FocusCheckBox {
  public SentenceSelect() {
    setModuleID(1101);
    dataNumbers = new int[] {0,1,0,1,   // b,i,d,S	String 1+
                             0,0,1,0,    // bA,iA,dA,SA					doubleArray 1+
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializeData() {
    addSetButton = true;
    displayValues = false;
    executeAfterCheck = false;
    maxLength = 100;
    boxNumber = 2;
    lineNumber = text.sentenceNumber;
    focusSets = text.focus.focusSentences;

    initBoxData();
    createInitialBox();
  }

  public String[] createWords() {
    String words[];

    words = new String[text.sentenceNumber];
    for (int i=0; i<text.sentenceNumber; i++) {
      words[i] = text.sentence[i].sentenceText;
    }

    return words;
  }

  public void createInitialBox() {
    double value[] = new double[lineNumber];

    for (int i=0; i<lineNumber; i++) {
      value[i] = lineNumber-i;
    }
    checkBoxPanel[0].setValue(value);
    checkBoxPanel[0].setWords(createWords(), maxLength);

    for (int i=0; i<lineNumber; i++) {
      value[i] = text.sentence[i].wordNumber;
    }
    checkBoxPanel[1].setValue(value);
    checkBoxPanel[1].setWords(createWords(), maxLength);

    if (isMenuInJapanese()) {
      checkBoxPanel[0].setLabel(inJapanese[4]);    //"Appearance"
      checkBoxPanel[1].setLabel(inJapanese[5]);    //"Frequency"
    } else {
      checkBoxPanel[0].setLabel("Appearance");    //"Appearance"
      checkBoxPanel[1].setLabel("# of Words");    //"Frequency"
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      setting = true;
      for (int i=0; i<lineNumber; i++)
        for (int j=0; j<boxNumber; j++) {
          checkBoxPanel[j].ch[i].setSelected(false);
        }
      setting = false;
      for (int i=0; i<lineNumber; i++) {
        focusSets[i] = false;
      }
    }

    if (e.getSource() == valueButton) {
      if (valueButton.isSelected()) {
        displayValues = true;
      } else {
        displayValues = false;
      }
      for (int i=0; i<boxNumber; i++) {
        checkBoxPanel[i].changeLabel();
      }
    }

    if (e.getSource() == setButton1) {
      createSentencePartialData(0);
    }
    if (e.getSource() == setButton2) {
      createSentencePartialData(1);
    }
  }
}
