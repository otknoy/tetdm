//
// Core Program for TETDM
// SettingData.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.io.*;
import java.util.*;


public class SettingData {
  public String guideComments[];

  public String kind_nouns[];	//Part of speech without conjugation
  public String kind_verbs[];	//Part of speech with conjugation
  public String kind_touns[];	//Period/Cut points of sentences
  public String kind_noises[];	//Noise/Stop words
  public String unknown;			//Unknown words
  public String segment_tag;		//Tag for cutting segments
  public String kind_objects[];	//Subject words/Nominative
  public String kind_objects_speech[];	//Part of speech for subject
  public String kind_speech[];	//All Part of speech

  public boolean leftTags = false;

  public String settingLines[];

  int comments=13;

  public int nouns=0;
  public int verbs=0;
  public int touns=0;
  public int noises=0;
  public int objects=0;

  int speeches = 9;//kind number of part of speech
  int settings = 11;


  public String miningLines[];
  public String visualizationLines[];

  int miningLineNumber = 0;
  int visualizationLineNumber = 0;

  //for setting panels
  public String panelCombinationLines[];
  public int panels = 0;
  int MAX_PANELS = 10;
  public int miningID[];
  public int visualizationID[];
  int balancedPanelSizeX[];

  public int lastMiningID[];
  public int lastVisualizationID[];

  boolean isWindows;
  public boolean isEnglish = false;
  public String defaultCode = "EUC-JP";		//Japanese code for ChaSen, MAC:EUC-JP, WIN:SJIS
  String chasenPath;
  String dictionaryPath;
  boolean chasenexe = true;
  public String morpheme;// = "Igo";

  String defaultChasenPath = "/opt/local/bin";

  public boolean excludeHiragana = true;
  public boolean excludeKatakana = true;
  public boolean excludeOne = true;
  public boolean excludeHankaku = true;

  public boolean inJapanese = true;


  public ModuleData moduleData;

  String settingFileName;

  String absolutePath;

  public SettingData(String settingFile, ModuleData moduleD) {
    settingFileName = settingFile;
    moduleData = moduleD;
    absolutePath = moduleData.absolutePath;

    miningID = new int[MAX_PANELS];
    visualizationID = new int[MAX_PANELS];
    balancedPanelSizeX = new int[MAX_PANELS];

    readSettingFile(new File(settingFileName));

    String osname = System.getProperty("os.name");
    if (osname.indexOf("Windows")>=0) {
      isWindows = true;
    } else {
      isWindows = false;
    }

    if (isWindows) {
      defaultCode = "SJIS";
    }

    System.out.println("DefaultCode= "+defaultCode);
  }

  public void readSettingFile(File settingFile) {
    String speechtext = FILEIO.TextFileAllReadCode(settingFile);

    count_numbers(speechtext);
    init_arrays();
    read_texts(speechtext);

    setModuleSettings();

    String words[] = settingLines[2].split(" ");
    if (words.length == 1) {
      chasenexe = false;
    } else {
      chasenexe = true;
      morpheme = words[1];
    }

    //chasenPath
    words = settingLines[3].split(" ");
    if (words.length == 1) {
      if (morpheme.equals("ChaSen")) {
        chasenPath = defaultChasenPath;
      } else {
        chasenPath = absolutePath;
      }
    } else {
      chasenPath = words[1];
    }

    //dictionaryPath
    words = settingLines[4].split(" ");
    if (words.length == 1) {
      dictionaryPath = absolutePath;
    } else {
      dictionaryPath = words[1];
    }

    words = settingLines[6].split(" ");
    excludeHiragana = words[1].equals("true");
    words = settingLines[7].split(" ");
    excludeKatakana = words[1].equals("true");
    words = settingLines[8].split(" ");
    excludeOne = words[1].equals("true");
    words = settingLines[9].split(" ");
    inJapanese = words[1].equals("true");
    words = settingLines[10].split(" ");
    leftTags = words[1].equals("true");
  }



