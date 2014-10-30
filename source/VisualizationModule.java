//
// Core Program for TETDM
// VisualizationModule.java Version 0.44
// Copyright(C) 2011-2013 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.TextData.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import java.awt.image.*;
import javax.imageio.ImageIO;

public abstract class VisualizationModule extends JPanel {
  private int moduleID = -1;
  private int panelID;

  public int toolType = 4;//1:simple 2:primitive 3:semi-primitive 4:special

  public TextData text;

  public String myModulePath;
  public String myModuleName;

  boolean makePanel = true;
  boolean firstTime = true;

  public int oldSizeX, oldSizeY;
  public int sizeX=0, sizeY=0;
  public double changeX, changeY;

  public int pairingMiningIndex[];

  //	FOR PROCESSING

  PopUpForProcessing processingPanel;

//	public boolean focusTouchDisplay = false;
//	public boolean focusClickDisplay = false;

  //-->public boolean focusDisplay = true;

  public VisualizationModule() {}

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

  public final void initPanel() {
    setLayout(new BorderLayout());
    setBackground(Color.white);

    myModulePath = text.absolutePath + this.getClass().getPackage().getName().replaceAll("\\.","/") + File.separator;
    myModuleName = this.getClass().getPackage().getName().replaceAll("module\\.VisualizationModules\\.","");

//		System.out.println("myModuleName= "+myModuleName);

    initializePanel();			// Step.3

    System.out.println(" + Visualization: "+this.getClass().getName());
  }

  public final void selected(TextData tex) {
    text = tex;
    if (makePanel) {
      initPanel();			//(to Step.3)
      makePanel = false;
    }
    initializeData();			// Step.4
  }

  //FOCUS
  public final void focusSelected(TextData tex) {
    text = tex;
    initializeData();			// Step.4
  }

  public abstract void initializePanel();		// #Step.3
  public abstract void initializeData();				// #Step.4
  public abstract void displayOperations(int optionNumber);		// #Step.6

  public final void setData() {}		// for check only

  int booleanNumber = 0;
  int integerNumber = 0;
  int doubleNumber = 0;
  int StringNumber = 0;
  int booleanArrayNumber = 0;
  int integerArrayNumber = 0;
  int doubleArrayNumber = 0;
  int StringArrayNumber = 0;
  int booleanArray2Number = 0;
  int integerArray2Number = 0;
  int doubleArray2Number = 0;

  public int dataNumbers[] = {0,0,0,0,0,0,0,0,0,0,0};

  public boolean checkDataNumbers() {
    if (booleanNumber < dataNumbers[0]) {
      return false;
    }
    if (integerNumber < dataNumbers[1]) {
      return false;
    }
    if (doubleNumber < dataNumbers[2]) {
      return false;
    }
    if (StringNumber < dataNumbers[3]) {
      return false;
    }
    if (booleanArrayNumber < dataNumbers[4]) {
      return false;
    }
    if (integerArrayNumber < dataNumbers[5]) {
      return false;
    }
    if (doubleArrayNumber < dataNumbers[6]) {
      return false;
    }
    if (StringArrayNumber < dataNumbers[7]) {
      return false;
    }
    if (booleanArray2Number < dataNumbers[8]) {
      return false;
    }
    if (integerArray2Number < dataNumbers[9]) {
      return false;
    }
    if (doubleArray2Number < dataNumbers[10]) {
      return false;
    }

    if (dataConstraint() == false) {
      return false;
    }

    return true;
  }

  public boolean dataConstraint() {
    return true;
  }

  public void resetData() {
    booleanNumber = 0;
    integerNumber = 0;
    doubleNumber = 0;
    StringNumber = 0;
    booleanArrayNumber = 0;
    integerArrayNumber = 0;
    doubleArrayNumber = 0;
    StringArrayNumber = 0;
    booleanArray2Number = 0;
    integerArray2Number = 0;
    doubleArray2Number = 0;
  }

  public boolean setData(int dataID, boolean data) {
    return false;
  }
  public boolean setData(int dataID, int data) {
    return false;
  }
  public boolean setData(int dataID, double data) {
    return false;
  }
  public boolean setData(int dataID, String data) {
    return false;
  }
  public boolean setData(int dataID, boolean data[]) {
    return false;
  }
  public boolean setData(int dataID, int data[]) {
    return false;
  }
  public boolean setData(int dataID, double data[]) {
    return false;
  }
  public boolean setData(int dataID, String data[]) {
    return false;
  }
  public boolean setData(int dataID, boolean data[][]) {
    return false;
  }
  public boolean setData(int dataID, int data[][]) {
    return false;
  }
  public boolean setData(int dataID, double data[][]) {
    return false;
  }

