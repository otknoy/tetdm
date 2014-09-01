//
// Core Program for TETDM
// TextData.java Version 0.50
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source.TextData;

import source.*;

import java.io.*;
import java.util.*;

import java.util.regex.*;

public class TextData {
  public int textID;

  public String originalText;		//Loaded text data
  public String sourceFilename;	//Load text filename
  public String absolutePath;		//Absolute path of execution directory
  public String defaultCode;

  //Segment Data
  public SegmentData segment[];
  public int segmentNumber;

  String segmentTag;

  //Sentence Data
  public SentenceData sentence[];		//pointer to segment[].sentence[]
  public int sentenceNumber;

  String sentenceTag;

  public int sentenceToSegment[];		//segment number of i-th sentence

  //Keywords Data
  public KeywordData keyword[];
  public int keywordNumber;

  public final int maxKeywordNumber = 10000;	//Maximum number of keywords, The process is NOT implemented when number is above 10000.

  //Word Data
  public int wordNumber;

  //Text Relation Data
//	public RelationalData segmentRelation, sentenceRelation, keywordRelationBySegment, keywordRelationBySentence;
  public RelationalData segmentRelation, keywordRelationBySegment;

  ////For PartialData
//	public TextData segmentPartialTextData;
//	public TextData sentencePartialTextData;

  public TextData segmentPartialTextData[];
  public boolean segmentPartialInitializationCheck[];
  public int maxSegmentPartialNumber = 3;
  public TextData sentencePartialTextData[];
  public boolean sentencePartialInitializationCheck[];
  public int maxSentencePartialNumber = 3;

  public int originalSegmentNumbers[];
  public int originalSentenceNumbers[];


  public TextData partOfTextData[];


  //display for visualization
  public int fontSize = 14;

  public class Focus {
    public boolean focusKeywords[];
    public boolean focusSegments[];
    public boolean focusSentences[];

    public int mainFocusKeyword, subFocusKeyword;
    public int mainFocusSegment, subFocusSegment;
    public int mainFocusSentence, subFocusSentence;

    Focus(int keywordNumber, int sentenceNumber, int segmentNumber) {
      focusKeywords = new boolean[keywordNumber];
      focusSegments = new boolean[segmentNumber];
      focusSentences = new boolean[sentenceNumber];

      clear();
    }

    public void clear() {
      mainFocusKeyword = subFocusKeyword = mainFocusSegment = subFocusSegment = mainFocusSentence = subFocusSentence = -1;

      for (int i=0; i<keywordNumber; i++) {
        focusKeywords[i] = false;
      }

      for (int i=0; i<segmentNumber; i++) {
        focusSegments[i] = false;
      }

      for (int i=0; i<sentenceNumber; i++) {
        focusSentences[i] = false;
      }
    }

    public void setMainFocusBoolean(boolean value) {
      control.focus.mainFocusBoolean = value;
    }
    public void setSubFocusBoolean(boolean value) {
      control.focus.subFocusBoolean = value;
    }
    public void setMainFocusInteger(int value) {
      control.focus.mainFocusInteger = value;
    }
    public void setSubFocusInteger(int value) {
      control.focus.subFocusInteger = value;
    }
    public void setMainFocusDouble(double value) {
      control.focus.mainFocusDouble = value;
    }
    public void setSubFocusDouble(double value) {
      control.focus.subFocusDouble = value;
    }
    public void setMainFocusString(String value) {
      control.focus.mainFocusString = value;
    }
    public void setSubFocusString(String value) {
      control.focus.subFocusString = value;
    }

    public void setFocusBooleanArray(boolean values[]) {
      control.focus.focusBooleanArray = new boolean[values.length];
      for (int i=0; i<values.length; i++) {
        control.focus.focusBooleanArray[i] = values[i];
      }
    }
    public void setFocusIntegerArray(int values[]) {
      control.focus.focusIntegerArray = new int[values.length];
      for (int i=0; i<values.length; i++) {
        control.focus.focusIntegerArray[i] = values[i];
      }
    }
    public void setFocusDoubleArray(double values[]) {
      control.focus.focusDoubleArray = new double[values.length];
      for (int i=0; i<values.length; i++) {
        control.focus.focusDoubleArray[i] = values[i];
      }
    }
    public void setFocusStringArray(String values[]) {
      control.focus.focusStringArray = new String[values.length];
      for (int i=0; i<values.length; i++) {
        control.focus.focusStringArray[i] = values[i];
      }
    }

    public void setFocusBooleanArray2(boolean values[][]) {
      control.focus.focusBooleanArray2 = new boolean[values.length][];
      for (int i=0; i<values.length; i++) {
        control.focus.focusBooleanArray2[i] = new boolean[values[i].length];
        for (int j=0; j<values.length; j++) {
          control.focus.focusBooleanArray2[i][j] = values[i][j];
        }
      }
    }
    public void setFocusIntegerArray2(int values[][]) {
      control.focus.focusIntegerArray2 = new int[values.length][];
      for (int i=0; i<values.length; i++) {
        control.focus.focusIntegerArray2[i] = new int[values[i].length];
        for (int j=0; j<values.length; j++) {
          control.focus.focusIntegerArray2[i][j] = values[i][j];
        }
      }
    }
    public void setFocusDoubleArray2(double values[][]) {
      control.focus.focusDoubleArray2 = new double[values.length][];
      for (int i=0; i<values.length; i++) {
        control.focus.focusDoubleArray2[i] = new double[values[i].length];
        for (int j=0; j<values.length; j++) {
          control.focus.focusDoubleArray2[i][j] = values[i][j];
        }
      }
    }

