//
// Core Program for TETDM
// SegmentData.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;
import java.util.*;

public class SegmentData {
  public int segmentID;
  public String segmentText;

  public SentenceData sentence[];			//Structure of sentence
  public int sentenceNumber;				//number of sentences

  public int wordNumber;					//Number of all words			//Relation.java
  public int kindKeywordNumber;			//kind number of keywords

  public int positionOfSegment;			//Number of this segment in the original text
  public int positionOfFirstSentence;		//Number of the first sentence in the original text

  public SegmentData() {}

  public SegmentData(int segID, String segText, int n) {
    segmentID = segID;
    segmentText = segText;
    positionOfSegment = n;
  }

  public void initializeSentence(String sentences, String keyids, String keyn, String sentences2, String subjectCheck, boolean english) {
    String sents[] = sentences.split("@@");		// all word list with spaces
    String sents2[] = sentences2.split("@@");	// all word list (end form) with spaces

    String keyidss[] = keyids.split("@");		// id list with spaces
    String keynumbers[] = keyn.split(" ");		// list of keyword numbers

    String subjectChecks[] = subjectCheck.split("@");

    sentence = new SentenceData[sentenceNumber];
    for (int i=0; i<sentenceNumber; i++) {
      sentence[i] = new SentenceData(positionOfFirstSentence+i, sents[i], sents2[i], keyidss[i], subjectChecks[i], Integer.parseInt(keynumbers[i]), positionOfSegment, positionOfFirstSentence+i, english);
    }
  }

  public void initializeSentence(TextData text, int originalNumber) {
    sentenceNumber = text.segment[originalNumber].sentenceNumber;
    sentence = new SentenceData[sentenceNumber];
    for (int i=0; i<sentenceNumber; i++) {
      sentence[i] = new SentenceData(text.segment[segmentID].positionOfFirstSentence+i, text, originalNumber, i, positionOfFirstSentence+i);
    }
  }

  public void initializePartialSentence(TextData text, int originalNumber) {
    int originalCount[] = new int[text.segment[originalNumber].sentenceNumber];
    int count = 0;
    for (int i=0; i<text.segment[originalNumber].sentenceNumber; i++)
      if (text.focus.focusSentences[text.segment[originalNumber].positionOfFirstSentence + i]) {
        originalCount[count++] = i;
      }

    sentenceNumber = count;
    sentence = new SentenceData[sentenceNumber];

    count=0;
    for (int i=0; i<text.segment[originalNumber].sentenceNumber; i++)
      if (text.focus.focusSentences[text.segment[originalNumber].positionOfFirstSentence + i]) {
        sentence[count] = new SentenceData(text.segment[segmentID].positionOfFirstSentence+originalCount[count], text, originalNumber, i, positionOfFirstSentence+count);
        count++;
      }
  }
}
