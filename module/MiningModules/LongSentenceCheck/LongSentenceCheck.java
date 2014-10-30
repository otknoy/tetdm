////////////////////////////////
// Mining Module for TETDM //
// LongSentenceCheck Version 0.30 2012.1.2 //
// Copyright(C) 2011.12.13 Tomoki Kajinami //
// *IMPORTANT*
// This program is provided under a LSC licence in addition to a TETDM licence.
// You should read README file.
////////////////////////////////

package module.MiningModules.LongSentenceCheck;

import source.*;
import source.TextData.*;
import java.text.Normalizer;

import java.io.*;

public final class LongSentenceCheck extends MiningModule {

  private int longThreshold,veryLongThreshold;
  private int longSentenceNumber,veryLongSentenceNumber;
  private String longColor,veryLongColor;
  private String inJapanese[];

  public LongSentenceCheck() {
    setModuleID(16); //
    pairingVisualizationID = new int[1]; //
    pairingVisualizationID[0] = 3; //TextDisplayHtml //
  }

  public void initializeData() {
    inJapanese = fileReadArray();

    // customer-changeable ///////////////////////////////////////////////////////////////////
    longThreshold=51; //customer-changeable //int //
    veryLongThreshold=100; //customer-changeable //int //
    longColor="purple"; //customer-changeable //color of long sentence
    veryLongColor="red"; //customer-changeable //color of very long sentence
    /////////////////////////////////////////////////////////////////////////////////////////
  }

  public void initializePanel() {}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        resetData();
        setDataString(MyMethod());
        setDataInteger(1,veryLongSentenceNumber);
        break;
    }
  }

  private final String MyMethod() {
    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    longSentenceNumber = veryLongSentenceNumber = 0;

    try {
      bw.write("<html><head><title>Long Sentence Check</title></head><body>");

      for (int i=0; i<text.segmentNumber; i++) {
        bw.write("<p>");
        for (int j=0; j<text.segment[i].sentenceNumber; j++) {
//					String sentence=text.segment[i].sentence[j].sentenceText;
          String sentence=text.segment[i].sentence[j].sentenceText.trim();
//					System.out.println(i+"	"+j+"	"+sentence+":end");
          sentence=Normalizer.normalize(sentence,Normalizer.Form.NFC);
          if (sentence.codePointCount(0,sentence.length())>=veryLongThreshold) {
//						bw.write("<span style=\"color:"+veryLongColor+";text-decoration:underline\"><b>"+text.segment[i].sentence[j].sentenceText+"</b></span><span style=\"font-weight:bold;color:"+veryLongColor+"\">["+inJapanese[0]+(sentence.codePointCount(0,sentence.length()))+inJapanese[1]+"]</span>"+inJapanese[2]);
            bw.write("<span style=\"color:"+veryLongColor+";text-decoration:underline\"><b>"+sentence+"</b></span><span style=\"font-weight:bold;color:"+veryLongColor+"\">["+inJapanese[0]+(sentence.codePointCount(0,sentence.length()))+inJapanese[1]+"]</span>"+inJapanese[2]);

            veryLongSentenceNumber++;
          } else if (sentence.codePointCount(0,sentence.length())>=longThreshold) {
//						bw.write("<span style=\"color:"+longColor+";text-decoration:underline\"><b>"+text.segment[i].sentence[j].sentenceText+"</b></span><span style=\"font-weight:bold;color:"+longColor+"\">["+inJapanese[0]+(sentence.codePointCount(0,sentence.length()))+inJapanese[1]+"]</span>"+inJapanese[2]);
            bw.write("<span style=\"color:"+longColor+";text-decoration:underline\"><b>"+sentence+"</b></span><span style=\"font-weight:bold;color:"+longColor+"\">["+inJapanese[0]+(sentence.codePointCount(0,sentence.length()))+inJapanese[1]+"]</span>"+inJapanese[2]);
            longSentenceNumber++;
          } else {
            bw.write(sentence);
          }
        }
        bw.write("</p>");
      }
      bw.write("</body></html>");

      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in LongSentenceCheck");
    }

    return sw.toString();
  }
}