    public boolean getMainFocusBoolean() {
      return control.focus.mainFocusBoolean;
    }
    public boolean getSubFocusBoolean() {
      return control.focus.subFocusBoolean;
    }
    public int getMainFocusInteger() {
      return control.focus.mainFocusInteger;
    }
    public int getSubFocusInteger() {
      return control.focus.subFocusInteger;
    }
    public double getMainFocusDouble() {
      return control.focus.mainFocusDouble;
    }
    public double getSubFocusDouble() {
      return control.focus.subFocusDouble;
    }
    public String getMainFocusString() {
      return control.focus.mainFocusString;
    }
    public String getSubFocusString() {
      return control.focus.subFocusString;
    }

    public boolean[] getFocusBooleanArray() {
      return control.focus.focusBooleanArray;
    }
    public int[] getFocusIntegerArray() {
      return control.focus.focusIntegerArray;
    }
    public double[] getFocusDoubleArray() {
      return control.focus.focusDoubleArray;
    }
    public String[] getFocusStringArray() {
      return control.focus.focusStringArray;
    }

    public boolean[][] getFocusBooleanArray2() {
      return control.focus.focusBooleanArray2;
    }
    public int[][] getFocusIntegerArray2() {
      return control.focus.focusIntegerArray2;
    }
    public double[][] getFocusDoubleArray2() {
      return control.focus.focusDoubleArray2;
    }

  }

  public Focus focus;

  public Control control;

  public SettingData settingData;

  public Connection connection;

  public TextData() {}

  public TextData(int ID, String sourcefile, String chafile, SettingData sp1, String absolute, Control cont) {
    textID = ID;
    sourceFilename = sourcefile;
    defaultCode = sp1.defaultCode;
    settingData = sp1;
    control = cont;//		this.cont = cont;
    absolutePath = absolute;
    segmentTag = sp1.segment_tag;
    sentenceTag = sp1.kind_touns[0];

    partOfTextData = new TextData[7];
    partOfTextData[0] = this;

    connection = new Connection(this);

    originalText = FILEIO.TextFileAllReadCode(new File(sourcefile));//Prepare the original text

    //Keywords Data
    keyword = new KeywordData[maxKeywordNumber];

    for (int i=0; i<maxKeywordNumber; i++) {
      keyword[i] = new KeywordData();
    }
    keywordNumber = 0;

    //Segment Data
    segmentNumber = FILEIO.CountTags(originalText, sp1.segment_tag);
    segment = new SegmentData[segmentNumber];
    String cut_text[] = new String[segmentNumber];
    FILEIO.CutText(originalText, cut_text, sp1.segment_tag, sp1.leftTags);
    for (int i=0; i<segmentNumber; i++) {
      segment[i] = new SegmentData(i, cut_text[i], i);
    }

    //Load words information from the result of ChaSen
    readChasenFile(chafile, sp1, sp1.isEnglish);	//Read word data from a cha-file	//count sentenceNumber

    initializeData(0);


    boolean active[] = new boolean[keywordNumber];
    for (int i=0; i<keywordNumber; i++)
      if (keyword[i].segmentFrequency < 2) {
        active[i] = false;
      } else {
        active[i] = true;
      }

//		keywordRelationBySegment.optimizeLink(keywordRelationBySegment.cos, 0.5, active);

    /*		MakeCluster mk = new MakeCluster(keywordRelationBySegment);   CLUSTERING */

//		boolean active[] = new boolean[segmentNumber];

//		segr.optimizeLink(segr.cos, 0.5, active);
//		MakeCluster mk = new MakeCluster(segr);


    //For partialData
    segmentPartialTextData = new TextData[maxSegmentPartialNumber];
    segmentPartialInitializationCheck = new boolean[maxSegmentPartialNumber];
    for (int i=0; i<maxSegmentPartialNumber; i++) {
      segmentPartialInitializationCheck[i] = false;
      segmentPartialTextData[i] = new TextData(1 + 2*i);
      segmentPartialTextData[i].createSegmentPartialData(this,1);

      partOfTextData[1 + 2*i] = segmentPartialTextData[i];
    }
    sentencePartialTextData = new TextData[maxSentencePartialNumber];
    sentencePartialInitializationCheck = new boolean[maxSentencePartialNumber];
    for (int i=0; i<maxSentencePartialNumber; i++) {
      sentencePartialInitializationCheck[i] = false;
      sentencePartialTextData[i] = new TextData(2 + 2*i);
      sentencePartialTextData[i].createSentencePartialData(this,1);

      partOfTextData[2 + 2*i] = sentencePartialTextData[i];
    }


//		segmentPartialTextData[0] = new TextData(1);
//		sentencePartialTextData[0] = new TextData(2);
  }

  public TextData(int ID) {
    textID = ID;
  }