  public void readOriginalSettingFile() {
    readSettingFile(new File(settingFileName+".original"));
  }

  public void count_numbers(String speechtext) {
    String ss;
    int count=0, n=0;

    int comm = 0;
    guideComments = new String[comments];

    try {
      BufferedReader br1 = new BufferedReader(new StringReader(speechtext));

      while ((ss = br1.readLine()) != null && comm < 13)
        if (ss.charAt(0) == '#') {
          guideComments[comm++] = ss;
          switch (count) {
            case 1:
              nouns = n;
              break;
            case 2:
              verbs = n;
              break;
            case 3:
              touns = n;
              break;
            case 4:
              noises = n;
              break;
            case 7:
              objects = n;
              break;
            case 10:
              miningLineNumber = n;
              break;
            case 11:
              visualizationLineNumber = n;
              break;
            case 12:
              panels = n;
              break;
          }
          count++;
          n=0;
        } else {
          n++;
        }
    } catch (Exception e) {
      System.out.println("Text Reading ERROR speechtext1");
    }
  }

  public void init_arrays() {
    kind_nouns = new String[nouns];
    kind_verbs = new String[verbs];
    kind_touns = new String[touns];
    kind_noises = new String[noises];
    kind_objects = new String[objects];
    kind_objects_speech = new String[objects];

    kind_speech = new String[speeches];

    settingLines = new String[settings];

    miningLines = new String[miningLineNumber];
    visualizationLines = new String[visualizationLineNumber];

    panels--;
    panelCombinationLines = new String[panels];
  }

