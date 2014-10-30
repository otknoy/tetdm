//
// Core Program for TETDM
// SetToolPanel.java Version 0.44
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.regex.*;


public class SetToolPanel extends JPanel {
  Control control;

  String moduleNames[];
  String moduleNamesInJapanese[];
  String visuModuleNames[];
  String visuModuleNamesInJapanese[];

  int moduleNumber;
  int visuModuleNumber;

  String source[];

  String focusWords[];
  String focusVariables[];
  String focusVariables2[];
  String focusFunctions[];
  String focusFunctions2[];

  String optionRequests[];
  String optionRequests2[];



  String dataRequest[];

  class ModuleInformation {
    class CheckedModule {
      boolean focusWordsExist[];
      boolean focusVariablesExist[];
      boolean focusVariables2Exist[];
      boolean focusFunctionsExist[];
      boolean focusFunctions2Exist[];

      boolean optionRequestsExist[];
      boolean optionRequests2Exist[];
      String optionRequestNumbers[];
      String optionRequestNumbers2[];

      boolean dataRequestExist[];
      String dataRequestNumbers[];

      CheckedModule() {
        focusWordsExist = new boolean[focusWords.length];
        focusVariablesExist = new boolean[focusVariables.length];
        focusVariables2Exist = new boolean[focusVariables2.length];
        focusFunctionsExist = new boolean[focusFunctions.length];
        focusFunctions2Exist = new boolean[focusFunctions2.length];

        optionRequestsExist = new boolean[optionRequests.length];
        optionRequests2Exist = new boolean[optionRequests2.length];

        dataRequestExist = new boolean[dataRequest.length];

        for (int i=0; i<focusWordsExist.length; i++) {
          focusWordsExist[i] = false;
        }
        for (int i=0; i<focusVariablesExist.length; i++) {
          focusVariablesExist[i] = false;
        }
        for (int i=0; i<focusVariables2Exist.length; i++) {
          focusVariables2Exist[i] = false;
        }
        for (int i=0; i<focusFunctionsExist.length; i++) {
          focusFunctionsExist[i] = false;
        }
        for (int i=0; i<focusFunctions2Exist.length; i++) {
          focusFunctions2Exist[i] = false;
        }

        for (int i=0; i<optionRequestsExist.length; i++) {
          optionRequestsExist[i] = false;
        }
        for (int i=0; i<optionRequests2Exist.length; i++) {
          optionRequests2Exist[i] = false;
        }

        for (int i=0; i<dataRequestExist.length; i++) {
          dataRequestExist[i] = false;
        }
      }
    }

    CheckedModule moduleChecked[];
    CheckedModule visuModuleChecked[];

    ModuleInformation() {
      moduleChecked = new CheckedModule[moduleNumber];
      visuModuleChecked = new CheckedModule[visuModuleNumber];

      for (int i=0; i<moduleNumber; i++) {
        moduleChecked[i] = new CheckedModule();
      }

      for (int i=0; i<visuModuleNumber; i++) {
        visuModuleChecked[i] = new CheckedModule();
      }
    }
  }

  ModuleInformation moduleInformation;