  public void initializeData(int option) {
    System.out.println("SEGMENT NUMBER = "+segmentNumber);
    System.out.println("SENTENCE NUMBER = "+sentenceNumber);
    System.out.println("KEYWORD NUMBER = "+keywordNumber);

    //Copy sentence, Calculate: sentenceToSegment[]
    sentenceToSegment = new int[sentenceNumber];
    sentence = new SentenceData[sentenceNumber];

    for (int i=0,s=0; i<segmentNumber; i++)
      for (int j=0; j<segment[i].sentenceNumber; j++) {
        sentence[s]	= segment[i].sentence[j];
        sentenceToSegment[s++] = i;
      }

    //move line.separator from top of sentences to last
//		String br = System.getProperty("line.separator");
//		for(int i=1;i<sentenceNumber;i++)
//			if(sentence[i].word[0].equals(br))
//			{
//				sentence[i-1].sentenceText += br;
//				sentence[i].sentenceText = sentence[i].sentenceText.substring(1,sentence[i].sentenceText.length());
//				}


    for (int i=0; i<keywordNumber; i++) {
      keyword[i].frequencyAsSubject = 0;
      keyword[i].segmentFrequencyAsSubject = 0;
      keyword[i].lastAppearingSegment = -1;
    }

    for (int i=0; i<segmentNumber; i++)
      for (int j=0; j<segment[i].sentenceNumber; j++)
        for (int k=0; k<segment[i].sentence[j].wordNumber; k++)
          if (segment[i].sentence[j].subjectCheck[k] && segment[i].sentence[j].wordIDList[k] >= 0) {
            keyword[segment[i].sentence[j].wordIDList[k]].frequencyAsSubject++;
            if (i != keyword[i].lastAppearingSegment) {
              keyword[segment[i].sentence[j].wordIDList[k]].segmentFrequencyAsSubject++;
              keyword[i].lastAppearingSegment = i;
            }
          }

    //Count word Number
    wordNumber=0;
    for (int i=0; i<sentenceNumber; i++) {
      wordNumber+=sentence[i].wordNumber;
    }

    for (int i=0; i<segmentNumber; i++) {
      segment[i].wordNumber = 0;
      for (int j=0; j<segment[i].sentenceNumber; j++) {
        segment[i].wordNumber += segment[i].sentence[j].wordNumber;
      }
    }

    //Calculate keyword appearance data: appearingSentenceTable[], appearingSentence[], sentenceFrequency, seg_times[], seg_sentimes[], appearingSegmentTable[], appearingSegment[], segmentFrequency
    createKeywordAppearance();

    //Calculate segment[].kind_num, sentence[].kind_num
    createKindNumbers();

    //PreProcess 1, Calculate cos similarity between segments, sentences, keywords by segment frequencies, and keywords by sentences frequencies

    System.out.println("START RELATION CALCULATION");
    if (option == 0) {
      createTextRelation();
    }

    focus = new Focus(keywordNumber,sentenceNumber,segmentNumber);
  }
  /*
  public void setSegmentPartialTextData()
  {
  	segmentPartialTextData[0].createSegmentPartialData(this);
  }

  public void setSentencePartialTextData()
  {
  	sentencePartialTextData[0].createSentencePartialData(this);
  }
  */
  //newly created
  public void setSegmentPartialTextData(int number) {
    if (number >= 0 && number < maxSegmentPartialNumber) {
      segmentPartialTextData[number].createSegmentPartialData(this,0);
    }
  }
  //newly created
  public void setSentencePartialTextData(int number) {
    if (number >= 0 && number < maxSentencePartialNumber) {
      sentencePartialTextData[number].createSentencePartialData(this,0);
    }
  }


//	public TextData(String sourcefile, String chafile, SettingData sp1, String absolute, Control cont)
  //Executed when the shrink button pushed
  public void createSegmentPartialData(TextData text, int option) {
    sourceFilename = text.sourceFilename;
    defaultCode = text.defaultCode;
    settingData = text.settingData;
    control = text.control;
    absolutePath = text.absolutePath;
    segmentTag = text.segmentTag;
    sentenceTag = text.sentenceTag;

    connection = text.connection;


    StringWriter sw = new StringWriter();			//originalText
    BufferedWriter bw = new BufferedWriter(sw);

    try {
      for (int i=0; i<text.segmentNumber; i++)
        if (text.focus.focusSegments[i]) {
          bw.write(text.segment[i].segmentText);
          bw.newLine();
        }
      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in Creating Partial TextData");
    }

    originalText = sw.toString();


    //Keywords Data
    keywordNumber = text.keywordNumber;
    keyword = new KeywordData[keywordNumber];

    for (int i=0; i<keywordNumber; i++) {
      keyword[i] = new KeywordData(text, i);
    }

    int count = 0;
    for (int i=0; i<text.segmentNumber; i++)
      if (text.focus.focusSegments[i])
        for (int j=0; j<keywordNumber; j++) {
          keyword[j].frequency += text.keyword[j].frequencyInSegment[i];

        }

    //Segment Data
    count = 0;
    for (int i=0; i<text.segmentNumber; i++)
      if (text.focus.focusSegments[i]) {
        count++;
      }

    originalSegmentNumbers = new int[count];
    segmentNumber = count;
    segment = new SegmentData[segmentNumber];

    count = 0;
    int count2 = 0;
    for (int i=0; i<text.segmentNumber; i++)
      if (text.focus.focusSegments[i]) {
        originalSegmentNumbers[count] = i;
        segment[count] = new SegmentData(i, text.segment[i].segmentText, count);
        segment[count].positionOfFirstSentence = count2;
        count2 += text.segment[i].sentenceNumber;
        count++;
      }

    //Sentence Data
    sentenceNumber = count2;
    originalSentenceNumbers = new int[count2];

    count = 0;
    count2 = 0;
    for (int i=0; i<text.segmentNumber; i++)
      if (text.focus.focusSegments[i]) {
        segment[count].initializeSentence(text, i);

        for (int j=0; j<text.segment[i].sentenceNumber; j++) {
          originalSentenceNumbers[count2+j] = text.segment[i].sentence[j].positionOfSentence;
        }

        count2 += text.segment[i].sentenceNumber;
        count++;
      }

    initializeData(option);

//		count=0;
//		for(int i=0;i<segmentNumber;i++)
//			for(int j=0;j<segment[i].sentenceNumber;j++)
//				System.out.println("ORIGINAL "+originalSegmentNumbers[i]+" "+originalSentenceNumbers[count++]);
  }

