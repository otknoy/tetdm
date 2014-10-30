//
// Core Program for TETDM
// MiningModule.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.TextData.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.regex.*;


public abstract class MiningModule extends JPanel {
  private int moduleID = -1;
  private int panelID;

  private int toolType = 4;//1:simple 2:primitive 3:semi-primitive 4:special

  public TextData text;
  String absolutePath;

  SetData setData = new SetData();

  public String myModulePath;
  public String myModuleName;
  public String visuModulePath;
  public String visuModuleName;

  boolean firstTime = true;
  boolean keep = false;

  public boolean focusTouchExecute = false;
  public boolean focusClickExecute = false;
  public boolean focusTimingExecute = false;

  CardLayout layout;
  JPanel visualPanel = new JPanel(layout = new CardLayout());
  public JPanel operationPanel = new JPanel(new GridLayout());
  CombinationPanel combinationPanel;

//	public boolean panelSet = false;
  boolean panelSet = true;

  private int setPanelModuleID[];
  private int setPanelVisualizationID[];
  private int setPanelTextID[];
  private int setBalancedPanelSizeX[];
  private int lastSetPanelModuleID[];
  private int lastSetPanelVisualizationID[];
  private int lastSetPanelTextID[];
  private int lastBalancedPanelSizeX[];
  public int defaultSetPanelModuleID[];
  public int defaultSetPanelVisualizationID[];
  public int defaultSetPanelTextID[];


  Class<?> visuModule[];
//    	public VisualizationModule visualization;
  private VisualizationModule visualization;
  VisualizationModule visual[];
  int visuModuleNumber;

  public int pairingVisualizationID[];
  int pairingVisualizationIndex[];
  public int pairingVisuID[];		//For SettingPanel

//	FOR PROCESSING

  PopUpForProcessing processingPanel;


// FOR PARTAIL

  public int partialDocumentA = -1; //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
  public int partialDocumentB = -1;

  PopUpForDiff popForDiff;
  int diffPatterns[][];
  int textID1, textID2;

  String buttonNamesInJapanese[];

  public MiningModule() {}

  public final void setModuleID(int moduleID) {
    this.moduleID = moduleID;
  }
  public final int getModuleID() {
    return moduleID;
  }
  public final void setPanelID(int panelID) {
    this.panelID = panelID;
  }
  public final int getPanelID() {
    return panelID;
  }
  public final void setToolType(int type) {
    this.toolType = type;
  }
  public final int getToolType() {
    return toolType;
  }

  public final void setVisualizationPanelID() {
    if (visual != null)
      for (int i=0; i<visual.length; i++)
//			if(visual[i] != null)
      {
        visual[i].setPanelID(panelID);
      }
  }

  public final void init_panel(TextData tex, Class<?> vis[], int selectedVisu, String absolute, int defaultPanelSizeX, int defaultPanelSizeY) {
    text = tex; // this is only used for calling text.settingData.inJapanese;

    setLayout(new BorderLayout());
    setBackground(Color.white);

    this.visuModule = vis;
    visuModuleNumber = visuModule.length;

    initializeVisuModules();
    add(visualPanel);

    setVisualizationPanelID();

    for (int i=0; i<visuModuleNumber; i++) {
      visual[i].sizeX = defaultPanelSizeX;
      visual[i].sizeY = defaultPanelSizeY;
    }

    absolutePath = absolute;
    myModulePath = absolutePath + this.getClass().getPackage().getName().replaceAll("\\.","/") + File.separator;
    myModuleName = this.getClass().getPackage().getName().replaceAll("module\\.MiningModules\\.","");

//		System.out.println("myModuleName= "+myModuleName);

    if (panelSet) {	// SET PANEL COMBINATION
      combinationPanel = new CombinationPanel(this);  // calling text.settingData.inJapanese;
      add(combinationPanel, BorderLayout.NORTH);
    }

    initializePanel();					// Step.1
    operationPanel.setBackground(Color.RED);
    add(operationPanel, BorderLayout.SOUTH);

    setVisu(selectedVisu);

    System.out.println("Mining: "+this.getClass().getName());
  }


  public final void reExecute() {
    resetFirstTime();
    selected(text);
  }

