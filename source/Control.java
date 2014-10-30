//
// Core Program for TETDM
// Control.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.TextData.*;


import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.*;
import javax.imageio.ImageIO;

import java.util.regex.*;

//Control panel :YELLOW
public class Control extends JPanel implements ActionListener, ComponentListener {
  MainFrame mainframe;
  final int MAX_PANEL = 10;
  int defaultPanelNumber = 2;
  int panelNumber = 0;
  int defaultPanelSizeY;
  int balancedPanelSizeX[];
  boolean first = true;
  boolean sizeBalance = false;

  public int windowSizeX;

  JPanel upperPanel;//upper select and setting panel

  JSplitPane splitpane[];

  String settingFile;
  public SettingData sp1;

  SettingPanel settingPanel;
  JPanel selectPanel;	//panel for modules

  Select panel[];
  Select stealthPanel;


  JPanel buttonPanel;	//lower button panel
  JPanel subButtonPanel[];
  JPanel subLabelPanel[];
  JLabel buttonLabel[];

  JButton bt10 = new JButton();
  JButton bt20 = new JButton();
  JButton bt30 = new JButton();
  JButton bt40 = new JButton();
  JButton bt50 = new JButton();
//	JToggleButton bt1 = new JToggleButton();
//	JToggleButton bt2 = new JToggleButton();
//	JToggleButton bt3 = new JToggleButton();
//	ButtonGroup bg = new ButtonGroup();
  JButton bt99 = new JButton();
  JButton bt52 = new JButton();
  JButton bt55 = new JButton();
  JButton bt60 = new JButton();
  JButton bt70 = new JButton();
  JButton bt71 = new JButton();
  JButton bt90 = new JButton();
  JToggleButton bt0 = new JToggleButton();
  JToggleButton btx = new JToggleButton();
  JToggleButton btxx = new JToggleButton();//SUPPORT
  PopUpForSupport popForSupport;//SUPPORT
  FlagBox flagbox = new FlagBox(this,100);//SUPPORT

  String buttonNamesInEnglish[] = {"Reload text and Restructure", "FontSize--", "FontSize++", "Add Panel", "Reduce Panel",
                                   "Quit", "Load Directory", "Load New File", "Add New File Data", "Save Combination", "Keyword Settings",
                                   "ALL", "SEG", "SEN","Balance","Save Image","Tool Settings","Character Size","Set Panels","Tutorial","Settings",
                                   "Input Text","Quit TETDM"
                                  };
  String buttonNamesInJapanese[];
  String inJapanese[] = new String[11];

  ModuleData moduleData;

  //TEXT DATA
  String original_file="", read_filename;
  public TextData text;		//temporarily public

  String absolutePath;

  long startTime, endTime;

  public class Focus {
    public boolean mainFocusBoolean, subFocusBoolean, focusBooleanArray[], focusBooleanArray2[][];
    public int mainFocusInteger, subFocusInteger, focusIntegerArray[], focusIntegerArray2[][];
    public double mainFocusDouble, subFocusDouble, focusDoubleArray[], focusDoubleArray2[][];
    public String mainFocusString, subFocusString, focusStringArray[];

    Focus() {
      clear();
    }

    public void clear() {
      mainFocusBoolean = subFocusBoolean = false;
      mainFocusInteger = subFocusInteger = 0;
      mainFocusDouble = subFocusDouble = 0.0;
      mainFocusString = subFocusString = "";

      focusBooleanArray = new boolean[] {false};
      focusIntegerArray = new int[] {0};
      focusDoubleArray = new double[] {0.0};
      focusStringArray = new String[] {""};

      focusBooleanArray2 = new boolean[][] {{false}};
      focusIntegerArray2 = new int[][] {{0}};
      focusDoubleArray2 = new double[][] {{0.0}};
    }

  }
  public Focus focus;



  public Control(MainFrame mainf, String file, String absolute, int windowSizeX, int popSizeY) {
    mainframe = mainf;
    absolutePath = absolute;

    setBackground(Color.white);

    splitpane = new JSplitPane[MAX_PANEL];

    addComponentListener(this);

    //Panel Creation 1
    setLayout(new BorderLayout());
    upperPanel = new JPanel(new GridLayout());
    add(upperPanel);

    selectPanel = new JPanel(new GridLayout());
    upperPanel.add(selectPanel, "select");


    //Data Settings
    moduleData = new ModuleData(absolutePath);

    settingFile = absolutePath + "tetdm.conf";
    sp1 = new SettingData(settingFile, moduleData);

    moduleData.initializeVisualiationModuleSelection();
    moduleData.initializeModuleSubset();

    if (sp1.panels > 0) {
      panelNumber = sp1.panels;
    } else {
      panelNumber = defaultPanelNumber;
    }

    balancedPanelSizeX = sp1.balancedPanelSizeX;


    focus = new Focus(); //////

    //Panel Creation 2
//		settingPanel = new SettingPanel(sp1);
//		upperPanel.add(settingPanel, "setting");

    panel = new Select[MAX_PANEL];

    readJapaneseLabels();
    initializeButtonPanel();

    this.windowSizeX = windowSizeX;
    PopUpWindow pop = new PopUpWindow(0,0,windowSizeX,popSizeY);

    createProcessingPop();

    textDataLoading(file);
    initializeSelectPanel();

    startTime = System.nanoTime();
  }

  public double getDiffTime() {
    endTime = System.nanoTime();
    return (double)(endTime - startTime)/1000000000.0;
  }
  /*
  	void calculateDefaultPanelSizeInitial(int panelNum)
  	{
  		if(panelNum == 1)
  			defaultPanelSizeX = mainframe.windowSizeX;
  		else
  			defaultPanelSizeX = (mainframe.windowSizeX - (11*(panelNum-1)))/panelNum;

  		defaultPanelSizeY = mainframe.windowSizeY - 194;
  	}

  	void calculateDefaultPanelSize(int panelNum)
  	{
  		if(panelNum == 1)
  			defaultPanelSizeX = getWidth();
  		else
  			defaultPanelSizeX = (getWidth() - (11*(panelNum-1)))/panelNum;

  		defaultPanelSizeY = getHeight() - 194;
  	}
  */
  void initializePanelSizeX() {
    for (int i=0; i<balancedPanelSizeX.length; i++) {
      balancedPanelSizeX[i] = 1;
    }
  }