  //Executed when the shrink button pushed
  public void createSentencePartialData(TextData text, int option) {
    sourceFilename = text.sourceFilename;
    defaultCode = text.defaultCode;
    settingData = text.settingData;
    control = text.control;
    absolutePath = text.absolutePath;
    segmentTag = text.segmentTag;
    sentenceTag = text.sentenceTag;

    connection = text.connection;


    StringWriter sw = new StringWriter();			//originalText
    BufferedWriter bw = new BufferedWriter(sw);

    try {
      for (int i=0; i<text.sentenceNumber; i++)
        if (text.focus.focusSentences[i]) {
          bw.write(text.sentence[i].sentenceText);
//					bw.newLine();
        }
      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in Creating Partial TextData");
    }

    originalText = sw.toString();


    //Keywords Data
    keywordNumber = text.keywordNumber;
    keyword = new KeywordData[keywordNumber];

    for (int i=0; i<keywordNumber; i++) {
      keyword[i] = new KeywordData(text, i);
    }

    for (int i=0; i<keywordNumber; i++) {
      keyword[i].frequency = text.keyword[i].frequency;
    }

    for (int i=0; i<text.sentenceNumber; i++)
      if (text.focus.focusSentences[i] == false)
        for (int j=0; j<text.sentence[i].keywordNumber; j++) {
          keyword[text.sentence[i].keywordIDList[j]].frequency--;

        }


    //Segment Data
    segmentNumber = text.segmentNumber;
    segment = new SegmentData[segmentNumber];

    originalSegmentNumbers = new int[text.segmentNumber];
    for (int i=0; i<text.segmentNumber; i++) {
      originalSegmentNumbers[i] = i;
    }

    int count = 0;
    int count2 = 0;
    for (int i=0; i<text.segmentNumber; i++) {
      String segmentText;
      try {
        count = count2;
        for (int j=0; j<text.segment[i].sentenceNumber; j++)
          if (text.focus.focusSentences[text.segment[i].positionOfFirstSentence + j]) {
            bw.write(text.sentence[count2].sentenceText);
            //					bw.newLine();
            count2++;
          }
        bw.flush();
      } catch (Exception e) {
        System.out.println("writing ERROR in Creating Partial TextData");
      }

      segmentText = sw.toString();

      segment[i] = new SegmentData(i, segmentText, i);
      segment[i].positionOfFirstSentence = count;
    }

    //Sentence Data
    sentenceNumber = count2;
    originalSentenceNumbers = new int[count2];

    count2=0;
    for (int i=0; i<text.sentenceNumber; i++)
      if (text.focus.focusSentences[i]) {
        originalSentenceNumbers[count2++] = i;
      }

    for (int i=0; i<segmentNumber; i++) {
      segment[i].initializePartialSentence(text, i);
    }

    initializeData(option);

//		count=0;
//		for(int i=0;i<segmentNumber;i++)
//			for(int j=0;j<segment[i].sentenceNumber;j++)
//				System.out.println("ORIGINAL "+originalSegmentNumbers[i]+" "+originalSentenceNumbers[count++]);
  }


//---------------------------

  public String getSegmentTag() {
    return segmentTag;
  }

  public String getSentenceTag() {
    return sentenceTag;
  }

  public void fileLoad() {
    control.textDataLoadingAgain();//////////
    control.resetSelectpanelData(this);//////////
  }