  SetToolPanel(Control control) {
    this.control = control;
    setLayout(new BorderLayout());

    moduleNames = control.moduleData.module_names;
    moduleNamesInJapanese = control.moduleData.miningModuleNamesInJapanese;
    moduleNumber = moduleNames.length;

    visuModuleNames = control.moduleData.visu_module_names;
    visuModuleNamesInJapanese = control.moduleData.visualizationModuleNamesInJapanese;
    visuModuleNumber = visuModuleNames.length;

    focusWords = new String[] {"focusKeywords","focusSegments","focusSentences",
                               "mainFocusKeyword","subFocusKeyword","mainFocusSegment","subFocusSegment","mainFocusSentence","subFocusSentence",
                               "MainFocusString","SubFocusString","MainFocusDouble","SubFocusDouble","MainFocusInteger","SubFocusInteger"
                              };
    focusVariables = new String[] {"focusTouchExecute","focusClickExecute"};
    focusVariables2 = new String[] {"4501","4502"};
    focusFunctions = new String[] {"repaintOthersByTouch(","repaintOthersByClick("};
    focusFunctions2 = new String[] {"executeAllByTouch","executeAllByClick","executeOthersByTouch","executeOthersByClick"};

    optionRequests = new String[] {"displayOtherModule(","displayOtherModuleFirst(","displayModule(","displayModuleFirst(",};
    optionRequests2 = new String[] {"executeOtherModule(","executeOtherModuleFirst(","executeModule(","executeModuleFirst("};

    dataRequest = new String[] {"getData","getNewData"};


    moduleInformation = new ModuleInformation();        ////////

    for (int i=0; i<moduleNumber; i++) {
      System.out.println(control.absolutePath+"module"+File.separator+"MiningModules"+File.separator+moduleNames[i]+File.separator+moduleNames[i]+".java");
    }

    for (int i=0; i<visuModuleNumber; i++) {
      System.out.println(control.absolutePath+"module"+File.separator+"VisualizationModules"+File.separator+visuModuleNames[i]+File.separator+visuModuleNames[i]+".java");
    }



    for (int i=0; i<moduleNumber; i++) {
      String filename = control.absolutePath+"module"+File.separator+"MiningModules"+File.separator+moduleNames[i]+File.separator+moduleNames[i]+".java";

      source = FILEIO.TextFileAllReadCodeArray(new File(filename));
      //focus variables
      for (int j=0; j<focusWords.length; j++) {
        moduleInformation.moduleChecked[i].focusWordsExist[j] = extractVariable(focusWords[j]);
      }
      //focus activate
      for (int j=0; j<focusVariables.length; j++) {
        moduleInformation.moduleChecked[i].focusVariablesExist[j] = extractLine(focusVariables[j]);
      }
      //option visual
      for (int j=0; j<optionRequests.length; j++) {
        moduleInformation.moduleChecked[i].optionRequestsExist[j] = extractCalledFunction(optionRequests[j]);
      }
      moduleInformation.moduleChecked[i].optionRequestNumbers = extractIDinCalledFunctions(optionRequests);
      //option mining
      for (int j=0; j<optionRequests2.length; j++) {
        moduleInformation.moduleChecked[i].optionRequests2Exist[j] = extractCalledFunction(optionRequests2[j]);
      }
      moduleInformation.moduleChecked[i].optionRequestNumbers2 = extractIDinCalledFunctions(optionRequests2);

      //data requests
      for (int j=0; j<dataRequest.length; j++) {
        moduleInformation.moduleChecked[i].dataRequestExist[j] = extractCalledFunction(dataRequest[j]);
      }
      moduleInformation.moduleChecked[i].dataRequestNumbers = extractIDinCalledFunctions(dataRequest);
    }

    for (int i=0; i<visuModuleNumber; i++) {
      String filename = control.absolutePath+"module"+File.separator+"VisualizationModules"+File.separator+visuModuleNames[i]+File.separator+visuModuleNames[i]+".java";

      source = FILEIO.TextFileAllReadCodeArray(new File(filename));
      //focus variables
      for (int j=0; j<focusWords.length; j++) {
        moduleInformation.visuModuleChecked[i].focusWordsExist[j] = extractVariable(focusWords[j]);
      }
      //focus activate
      for (int j=0; j<focusVariables2.length; j++) {
        moduleInformation.visuModuleChecked[i].focusVariables2Exist[j] = extractVariable(focusVariables2[j]);
      }
      //focus visual call
      for (int j=0; j<focusFunctions.length; j++) {
        moduleInformation.visuModuleChecked[i].focusFunctionsExist[j] = extractCalledFunction(focusFunctions[j]);
      }
      //focus mining call
      for (int j=0; j<focusFunctions2.length; j++) {
        moduleInformation.visuModuleChecked[i].focusFunctions2Exist[j] = extractCalledFunction(focusFunctions2[j]);
      }
      //option visual
      for (int j=0; j<optionRequests.length; j++) {
        moduleInformation.visuModuleChecked[i].optionRequestsExist[j] = extractCalledFunction(optionRequests[j]);
      }
      moduleInformation.visuModuleChecked[i].optionRequestNumbers = extractIDinCalledFunctions(optionRequests);
      //option mining
      for (int j=0; j<optionRequests2.length; j++) {
        moduleInformation.visuModuleChecked[i].optionRequests2Exist[j] = extractCalledFunction(optionRequests2[j]);
      }
      moduleInformation.visuModuleChecked[i].optionRequestNumbers2 = extractIDinCalledFunctions(optionRequests2);
    }

  }

  //////////DRAWING
  void draw_background(Graphics2D g2) {
    g2.setColor(new Color(0,128,255));
    g2.fillRect(0,0, getWidth(), getHeight());
  }

  String ox(boolean check) {
    if (check) {
      return "o";
    }
    return "_";
  }

  String ox(boolean check, String word, int j, Graphics2D g2, Color color) {
    g2.setColor(color);
    if (check) {
      return word.substring(j,j+1);
    }
    return "_";
  }