  public void read_texts(String speechtext) {
    String ss;

    try {
      BufferedReader br1 = new BufferedReader(new StringReader(speechtext));

      ss = br1.readLine();
      for (int i=0; i<nouns; i++) {
        kind_nouns[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<verbs; i++) {
        kind_verbs[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<touns; i++) {
        kind_touns[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<noises; i++) {
        kind_noises[i] = br1.readLine();
      }

      ss = br1.readLine();
      unknown = br1.readLine();

      ss = br1.readLine();
      segment_tag = br1.readLine();

      ss = br1.readLine();
      for (int i=0; i<objects; i++) {
        String shugo[] = (br1.readLine()).split(" ");
        kind_objects[i] = shugo[0];
        kind_objects_speech[i] = shugo[1];
      }

      ss = br1.readLine();
      for (int i=0; i<speeches; i++) {
        kind_speech[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<settings; i++) {
        settingLines[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<miningLineNumber; i++) {
        miningLines[i] = br1.readLine();
      }

      ss = br1.readLine();
      for (int i=0; i<visualizationLineNumber; i++) {
        visualizationLines[i] = br1.readLine();
      }

      ss = br1.readLine();
      ss = br1.readLine();
      for (int i=0; i<panels; i++) {
        panelCombinationLines[i] = br1.readLine();
        String lineData[] = panelCombinationLines[i].split(" ");
        miningID[i] = Integer.parseInt(lineData[0]);
        visualizationID[i] = Integer.parseInt(lineData[1]);
      }

      ss = br1.readLine();
      String lineData[] = br1.readLine().split(" ");
      if (lineData.length < panels || lineData[0].charAt(0) == '#')
        for (int i=0; i<panels; i++) {
          balancedPanelSizeX[i] = 1;
        }
      else
        for (int i=0; i<panels; i++) {
          balancedPanelSizeX[i] = Integer.parseInt(lineData[i]);
        }
    } catch (Exception e) {
      System.out.println("Text Reading ERROR speechtext2");
    }
  }

  public void modifyModuleSettings(int miningIndex, int visualizationID) {
    for (int i=0; i<moduleData.mining[miningIndex].pairingVisualizationID.length; i++)
      if (moduleData.mining[miningIndex].pairingVisualizationID[i] == visualizationID) {
        return;
      }

    int pairingIDs[] = new int[moduleData.mining[miningIndex].pairingVisualizationID.length + 1];

    for (int i=0; i<moduleData.mining[miningIndex].pairingVisualizationID.length; i++) {
      pairingIDs[i] = moduleData.mining[miningIndex].pairingVisualizationID[i];
    }
    pairingIDs[moduleData.mining[miningIndex].pairingVisualizationID.length] = visualizationID;

    moduleData.mining[miningIndex].pairingVisualizationID = new int[pairingIDs.length];
    for (int i=0; i<pairingIDs.length; i++) {
      moduleData.mining[miningIndex].pairingVisualizationID[i] = pairingIDs[i];
    }
  }

  public void setModuleSettings() {
    for (int i=0; i<miningLineNumber; i++) {
      String miningLineData[] = miningLines[i].split(" ");
      int id = Integer.parseInt(miningLineData[0]);
      int pairingIDs[] = new int[miningLineData.length - 4];
      for (int j=0; j<pairingIDs.length; j++) {
        pairingIDs[j] = Integer.parseInt(miningLineData[j+2]);
      }
      boolean selected = miningLineData[miningLineData.length - 2].equals("true");


      for (int j=0; j<moduleData.module_names.length; j++)
        if (id == moduleData.moduleIDs[j]) {
          moduleData.mining[j].pairingVisualizationID = new int[pairingIDs.length];
          for (int k=0; k<pairingIDs.length; k++) {
            moduleData.mining[j].pairingVisualizationID[k] = pairingIDs[k];
          }
          moduleData.setModuleIndex(j, moduleData.mining[j].pairingVisualizationID);

          if (pairingIDs.length == 0) {
            selected = false;
          }
          moduleData.miningModuleSelection[j] = selected;
        }
    }

    for (int i=0; i<moduleData.visu_module_names.length; i++) {
      moduleData.createPairingMiningID(i);
    }
  }

  public void setModulesToPanels(int minID[], int visID[]) {
    panels = minID.length;
    for (int i=0; i<panels; i++) {
      for (int j=0; j<moduleData.module_names.length; j++)
        if (moduleData.moduleIDs[j] == minID[i]) {
          miningID[i] = minID[i];
          moduleData.miningModuleSelection[j] = true;
        }

      for (int j=0; j<moduleData.visu_module_names.length; j++)
        if (moduleData.visuModuleIDs[j] == visID[i]) {
          visualizationID[i] = visID[i];
          moduleData.visualizationModuleSelection[j] = true;


        }
    }
    moduleData.initializeModuleSubset();
  }
  /*
      public void createBackupID()
      {
          lastMiningID = new int[miningID.length];
          lastVisualizationID = new int[visualizationID.length];

          for(int i=0;i<miningID.length;i++)
              lastMiningID[i] = miningID[i];

          for(int i=0;i<visualizationID.length;i++)
              lastVisualizationID[i] = visualizationID[i];
      }

      public void traceBackupID()
      {
          miningID = new int[lastMiningID.length];
          visualizationID = new int[lastVisualizationID.length];

          for(int i=0;i<lastMiningID.length;i++)
              miningID[i] = lastMiningID[i];

          for(int i=0;i<lastVisualizationID.length;i++)
              visualizationID[i] = lastVisualizationID[i];
      }
  */
  public void writeToWriter(BufferedWriter bw1, String words) {
    try {
      bw1.write(words);
      bw1.newLine();
    } catch (Exception e) {
      System.out.println("Text Writing ERROR saveSettingTexts");
    }
  }

  public void saveSettingFile() {
    String saveSettingTexts = "";

    try {
      StringWriter sw1 = new StringWriter();
      BufferedWriter bw1 = new BufferedWriter(sw1);

      writeToWriter(bw1, guideComments[0]);
      for (int i=0; i<nouns; i++) {
        writeToWriter(bw1, kind_nouns[i]);
      }

      writeToWriter(bw1, guideComments[1]);
      for (int i=0; i<verbs; i++) {
        writeToWriter(bw1, kind_verbs[i]);
      }

      writeToWriter(bw1, guideComments[2]);
      for (int i=0; i<touns; i++) {
        writeToWriter(bw1, kind_touns[i]);
      }

      writeToWriter(bw1, guideComments[3]);
      for (int i=0; i<noises; i++) {
        writeToWriter(bw1, kind_noises[i]);
      }

      writeToWriter(bw1, guideComments[4]);
      writeToWriter(bw1, unknown);

      writeToWriter(bw1, guideComments[5]);
      writeToWriter(bw1, segment_tag);

      writeToWriter(bw1, guideComments[6]);
      for (int i=0; i<objects; i++) {
        writeToWriter(bw1, kind_objects[i] +" "+kind_objects_speech[i]);
      }

      writeToWriter(bw1, guideComments[7]);
      for (int i=0; i<speeches; i++) {
        writeToWriter(bw1, kind_speech[i]);
      }

      writeToWriter(bw1, guideComments[8]);
      writeToWriter(bw1, "Windows= "+isWindows);
      writeToWriter(bw1, "English= "+isEnglish);
      writeToWriter(bw1, "MorphemeAnalysisExecution= "+morpheme);

      if (chasenPath.equals(absolutePath)) {
        writeToWriter(bw1, "MorphemeAnalysisPath= ");
      } else {
        writeToWriter(bw1, "MorphemeAnalysisPath= "+chasenPath);
      }
      if (dictionaryPath.equals(absolutePath)) {
        writeToWriter(bw1, "MorphemeAnalysisDictionaryPath= ");
      } else {
        writeToWriter(bw1, "MorphemeAnalysisDictionaryPath= "+dictionaryPath);
      }
      writeToWriter(bw1, "ChaSenCode= "+defaultCode);
      writeToWriter(bw1, "ExcludeHiragana= "+excludeHiragana);
      writeToWriter(bw1, "ExcludeKatakana= "+excludeKatakana);
      writeToWriter(bw1, "ExcludeOne= "+excludeOne);
      writeToWriter(bw1, "JapaneseMenu= "+inJapanese);
      writeToWriter(bw1, "LeftTags= "+leftTags);

      writeToWriter(bw1, guideComments[9]);
      for (int i=0; i<moduleData.module_names.length; i++) {
        bw1.write(moduleData.moduleIDs[i]+" "+moduleData.module_names[i]+" ");
        for (int j=0; j<moduleData.mining[i].pairingVisualizationID.length; j++) {
          bw1.write(moduleData.mining[i].pairingVisualizationID[j]+" ");
        }
        bw1.write(moduleData.miningModuleSelection[i]+" ]");
        bw1.newLine();
      }

      writeToWriter(bw1, guideComments[10]);
      for (int i=0; i<moduleData.visu_module_names.length; i++) {
        bw1.write(moduleData.visuModuleIDs[i]+" "+moduleData.visu_module_names[i]+" ");
        bw1.write(moduleData.visualizationModuleSelection[i]+" ]");
        bw1.newLine();
      }

      writeToWriter(bw1, guideComments[11]);
      writeToWriter(bw1, "panelNumber="+panels);
      for (int i=0; i<panels; i++) {
        bw1.write(miningID[i]+" "+visualizationID[i]);
        bw1.newLine();
      }

      bw1.write("#Panel Balance");
      bw1.newLine();
      for (int i=0; i<panels; i++) {
        bw1.write(balancedPanelSizeX[i]+" ");
      }
      bw1.newLine();

      writeToWriter(bw1, guideComments[12]);

      bw1.flush();
      sw1.flush();
      saveSettingTexts = sw1.toString();
      bw1.close();
      sw1.close();
    } catch (Exception e) {
      System.out.println("Text Writing ERROR saveSettingTexts");
    }
    FILEIO.TextFileWriteCode(new File(settingFileName), saveSettingTexts, "SJIS");
  }
}