  public final void initializeVisuModules() {
    visual = new VisualizationModule[visuModuleNumber];
    try {
      for (int i=0; i<visuModuleNumber; i++) {
        visual[i] = (VisualizationModule)visuModule[i].newInstance();
      }
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    for (int i=0; i<visuModuleNumber; i++) {
      visualPanel.add(visual[i],"VISUAL"+i);
    }
  }

  public final void setPartialDocument() {
    if (partialDocumentB == -1) //NOT SELECTED
      switch (partialDocumentA) { //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
        case 2:
          text = text.segmentPartialTextData[0];
          break;

        case 3:
          text = text.segmentPartialTextData[1];
          break;

        case 4:
          text = text.sentencePartialTextData[0];
          break;

        case 5:
          text = text.sentencePartialTextData[1];
          break;
      }
  }

  public final void selected(TextData tex) {
    text = tex;

    setPartialDocument();


    if (firstTime) {
      initializeData();				// Step.2	Set moduleID
      firstTime = false;
    }

    if (visualization.firstTime) {
//			visualization.setPanelID(panelID);////
      visualization.selected(text);		// (to Step.3,4)

      miningOperations(0);	// Step.5
      setFont();							// (to Step.6)
      visualization.firstTime = false;
    }

    if (visualization.toolType == 1 || visualization.toolType == 2) {
      combinationPanel.displayDiffButton.setEnabled(true);
    } else {
      combinationPanel.displayDiffButton.setEnabled(false);
    }
  }

  //FOCUS   Because this method is callled from this class only, TextData tex may be NO need.
  public final void focusSelected(TextData tex, int operationID) {
    text = tex;
    initializeData();				// Step.2	Set moduleID
    visualization.focusSelected(text);		// (to Step.4)
    miningOperations(operationID);	// Step.5 case 4501, 4502
    visualization.setFont();
    displayOperations(0);				// Step.6
  }

  public final void createInitialData() {} // for check only
  public final void setInitialData() {} // for check only

  public final void initDiffPatterns() {
    diffPatterns = new int[11][];
    for (int i=0; i<11; i++)
      if (visualization.dataNumbers[i] > 0) {
        diffPatterns[i] = new int[visualization.dataNumbers[i] + 1];
        for (int j=0; j<visualization.dataNumbers[i] + 1; j++) {
          diffPatterns[i][j] = 0;
        }
      }
  }

  public final void setVisu(int selectedVisu) {
    visualization = visual[selectedVisu];
    layout.show(visualPanel, "VISUAL"+selectedVisu);

    visuModulePath = absolutePath + visualization.getClass().getPackage().getName().replaceAll("\\.","/") + File.separator;
    visuModuleName = visualization.getClass().getPackage().getName().replaceAll("module\\.VisualizationModules\\.","");

    initDiffPatterns();
  }

  public void initializePanel() {};		// #Step.1
  public void initializeData() {};				// #Step.2

  public abstract void miningOperations(int n);			// #Step.5

  //display for visualization
  public final void setFont() {
    visualization.setFont();
    displayOperations(0);				// Step.6
  }

  public final void displayOperations(int optionNumber) {
    visualization.displayOperations(optionNumber);
  }

  public final void resetFirstTime() {
    firstTime = true;
    visualization.firstTime = true;
  }

  public final void panelReset() {
//		combinationPanel.panelReset();  // reset partial text buttons
    resetFirstTime();
//		selected(text.control.panel[panelID].text);
  }

  public String fileRead() {
    return FILEIO.TextFileAllReadCode(new File(myModulePath+"Japanese.txt"));
  }

  public String fileRead(String filename) {
    return FILEIO.TextFileAllReadCode(new File(filename));
  }

  public String[] fileReadArray() {
    return FILEIO.TextFileAllReadCodeArray(new File(myModulePath+"Japanese.txt"));
  }

  public String[] fileReadArray(String filename) {
    return FILEIO.TextFileAllReadCodeArray(new File(filename));
  }

  public void fileWrite(String filename, String writeText) {
    FILEIO.TextFileWrite2(new File(filename), writeText);
  }

  public void fileWriteArray(String filename, String writeText[], int lineNumber) {
    FILEIO.TextFileWrite(new File(filename), writeText, lineNumber);
  }

  //CREATE DICTIONARY
  public void createDictionary() { // windows,  commandPath, dictionaryPath
    EXECUTE.createDictionaryByIgo(text.control.sp1.isWindows, text.control.sp1.chasenPath, text.control.sp1.absolutePath);
  }

  //FOCUS*
  public final void selectedByTouchFocus() {
    if (focusTouchExecute) {
      focusSelected(text, 4501);
    }
  }
  //FOCUS*
  public final void selectedByClickFocus() {
    if (focusClickExecute) {
      focusSelected(text, 4502);
    }
  }
  //FOCUS*
  public final void selectedByTimingFocus() {
    if (focusTimingExecute) {
      focusSelected(text, 4503);
    }
  }

  //FOCUS*
  public final void setDisplayByTouchFocus() {
    visualization.setDisplayByTouchFocus();
  }
  //FOCUS*
  public final void setDisplayByClickFocus() {
    visualization.setDisplayByClickFocus();
  }
  //FOCUS*
  public final void setDisplayByTimingFocus() {
    visualization.setDisplayByTimingFocus();
  }

  /*
  //FOCUS PARTIAL SEGMENTS
  public void createSegmentPartialData()
  {
  	text.control.resetBySegmentPartialData();
  }*/
  //FOCUS PARTIAL SENTENCES
  /*
  public void createSentencePartialData()
  {
  	text.control.resetBySentencePartialData();
  }
  */
  //FOCUS PARTIAL SEGMENTS
  public void createSegmentPartialData(int number) {
    text.control.resetBySegmentPartialData(number);
  }
  //FOCUS PARTIAL SENTENCES
  public void createSentencePartialData(int number) {
    text.control.resetBySentencePartialData(number);
  }

  //SET DATA
  public final void resetData() {
    visualization.resetData();
  }

  String dataType[] = {"boolean","int","double","String","boolean[]","int[]","double[]","String[]","boolean[][]","int[][]","double[][]"};

  public void printResult(int from, int to) {
    if (from == to) {
      System.out.println("Data type "+dataType[from]+" was set.");
      return;
    }
    if (to == 11) {
      System.out.println("Data type "+dataType[from]+" was NOT set.");
      return;
    }
    System.out.println("Data type "+dataType[from]+" was CONVERTED to "+dataType[to]+".");
  }

  public final boolean checkTextID() {
    if (partialDocumentA == -1 || partialDocumentB == -1) { //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
      textID1 = textID2 = 0;	// NOT CREATE DIFFERENCE
      return false;
    }

    if (partialDocumentA == partialDocumentB) { // SELECTED SAME DATA
      textID1 = textID2 = 0;
      partialDocumentA = -1;
      partialDocumentB = -1;

      combinationPanel.partialDocument.setSelectedIndex(0);
      return false;
    }

    int indexToID[] = {0,0,1,3,2,4};

    textID1 = indexToID[partialDocumentA];
    textID2 = indexToID[partialDocumentB];

    return true;
  }

  public final int checkDiff(int typeNumber, int dataID) {
    int diff = -1;
    if (textID1 != textID2) {
      if (diffPatterns[typeNumber] != null) {
        if (dataID < diffPatterns[typeNumber].length-1) {
          diff = diffPatterns[typeNumber][dataID+1];
        } else {
          diff = diffPatterns[typeNumber][0];
        }
      } else {
        diff = 0;
      }
    }
    return diff;
  }

  public final void setDataBoolean(int dataID, boolean data) {
    checkTextID();
    setData.setDataBoolean(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(0, dataID));
  }
  public final void setDataInteger(int dataID, int data) {
    checkTextID();
    setData.setDataInteger(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(1, dataID));
  }
  public final void setDataDouble(int dataID, double data) {
    checkTextID();
    setData.setDataDouble(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(2, dataID));
  }
  public final void setDataString(int dataID, String data) {
    checkTextID();
    setData.setDataString(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(3, dataID));
  }
  public final void setDataBooleanArray(int dataID, boolean data[]) {
    checkTextID();
    setData.setDataBooleanArray(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(4, dataID));
  }
  public final void setDataIntegerArray(int dataID, int data[]) {
    checkTextID();
    setData.setDataIntegerArray(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(5, dataID));
  }
  public final void setDataDoubleArray(int dataID, double data[]) {
    checkTextID();
    setData.setDataDoubleArray(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(6, dataID));
  }
  public final void setDataStringArray(int dataID, String data[]) {
    checkTextID();
    setData.setDataStringArray(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(7, dataID));
  }
  public final void setDataBooleanArray2(int dataID, boolean data[][]) {
    checkTextID();
    setData.setDataBooleanArray2(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(8, dataID));
  }
  public final void setDataIntegerArray2(int dataID, int data[][]) {
    checkTextID();
    setData.setDataIntegerArray2(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(9, dataID));
  }
  public final void setDataDoubleArray2(int dataID, double data[][]) {
    checkTextID();
    setData.setDataDoubleArray2(dataID, data,moduleID,text,visualization, textID1, textID2, checkDiff(10, dataID));
  }


  public final void setDataBoolean(boolean data) {
    checkTextID();
    int to = setData.setDataBoolean(data,moduleID,text,visualization, textID1, textID2, checkDiff(0, visualization.booleanNumber));
    printResult(0,to);
  }
  public final void setDataInteger(int data) {
    checkTextID();
    int to = setData.setDataInteger(data,moduleID,text,visualization, textID1, textID2, checkDiff(1, visualization.integerNumber));
    printResult(1,to);
  }
  public final void setDataDouble(double data) {
    checkTextID();
    int to = setData.setDataDouble(data,moduleID,text,visualization, textID1, textID2, checkDiff(2, visualization.doubleNumber));
    printResult(2,to);
  }
  public final void setDataString(String data) {
    checkTextID();
    int to = setData.setDataString(data,moduleID,text,visualization, textID1, textID2, checkDiff(3, visualization.StringNumber));
    printResult(3,to);
  }
  public final void setDataBooleanArray(boolean data[]) {
    checkTextID();
    int to = setData.setDataBooleanArray(data,moduleID,text,visualization, textID1, textID2, checkDiff(4, visualization.booleanArrayNumber));
    printResult(4,to);
  }
  public final void setDataIntegerArray(int data[]) {
    checkTextID();
    int to = setData.setDataIntegerArray(data,moduleID,text,visualization, textID1, textID2, checkDiff(5, visualization.integerArrayNumber));
    printResult(5,to);
  }
  public final void setDataDoubleArray(double data[]) {
    checkTextID();
    int to = setData.setDataDoubleArray(data,moduleID,text,visualization, textID1, textID2, checkDiff(6, visualization.doubleArrayNumber));
    printResult(6,to);
  }
  public final void setDataStringArray(String data[]) {
    checkTextID();
    int to = setData.setDataStringArray(data,moduleID,text,visualization, textID1, textID2, checkDiff(7, visualization.StringArrayNumber));
    printResult(7,to);
  }
  public final void setDataBooleanArray2(boolean data[][]) {
    checkTextID();
    int to = setData.setDataBooleanArray2(data,moduleID,text,visualization, textID1, textID2, checkDiff(8, visualization.booleanArray2Number));
    printResult(8,to);
  }
  public final void setDataIntegerArray2(int data[][]) {
    checkTextID();
    int to = setData.setDataIntegerArray2(data,moduleID,text,visualization, textID1, textID2, checkDiff(9, visualization.integerArray2Number));
    printResult(9,to);
  }
  public final void setDataDoubleArray2(double data[][]) {
    checkTextID();
    int to = setData.setDataDoubleArray2(data,moduleID,text,visualization, textID1, textID2, checkDiff(10, visualization.doubleArray2Number));
    printResult(10,to);
  }




  //GET DATA
  public final boolean getDataBoolean(int getModuleID, int dataID) {
    return text.connection.getDataBoolean(text.textID, getModuleID, dataID);
  }
  public final int getDataInteger(int getModuleID, int dataID) {
    return text.connection.getDataInteger(text.textID, getModuleID, dataID);
  }
  public final double getDataDouble(int getModuleID, int dataID) {
    return text.connection.getDataDouble(text.textID, getModuleID, dataID);
  }
  public final String getDataString(int getModuleID, int dataID) {
    return text.connection.getDataString(text.textID, getModuleID, dataID);
  }
  public final boolean[] getDataBooleanArray(int getModuleID, int dataID) {
    return text.connection.getDataBooleanArray(text.textID, getModuleID, dataID);
  }
  public final int[] getDataIntegerArray(int getModuleID, int dataID) {
    return text.connection.getDataIntegerArray(text.textID, getModuleID, dataID);
  }
  public final double[] getDataDoubleArray(int getModuleID, int dataID) {
    return text.connection.getDataDoubleArray(text.textID, getModuleID, dataID);
  }
  public final String[] getDataStringArray(int getModuleID, int dataID) {
    return text.connection.getDataStringArray(text.textID, getModuleID, dataID);
  }
  public final boolean[][] getDataBooleanArray2(int getModuleID, int dataID) {
    return text.connection.getDataBooleanArray2(text.textID, getModuleID, dataID);
  }
  public final int[][] getDataIntegerArray2(int getModuleID, int dataID) {
    return text.connection.getDataIntegerArray2(text.textID, getModuleID, dataID);
  }
  public final double[][] getDataDoubleArray2(int getModuleID, int dataID) {
    return text.connection.getDataDoubleArray2(text.textID, getModuleID, dataID);
  }

  public final boolean getDataBoolean(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataBoolean(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int getDataInteger(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataInteger(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double getDataDouble(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataDouble(text.textID, getModuleID, dataID, optionNumber);
  }
  public final String getDataString(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataString(text.textID, getModuleID, dataID, optionNumber);
  }
  public final boolean[] getDataBooleanArray(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataBooleanArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int[] getDataIntegerArray(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataIntegerArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double[] getDataDoubleArray(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataDoubleArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final String[] getDataStringArray(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataStringArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final boolean[][] getDataBooleanArray2(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataBooleanArray2(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int[][] getDataIntegerArray2(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataIntegerArray2(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double[][] getDataDoubleArray2(int getModuleID, int dataID, int optionNumber) {
    return text.connection.getDataDoubleArray2(text.textID, getModuleID, dataID, optionNumber );
  }


  //GET NEW DATA	// CAUTION: delete all data of the same dataID,
  //CAUTION: If the indicated module used another getData, that may use old data. This method should delete all relevant data.
  public final boolean getNewDataBoolean(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 0);
    return text.connection.getDataBoolean(text.textID, getModuleID, dataID);
  }
  public final int getNewDataInteger(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 1);
    return text.connection.getDataInteger(text.textID, getModuleID, dataID);
  }
  public final double getNewDataDouble(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 2);
    return text.connection.getDataDouble(text.textID, getModuleID, dataID);
  }
  public final String getNewDataString(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 3);
    return text.connection.getDataString(text.textID, getModuleID, dataID);
  }
  public final boolean[] getNewDataBooleanArray(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 4);
    return text.connection.getDataBooleanArray(text.textID, getModuleID, dataID);
  }
  public final int[] getNewDataIntegerArray(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 5);
    return text.connection.getDataIntegerArray(text.textID, getModuleID, dataID);
  }
  public final double[] getNewDataDoubleArray(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 6);
    return text.connection.getDataDoubleArray(text.textID, getModuleID, dataID);
  }
  public final String[] getNewDataStringArray(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 7);
    return text.connection.getDataStringArray(text.textID, getModuleID, dataID);
  }
  public final boolean[][] getNewDataBooleanArray2(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 8);
    return text.connection.getDataBooleanArray2(text.textID, getModuleID, dataID);
  }
  public final int[][] getNewDataIntegerArray2(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 9);
    return text.connection.getDataIntegerArray2(text.textID, getModuleID, dataID);
  }
  public final double[][] getNewDataDoubleArray2(int getModuleID, int dataID) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 10);
    return text.connection.getDataDoubleArray2(text.textID, getModuleID, dataID);
  }

  public final boolean getNewDataBoolean(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 0);
    return text.connection.getDataBoolean(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int getNewDataInteger(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 1);
    return text.connection.getDataInteger(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double getNewDataDouble(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 2);
    return text.connection.getDataDouble(text.textID, getModuleID, dataID, optionNumber);
  }
  public final String getNewDataString(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 3);
    return text.connection.getDataString(text.textID, getModuleID, dataID, optionNumber);
  }
  public final boolean[] getNewDataBooleanArray(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 4);
    return text.connection.getDataBooleanArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int[] getNewDataIntegerArray(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 5);
    return text.connection.getDataIntegerArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double[] getNewDataDoubleArray(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 6);
    return text.connection.getDataDoubleArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final String[] getNewDataStringArray(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 7);
    return text.connection.getDataStringArray(text.textID, getModuleID, dataID, optionNumber);
  }
  public final boolean[][] getNewDataBooleanArray2(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 8);
    return text.connection.getDataBooleanArray2(text.textID, getModuleID, dataID, optionNumber);
  }
  public final int[][] getNewDataIntegerArray2(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 9);
    return text.connection.getDataIntegerArray2(text.textID, getModuleID, dataID, optionNumber);
  }
  public final double[][] getNewDataDoubleArray2(int getModuleID, int dataID, int optionNumber) {
    text.connection.deleteData(text.textID, getModuleID, dataID, 10);
    return text.connection.getDataDoubleArray2(text.textID, getModuleID, dataID, optionNumber);
  }


  //EXECUTION
  public final void executeOtherModuleFirst(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOptionFirst(panelID, executeModuleID, optionNumber);
  }
  public final void executeOtherModule(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOption(panelID, executeModuleID, optionNumber);
  }
  public final void executeModuleFirst(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOptionFirstFromXXX(executeModuleID, optionNumber);
  }
  public final void executeModule(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOptionFromXXX(executeModuleID, optionNumber);
  }

  //DISPLAY
  public final void displayOtherModuleFirst(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOptionFirst(panelID, displayModuleID, optionNumber);
  }
  public final void displayOtherModule(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOption(panelID, displayModuleID, optionNumber);
  }
  public final void displayModuleFirst(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOptionFirstFromXXX(displayModuleID, optionNumber);
  }
  public final void displayModule(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOptionFromXXX(displayModuleID, optionNumber);
  }

  //JAPANESE
  public boolean isMenuInJapanese() {
    return text.control.text.settingData.inJapanese;
  }

  //DISPLAY PROCESS
  public void createProcessingPop() {
    processingPanel = new PopUpForProcessing(text.control.windowSizeX/2 - 250, 500,500,200, myModuleName+" in PANEL "+(panelID+1), Color.green);
  }

  public void setProcessingPercent(int percent) {
    processingPanel.setPercent(percent);
  }

  public void disposeProcessingPop() {
    processingPanel.dispose();
  }


  //SAVE ACTION LOG
  String logFilename;
  public void writeActionLog(String data) {
    logFilename = absolutePath+"ACTIONLOG";
    try {
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));
      BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "Shift_JIS"));

      bw1.write(String.format("%.2f",text.control.getDiffTime())+" Mining PanelID="+panelID+" "+"ModuleID="+moduleID+" "+data+System.getProperty("line.separator"));
      bw1.close();
    } catch (Exception e) {
      System.out.println("File Writing ERROR ACTIONLOG");
    }
  }

  //PANEL SET
  public void additionalPanelSet() {}

  //PANEL SET
  public class CombinationPanel extends JPanel implements ActionListener {
    public JButton panelDeleteButton;
    public JButton panelBackButton;
    public JButton panelSetButton;
    public JToggleButton panelKeepButton;
    public JButton panelCaptureButton;

    public JToggleButton displayReadmeButton;
    PopUpWindow popForMining, popForVisualization;

    public JButton panelLoadButton;
    public JButton panelRecordButton;

    public JToggleButton displayDiffButton;


//		public JComboBox partialSegments1;
//		public JComboBox partialSegments2;
//		public JComboBox partialSentences1;
//		public JComboBox partialSentences2;

    public JComboBox partialDocument;

    JPanel subLabelPanel[];
    JLabel buttonLabel[];
    JPanel partial;

    MiningModule mining;

    boolean autoSet = false;

    CombinationPanel(MiningModule mining) {
      this.mining = mining;

      setLayout(new GridLayout());
      initializePanel();
    }

    void initializePanel() {
      if (defaultSetPanelModuleID == null) {	// NOT GIVEN IN THE CONSTRUCTOR
        defaultSetPanel();  //SET DEFAULT PANEL COMBINATION
      }

      if (defaultSetPanelTextID == null) {	// NOT GIVEN IN THE CONSTRUCTOR
        defaultSetPanelTextID();
      }

      String filename = absolutePath+"source"+File.separator+"JapaneseMining.txt";
      File jpname = new File(filename);

      buttonNamesInJapanese = FILEIO.TextFileAllReadCodeArray(jpname);



      int labelNumber = 8;
      subLabelPanel = new JPanel[labelNumber];
      buttonLabel = new JLabel[labelNumber];
      for (int i=0; i<labelNumber; i++) {
        subLabelPanel[i] = new JPanel(new BorderLayout());
        if (i<4) {
          subLabelPanel[i].setBackground(Color.PINK);
        }
//                        subLabelPanel[i].setBackground(new Color(0xff,0xda,0xb9));
        else if (i<6)
//                    subLabelPanel[i].setBackground(new Color(0xff,0xda,0xb9));
        {
          subLabelPanel[i].setBackground(new Color(0xfb,0xef,0xf8));
        }
//                                    subLabelPanel[i].setBackground(new Color(0xe0,0xf2,0xf7));
        //     subLabelPanel[i].setBackground(Color.PINK);
        else {
          subLabelPanel[i].setBackground(Color.GREEN);
        }
        //                  subLabelPanel[i].setBackground(new Color(0x58,0xfa,0x82));
        buttonLabel[i] = new JLabel();
        buttonLabel[i].setFont(new Font("Dialog", Font.BOLD, 10));

      }


      String partialDocumentNames[] = {"-","ALL","SEG-SET-1","SEG-SET-2","SEN-SET-1","SEN-SET-2"};


      if (isMenuInJapanese()) {
        buttonLabel[0].setText(buttonNamesInJapanese[11]);
        buttonLabel[1].setText(" ");
        buttonLabel[2].setText(" ");
        buttonLabel[3].setText(" ");
//                buttonLabel[4].setText(buttonNamesInJapanese[12]);
        buttonLabel[4].setText("");
        buttonLabel[5].setText("");
        buttonLabel[6].setText(buttonNamesInJapanese[13]);
        buttonLabel[7].setText("");

        panelDeleteButton = new JButton(buttonNamesInJapanese[0],new ImageIcon(absolutePath+"source"+File.separator+"delete.png"));
        panelBackButton = new JButton(buttonNamesInJapanese[1],new ImageIcon(absolutePath+"source"+File.separator+"back.png"));
        panelSetButton = new JButton(buttonNamesInJapanese[2],new ImageIcon(absolutePath+"source"+File.separator+"set.png"));
        panelKeepButton = new JToggleButton(buttonNamesInJapanese[3],new ImageIcon(absolutePath+"source"+File.separator+"keep.png"));
        panelCaptureButton = new JButton(buttonNamesInJapanese[4],new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));

        panelLoadButton = new JButton(buttonNamesInJapanese[5],new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));
        panelRecordButton = new JButton(buttonNamesInJapanese[6],new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));

        partialDocumentNames[1] = buttonNamesInJapanese[15];
        for (int i=2; i<6; i++) {
          partialDocumentNames[i] = buttonNamesInJapanese[16]+"-"+(i-1);
        }
        partialDocument = new JComboBox(partialDocumentNames);

        displayReadmeButton = new JToggleButton(buttonNamesInJapanese[9],new ImageIcon(absolutePath+"source"+File.separator+"readme.png"));
        displayDiffButton = new JToggleButton(buttonNamesInJapanese[14]);
      } else {
        buttonLabel[0].setText("Panel");
        buttonLabel[1].setText(" ");
        buttonLabel[2].setText(" ");
        buttonLabel[3].setText(" ");
        buttonLabel[4].setText("Tool");
        buttonLabel[5].setText(" ");
        buttonLabel[6].setText("Text");
        buttonLabel[7].setText("");



        panelDeleteButton = new JButton("DELETE",new ImageIcon(absolutePath+"source"+File.separator+"delete.png"));
        panelBackButton = new JButton("BACK",new ImageIcon(absolutePath+"source"+File.separator+"back.png"));
        panelSetButton = new JButton("SET",new ImageIcon(absolutePath+"source"+File.separator+"set.png"));
        panelKeepButton = new JToggleButton("KEEP",new ImageIcon(absolutePath+"source"+File.separator+"keep.png"));
        panelCaptureButton = new JButton("CAMERA",new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));

        panelLoadButton = new JButton("MYSET",new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));
        panelRecordButton = new JButton("REC",new ImageIcon(absolutePath+"source"+File.separator+"camera.png"));

        partialDocument = new JComboBox(partialDocumentNames);

        displayReadmeButton = new JToggleButton("README",new ImageIcon(absolutePath+"source"+File.separator+"readme.png"));
        displayDiffButton = new JToggleButton("DIFF");

      }
      displayReadmeButton.setSelectedIcon(new ImageIcon(absolutePath+"source"+File.separator+"close.png"));
      displayReadmeButton.setForeground(Color.blue);

      panelKeepButton.addActionListener(this);
      panelSetButton.addActionListener(this);
      panelBackButton.addActionListener(this);

      panelCaptureButton.addActionListener(this);
      displayReadmeButton.addActionListener(this);
      panelDeleteButton.addActionListener(this);

      displayDiffButton.addActionListener(this);

      panelLoadButton.addActionListener(this);
      panelRecordButton.addActionListener(this);


      partialDocument.addActionListener(this);

      autoSet = true;
      partialDocument.setSelectedIndex(0);
      autoSet = false;


      for (int i=0; i<labelNumber; i++) {
        add(subLabelPanel[i]);
      }

      subLabelPanel[0].add(panelDeleteButton);
      subLabelPanel[1].add(panelKeepButton);
      subLabelPanel[2].add(panelSetButton);
      subLabelPanel[3].add(panelBackButton);
      subLabelPanel[4].add(panelCaptureButton);
      subLabelPanel[5].add(displayReadmeButton);

      partial = new JPanel();
      partial.setLayout(new GridLayout(1,2));
      partial.setBackground(Color.green);
      subLabelPanel[6].add(partial);


      partial.add(partialDocument);

      for (int i=0; i<labelNumber; i++) {
        subLabelPanel[i].add(buttonLabel[i],BorderLayout.NORTH);
      }

      subLabelPanel[7].add(displayDiffButton);

      //add(setFocusSegmentsButton);
      //add(setFocusSentencesButton);

      //			add(panelLoadButton);
      //			add(panelRecordButton);