  final public boolean setData(boolean data) {
    return false;
  }
  final public boolean setData(int data) {
    return false;
  }
  final public boolean setData(double data) {
    return false;
  }
  final public boolean setData(String data) {
    return false;
  }
  final public boolean setData(boolean data[]) {
    return false;
  }
  final public boolean setData(int data[]) {
    return false;
  }
  final public boolean setData(double data[]) {
    return false;
  }
  final public boolean setData(String data[]) {
    return false;
  }
  final public boolean setData(boolean data[][]) {
    return false;
  }
  final public boolean setData(int data[][]) {
    return false;
  }
  final public boolean setData(double data[][]) {
    return false;
  }

  //display for visualization
  public void setFont() {}

  public boolean getPanelSize() {
    boolean change = false;

    oldSizeX = sizeX;
    oldSizeY = sizeY;

    sizeX = getWidth();
    sizeY = getHeight();

    if (sizeX != oldSizeX || sizeY != oldSizeY) {
      change = true;
    }

    changeX = changeY = 1.0;
    if (oldSizeX != 0) {
      changeX = sizeX / (double)oldSizeX;
    }
    if (oldSizeY != 0) {
      changeY = sizeY / (double)oldSizeY;
    }

    return change;
  }

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

  //FOCUS
  public final void setDisplayByTouchFocus() {
    displayOperations(4501);
  }
  //FOCUS
  public final void setDisplayByClickFocus() {
    displayOperations(4502);
  }
  //FOCUS
  public final void setDisplayByTimingFocus() {
    displayOperations(4503);
  }


  //FOCUS
  protected final void repaintOthersByTouch() {
    text.control.setDisplayOthersByTouchFocus(panelID);
  }
  //FOCUS
  protected final void repaintOthersByClick() {
    text.control.setDisplayOthersByClickFocus(panelID);
  }
  //FOCUS
  protected final void repaintOthersByTiming() {
    text.control.setDisplayOthersByTimingFocus(panelID);
  }
  //FOCUS
  protected final void executeOthersByTouch() {
    text.control.resetSelectPanelDataByTouchFocus(panelID);
  }
  //FOCUS
  protected final void executeOthersByClick() {
    text.control.resetSelectPanelDataByClickFocus(panelID);
  }
  //FOCUS
  protected final void executeOthersByTiming() {
    text.control.resetSelectPanelDataByTimingFocus(panelID);
  }
  //FOCUS	ALL
  protected final void executeAllByTouch() {
    text.control.resetSelectPanelDataByTouchFocusAll(panelID);
  }
  //FOCUS	ALL
  protected final void executeAllByClick() {
    text.control.resetSelectPanelDataByClickFocusAll(panelID);
  }
  //FOCUS	ALL
  protected final void executeAllByTiming() {
    text.control.resetSelectPanelDataByTimingFocusAll(panelID);
  }

  /*
    //FOCUS PARTIAL SEGMENTS
    public void createSegmentPartialData()
    {
        text.control.resetBySegmentPartialData(0);
    }
    //FOCUS PARTIAL SENTENCES
    public void createSentencePartialData()
    {
        text.control.resetBySentencePartialData(0);
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

  //EXECUTION
  public final void executeOtherModuleFirst(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOptionFirst(panelID, executeModuleID, optionNumber);
  }

  public final void executeOtherModule(int executeModuleID, int optionNumber) {
    text.connection.executeModuleWithOption(panelID, executeModuleID, optionNumber);
  }

  //DISPLAY
  public final void displayOtherModuleFirst(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOptionFirst(panelID, displayModuleID, optionNumber);
  }

  public final void displayOtherModule(int displayModuleID, int optionNumber) {
    text.connection.displayModuleWithOption(panelID, displayModuleID, optionNumber);
  }

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
    logFilename = text.absolutePath+"ACTIONLOG";
    try {
      BufferedWriter bw1 = new BufferedWriter(new FileWriter(logFilename, true));

      bw1.write(text.control.getDiffTime()+" Visualization PanelID="+panelID+" "+"ModuleID="+moduleID+" "+data+System.getProperty("line.separator"));
      bw1.close();
    } catch (Exception e) {
      System.out.println("File Writing ERROR ACTIONLOG");
    }
  }
}