  void calculateBalancedPanelSizeInitial(int panelNum) {
    if (panelNum == 1) {
      balancedPanelSizeX[0] = mainframe.windowSizeX;
    } else {
      int sum=0;
      for (int i=0; i<panelNum; i++) {
        sum += balancedPanelSizeX[i];
      }

      int max=0;
      max = mainframe.windowSizeX - (11*(panelNum-1));

      for (int i=0; i<panelNum; i++) {
        balancedPanelSizeX[i] = (int)((double)max * (double)balancedPanelSizeX[i]/(double)sum);
      }
      for (int i=panelNum; i<MAX_PANEL; i++) {
        balancedPanelSizeX[i] = (mainframe.windowSizeX - (11*i))/(i+1);
      }
    }
    defaultPanelSizeY = mainframe.windowSizeY - 194;
  }

  void calculateBalancedPanelSize(int panelNum) {
    if (panelNum == 1) {
      balancedPanelSizeX[0] = getWidth();
    } else {
      int sum=0;
      for (int i=0; i<panelNum; i++) {
        sum += balancedPanelSizeX[i];
      }

      int max=0;
      max = getWidth() - (11*(panelNum-1));

      for (int i=0; i<panelNum; i++) {
        balancedPanelSizeX[i] = (int)((double)max * (double)balancedPanelSizeX[i]/(double)sum);
      }
      for (int i=panelNum; i<MAX_PANEL; i++) {
        balancedPanelSizeX[i] = (getWidth() - (11*i))/(i+1);
      }
    }
    defaultPanelSizeY = getHeight() - 194;
  }

  void getBalancedPanelSize(int panelNum) {
    if (panelNum == 1) {
      balancedPanelSizeX[0] = getWidth();
    } else
      for (int i=0; i<panelNum; i++) {
        balancedPanelSizeX[i] = panel[i].getWidth();
      }
  }

  void textDataLoading(String loadFile) {
    //TEXT READ
    original_file = loadFile;		//Original text file
    read_filename = loadFile+"2";	//Copied text file

    if (sp1.morpheme.equals("Igo")) {
      EXECUTE.filecopy(loadFile, read_filename, "SHIFT-JIS");  //make copy, ALL procedures only for this copy
    } else {
      EXECUTE.filecopy(loadFile, read_filename, sp1.defaultCode);  //make copy, ALL procedures only for this copy
    }

    textDataLoadingWithoutCopy();
  }

  public void textDataLoadingAgain() {
    EXECUTE.filecopy(original_file, read_filename);	//make copy, ALL procedures only for this copy
    textDataLoadingWithoutCopy();
  }

  public void textDataLoadingWithoutCopy() {
    String source_filename = new File(read_filename).getAbsolutePath();
    String chafile = source_filename + ".cha";
    String ext = "";

    int point = source_filename.lastIndexOf(".");
    if (point != -1) {
      ext = source_filename.substring(point + 1);
    }

    if (ext.equals("csv2")) {
      Makecha makecha = new Makecha(source_filename, absolutePath, sp1.defaultCode);
    } else if (sp1.isEnglish) {
//			Makecha makecha = new Makecha(source_filename, sp1.isWindows);
      Makecha makecha = new Makecha(source_filename);
    } else {
      if (sp1.morpheme.equals("ChaSen")) {
        EXECUTE.exe_chasen(sp1.chasenexe, source_filename, chafile, sp1.isWindows, sp1.chasenPath);
      } else if (sp1.morpheme.equals("Igo")) {
        EXECUTE.exe_igo(sp1.chasenexe, source_filename, chafile, sp1.isWindows, sp1.chasenPath, sp1.dictionaryPath);
      }
    }

    text = new TextData(0, source_filename, chafile, sp1, absolutePath, this);
  }

  void setBalancedSplitDivider(int n) {
    int divideLine=0;
    for (int i=0; i<=n; i++) {
      divideLine += balancedPanelSizeX[i];
    }
    if (n > 0) {
      divideLine += 11 * (n - 1);
    }
    splitpane[n].setDividerLocation(divideLine);
  }

  void setPanels() {
    if (panelNumber == 1) {
      selectPanel.add(panel[0]);
    } else {
      for (int i=0; i<panelNumber-1; i++) {
        splitpane[i] = new JSplitPane();
        splitpane[i].setOneTouchExpandable(true);
        splitpane[i].setContinuousLayout(true);

        setBalancedSplitDivider(i);

        splitpane[i].setRightComponent(panel[i+1]);
        if (i==0) {
          splitpane[0].setLeftComponent(panel[0]);
        } else {
          splitpane[i].setLeftComponent(splitpane[i-1]);
        }
      }
      selectPanel.add(splitpane[panelNumber-2]);
    }
  }

  PopUpForProcessing processingPanel = null;

  //DISPLAY PROCESS
  public void createProcessingPop() {
    if (processingPanel == null) {
      processingPanel = new PopUpForProcessing(windowSizeX/2 - 250, 300,500,200, "Now Processing", Color.yellow);
    }
  }

  public void setProcessingPercent(int percent) {
    processingPanel.setPercent(percent);
  }

  public void disposeProcessingPop() {
    processingPanel.dispose();
    processingPanel = null;
  }

  void initializeSelectPanel() {
    if (first) {
      calculateBalancedPanelSizeInitial(panelNumber);
//			calculateDefaultPanelSizeInitial(panelNumber);
      first = false;
    } else
//			calculateDefaultPanelSize(panelNumber);
    {
      calculateBalancedPanelSize(panelNumber);
    }

    stealthPanel = new Select(text, 10000, moduleData, inJapanese, 0, 0);

    setProcessingPercent((int)(1*100/(panelNumber+1)));

    for (int i=0; i<panelNumber; i++) {
//			panel[i] = new Select(text, i, moduleData, inJapanese, defaultPanelSizeX, defaultPanelSizeY);
      panel[i] = new Select(text, i, moduleData, inJapanese, balancedPanelSizeX[i], defaultPanelSizeY);
      panel[i].setMinimumSize(new Dimension(0,0));
      panel[i].changePanel(sp1.miningID[i],sp1.visualizationID[i]);

      System.out.println(sp1.miningID[i]+" : "+sp1.visualizationID[i]);

      setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
    }

    setPanels();

    disposeProcessingPop();
  }

//			createProcessingPop();
//			setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
//			disposeProcessingPop();