//            setBackground(new Color(0x58,0xfa,0x82));
      setBackground(Color.green);

      if (!text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].panelRecorded) {
        panelLoadButton.setEnabled(false);
      }
      if (!text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].panelBack) {
        panelBackButton.setEnabled(false);
      }
    }


    //Action listener
    public void actionPerformed(ActionEvent e) {
      if (autoSet) {
        return;
      }

      String log = e.getSource().toString();
//            String regex = "text=(.*),";
      String regex = "text=(.*)]";
      Pattern p = Pattern.compile(regex);

      Matcher m = p.matcher(log);

      if (m.find()) {
        writeActionLog(m.group(1));
      } else {
        writeActionLog(log);
      }


      //			insideOfActionPerformed(e);
      if (e.getSource() == panelDeleteButton) {
        panelDelete();
      }

      if (e.getSource() == panelBackButton) {
        if (popForDiff != null) {
          popForDiff.dispose();
        }

        panelBack();
      }

      if (e.getSource() == panelSetButton) {
        if (popForDiff != null) {
          popForDiff.dispose();
        }

        panelSet();
      }

      if (e.getSource() == panelKeepButton) {
        panelKeep();
      }

      if (e.getSource() == panelCaptureButton) {
        visualization.imageCapture();
      }

      if (e.getSource() == displayReadmeButton) {
        displayReadme();
        if (text.control.popForSupport != null) {
          text.control.popForSupport.support.setAnsweredFlagTrue(0,16);
        }
      }

      if (e.getSource() == panelLoadButton) {
        panelLoad();
      }

      if (e.getSource() == panelRecordButton) {
        panelRecord();
      }

      /*
      if(e.getSource() == partialSegments1)
      setPartialSegments(0);

      if(e.getSource() == partialSegments2)
      setPartialSegments(1);

      if(e.getSource() == partialSentences1)
      setPartialSentences(0);

      if(e.getSource() == partialSentences2)
      setPartialSentences(1);
       */

      if (e.getSource() == partialDocument) {
        setPartialDocument();
      }

      if (e.getSource() == displayDiffButton) {
        if (!displayDiffButton.isSelected()) {
          popForDiff.dispose();
        } else {
          panelSetForDiff(1,3);  //seg1, seg2
        }
      }


    }

    final void displayDiff() {

      if (displayDiffButton.isSelected()) {
        Point position = getLocationOnScreen();
        popForDiff = new PopUpForDiff((int)position.getX(), (int)position.getY()+35, 600, 400, visualization.dataNumbers, diffPatterns, mining);
        popForDiff.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      } else {
        popForDiff.dispose();
      }
    }

    //PANEL SET
    final void defaultSetPanel() {
      defaultSetPanelModuleID= new int[pairingVisualizationID.length];
      defaultSetPanelVisualizationID = new int[pairingVisualizationID.length];
      defaultSetPanelTextID = new int[pairingVisualizationID.length];

      for (int i=0; i<pairingVisualizationID.length; i++) {
        defaultSetPanelModuleID[i] = moduleID;
        defaultSetPanelVisualizationID[i] = pairingVisualizationID[i];
        defaultSetPanelTextID[i] = 0;
      }
    }

    final void defaultSetPanelTextID() {
      defaultSetPanelTextID = new int[defaultSetPanelModuleID.length];

      for (int i=0; i<defaultSetPanelModuleID.length; i++) {
        defaultSetPanelTextID[i] = 0;
      }
    }

    final void recPanelConstitution(int MID[], int VID[], int sizeX[]) {
      //MID = new int[text.control.panelNumber];
      //	VID = new int[text.control.panelNumber];
      for (int i=0; i<text.control.panelNumber; i++) {
        MID[i] = text.control.moduleData.msub.selectedMiningIDs[text.control.panel[i].selected_module];
        VID[i] = text.control.moduleData.msub.selectedVisualizationIDs[text.control.panel[i].pair_visu[text.control.panel[i].selected_module]];
        sizeX[i] = text.control.balancedPanelSizeX[i];
      }
    }

    final void panelSet() {
      panelSetBasic();
      additionalPanelSet();
    }

    final void panelSetBasic() {
      lastSetPanelModuleID = new int[text.control.panelNumber];
      lastSetPanelVisualizationID = new int[text.control.panelNumber];
      lastSetPanelTextID = new int[text.control.panelNumber];
      lastBalancedPanelSizeX = new int[text.control.panelNumber];

      recPanelConstitution(lastSetPanelModuleID, lastSetPanelVisualizationID, lastBalancedPanelSizeX);

      text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].setBackPanelData(lastSetPanelModuleID, lastSetPanelVisualizationID, lastSetPanelTextID, lastBalancedPanelSizeX);
      panelBackButton.setEnabled(true);

      text.control.panelSetWithKeep(panelID, defaultSetPanelModuleID, defaultSetPanelVisualizationID, defaultSetPanelTextID);
    }

    final void panelSetForDiff(int text1ID, int text2ID) {
      if (popForDiff != null) {
        popForDiff.dispose();
      }

      lastSetPanelModuleID = new int[text.control.panelNumber];
      lastSetPanelVisualizationID = new int[text.control.panelNumber];
      lastSetPanelTextID = new int[text.control.panelNumber];
      lastBalancedPanelSizeX = new int[text.control.panelNumber];

      recPanelConstitution(lastSetPanelModuleID, lastSetPanelVisualizationID, lastBalancedPanelSizeX);

      text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].setBackPanelData(lastSetPanelModuleID, lastSetPanelVisualizationID, lastSetPanelTextID, lastBalancedPanelSizeX);
      panelBackButton.setEnabled(true);

      int mIDs[] = new int[3];
      int vIDs[] = new int[3];
      int tIDs[] = new int[3];
      for (int i=0; i<3; i++) {
        mIDs[i] = moduleID;
        vIDs[i] = visualization.getModuleID();
      }



      int IDtoIndex[] = {1,2,4,3,5};

      // 1:ALL, 2:SEG1, 3:SEG2, 4:SEN1, 5:SEN2        //-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
      tIDs[0] = text1ID;//1
      tIDs[1] = text2ID;//3
      tIDs[2] = -1 * (IDtoIndex[text1ID]*10 + IDtoIndex[text2ID]);

      System.out.println("SET TEXTID "+text1ID+"-"+text2ID+":"+tIDs[2]);

      text.control.panelSetWithKeep(panelID, mIDs, vIDs, tIDs);
    }

    final void panelDelete() {
      text.control.delete(panelID);
    }

    final void panelKeep() {
      keep = panelKeepButton.isSelected();
    }

    final void displayReadme() {
      if (displayReadmeButton.isSelected()) {
        Point position = getLocationOnScreen();
//				String readmeTextForMining = fileRead(myModulePath+"README.txt");//visuModuleName visuModulePath
        String readmeTextForMining = text.control.moduleData.miningReadmeText[text.control.moduleData.findModule(moduleID)];
//				String japaneseMiningName = fileRead(myModulePath+myModuleName+".txt");
        String japaneseMiningName = text.control.moduleData.miningModuleNamesInJapanese[text.control.moduleData.findModule(moduleID)];
//				String readmeTextForVisualization = fileRead(visuModulePath+"README.txt");//visuModuleName visuModulePath
        String readmeTextForVisualization = text.control.moduleData.visualizationReadmeText[text.control.moduleData.findVisualizationModule(visualization.getModuleID())];
//				String japaneseVisualizationName = fileRead(visuModulePath+visuModuleName+".txt");
        String japaneseVisualizationName = text.control.moduleData.visualizationModuleNamesInJapanese[text.control.moduleData.findVisualizationModule(visualization.getModuleID())];
        popForMining = new PopUpWindow(japaneseMiningName+"("+myModuleName+"(ID="+moduleID+"))"+buttonNamesInJapanese[10],
                                       readmeTextForMining, (int)position.getX(), (int)position.getY()+35, 600, 400, text.fontSize, new Color(0xff,0xf0,0xf5));
        popForMining.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popForVisualization = new PopUpWindow(japaneseVisualizationName+"("+visuModuleName+"(ID="+visualization.getModuleID()+"))"+buttonNamesInJapanese[10], readmeTextForVisualization, (int)position.getX(), (int)position.getY()+435, 600, 400, text.fontSize, new Color(0xe0,0xff,0xff));
        popForVisualization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      } else {
        popForMining.dispose();
        popForVisualization.dispose();
      }
    }

    final void panelLoad() {
      lastSetPanelModuleID = new int[text.control.panelNumber];
      lastSetPanelVisualizationID = new int[text.control.panelNumber];
      lastSetPanelTextID = new int[text.control.panelNumber];
      lastBalancedPanelSizeX = new int[text.control.panelNumber];

      recPanelConstitution(lastSetPanelModuleID, lastSetPanelVisualizationID, lastBalancedPanelSizeX);

      text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].setBackPanelData(lastSetPanelModuleID, lastSetPanelVisualizationID, lastSetPanelTextID, lastBalancedPanelSizeX);
      panelBackButton.setEnabled(true);

      //		if(text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].panelRecorded)
      text.control.panelSetByMiningModule(moduleID);
      //		else
      //	text.control.panelSet(defaultSetPanelModuleID, defaultSetPanelVisualizationID);

    }

    final void panelBack() {
      text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].panelBack = false;
      panelBackButton.setEnabled(false);

      text.control.backPanelSetByMiningModule(moduleID);
    }

    public void resetModuleForPartialData() {
      TextData originalTextData = text.control.text;

      text.control.stealthPanel.resetFirstTime_for_stealth();

      for (int i=0; i<visuModuleNumber; i++) {
        visual[i].firstTime = true;
      }

      resetFirstTime();
      selected(originalTextData);
      text.control.panel[panelID].setTitle2();
    }


    //////NEW FOR PARTIAL

    final void panelSetFocusSegments(int number) {
      TextData originalTextData = text.control.text;

      originalTextData.setSegmentPartialTextData(number);
      text.control.panel[panelID].setTitle2();
    }

    final void panelSetFocusSentences(int number) {
      TextData originalTextData = text.control.text;

      originalTextData.setSentencePartialTextData(number);
      text.control.panel[panelID].setTitle2();
    }

    final void setPartialDocument() {
      TextData originalTextData = text.control.text;

      partialDocumentA = partialDocument.getSelectedIndex();//-1:---,0:---,1:ALL,2:SEG1,3:SEG2,4:SEN1,5:SEN2
      if (partialDocumentA == 0) {
        partialDocumentA = -1;
      }

      System.out.println("Selected partial = "+partialDocumentA);

      text.control.panel[panelID].setTitle2();

      if (!autoSet) {
        resetModuleForPartialData();
      }
    }

    boolean compare(int a[], int b[]) {
      if (a.length != b.length) {
        return false;
      }
      for (int i=0; i<a.length; i++)
        if (a[i] != b[i]) {
          return false;
        }
      return true;
    }

    final void panelRecord() {
      setPanelModuleID = new int[text.control.panelNumber];
      setPanelVisualizationID = new int[text.control.panelNumber];
      setPanelTextID = new int[text.control.panelNumber];
      setBalancedPanelSizeX = new int[text.control.panelNumber];

      recPanelConstitution(setPanelModuleID, setPanelVisualizationID, setBalancedPanelSizeX);

      if (compare(setPanelModuleID, defaultSetPanelModuleID) && compare(setPanelVisualizationID, defaultSetPanelVisualizationID)) {
        text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].panelRecorded = false;
        panelLoadButton.setEnabled(false);
        return;
      }

      text.control.moduleData.miningPanelSet[text.control.moduleData.findModule(moduleID)].setPanelData(setPanelModuleID, setPanelVisualizationID, setPanelTextID, setBalancedPanelSizeX);
      panelLoadButton.setEnabled(true);
    }
  }

}
