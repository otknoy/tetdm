//
// Core Program for TETDM
// KeywordData.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;


import java.io.*;

public class KeywordData {
  public String word;	//End-form of a word

  public int partOfSpeech;					//Part of speech 1:Noun, 2:Verb, 3:Adjective, 4:Conjunction, 5:Adverb, 6:Interjection, 7:Particle, 8:Auxiliary verb, 9:pre-noun ajectival
  public int frequency;						//Frequency
  public int frequencyInSegment[];			//Frequency in each segment	//	int seg_times[];
  public int sentenceFrequencyInSegment[];	//Sentence frequency in each segment//	int seg_stimes[];

  public int appearingSegment[];		//List of appeared segments	//maker.h//	int apseglist[];
  public int appearingSegmentTable[];	//Appeared segments, 0:No appear, 1:appeared //, 2:appeared related to topic(After distance calculation) //maker.h//	int apseg[];
  public int segmentFrequency;		//Number of appeared segments //maker.h//	int apsegnum;
  public int lastAppearingSegment;

  public int appearingSentence[];		//List of appeared sentences//    int apline[];
  public int appearingSentenceTable[];//Appeared segments, 0:No appear, 1:appeared
  public int sentenceFrequency;		//Sentence frequency	//    int apnum;
  public int lastAppearingSentence;

  public int frequencyAsSubject;	//Frequency as a Subject(Nominative)  //subject
  public int segmentFrequencyAsSubject;	//Segment Frequency as a Subject	//subject_sn

  public KeywordData() {}

  public KeywordData(TextData text, int originalNumber) {
    word = text.keyword[originalNumber].word;
    partOfSpeech = text.keyword[originalNumber].partOfSpeech;
    frequency = 0;
  }

  public void initializeTables(int segmentNum, int sentenceNum) {
    initializeSegmentTable(segmentNum);
    initializeSentenceTable(sentenceNum);
  }

  public void initializeSegmentTable(int segmentNum) {
    segmentFrequency = 0;
    lastAppearingSegment = -1;

    appearingSegmentTable = new int[segmentNum];
    appearingSegment = new int[segmentNum];
    frequencyInSegment = new int[segmentNum];
    sentenceFrequencyInSegment = new int[segmentNum];
    for (int i=0; i<segmentNum; i++) {
      appearingSegmentTable[i] = 0;
      appearingSegment[i] = -1;
      frequencyInSegment[i] = 0;
      sentenceFrequencyInSegment[i] = 0;
    }
  }

  public void initializeSentenceTable(int sentenceNum) {
    sentenceFrequency = 0;
    lastAppearingSentence = -1;

    appearingSentenceTable = new int[sentenceNum];
    appearingSentence = new int[sentenceNum];
    for (int i=0; i<sentenceNum; i++) {
      appearingSentenceTable[i] = 0;
      appearingSentence[i] = -1;
    }
  }
}

//	int first_seg;		//First appeared segment //maker.h

