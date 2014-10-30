
//
// Mining module for TETDM
// HalfText.java Version 0.30
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.HalfText;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class HalfText extends MiningModule implements ActionListener {
  JButton button[];
  int buttonNumber;
  String buttonNamesInJapanese[];


  public HalfText() {
    setModuleID(25);						// Set your module ID after you have got it
    pairingVisualizationID = new int[] {21};
    setToolType(2);

    defaultSetPanelModuleID = new int[] {19,19,19,25};
    defaultSetPanelVisualizationID = new int[] {3,3,3,21};
    defaultSetPanelTextID = new int[] {0,1,3,0};
  }

  public void initializePanel() {
    buttonNamesInJapanese = fileReadArray();

    operationPanel.setBackground(Color.RED);

    buttonNumber = 4;

    button = new JButton[buttonNumber];

    operationPanel.setLayout(new GridLayout(2,2));

    for (int i=0; i<buttonNumber; i++) {
      button[i] = new JButton();
      button[i].setBackground(Color.RED);
      button[i].addActionListener(this);
      operationPanel.add(button[i]);
    }
  }

  public void initializeData() {
    String buttonNamesInEnglish[] = {"Half-SEG","Half-SEN","SEG Set","SEG Set"};


    if (isMenuInJapanese()) {
      for (int i=0; i<buttonNumber; i++) {
        button[i].setText(buttonNamesInJapanese[i]);
      }
    } else {
      for (int i=0; i<buttonNumber; i++) {
        button[i].setText(buttonNamesInEnglish[i]);
      }
    }
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        break;
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button[0]) {
      for (int i=0; i<text.segmentNumber; i++)
        if (i < text.segmentNumber/2) {
          text.focus.focusSegments[i] = true;
        } else {
          text.focus.focusSegments[i] = false;
        }

      createSegmentPartialData(0);

      for (int i=0; i<text.segmentNumber; i++)
        if (i < text.segmentNumber/2) {
          text.focus.focusSegments[i] = false;
        } else {
          text.focus.focusSegments[i] = true;
        }

      createSegmentPartialData(1);
    }

    if (e.getSource() == button[1]) {
      for (int i=0; i<text.sentenceNumber; i++)
        if (i < text.sentenceNumber/2) {
          text.focus.focusSentences[i] = true;
        } else {
          text.focus.focusSentences[i] = false;
        }

      createSentencePartialData(0);

      for (int i=0; i<text.sentenceNumber; i++)
        if (i < text.sentenceNumber/2) {
          text.focus.focusSentences[i] = false;
        } else {
          text.focus.focusSentences[i] = true;
        }

      createSentencePartialData(1);
    }

    if (e.getSource() == button[2]) {
      if (text.segmentPartialTextData == null) {
        return;
      }

      int frequency[] = new int[text.keywordNumber];

      for (int i=0; i<text.keywordNumber; i++) {
        frequency[i] = text.segmentPartialTextData[0].keyword[i].segmentFrequency;
      }

      setDataIntegerArray(0,frequency);

      for (int i=0; i<text.keywordNumber; i++) {
        frequency[i] = text.segmentPartialTextData[1].keyword[i].segmentFrequency;
      }

      setDataIntegerArray(1,frequency);
    }


    if (e.getSource() == button[3]) {
      if (text.sentencePartialTextData == null) {
        return;
      }

      int frequency[] = new int[text.keywordNumber];

      for (int i=0; i<text.keywordNumber; i++) {
        frequency[i] = text.sentencePartialTextData[0].keyword[i].sentenceFrequency;
      }

      setDataIntegerArray(0,frequency);

      for (int i=0; i<text.keywordNumber; i++) {
        frequency[i] = text.sentencePartialTextData[1].keyword[i].sentenceFrequency;
      }

      setDataIntegerArray(1,frequency);
    }
  }
}
