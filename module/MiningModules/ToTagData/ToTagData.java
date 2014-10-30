
//
// Mining module for TETDM
// YKPExtraction.java Version 0.30
// Copyright(C) 2011 YASUFUMI TAKAMA All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

/* Create Data for Tag Cloud by extracting Key phrases with Yahoo API
  * Output: set_data(0,keyphrase) ... Array for Key phrases(String[])
  *      set_data(101,val) ... Frequencies for Key phrases(int[])
  *      set_data(201,sentence) ... List of Key phrases with Tab(String[])*some phrases appear twice or more */


package module.MiningModules.ToTagData;		// Replace ALL [MiningStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.io.*;

public class ToTagData extends MiningModule {
  String[] words;
  int[] vals;
  String[] sentences;

  public ToTagData() {
    setModuleID(14);
    pairingVisualizationID = new int[1];
    pairingVisualizationID[0] = 14;
  }

  //	public void initializePanel(){}

  //	public void initializeData(){}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        getKData();
        getSentences();
        setDataStringArray(0,words);
        setDataIntegerArray(1,vals);
        setDataStringArray(2,sentences);
        break;
    }
  }

  void getKData() {
    words = new String[text.keywordNumber];
    vals = new int[text.keywordNumber];
    for (int i=0; i<text.keywordNumber; i++) {
      words[i]=text.keyword[i].word;
      vals[i]=text.keyword[i].sentenceFrequency;
    }
  }

  void getSentences() {
    sentences=new String[text.sentenceNumber];

    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    try {

      for (int i=0; i<text.sentenceNumber; i++) {
        sentences[i]="";
        SentenceData s1=text.sentence[i];

        if (s1.keywordNumber != 0) {
          bw.write(text.keyword[s1.keywordIDList[0]].word);

          for (int j=1; j<s1.keywordNumber; j++) {
            bw.write("\t");
            bw.write(text.keyword[s1.keywordIDList[j]].word);
          }
          bw.flush();
          sentences[i] = sw.toString();
          sw.getBuffer().delete(0,sw.getBuffer().length());
        }
      }
    } catch (Exception e) {
      System.out.println("writing ERROR in ToTagData");
    }
  }
}