  public void fileSaveOnly(String alltext) {
    File savefile = new File(sourceFilename);

    try {
      BufferedWriter bw1;
      if (settingData.morpheme.equals("Igo")) {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), "SJIS"));
      } else {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), defaultCode));
      }

      bw1.write(alltext);
      bw1.close();
      System.out.println("Save text to the file: "+savefile.getName());
    } catch (Exception e) {
      System.out.println("File Writing ERROR Text data");
    }

    EXECUTE.filecopy(sourceFilename,absolutePath+"REVISEDTEXT");
    System.out.println("Save to the file "+absolutePath+"REVISEDTEXT");
  }

  public void fileSaveOnly(String subtexts[]) {
    File savefile = new File(sourceFilename);

    try {
      BufferedWriter bw1;
      if (settingData.morpheme.equals("Igo")) {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), "SJIS"));
      } else {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), defaultCode));
      }

      for (int i=0; i<subtexts.length; i++) {
        bw1.write(subtexts[i]);
      }
      bw1.close();
      System.out.println("Save text to the file: "+savefile.getName());
    } catch (Exception e) {
      System.out.println("File Writing ERROR Text data");
    }

    EXECUTE.filecopy(sourceFilename,absolutePath+"REVISEDTEXT");
    System.out.println("Save to the file "+absolutePath+"REVISEDTEXT");
  }

  public void fileSave(String alltext) {
    File savefile = new File(sourceFilename);

    try {
      BufferedWriter bw1;
      if (settingData.morpheme.equals("Igo")) {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), "SJIS"));
      } else {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), defaultCode));
      }

      bw1.write(alltext);
      bw1.close();
      System.out.println("Save text to the file: "+savefile.getName());
    } catch (Exception e) {
      System.out.println("File Writing ERROR Text data");
    }
    control.textDataLoadingWithoutCopy();//////////
    control.resetSelectpanelData();//////////

    EXECUTE.filecopy(sourceFilename,absolutePath+"REVISEDTEXT");
    System.out.println("Save to the file "+absolutePath+"REVISEDTEXT");
  }

  public void fileSave(String subtexts[]) {
    File savefile = new File(sourceFilename);

    try {
      BufferedWriter bw1;
      if (settingData.morpheme.equals("Igo")) {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), "SJIS"));
      } else {
        bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savefile), defaultCode));
      }

      for (int i=0; i<subtexts.length; i++) {
        bw1.write(subtexts[i]);
      }
      bw1.close();
      System.out.println("Save text to the file: "+savefile.getName());
    } catch (Exception e) {
      System.out.println("File Writing ERROR Text data");
    }
    control.textDataLoadingWithoutCopy();//////////
    control.resetSelectpanelData();//////////

    EXECUTE.filecopy(sourceFilename,absolutePath+"REVISEDTEXT");
    System.out.println("Save to the file "+absolutePath+"REVISEDTEXT");
  }


  public void createKeywordAppearance() {
    int id;

    for (int i=0; i<keywordNumber; i++) {
      keyword[i].initializeTables(segmentNumber, sentenceNumber);
    }

    for (int i=0, k=0; i<segmentNumber; i++)
      for (int j=0; j<segment[i].sentenceNumber; j++,k++)
        for (int n=0; n<segment[i].sentence[j].keywordNumber; n++) {
          id = segment[i].sentence[j].keywordIDList[n];

          if (keyword[id].lastAppearingSegment != i && keyword[id].frequency > 0)
            // Avoid from counting twice in a segment, Noise may have id but frequency must be zero
          {
            keyword[id].appearingSegmentTable[i] = 1;
            keyword[id].lastAppearingSegment = keyword[id].appearingSegment[keyword[id].segmentFrequency++] = i;
          }

          if (keyword[id].lastAppearingSentence != k && keyword[id].frequency > 0)
            // Avoid from counting twice in a sentence, Noise may have id but frequency must be zero
          {
            keyword[id].appearingSentenceTable[k] = 1;
            keyword[id].lastAppearingSentence = keyword[id].appearingSentence[keyword[id].sentenceFrequency++] = k;

            keyword[id].sentenceFrequencyInSegment[i]++;
          }
          keyword[id].frequencyInSegment[i]++;
        }
  }

  public void createKeywordAppearanceSegmentOnly() {
    int id;

    for (int i=0; i<keywordNumber; i++) {
      keyword[i].initializeSegmentTable(segmentNumber);
    }

    for (int i=0, k=0; i<segmentNumber; i++)
      for (int j=0; j<segment[i].sentenceNumber; j++,k++)
        for (int n=0; n<segment[i].sentence[j].keywordNumber; n++) {
          id = segment[i].sentence[j].keywordIDList[n];

          if (keyword[id].lastAppearingSegment != i && keyword[id].frequency > 0)
            // Avoid from counting twice in a segment, Noise may have id but frequency must be zero
          {
            keyword[id].appearingSegmentTable[i] = 1;
            keyword[id].lastAppearingSegment = keyword[id].appearingSegment[keyword[id].segmentFrequency++] = i;
          }

          if (keyword[id].lastAppearingSentence != k && keyword[id].frequency > 0)
            // Avoid from counting twice in a sentence, Noise may have id but frequency must be zero
          {
            keyword[id].sentenceFrequencyInSegment[i]++;
          }
          keyword[id].frequencyInSegment[i]++;
        }
  }

  public void createKindNumbers() {
    createKindNumbersForSegment();
    createKindNumbersForSentence();
  }

  public void createKindNumbersForSegment() {
    int kindNumberList[] = new int[segmentNumber];
    for (int i=0; i<segmentNumber; i++) {
      kindNumberList[i] = 0;

      for (int j=0; j<keywordNumber; j++)
        for (int k=0; k<segmentNumber && keyword[j].appearingSegment[k] != -1; k++) {
          kindNumberList[keyword[j].appearingSegment[k]]++;
        }

      segment[i].kindKeywordNumber = kindNumberList[i];
    }
  }

  public void createKindNumbersForSentence() {
    int kindNumberList[] = new int[sentenceNumber];
    for (int i=0; i<sentenceNumber; i++) {
      kindNumberList[i] = 0;

      for (int j=0; j<keywordNumber; j++)
        for (int k=0; k<sentenceNumber && keyword[j].appearingSentence[k] != -1; k++) {
          kindNumberList[keyword[j].appearingSentence[k]]++;
        }

      sentence[i].kindKeywordNumber = kindNumberList[i];
    }
  }

  //---------------------------


  public int[] getCommonKeywords(boolean segments[]) {
    int commonKeywords[] = new int[keywordNumber];

    for (int i=0; i<keywordNumber; i++) {
      commonKeywords[i] = 0;
    }

    for (int i=0; i<keywordNumber; i++)
      for (int j=0; j<segmentNumber; j++) {
        if (segments[j] == false) {
          continue;
        }

        if (keyword[i].frequencyInSegment[j] == 0) {
          commonKeywords[i] = 0;
          break;
        } else {
          commonKeywords[i] += keyword[i].frequencyInSegment[j];
        }
      }

    return commonKeywords;
  }