  void initializeSelectPanel(int tIDs[]) {
    if (first) {
      calculateBalancedPanelSizeInitial(panelNumber);
      //			calculateDefaultPanelSizeInitial(panelNumber);
      first = false;
    } else
      //			calculateDefaultPanelSize(panelNumber);
    {
      calculateBalancedPanelSize(panelNumber);
    }

    stealthPanel = new Select(text, 10000, moduleData, inJapanese, 0, 0);

    setProcessingPercent((int)(1*100/(panelNumber+1)));

    for (int i=0; i<panelNumber; i++) {
      //			panel[i] = new Select(text, i, moduleData, inJapanese, defaultPanelSizeX, defaultPanelSizeY);
      panel[i] = new Select(text, i, moduleData, inJapanese, balancedPanelSizeX[i], defaultPanelSizeY);
      panel[i].setMinimumSize(new Dimension(0,0));
      panel[i].changePanel(sp1.miningID[i],sp1.visualizationID[i], tIDs[i]);

      System.out.println(sp1.miningID[i]+" : "+sp1.visualizationID[i]+" : "+tIDs[i]);

      setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
    }

    setPanels();

    for (int i=0; i<panelNumber; i++) {
      if (tIDs[i] < 0) {    // for Diff Panel
        panel[i].createDiffPanel();
      }
    }

    disposeProcessingPop();
  }

  //ADD PANEL
  public void addNewPanel(TextData text) {
    if (panelNumber > MAX_PANEL - 1) {
      return;
    }

//		calculateDefaultPanelSize(panelNumber+1);
    calculateBalancedPanelSize(panelNumber+1);
//		panel[panelNumber] = new Select(text, panelNumber, moduleData, inJapanese, defaultPanelSizeX, defaultPanelSizeY);
    panel[panelNumber] = new Select(text, panelNumber, moduleData, inJapanese, balancedPanelSizeX[panelNumber], defaultPanelSizeY);
    panel[panelNumber].setMinimumSize(new Dimension(0,0));

    splitpane[panelNumber-1] = new JSplitPane();

    for (int i=0; i<panelNumber; i++)
//			setSplitDivider(i);
    {
      setBalancedSplitDivider(i);
    }


    splitpane[panelNumber-1].setRightComponent(panel[panelNumber]);

    if (panelNumber == 1) {
      selectPanel.remove(panel[0]);
      splitpane[0].setLeftComponent(panel[0]);
    } else {
      selectPanel.remove(splitpane[panelNumber-2]);
      splitpane[panelNumber-1].setLeftComponent(splitpane[panelNumber-2]);
    }

    selectPanel.add(splitpane[panelNumber-1]);
    selectPanel.revalidate();
    panelNumber++;
  }

  //DELETE PANEL
  void deleteSplitPane(int deleteNumber) {
    for (int i=deleteNumber; i<panelNumber-2; i++) {
      splitpane[i] = splitpane[i+1];
    }
  }

  void deletePanel(int deleteNumber) {
    for (int i=deleteNumber; i<panelNumber-1; i++) {
      panel[i] = panel[i+1];
      panel[i].setPanelID(i);
      panel[i].setTitle2();
    }
    panelNumber--;
  }

  void delete(int deleteNumber) {
    if (panelNumber == 1 || deleteNumber >= panelNumber || deleteNumber < 0) {
      return;
    }

//		calculateDefaultPanelSize(panelNumber-1);
    calculateBalancedPanelSize(panelNumber-1);

    selectPanel.remove(splitpane[panelNumber-2]);
    for (int i=0; i<panelNumber-1; i++) {
      splitpane[i].removeAll();
    }

    deletePanel(deleteNumber);

    setPanels();
    selectPanel.revalidate();
  }


  /*
    //Partial for Segments
    public void resetBySegmentPartialData()
    {
        text.setSegmentPartialTextData(0);

  	stealthPanel.resetAll_for_stealth(text.segmentPartialTextData[0]);	//FOR reset moduleData /////////// now 0 only

  	for(int i=0;i<panelNumber;i++)
            if(panel[i].mining[panel[i].selected_module].combinationPanel.setFocusSegmentsButton.isSelected())
                panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
    }

    //Partial for Sentences
    public void resetBySentencePartialData()
    {
        text.setSentencePartialTextData();

  	stealthPanel.resetAll_for_stealth(text.sentencePartialTextData[0]);	//FOR reset moduleData /////////// now 0 only

  	for(int i=0;i<panelNumber;i++)
            if(panel[i].mining[panel[i].selected_module].combinationPanel.setFocusSentencesButton.isSelected())
                panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
    }
   */
  //Partial for Segments   newly half created

  public void resetBySegmentPartialData(int number) {
    text.setSegmentPartialTextData(number);

    stealthPanel.resetAll_for_stealth(text.segmentPartialTextData[number]);

    for (int i=0; i<panelNumber; i++) {	// this part should be modified
      if (number == 0)
        if (panel[i].mining[panel[i].selected_module].partialDocumentA == 2) {
          panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
        }

      if (number == 1)
        if (panel[i].mining[panel[i].selected_module].partialDocumentA == 3) {
          panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
        }
    }
  }

  //Partial for Sentences   newly half created
  public void resetBySentencePartialData(int number) {
    text.setSentencePartialTextData(number);

    stealthPanel.resetAll_for_stealth(text.sentencePartialTextData[number]);

    for (int i=0; i<panelNumber; i++) {	// this part should be modified
      if (number == 0)
        if (panel[i].mining[panel[i].selected_module].partialDocumentA == 4) {
          panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
        }

      if (number == 1)
        if (panel[i].mining[panel[i].selected_module].partialDocumentA == 5) {
          panel[i].mining[panel[i].selected_module].combinationPanel.resetModuleForPartialData();
        }
    }
  }