  ////Dispaly names
  public void draw_name(Graphics2D g2) {
    int topMargin = 50;
    int lineFeed = 25;

    for (int i=0; i<moduleNumber; i++) {
      g2.setFont(new Font("Dialog", Font.BOLD, 10));
      g2.setColor(Color.white);
      g2.drawString(control.moduleData.moduleIDs[i]+" : "+moduleNamesInJapanese[i], 100, lineFeed*i+topMargin);
      for (int j=0; j<focusWords.length; j++) {
        g2.drawString(ox(moduleInformation.moduleChecked[i].focusWordsExist[j],"KGSkkggssSSDDII",j,g2,Color.green), 100+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<focusVariables.length; j++) {
        g2.drawString(ox(moduleInformation.moduleChecked[i].focusVariablesExist[j],"tc",j,g2,Color.pink), 230+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<optionRequests.length; j++) {
        g2.drawString(ox(moduleInformation.moduleChecked[i].optionRequestsExist[j],"dddd",j,g2,Color.cyan), 300+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<optionRequests2.length; j++) {
        g2.drawString(ox(moduleInformation.moduleChecked[i].optionRequests2Exist[j],"eeee",j,g2,Color.pink), 350+8*j, lineFeed*i+topMargin+10);
      }
      g2.setColor(Color.yellow);
      for (int j=0; j<dataRequest.length; j++) {
        g2.drawString(ox(moduleInformation.moduleChecked[i].dataRequestExist[j]), 400+8*j, lineFeed*i+topMargin+10);
      }

      g2.setFont(new Font("Dialog", Font.BOLD, 8));
      g2.setColor(Color.cyan);
      for (int j=0; j<moduleInformation.moduleChecked[i].optionRequestNumbers.length; j++) {
        g2.drawString(moduleInformation.moduleChecked[i].optionRequestNumbers[j], 200+16*j, lineFeed*i+topMargin);
      }
      g2.setColor(Color.pink);
      for (int j=0; j<moduleInformation.moduleChecked[i].optionRequestNumbers2.length; j++) {
        g2.drawString(moduleInformation.moduleChecked[i].optionRequestNumbers2[j], 250+16*j, lineFeed*i+topMargin);
      }
      g2.setColor(Color.yellow);
      for (int j=0; j<moduleInformation.moduleChecked[i].dataRequestNumbers.length; j++) {
        g2.drawString(moduleInformation.moduleChecked[i].dataRequestNumbers[j], 300+16*j, lineFeed*i+topMargin);
      }

    }
    for (int i=0; i<visuModuleNumber; i++) {
      g2.setFont(new Font("Dialog", Font.BOLD, 10));
      g2.setColor(Color.white);
      g2.drawString(control.moduleData.visuModuleIDs[i]+" : "+visuModuleNamesInJapanese[i], 500, lineFeed*i+topMargin);
      for (int j=0; j<focusWords.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].focusWordsExist[j],"KGSkkggssSSDDII",j,g2,Color.green), 500+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<focusVariables2.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].focusVariables2Exist[j],"tc",j,g2,Color.blue), 630+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<focusFunctions.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].focusFunctionsExist[j],"tc",j,g2,Color.cyan), 650+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<focusFunctions2.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].focusFunctions2Exist[j],"tctc",j,g2,Color.pink), 670+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<optionRequests.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].optionRequestsExist[j],"dddd",j,g2,Color.cyan), 720+8*j, lineFeed*i+topMargin+10);
      }
      for (int j=0; j<optionRequests2.length; j++) {
        g2.drawString(ox(moduleInformation.visuModuleChecked[i].optionRequests2Exist[j],"eeee",j,g2,Color.pink), 770+8*j, lineFeed*i+topMargin+10);
      }

      g2.setFont(new Font("Dialog", Font.BOLD, 8));
      g2.setColor(Color.cyan);
      for (int j=0; j<moduleInformation.visuModuleChecked[i].optionRequestNumbers.length; j++) {
        g2.drawString(moduleInformation.visuModuleChecked[i].optionRequestNumbers[j], 600+16*j, lineFeed*i+topMargin);
      }
      g2.setColor(Color.pink);
      for (int j=0; j<moduleInformation.visuModuleChecked[i].optionRequestNumbers2.length; j++) {
        g2.drawString(moduleInformation.visuModuleChecked[i].optionRequestNumbers2[j], 750+16*j, lineFeed*i+topMargin);
      }
    }
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    draw_background(g2);	//Set background
    draw_name(g2);
  }

  public void update(Graphics g) {	//Avoid from blinking
    paintComponent(g);
  }

  boolean extractVariable(String variable) {
    for (int i=0; i<source.length; i++)
      if (source[i].indexOf(variable) != -1) {
        return true;
      }

    return false;
  }

  boolean extractLine(String variable) {
    for (int i=0; i<source.length; i++)
      if (source[i].indexOf(variable) != -1 && source[i].indexOf("true") != -1) {
        return true;
      }

    return false;
  }

  boolean extractCalledFunction(String functionName) {
    for (int i=0; i<source.length; i++)
      if (source[i].indexOf(functionName) != -1) {
        return true;
      }

    return false;
  }

  Pattern pattern = Pattern.compile("([0-9]+)");		//Number

  String extractIDinCalledFunction(String functionName) {
    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    try {
      for (int i=0; i<source.length; i++)
        if (source[i].indexOf(functionName) != -1) {
          Matcher matcher = pattern.matcher(source[i]);
          if (matcher.find()) {
            bw.write(matcher.group(1)+" ");
          }
        }
      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in SetToolPanel");
    }

    return sw.toString();
  }

  String[] extractIDinCalledFunctions(String functionNames[]) {
    String out = "";

    for (int i=0; i<functionNames.length; i++) {
      out += extractIDinCalledFunction(functionNames[i]);
    }

    return out.split(" ");
  }

}
//    int newPanelNumber = 0;
/*
 newPanelNumber = 2;

 control.miningIDs = new int[newPanelNumber];
 control.visualizationIDs = new int[newPanelNumber];

 control.miningIDs[0] = 12;
 control.visualizationIDs[0] = 4;
 control.miningIDs[1] = 19;
 control.visualizationIDs[1] = 3;

 control.panelSet();
 */