//---------------------------

  public String nameList[];
  int frequencyList[];
  int coFrequencyList[][];

  public void createTextRelation() {
    System.out.println("Start SegmentRelation");
    createSegmentRelation();
//        System.out.println("Start SentenceRelation");
//		createSentenceRelation();
    System.out.println("Start KeywordsRelation");
    createKeywordsRelation();
  }

  public void createSegmentRelation() {
    int limit = 1000;

    if (segmentNumber > limit) {
      System.out.println("NOT CREATED SEGMENT RELATION segmentNumber/max =:"+segmentNumber +"/"+limit);
    } else {
      Pattern pattern = Pattern.compile("([A-Za-z0-9_]+.txt)");		//TEXT FILE NAME

      segmentRelation = new RelationalData(segmentNumber);
      initializeLists(segmentNumber);
      for (int i=0; i<segmentNumber; i++)
        if (segment[i].sentenceNumber > 0) {
          Matcher matcher = pattern.matcher(segment[i].sentence[0].sentenceText);
          if (matcher.find()) {
            nameList[i] = matcher.group(1);
          } else {
            nameList[i] = "SEG"+(segment[i].segmentID+1);
          }
        } else {
          nameList[i] = "SEG"+(segment[i].segmentID+1);
        }

      for (int i=0; i<segmentNumber; i++) {
        frequencyList[i] = segment[i].kindKeywordNumber;
      }
      for (int i=0; i<segmentNumber; i++)
        for (int j=0; j<=i; j++) {
          coFrequencyList[i][j] = coFrequencyList[j][i] = 0;
          for (int k=0; k<keywordNumber; k++) {
            coFrequencyList[i][j] = coFrequencyList[j][i] += (keyword[k].appearingSegmentTable[i] * keyword[k].appearingSegmentTable[j]);
          }
        }
      calcurateValues(segmentRelation);
    }
  }
  /*
  public void createSentenceRelation()
  {
  	int limit = 1000;

  	if(sentenceNumber > limit)
  		System.out.println("NOT CREATED SENTENCE RELATION sentenceNumber/max =:"+sentenceNumber +"/"+limit);
  	else
  	{
  		sentenceRelation = new RelationalData(sentenceNumber);
  		initializeLists(sentenceNumber);
  		for(int i=0;i<sentenceNumber;i++)
  			nameList[i] = "SEN"+(sentence[i].sentenceID+1);
  		for(int i=0;i<sentenceNumber;i++)
  			frequencyList[i] = sentence[i].kindKeywordNumber;
  		for(int i=0;i<sentenceNumber;i++)
  			for(int j=0;j<=i;j++)
  			{
  				coFrequencyList[i][j] = coFrequencyList[j][i] = 0;
  				for(int k=0;k<keywordNumber;k++)
  					coFrequencyList[i][j] = coFrequencyList[j][i] += (keyword[k].appearingSentenceTable[i] * keyword[k].appearingSentenceTable[j]);
  			}
  		calcurateValues(sentenceRelation);
  	}
  }
  */
  public void createKeywordsRelation() {
    int limit = 1500;

    if (keywordNumber > limit) {
      System.out.println("NOT CREATED KEYWORD RELATION keywordNumber/max =:"+keywordNumber +"/"+limit);
    } else {
      //keyword relation by segment
      keywordRelationBySegment = new RelationalData(keywordNumber);
      initializeLists(keywordNumber);
      for (int i=0; i<keywordNumber; i++) {
        nameList[i] = keyword[i].word;
      }
      for (int i=0; i<keywordNumber; i++) {
        frequencyList[i] = keyword[i].segmentFrequency;
      }
      for (int i=0; i<keywordNumber; i++)
        for (int j=0; j<=i; j++) {
          coFrequencyList[i][j] = coFrequencyList[j][i] = 0;
          for (int k=0; k<segmentNumber; k++) {
            coFrequencyList[i][j] = coFrequencyList[j][i] += (keyword[i].appearingSegmentTable[k] * keyword[j].appearingSegmentTable[k]);
          }
        }
      calcurateValues(keywordRelationBySegment);

      //keyword relation by sentence
      /*
      keywordRelationBySentence = new RelationalData(keywordNumber);
      initializeLists(keywordNumber);
      for(int i=0;i<keywordNumber;i++)
      nameList[i] = keyword[i].word;
      for(int i=0;i<keywordNumber;i++)
      frequencyList[i] = keyword[i].sentenceFrequency;
      for(int i=0;i<keywordNumber;i++)
      for(int j=0;j<=i;j++)
      {
      coFrequencyList[i][j] = coFrequencyList[j][i] = 0;
      for(int k=0;k<sentenceNumber;k++)
      coFrequencyList[i][j] = coFrequencyList[j][i] += (keyword[i].appearingSentenceTable[k] * keyword[j].appearingSentenceTable[k]);
      }
      calcurateValues(keywordRelationBySentence);
       */
    }
  }

  void initializeLists(int num) {
    nameList = new String[num];
    frequencyList = new int[num];
    coFrequencyList = new int[num][num];
  }

  void calcurateValues(RelationalData rd) {
    rd.setName(nameList);
    rd.setFrequency(frequencyList);
    rd.setCoFrequency(coFrequencyList);
    rd.createCondValue();
    rd.createCosValue();
  }