  /*
  public void resetStealthBySentencePartialData()
  {
  stealthPanel.resetAll_for_stealth(text.sentencePartialTextData[0]);	//FOR reset moduleData /////////// now 0 only
  }
  */

  //			createProcessingPop();
  //			setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
  //			disposeProcessingPop();


  public void resetSelectpanelData() {
    createProcessingPop();

    stealthPanel.resetAll_for_stealth(text);	//FOR reset moduleData

    for (int i=0; i<panelNumber; i++) {
      panel[i].resetAll(text);
      setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
    }

    disposeProcessingPop();
  }

  public void resetSelectpanelData(TextData newText) {
    createProcessingPop();

    stealthPanel.resetAll_for_stealth(newText);	//FOR reset moduleData

    for (int i=0; i<panelNumber; i++) {
      panel[i].resetAll(newText);
      setProcessingPercent((int)((1+i)*100/(panelNumber+1)));
    }

    disposeProcessingPop();
  }

  //FOCUS
  public void setDisplayOthersByTouchFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].setDisplayByTouchFocus();
      }
  }
  //FOCUS
  public void setDisplayOthersByClickFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].setDisplayByClickFocus();
      }
  }
  //FOCUS
  public void setDisplayOthersByTimingFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].setDisplayByTimingFocus();
      }
  }
  //FOCUS
  public void resetSelectPanelDataByTouchFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].resetMiningByTouchFocus();
      }
  }
  //FOCUS
  public void resetSelectPanelDataByClickFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].resetMiningByClickFocus();
      }
  }
  //FOCUS
  public void resetSelectPanelDataByTimingFocus(int panelID) {
    for (int i=0; i<panelNumber; i++)
      if (i != panelID) {
        panel[i].resetMiningByTimingFocus();
      }
  }
  //FOCUS	ALL
  public void resetSelectPanelDataByTouchFocusAll(int panelID) {
    panel[panelID].resetMiningByTouchFocus();
    resetSelectPanelDataByTouchFocus(panelID);
  }
  //FOCUS	ALL
  public void resetSelectPanelDataByClickFocusAll(int panelID) {
    panel[panelID].resetMiningByClickFocus();
    resetSelectPanelDataByClickFocus(panelID);
  }
  //FOCUS	ALL
  public void resetSelectPanelDataByTimingFocusAll(int panelID) {
    panel[panelID].resetMiningByTimingFocus();
    resetSelectPanelDataByTimingFocus(panelID);
  }


  public void resetFontSize() {
    for (int i=0; i<panelNumber; i++) {
      panel[i].reset_font_size();
    }
  }


  //FOR MODULE EXECUTION
  public int findModule(int moduleID, boolean flags[]) {
    for (int i=0; i<panelNumber; i++) {
//			System.out.println(i+":"+moduleID+"ID:panelmoduleID "+panel[i].miningIDs[panel[i].selected_module]);

      if (panel[i].miningIDs[panel[i].selected_module] == moduleID && flags[i] == false) {
        return i;
      }

    }

    return -1;
  }

  //getModuleName
  public String getModuleName(int miningModuleID) {
    return moduleData.getModuleName(miningModuleID);
  }

  public String getVisualizationModuleName(int visualizationModuleID) {
    return moduleData.getVisualizationModuleName(visualizationModuleID);
  }

  public int findVisualizationModule(int visuModuleID, boolean flags[]) {
    for (int i=0; i<panelNumber; i++) {
//			System.out.println(i+":"+visuModuleID+"ID:panelmoduleID "+panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]]);

      if (panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]] == visuModuleID && flags[i] == false) {
        return i;
      }
    }

    return -1;
  }


  //SAVE ACTION LOG
  String logFilename;
  public void writeActionLog(String data) {
    logFilename = absolutePath+"ACTIONLOG";
    try {
      BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));

      bw1.write(String.format("%.2f",text.control.getDiffTime())+" CONTROL "+data+System.getProperty("line.separator"));
      bw1.close();
    } catch (Exception e) {
      System.out.println("File Writing ERROR ACTIONLOG in Control-1");
    }
  }

  public void writeSetModules(String data) {
    logFilename = absolutePath+"ACTIONLOG";
    try {
      BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));

      bw1.write(String.format("%.2f",text.control.getDiffTime())+" SET MODULE from "+data+" ");
      String moduleNames="";

      for (int i=0; i<panelNumber; i++)
        if (panel[i] != null) {
          moduleNames += ("("+panel[i].miningIDs[panel[i].selected_module]+","+panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]]+")");
        }
      bw1.write(moduleNames+" "+System.getProperty("line.separator"));
      bw1.close();
    } catch (Exception e) {
      System.out.println("File Writing ERROR ACTIONLOG in Control-2");
    }
  }

  public boolean fileChoose() {
    JFileChooser fc = new JFileChooser(absolutePath+File.separator+"text");
    FileFilter filter1;
    FileFilter filter2;


    if (sp1.inJapanese) {
      filter1 = new FileNameExtensionFilter(buttonNamesInJapanese[13], "txt");
      filter2 = new FileNameExtensionFilter(buttonNamesInJapanese[14], "txt");
    } else {
      filter1 = new FileNameExtensionFilter("Text in Japanese", "txt");
      filter2 = new FileNameExtensionFilter("Text in English", "txt");
    }

    fc.addChoosableFileFilter(filter2);
    fc.addChoosableFileFilter(filter1);

    int selected = fc.showOpenDialog(this);

    if (selected != JFileChooser.APPROVE_OPTION) {
      return false;
    }

    File inputFile = fc.getSelectedFile();
    original_file = inputFile.getAbsolutePath();

    if (fc.getFileFilter() == filter2) {
      sp1.isEnglish = true;
    } else {
      sp1.isEnglish = false;
    }

    System.out.println("English = "+sp1.isEnglish);

    return true;
  }

  public boolean fileChooseFromDirectory() {
    JFileChooser fc = new JFileChooser(absolutePath);
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    int selected = fc.showOpenDialog(this);

    if (selected != JFileChooser.APPROVE_OPTION) {
      return false;
    }

    File inputFile = fc.getSelectedFile();
    original_file = FILEIO.makeOne(inputFile,absolutePath,sp1.defaultCode,sp1.segment_tag);

    return true;
  }

  public void panelSetWithKeep(int panelID, int moduleIDs[], int displayIDs[], int textIDs[]) {
    int mIDs[];
    int dIDs[];
    int tIDs[];
    boolean keepList[] = new boolean[panelNumber];
    int count=0;

    createProcessingPop();

    initializePanelSizeX();

    for (int i=0; i<panelNumber; i++)
      if (panel[i].mining[panel[i].selected_module].keep) {
        keepList[i] = true;
        count++;
      } else {
        keepList[i] = false;
      }

    if (count == 0) {
      panelSet(moduleIDs,displayIDs,textIDs,balancedPanelSizeX);
      return;
    }

    mIDs = new int[moduleIDs.length+count];
    dIDs = new int[moduleIDs.length+count];
    tIDs = new int[moduleIDs.length+count];

    count=0;
    for (int i=0; i<panelID; i++)
      if (keepList[i]) {
        mIDs[count] = panel[i].miningIDs[panel[i].selected_module];
        dIDs[count] = panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]];
        tIDs[count] = panel[i].text.textID;
        count++;
      }

    for (int i=0; i<moduleIDs.length; i++) {
      mIDs[count+i] = moduleIDs[i];
      dIDs[count+i] = displayIDs[i];
      tIDs[count+i] = textIDs[i];
    }

    for (int i=panelID; i<panelNumber; i++) {
      if (keepList[i]) {
        mIDs[moduleIDs.length + count] = panel[i].miningIDs[panel[i].selected_module];
        dIDs[moduleIDs.length + count] = panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]];
        tIDs[moduleIDs.length + count] = panel[i].text.textID;
        count++;
      }
    }

    panelNumber = moduleIDs.length + count;

    sp1.setModulesToPanels(mIDs,dIDs);
    panelsReConstruction(tIDs);
  }

  public void panelSet(int moduleIDs[], int displayIDs[], int textIDs[], int sizeX[]) {
    createProcessingPop();

    panelNumber = moduleIDs.length;

    for (int i=0; i<panelNumber; i++) {
      balancedPanelSizeX[i] = sizeX[i];
    }

    sp1.setModulesToPanels(moduleIDs,displayIDs);
    panelsReConstruction(textIDs);
  }

  public void panelSet() {
    createProcessingPop();

    panelNumber = miningIDs.length;

    calculateBalancedPanelSizeInitial(panelNumber);

    sp1.setModulesToPanels(miningIDs,visualizationIDs);
    panelsReConstruction();
  }

  public void panelSetByMiningModule(int moduleID) {
    int rID = moduleData.findModule(moduleID);

    panelSet(moduleData.miningPanelSet[rID].MiningIDs, moduleData.miningPanelSet[rID].VisualizationIDs, moduleData.miningPanelSet[rID].TextIDs, moduleData.miningPanelSet[rID].sizeX);
  }

  public void backPanelSetByMiningModule(int moduleID) {
    int rID = moduleData.findModule(moduleID);

    //resetPartial();

    panelSet(moduleData.miningPanelSet[rID].backMiningIDs, moduleData.miningPanelSet[rID].backVisualizationIDs, moduleData.miningPanelSet[rID].backTextIDs, moduleData.miningPanelSet[rID].backSizeX);
  }

  //Image Capture // same as in Visualization.java
  public void imageCapture() {
    JFileChooser chooser = new JFileChooser(text.absolutePath);
    BufferedImage image = null;

    try {
      Robot robo = new Robot();
      image = robo.createScreenCapture(new Rectangle(getLocationOnScreen(),getSize()));
    } catch (Exception e) {}

    int result = chooser.showSaveDialog(this);
    File selectedFile = chooser.getSelectedFile();

    if (result == JFileChooser.APPROVE_OPTION)
      try {
        String filename = selectedFile.getName();
        if (filename.endsWith(".png") == false) {
          filename += ".png";
        }

        ImageIO.write(image,"png", new File(selectedFile.getParent(), filename));
      } catch (Exception e) {}
  }

  PopUpForSetting popForSetting;
  PopUpForSetModule popForSetModule;

  public void resetPartial() {
    resetSelectpanelData(text);

    for (int i=0; i<panelNumber; i++) {
      panel[i].mining[panel[i].selected_module].partialDocumentA = -1;
      panel[i].mining[panel[i].selected_module].partialDocumentB = -1;
    }
  }

  //Action listener
  public void actionPerformed(ActionEvent e) {
    String log = e.getSource().toString();
//        String regex = "text=(.*),";
    String regex = "text=(.*)]";
    Pattern p = Pattern.compile(regex);

    Matcher m = p.matcher(log);

    if (m.find()) {
      writeActionLog(m.group(1));
    } else {
      writeActionLog(log);
    }

    if (e.getSource() == bt10) { //Reload and ReExecute
      createProcessingPop();

      textDataLoadingWithoutCopy();/////
      resetSelectpanelData(text);
    }

    if (e.getSource() == bt20) { //Font+
      if (text.fontSize > 1) {
        text.fontSize--;
      }
      resetFontSize();
    }

    if (e.getSource() == bt30) { //Font-
      if (text.fontSize < 100) {
        text.fontSize++;
      }
      resetFontSize();
    }

    if (e.getSource() == bt40) { //Add Panel
      addNewPanel(text);
    }

    if (e.getSource() == bt50) { //Delete Panel
      delete(panelNumber-1);
    }


    if (e.getSource() == bt99) { //Quit application
      writeActionLog("quit");
      mainframe.dispose(); //destroy frame
      System.exit(0);
    }

    if (e.getSource() == bt52) { //Load Folder
      if (!fileChooseFromDirectory()) {
        return;
      }

      createProcessingPop();

      textDataLoading(original_file);
      resetSelectpanelData(text);
    }

    if (e.getSource() == bt55) { //Load A File
      if (!fileChoose()) {
        return;
      }

      createProcessingPop();

      textDataLoading(original_file);
      resetSelectpanelData(text);
    }

    if (e.getSource() == bt60) { // Add New Text Data, Now not used
      if (!fileChoose()) {
        return;
      }

      textDataLoading(original_file);

      // TODO show dialog for preprocessing for text file
      //		    TextLoader tl = new TextLoader(original_file,isEnglish);
      // TODO avoid from opening more than two files
      //		    text = tl.getTextData();

      addNewPanel(text);
    }

    if (e.getSource() == bt70) { // Save Combination
      int miningIndex=0;

      getBalancedPanelSize(panelNumber);

      sp1.panels = panelNumber;
      for (int i=0; i<panelNumber; i++) {
        sp1.miningID[i] = moduleData.msub.selectedMiningIDs[panel[i].selected_module];
        for (int j=0; j<moduleData.moduleIDs.length; j++)
          if (moduleData.moduleIDs[j] == sp1.miningID[i]) {
            moduleData.miningModuleSelection[j] = true;
            miningIndex = j;
          }
        sp1.visualizationID[i] = moduleData.msub.selectedVisualizationIDs[panel[i].pair_visu[panel[i].selected_module]];
        for (int j=0; j<moduleData.visuModuleIDs.length; j++)
          if (moduleData.visuModuleIDs[j] == sp1.visualizationID[i]) {
            moduleData.visualizationModuleSelection[j] = true;
          }

        sp1.modifyModuleSettings(miningIndex, sp1.visualizationID[i]);

//				System.out.println(moduleData.msub.selectedMiningIDs[panel[i].selected_module] +":"
//								   +moduleData.msub.selectedVisualizationIDs[panel[i].pair_visu[panel[i].selected_module]]);
      }
      sp1.saveSettingFile();

      if (popForSupport != null) {
        popForSupport.support.setAnsweredFlagTrue(0,14);
      }
    }

    if (e.getSource() == bt71) { // Set balanced Panel Size
      if (panelNumber == 1) {
        balancedPanelSizeX[0] = getWidth();
      } else
        for (int i=0; i<panelNumber; i++) {
          balancedPanelSizeX[i] = (getWidth() - (11*(panelNumber-1)))/panelNumber;
        }

      for (int i=0; i<panelNumber-1; i++) {
        setBalancedSplitDivider(i);
      }

      if (popForSupport != null) {
        popForSupport.support.setAnsweredFlagTrue(0,8);
      }
    }

    if (e.getSource() == bt90) { // Image Capture
      imageCapture();
    }

    if (e.getSource() == bt0) { //SettingPanel
      if (bt0.isSelected()) {
        int x,y,width;
        x = width = getWidth();
        y = getHeight()+20;
        if (x > 600) {
          x = 600;
        }
        if (y > 920) {
          y = 920;
        }

        Point position = getLocationOnScreen();
        popForSetting = new PopUpForSetting(sp1, (int)((width-x)/2), (int)position.getY()-20, x, y, this);

        if (popForSupport != null) {
          popForSupport.support.setAnsweredFlagTrue(0,2);
        }
      } else {
        popForSetting.dispose();
      }


    }

    if (e.getSource() == btx) { //SetModulePanel
      if (btx.isSelected()) {
        int x,y,width;
        x = width = getWidth();
        y = getHeight();
        if (x > 950) {
          x = 950;
        }
        if (y > 900) {
          y = 900;
        }
        Point position = getLocationOnScreen();
        getPanelInformation(); //Update miningIDs, visualizationIDs
        popForSetModule = new PopUpForSetModule(sp1, (int)((width-x)/2), (int)position.getY()-20, x, y, this);
        writeActionLog("OPEN-MODULE-SELECT-WINDOW");
      } else {
        popForSetModule.dispose();
        writeActionLog("CLOSE-MODULE-SELECT-WINDOW");
      }
    }

    if (e.getSource() == btxx) { //SUPPORT
      if (btxx.isSelected()) {
        int x,y,width;
        x = width = getWidth();
        y = getHeight();
        if (x > 950) {
          x = 950;
        }
        if (y > 900) {
          y = 900;
        }
        Point position = getLocationOnScreen();
        getPanelInformation(); //Update miningIDs, visualizationIDs
//				popForSupport = new PopUpForSupport((int)((width-x)/2), (int)position.getY()-20, x, y, this,flagbox);
        popForSupport = new PopUpForSupport((int)(2*width/3), (int)position.getY()-20, x, y, this,flagbox);
        //			writeActionLog("OPEN-MODULE-SUPPORT-WINDOW");
      } else {
        popForSupport.dispose();
        //			writeActionLog("CLOSE-MODULE-SUPPORT-WINDOW");
      }
    }

  }

  //ComponentListener
  public void componentHidden(ComponentEvent e) {}
  public void componentMoved(ComponentEvent e) {}
  public void componentShown(ComponentEvent e) {}
  public void componentResized(ComponentEvent e) {
    calculateBalancedPanelSize(panelNumber);
    for (int i=0; i<panelNumber-1; i++) {
      setBalancedSplitDivider(i);
    }
  }

  void panelsReConstruction() {
    for (int i=0; i<panelNumber; i++)
      if (panel[i] != null)
        if (panel[i].popModuleSelect != null) {
          panel[i].popModuleSelect.setMenu();
        }

    upperPanel.remove(selectPanel);
    selectPanel = new JPanel(new GridLayout());
    upperPanel.add(selectPanel, "select");
    initializeSelectPanel();
    upperPanel.revalidate();

    if (btx.isSelected()) {
      popForSetModule.setMenu();
    }
  }

  void panelsReConstruction(int tIDs[]) {
    for (int i=0; i<panelNumber; i++)
      if (panel[i] != null)
        if (panel[i].popModuleSelect != null) {
          panel[i].popModuleSelect.setMenu();
        }

    upperPanel.remove(selectPanel);
    selectPanel = new JPanel(new GridLayout());
    upperPanel.add(selectPanel, "select");

    initializeSelectPanel(tIDs);
    upperPanel.revalidate();

    if (btx.isSelected()) {
      popForSetModule.setMenu();
    }
  }

  /*
  	void panelsReConstruction(int panelID, int miningID, int visualizationID)
  	{
  		upperPanel.remove(selectPanel);
  		selectPanel = new JPanel(new GridLayout());
  		upperPanel.add(selectPanel, "select");
  		initializeSelectPanel();
  		upperPanel.revalidate();
  	}
  */
  int miningIDs[];
  int visualizationIDs[];
  int textIDs[];

  int lastMiningIDs[];
  int lastVisualizationIDs[];
  int lastTextIDs[];

  void getPanelInformation() {
    miningIDs = new int[panelNumber];
    visualizationIDs = new int[panelNumber];
    textIDs = new int[panelNumber];

    for (int i=0; i<panelNumber; i++) {
      miningIDs[i] = panel[i].miningIDs[panel[i].selected_module];
      visualizationIDs[i] = panel[i].visualizationIDs[panel[i].pair_visu[panel[i].selected_module]];
      textIDs[i] = panel[i].text.textID;
    }
  }

  public void createBackupIDs() {
    getPanelInformation();

    lastMiningIDs = new int[miningIDs.length];
    lastVisualizationIDs = new int[visualizationIDs.length];
    lastTextIDs = new int[textIDs.length];

    for (int i=0; i<miningIDs.length; i++) {
      lastMiningIDs[i] = miningIDs[i];
    }

    for (int i=0; i<visualizationIDs.length; i++) {
      lastVisualizationIDs[i] = visualizationIDs[i];
    }

    for (int i=0; i<textIDs.length; i++) {
      lastTextIDs[i] = textIDs[i];
    }
  }

  public void traceBackupIDs() {
    miningIDs = new int[lastMiningIDs.length];
    visualizationIDs = new int[lastVisualizationIDs.length];
    textIDs = new int[lastTextIDs.length];

    for (int i=0; i<lastMiningIDs.length; i++) {
      miningIDs[i] = lastMiningIDs[i];
    }

    for (int i=0; i<lastVisualizationIDs.length; i++) {
      visualizationIDs[i] = lastVisualizationIDs[i];
    }

    for (int i=0; i<lastTextIDs.length; i++) {
      textIDs[i] = lastTextIDs[i];
    }
  }

  //buttonPanel
  void initializeButtonPanel() {
    buttonPanel = new JPanel(new GridLayout());
    buttonPanel.setBackground(Color.YELLOW);

    //PANEL SETTINGS
    int subPanelNumber = 11;

    //subLabelPanel = buttonLabel + subButtonPanel
    subLabelPanel = new JPanel[subPanelNumber];
    buttonLabel = new JLabel[subPanelNumber];
    subButtonPanel = new JPanel[subPanelNumber];

    for (int i=0; i<subPanelNumber; i++) {
      subLabelPanel[i] = new JPanel(new BorderLayout());
      buttonLabel[i] = new JLabel();
      buttonLabel[i].setFont(new Font("Dialog", Font.BOLD, 10));
      subButtonPanel[i] = new JPanel(new GridLayout());

      if (i < subPanelNumber-1) {
        subLabelPanel[i].add(buttonLabel[i]);
        subLabelPanel[i].add(subButtonPanel[i], BorderLayout.SOUTH);
      }
    }

    //sub[6],sub[7],sub[8]
    buttonPanel.add(subLabelPanel[6]);
    buttonPanel.add(subLabelPanel[7]);
    buttonPanel.add(subLabelPanel[8]);

    subLabelPanel[6].setBackground(Color.YELLOW);
    subButtonPanel[6].setBackground(Color.YELLOW);
    subLabelPanel[7].setBackground(Color.YELLOW);
    subButtonPanel[7].setBackground(Color.YELLOW);
    subLabelPanel[8].setBackground(Color.YELLOW);
    subButtonPanel[8].setBackground(Color.YELLOW);

    bt55.addActionListener(this);
    bt52.addActionListener(this);
    bt10.addActionListener(this);

    subButtonPanel[6].add(bt55);
    subButtonPanel[7].add(bt52);
    subButtonPanel[8].add(bt10);



    //sub[1],sub[2],sub[5],sub[9]
    buttonPanel.add(subLabelPanel[1]);
    buttonPanel.add(subLabelPanel[2]);
    buttonPanel.add(subLabelPanel[5]);
    buttonPanel.add(subLabelPanel[9]);

    subLabelPanel[1].setBackground(Color.PINK);
    subButtonPanel[1].setBackground(Color.PINK);
    subLabelPanel[2].setBackground(Color.PINK);
    subButtonPanel[2].setBackground(Color.PINK);
    subLabelPanel[5].setBackground(Color.PINK);
    subButtonPanel[5].setBackground(Color.PINK);
    subLabelPanel[9].setBackground(Color.PINK);
    subButtonPanel[9].setBackground(Color.PINK);

    bt40.addActionListener(this);
    bt50.addActionListener(this);
    bt71.addActionListener(this);
    bt90.addActionListener(this);
    btx.addActionListener(this);
    btxx.addActionListener(this);//SUPPORT
    bt70.addActionListener(this);

    subButtonPanel[1].add(bt40);
    subButtonPanel[1].add(bt50);

    subButtonPanel[2].add(bt71);
    subButtonPanel[2].add(bt90);

    subButtonPanel[5].add(btx);//TOOL


    subButtonPanel[9].add(bt70);

    //sub[0]
    buttonPanel.add(subLabelPanel[0]);

    subLabelPanel[0].setBackground(Color.YELLOW);
    subButtonPanel[0].setBackground(Color.YELLOW);

    bt20.addActionListener(this);
    bt30.addActionListener(this);

    subButtonPanel[0].add(bt20);
    subButtonPanel[0].add(bt30);


    //sub[3]
    buttonPanel.add(subLabelPanel[3]);

    subLabelPanel[3].setBackground(new Color(0xad, 0xff, 0x2f));
    subButtonPanel[3].setBackground(new Color(0xad, 0xff, 0x2f));

    subButtonPanel[3].add(btxx);//SUPPORT
    /*
    bt1.addActionListener(this);
    bt1.setSelected(true);

    bt2.addActionListener(this);
    bt3.addActionListener(this);

    subButtonPanel[3].add(bt1);
    subButtonPanel[3].add(bt2);
    subButtonPanel[3].add(bt3);
    bg.add(bt1);
    bg.add(bt2);
    bg.add(bt3);
    */

    //sub[4]
    buttonPanel.add(subLabelPanel[4]);


    subLabelPanel[4].setBackground(Color.CYAN);
    subButtonPanel[4].setBackground(Color.CYAN);

    bt0.addActionListener(this);

    subButtonPanel[4].add(bt0);


    //QUIT sub[10]
    buttonPanel.add(subLabelPanel[10]);

    subLabelPanel[10].add(buttonLabel[10]);
    subLabelPanel[10].add(subButtonPanel[10], BorderLayout.SOUTH);

    subLabelPanel[10].setBackground(Color.YELLOW);
    subButtonPanel[10].setBackground(Color.YELLOW);

    bt99.addActionListener(this);

    subButtonPanel[10].add(bt99);




    //bt60.setBackground(Color.YELLOW);       //Tsuika Text
    //bt60.addActionListener(this);
    //		buttonPanel.add(bt60);



    //		add(buttonPanel, BorderLayout.NORTH);
    setButtonLabel();
  }

  public void readJapaneseLabels() {
    String filename = absolutePath + "Japanese.txt";
    File jpname = new File(filename);
    buttonNamesInJapanese = FILEIO.TextFileAllReadCodeArray(jpname);
    inJapanese[0] = buttonNamesInJapanese[11];
    inJapanese[1] = buttonNamesInJapanese[15];
    inJapanese[2] = buttonNamesInJapanese[16];
    inJapanese[3] = buttonNamesInJapanese[17];
    inJapanese[4] = buttonNamesInJapanese[30];
    inJapanese[5] = buttonNamesInJapanese[31];
    inJapanese[6] = buttonNamesInJapanese[32];
    inJapanese[7] = buttonNamesInJapanese[33];
    inJapanese[8] = buttonNamesInJapanese[34];
    inJapanese[9] = buttonNamesInJapanese[35];
    inJapanese[10] = buttonNamesInJapanese[36];
  }

  public void setButtonLabel() {
    if (sp1.inJapanese) {
      bt10.setText(buttonNamesInJapanese[0]);
      bt20.setText(buttonNamesInJapanese[1]);
      bt30.setText(buttonNamesInJapanese[2]);
      bt40.setText(buttonNamesInJapanese[3]);
      bt50.setText(buttonNamesInJapanese[4]);
      bt99.setText(buttonNamesInJapanese[5]);
      bt52.setText(buttonNamesInJapanese[6]);
      bt55.setText(buttonNamesInJapanese[7]);
      bt60.setText(buttonNamesInJapanese[8]);
      bt70.setText(buttonNamesInJapanese[9]);
      bt0.setText(buttonNamesInJapanese[10]);
//			bt1.setText(buttonNamesInJapanese[18]);
//			bt2.setText(buttonNamesInJapanese[19]);
//			bt3.setText(buttonNamesInJapanese[20]);
      bt71.setText(buttonNamesInJapanese[21]);
      bt90.setText(buttonNamesInJapanese[22]);
      btx.setText(buttonNamesInJapanese[23]);
      btxx.setText(buttonNamesInJapanese[26]);

      buttonLabel[0].setText(buttonNamesInJapanese[24]);
      buttonLabel[1].setText(buttonNamesInJapanese[25]);
      buttonLabel[3].setText(buttonNamesInJapanese[26]);
      buttonLabel[4].setText(buttonNamesInJapanese[27]);
      buttonLabel[6].setText(buttonNamesInJapanese[28]);
      buttonLabel[10].setText(buttonNamesInJapanese[29]);
    } else {
      bt10.setText(buttonNamesInEnglish[0]);
      bt20.setText(buttonNamesInEnglish[1]);
      bt30.setText(buttonNamesInEnglish[2]);
      bt40.setText(buttonNamesInEnglish[3]);
      bt50.setText(buttonNamesInEnglish[4]);
      bt99.setText(buttonNamesInEnglish[5]);
      bt52.setText(buttonNamesInEnglish[6]);
      bt55.setText(buttonNamesInEnglish[7]);
      bt60.setText(buttonNamesInEnglish[8]);
      bt70.setText(buttonNamesInEnglish[9]);
      bt0.setText(buttonNamesInEnglish[10]);
//			bt1.setText(buttonNamesInEnglish[11]);
//			bt2.setText(buttonNamesInEnglish[12]);
//			bt3.setText(buttonNamesInEnglish[13]);
      bt71.setText(buttonNamesInEnglish[14]);
      bt90.setText(buttonNamesInEnglish[15]);
      btx.setText(buttonNamesInEnglish[16]);
      btxx.setText(buttonNamesInEnglish[19]);			//SUPPORT

      buttonLabel[0].setText(buttonNamesInEnglish[17]);
      buttonLabel[1].setText(buttonNamesInEnglish[18]);
      buttonLabel[3].setText(buttonNamesInEnglish[19]);
      buttonLabel[4].setText(buttonNamesInEnglish[20]);
      buttonLabel[6].setText(buttonNamesInEnglish[21]);
      buttonLabel[10].setText(buttonNamesInEnglish[22]);
    }
    bt55.setForeground(Color.red);
    bt0.setForeground(Color.red);
    btx.setForeground(Color.red);
    btxx.setForeground(Color.red);//SUPPORT

    bt0.setIcon(new ImageIcon(absolutePath+"source"+File.separator+"segment.png"));
    bt0.setSelectedIcon(new ImageIcon(absolutePath+"source"+File.separator+"close.png"));
    btx.setIcon(new ImageIcon(absolutePath+"source"+File.separator+"segment.png"));
    btx.setSelectedIcon(new ImageIcon(absolutePath+"source"+File.separator+"close.png"));
    btxx.setIcon(new ImageIcon(absolutePath+"source"+File.separator+"segment.png"));//SUPPORT
    btxx.setSelectedIcon(new ImageIcon(absolutePath+"source"+File.separator+"close.png"));	//SUPPORT

    bt90.setIcon(new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));
  }

  public class PopUpWindow extends JFrame {
    Container pane = getContentPane();
    JPanel popPanel = new JPanel(new BorderLayout());

    public PopUpWindow(int X, int Y, int width, int height) {
      setBounds(X, Y, width, height);

//            setAlwaysOnTop(true);
      pane.setBackground(Color.white);
      setUndecorated(true);

      pane.add(popPanel);
      popPanel.add(buttonPanel,BorderLayout.SOUTH);

      setVisible(true);
    }
  }
}

