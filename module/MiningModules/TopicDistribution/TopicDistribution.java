//
// Mining module for TETDM
// TopicDistribution.java Version 0.1 2012.12.25
// Copyright(C) 2012 Tatsuya Ishii, Tomoki Kajinami All RIGHTS RESERVED.
// This program is provided under the license.
// You should read the README file.
//

package module.MiningModules.TopicDistribution;

import source.*;
import source.TextData.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TopicDistribution extends MiningModule implements ActionListener {

  private JComboBox modeSelector;

  private String buttonNamesInJapanese[];

  private int topicList[];

  public TopicDistribution() {
    setModuleID(23);						// Set your module ID after you have got it
    pairingVisualizationID=new int[1];
    pairingVisualizationID[0]=16;
    focusClickExecute=true;
  }

  public void initializePanel() {
    buttonNamesInJapanese = fileReadArray();
    if (isMenuInJapanese()) {
      String[] modeName= {buttonNamesInJapanese[0],buttonNamesInJapanese[1],buttonNamesInJapanese[2],buttonNamesInJapanese[3],buttonNamesInJapanese[4]};
      modeSelector=new JComboBox(modeName);
    } else {
      String[] modeName= {"Topic over segment number","Topic as subject over segment number","Topic over sentence number","Topic as subject over sentence number","Topic over keyword number"};
      modeSelector=new JComboBox(modeName);
    }

    modeSelector.addActionListener(this);
    operationPanel.add(modeSelector);
  }

  public void initializeData() {}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 4502:
        int count=0;
        for (int i=0; i<text.keywordNumber; i++) {
          if (text.focus.focusKeywords[i]) {
            count++;
          }
        }
        if (count == 0) {
          topicList=getNewDataIntegerArray(11,0);
        } else {
          topicList = new int[count];
          count=0;
          for (int i=0; i<text.keywordNumber; i++) {
            if (text.focus.focusKeywords[i]) {
              topicList[count]=i;
              count++;
            }
          }
        }
        setDataString(98,makeData());
        displayOperations(0);
        break;

      case 0:
        topicList=getNewDataIntegerArray(11,0);
        setDataString(98,makeData());
        displayOperations(0);
        break;
    }
  }

  private String makeData() {
    StringWriter sw=new StringWriter();
    BufferedWriter bw=new BufferedWriter(sw);

    try {
      if (topicList.length==0) {
        bw.write("Dummy,1"+System.getProperty("line.separator"));
      }
      for (int i=0; i<topicList.length; i++) {
        switch (modeSelector.getSelectedIndex()) {
          case 0:
            bw.write(text.keyword[topicList[i]].word+","+(double)text.keyword[topicList[i]].segmentFrequency/text.segmentNumber+System.getProperty("line.separator"));
            break;
          case 1:
            bw.write(text.keyword[topicList[i]].word+","+(double)text.keyword[topicList[i]].segmentFrequencyAsSubject/text.segmentNumber+System.getProperty("line.separator"));
            break;
          case 2:
            bw.write(text.keyword[topicList[i]].word+","+(double)text.keyword[topicList[i]].sentenceFrequency/text.sentenceNumber+System.getProperty("line.separator"));
            break;
          case 3:
            bw.write(text.keyword[topicList[i]].word+","+(double)text.keyword[topicList[i]].frequencyAsSubject/text.sentenceNumber+System.getProperty("line.separator"));
            break;
          case 4:
            bw.write(text.keyword[topicList[i]].word+","+(double)text.keyword[topicList[i]].frequency/text.keywordNumber+System.getProperty("line.separator"));
            break;
        }
      }

      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in RadarChartTest");
    }
    return sw.toString();
  }

  public void actionPerformed(ActionEvent e) {
    miningOperations(0);
  }
}