//---------------------------


  //Save load data to buffer and copies after all
  public void readChasenFile(String chaf, SettingData sp1, boolean english) {
    File chafile = new File(chaf);
    String ss, ssNext;
    String tempword, gomi1, endformword, hinshi, tempword2;
    int notr = 0;		//Flag for eliminating new-lines without words at the end of a segment

//		String br = System.getProperty("line.separator");

    System.out.println("##chafile = "+chaf);

    String keywords = "";

    String japaneseWords[];
    japaneseWords = FILEIO.TextFileAllReadCodeArray(new File(absolutePath + "source" + File.separator + "TextData" + File.separator + "Japanese.txt"));

    try {
      int countw1 = 0;	//Number of keywords in a sentence
      int countw2 = 0;	//Number of all words in a sentence
      int counts = 0;		//Number of lines
      int total_counts = 0;	//Number of all lines in original text
      int seg_n = 0;		//Number of segment that is currently loaded

      int countSub = 999;		//For search subjects(Nominative)

      boolean checked = false;	//For checking subjects

      boolean igo;

      if (sp1.morpheme.equals("Igo")) {
        igo = true;
      } else {
        igo = false;
      }

      BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(chafile), "JISAutoDetect"));

      StringWriter sw1 = new StringWriter();			//For words
      BufferedWriter bw1 = new BufferedWriter(sw1);
      StringWriter sw2 = new StringWriter();			//For word IDs
      BufferedWriter bw2 = new BufferedWriter(sw2);
      StringWriter sw3 = new StringWriter();			//For keywords
      BufferedWriter bw3 = new BufferedWriter(sw3);
      StringWriter sw4 = new StringWriter();			//For end-form of words
      BufferedWriter bw4 = new BufferedWriter(sw4);

      StringWriter sw5 = new StringWriter();			//For subject words
      BufferedWriter bw5 = new BufferedWriter(sw5);

      ss = br1.readLine();

      for (; ss != null; ss = ssNext) {
        ssNext = br1.readLine();

        if (ss.equals("")) {
          continue;
        }

        String oneline[] = ss.split("	"); //TAB
        tempword = oneline[0];



        // System.out.println(tempword);//
        // System.out.println("counts = "+counts+" total_counts= "+total_counts);//


        if (tempword.equals("EOS")) {

          bw1.newLine();
          bw1.write(" ");

          bw4.newLine();
          bw4.write(" ");
          bw2.write("-8 ");
          bw5.write("-8 ");

          countw2++;
          continue;
        }

        if (igo) {
          String oneline9[] = oneline[1].split(",");//Igo/////////////
          endformword = oneline9[6];
          if (endformword.equals("*")) { //unknown words for IGO
            endformword = tempword;
          }

          hinshi = oneline9[0];//+"-"+oneline9[1]+"-"+oneline9[2]+"-"+oneline9[3];
          for (int i=0; i<3; i++)
            if (oneline9[i+1].equals("*")) {
              break;
            } else {
              hinshi = hinshi+"-"+oneline9[i+1];
            }
        } else {
          endformword = oneline[2];
          hinshi = oneline[3];
        }
        /*
        				gomi1 = oneline[1];

         */

        //SEGMENT DIVISION
        if (tempword.equals(sp1.segment_tag)) {	//The end of the segment [sunaribarafuto]
          String oneline2[] = ssNext.split("	"); //TAB
          tempword2 = oneline2[0];


          //Process at the end of a sentence//Do not count as sentence that is only new-line
          if (countw2 > 0 && notr == 1) {
            counts++;
            bw1.write(" @@");
            bw2.write(" @");
            bw3.write(countw1+" ");
            bw4.write(" @@");
            bw5.write(" @");
            //		System.out.println("SEN:"+counts+" "+countw1+"/"+countw2);//
            countw1 = countw2 = 0;
            notr = 0;
            countSub = 999; //NEW
          }

          //Process at the end of a segment
          segment[seg_n].sentenceNumber = counts;
          segment[seg_n].positionOfFirstSentence = total_counts;
          total_counts += counts;
          bw1.flush();
          bw2.flush();
          bw3.flush();
          bw4.flush();
          bw5.flush();
          segment[seg_n].initializeSentence(sw1.toString(), sw2.toString(), sw3.toString(), sw4.toString(), sw5.toString(), english);
          sw1.getBuffer().delete(0,sw1.getBuffer().length());
          sw2.getBuffer().delete(0,sw2.getBuffer().length());
          sw3.getBuffer().delete(0,sw3.getBuffer().length());
          sw4.getBuffer().delete(0,sw4.getBuffer().length());
          sw5.getBuffer().delete(0,sw5.getBuffer().length());
          seg_n++;
          //			System.out.println("SEG-"+seg_n+" sen:"+counts);//
          counts = 0;

          if (sp1.leftTags == false) {
            continue;  // IF YOU WANT TO ADD TAG-WORD INTO TEXT DATA, NOT CONTINUE;  AND YOU ALSO CHANGE CutText method in FILEIO.java
          }
        }


        //UNKNOWN word
        if (hinshi.equals(sp1.unknown)) {
          bw1.write(tempword+" ");
          bw4.write(endformword+" ");
          bw2.write("-1 ");
          bw5.write("0 ");
          countw2++;
          notr = 1;

          if (countSub > 1) {
            countSub = 0;  //Subject candidate
          }

          continue;
        }



//System.out.println(tempword);

        if (oneline[1].equals("")) {	//For ChaSen bug { Symbol opening parenthesis //It's OK if morpheme was IGO
          bw1.write(tempword+" ");
          bw4.write(endformword+" ");
          bw2.write("-2 ");
          bw5.write("0 ");
          countw2++;
          notr = 1;
          continue;
        }

        bw1.write(tempword+" ");	//All other words
        bw4.write(endformword+" ");	//All other words
        notr = 1;
        countw2++;

        int k=0;	//For ID

        //			System.out.println("countw2= "+countw2+" notr= "+notr);

        // End of a sentence
        for (int i=0; i<sp1.touns; i++)
          if (tempword.equals(sp1.kind_touns[i])) {
            String oneline2[] = ssNext.split("	"); //TAB
            tempword2 = oneline2[0];

            if (tempword2.equals("EOS")) {
              bw1.newLine();
              bw1.write(" ");

              bw4.newLine();
              bw4.write(" ");
              bw2.write("-8 ");
              bw5.write("-8 ");

              countw2++;
              ssNext = br1.readLine();
            }

            counts++;
            bw1.write(" @@");
            bw2.write("-3 @");
            bw3.write(countw1+" ");
            bw4.write(" @@");
            bw5.write("-3 @");

            //				System.out.println("SEN:"+counts+" "+countw1+"/"+countw2);//
            countw1 = countw2 = 0;
            notr = 0;
            k=1;
            countSub = 9999; //NEW
//						System.out.println(tempword+"countS = "+countSub);
          }

        if (countSub == 9999) {
          countSub++;
          continue;
        }

        countSub++;//ALL WORDS except ends of segments, sentences, and {

        checked = false;

        //Particles for finding subject
        for (int i=0; i<sp1.objects; i++)
          if (tempword.equals(sp1.kind_objects[i]) && hinshi.indexOf(sp1.kind_objects_speech[i]) == 0) {
            bw5.write(countSub+" ");
//						System.out.println("bw5="+countSub+" ");//FOR SUBJECT
            checked = true;
            break;
          }


        //FOR FINDING SUBJECTS - NOUN
        for (int i=0; i<japaneseWords.length; i++)
          if (hinshi.indexOf(japaneseWords[i]) == 0) {
            if (countSub > 1) {
              countSub = 0;  //Subject candidate
            }

//						System.out.println(tempword+"countS = "+countSub);
          }


        //Process for noise-1: Elimination of stop words
        {
          int i;
          for (i=0; i<sp1.noises; i++)
            if (endformword.equals(sp1.kind_noises[i])) {
              break;
            }
          if (i < sp1.noises) {
            bw2.write("-4 ");
            if (!checked) {
              bw5.write("-1 ");
            }
            continue;
          }
        }

        //Process for noise-2: Elimination of HIRAGANA, one letter except for periods(k==0)
        if (endformword.length() == 1 && k == 0) //For One character words
          if (sp1.excludeOne) {
            bw2.write("-5 ");
            if (!checked) {
              bw5.write("-1 ");
            }
            continue;
          }

        char code = tempword.charAt(0);
        if (code>=0x3041 && code<=0x3093)	//For HIRAGANA words
          if (sp1.excludeHiragana) {
            bw2.write("-6 ");
            if (!checked) {
              bw5.write("-1 ");
            }
            continue;
          }

        if (code>=0x30a1 && code<=0x30f3)	//For KATAKANA words
          if (sp1.excludeKatakana) {
            bw2.write("-6 ");
            if (!checked) {
              bw5.write("-1 ");
            }
            continue;
          }

        if (tempword.getBytes().length == tempword.length()) //For HANKAKU words
          if (sp1.excludeHankaku) {
            bw2.write("-6 ");
            if (!checked) {
              bw5.write("-1 ");
            }
            continue;
          }

        //Keywords without conjugation
        for (int i=0; i<sp1.nouns; i++)
          if (hinshi.indexOf(sp1.kind_nouns[i]) == 0) {
            //Entry for Keyword list
            countw1++;
            int j;
            for (j=0; j<keywordNumber; j++)
              if (endformword.equals(keyword[j].word)) {
                break;
              }
            if (j == keywordNumber) {
              keyword[keywordNumber].word = endformword;
              keywordNumber++;
              keywords += (tempword+" ");
            }

            keyword[j].frequency++;	//Count frequency


            bw2.write(j+" ");

            if (!checked) {
              bw5.write("-1 ");
            }
            k=1;

            if (hinshi.indexOf(sp1.kind_speech[0]) ==0) {
              keyword[j].partOfSpeech = 1;  //NOUN
            } else if (hinshi.indexOf(sp1.kind_speech[1]) ==0) {
              keyword[j].partOfSpeech = 4;  //CONJUNCTION
            } else if (hinshi.indexOf(sp1.kind_speech[2]) ==0) {
              keyword[j].partOfSpeech = 5;  //ADVERB
            } else if (hinshi.indexOf(sp1.kind_speech[3]) ==0) {
              keyword[j].partOfSpeech = 9;  //pre-noun adjectival
            } else if (hinshi.indexOf(sp1.kind_speech[4]) ==0) {
              keyword[j].partOfSpeech = 6;  //INTERJECTION
            } else if (hinshi.indexOf(sp1.kind_speech[5]) ==0) {
              keyword[j].partOfSpeech = 7;  //PARTICLE
            }

          }

        //Keywords with conjugation
        for (int i=0; i<sp1.verbs; i++)
          if (hinshi.indexOf(sp1.kind_verbs[i]) == 0) {
            //Entry for keyword list
            countw1++;
            int j;
            for (j=0; j<keywordNumber; j++)
              if (endformword.equals(keyword[j].word)) {
                break;
              }
            if (j == keywordNumber) {
              keyword[keywordNumber].word = endformword;
              keywordNumber++;
              keywords += (" "+tempword);
            }
            keyword[j].frequency++;	//Count frequency

            bw2.write(j+" ");
            if (!checked) {
              bw5.write("-1 ");
            }
            k=1;

            if (hinshi.indexOf(sp1.kind_speech[6]) ==0) {
              keyword[j].partOfSpeech = 2;  //VERB
            } else if (hinshi.indexOf(sp1.kind_speech[7]) ==0) {
              keyword[j].partOfSpeech = 3;  //ADJECTIVE
            } else if (hinshi.indexOf(sp1.kind_speech[8]) ==0) {
              keyword[j].partOfSpeech = 8;  //AUXILIARY VERB
            }
          }
        if (k == 0) {
          bw2.write("-7 ");
          if (!checked) {
            bw5.write("-1 ");
          }
        }

      }

      br1.close();

      if (countw2 > 1 && notr == 1)	//When text does not end with a period
        //Process at the end of sentences//Do not count as sentence which has only new-line
      {
        counts++;
        bw3.write(countw1+" ");
        //	System.out.println("[SEN]:"+counts+" "+countw1+"/"+countw2);//
      }

      //AT THE END OF A SEGMENT

      segment[seg_n].sentenceNumber = counts;
      segment[seg_n].positionOfFirstSentence = total_counts;
      total_counts += counts;
      bw1.flush();
      bw2.flush();
      bw3.flush();
      bw4.flush();
      bw5.flush();
      segment[seg_n].initializeSentence(sw1.toString(), sw2.toString(), sw3.toString(), sw4.toString(), sw5.toString(), english);
      sw1.getBuffer().delete(0,sw1.getBuffer().length());
      sw2.getBuffer().delete(0,sw2.getBuffer().length());
      sw3.getBuffer().delete(0,sw3.getBuffer().length());
      sw4.getBuffer().delete(0,sw4.getBuffer().length());
      sw5.getBuffer().delete(0,sw5.getBuffer().length());
      seg_n++;
      //System.out.println("[SEG]-"+seg_n+" sen:"+counts);//

      sentenceNumber = total_counts;
    } catch (Exception e) {
      System.out.println("File Reading ERROR chasen data in TextData.java");
    }
  }
}

