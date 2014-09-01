//
// Core Program for TETDM
// SentenceData.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;
import java.util.*;

public class SentenceData {
  public int sentenceID;
  public String sentenceText;		//Sentence text

  public int keywordIDList[];		//List of keyword ID
  public int keywordNumber;		//Number of keywords
  public int kindKeywordNumber;	//kind number of keywords

  public String word[];			//All words
  public String wordEndForm[];	//All words end-form
  public int wordIDList[];		//ID list for all words, -1: not in keywords
  public int wordNumber;			//Number of all words

  public int positionOfSegment;	//Segment position number in the original text
  public int positionOfSentence;	//Sentence position number in the original text

  public boolean subjectCheck[];
  public boolean hasSubject;

  public SentenceData() {}

  public SentenceData(int senID, String senText, String senText2, String idlist, String subjectPlace, int keynum, int n1, int n2, boolean english) {
    sentenceID = senID;

    if (english) {
      sentenceText = senText;
    } else {
      sentenceText = senText.replace(" ","");
    }

    word = senText.split(" ");
    wordEndForm = senText2.split(" ");

    wordNumber = word.length;
    keywordNumber = keynum;

    keywordIDList = new int[keywordNumber];
    wordIDList = new int[wordNumber];

    subjectCheck = new boolean[wordNumber];

    String idlist2[] = idlist.split(" ");

    for (int i=0; i<wordNumber; i++) {
      wordIDList[i] = Integer.parseInt(idlist2[i]);
    }

    for (int i=0,k=0; i<wordNumber; i++) {
      if (wordIDList[i] >= 0) {
        keywordIDList[k++] = wordIDList[i];
      }
      subjectCheck[i] = false;
    }

    positionOfSegment = n1;
    positionOfSentence = n2;

    hasSubject = false;
    String placeList[] = subjectPlace.split(" ");
    int subjectPlaceList[] = new int[wordNumber];
    for (int i=0; i<wordNumber; i++) {
      subjectPlaceList[i] = Integer.parseInt(placeList[i]);
    }

    for (int i=0; i<wordNumber; i++)
      if (subjectPlaceList[i] > 0 && subjectPlaceList[i] <= 2) { //Permit including [SAMA, MONO(SHA), TACHI],[DE, NI, NIOITE, NITSUITE]
        subjectCheck[i - subjectPlaceList[i]] = true;
        hasSubject = true;
      }
  }

  public SentenceData(int senID, TextData text, int originalNumber, int number, int number2) {
    sentenceID = senID;
    sentenceText = text.segment[originalNumber].sentence[number].sentenceText;

    word = text.segment[originalNumber].sentence[number].word;
    wordEndForm = text.segment[originalNumber].sentence[number].wordEndForm;

    wordNumber = word.length;
    keywordNumber = text.segment[originalNumber].sentence[number].keywordNumber;

    keywordIDList = text.segment[originalNumber].sentence[number].keywordIDList; /* to be considered keywordID */
    wordIDList = text.segment[originalNumber].sentence[number].wordIDList; /* to be considered keywordID */

    subjectCheck = text.segment[originalNumber].sentence[number].subjectCheck;
    hasSubject = text.segment[originalNumber].sentence[number].hasSubject;

    positionOfSegment = originalNumber;
    positionOfSentence = number2;
  }
}
