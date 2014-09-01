//
// Core Program for TETDM
// SettingPanel.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.Alignment;


public class SettingPanel extends JPanel implements ActionListener, ItemListener {
  JPanel allTopPanel;
  JPanel leftPanel;

  JPanel buttonPanel;
  JPanel buttonPanel1;
  JPanel buttonPanel2;


  //Top
  JLabel message;

  //Middle
  JScrollPane scroll[];

  JPanel checkBoxPanel1;
  JPanel pathPanel;
  JPanel keyPanel1;
  JPanel keySubPanel1;
  JPanel keySubPanel2;
  JPanel keyPanel2;
  JPanel keySubPanel3;
  JPanel keySubPanel4;
  JPanel keyPanel3;
  JPanel checkBoxPanel2;

  //checkBoxPanel1
  JLabel boxLabel[];
  JCheckBox setBoxA[];
  ButtonGroup checkGroup[];

  //pathPanel
  JLabel setFieldLabel[];
  JTextField setField[];

  //keyPanel1-3
  JLabel areaLabel[];
  JTextField textField[];
  JTextArea noisePane;
  String displayText[];	//display text

  JCheckBox pos[] = new JCheckBox[9];// part of speech
  JCheckBox period[] = new JCheckBox[3];//periods
  JCheckBox suna;//sunaribarafuto
  JCheckBox left[] = new JCheckBox[2]; //left tags
  ButtonGroup leftGroup;

  String posString[] = new String[9];
  String periodString[] = new String[3];
  String sunaString;


  //checkBoxPanel2
  JCheckBox setBoxB[];


  //Buttom
  JButton bt0 = new JButton();
  JButton bt10 = new JButton();
  JButton bt30 = new JButton();
  JButton bt40 = new JButton();


  int fontsize = 16;

  String menuInJapanese[];

  SettingData settingData;

  boolean autoSet;

  Control control;


  SettingPanel(SettingData settingD, Control control) {
    setLayout(new BorderLayout());

    settingData = settingD;
    this.control = control;

    menuInJapanese = FILEIO.TextFileAllReadCodeArray(new File(settingData.absolutePath + "source" + File.separator + "Japanese.txt"));

    //ALL
    allTopPanel = new JPanel();
    allTopPanel.setBackground(Color.blue);
    add(allTopPanel, BorderLayout.NORTH);

    message = new JLabel();
    message.setFont(new Font("Dialog", Font.BOLD, fontsize+2));
    message.setForeground(Color.white);

    setTitleNames();
    allTopPanel.add(message);

    //MIDDLE
    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(6,1));
    add(leftPanel);
    scroll = new JScrollPane[6];

    //MIDDLE-INSIDE
    createSetPanel();

    displayText = new String[5];
    setData();
    setText();
    initializeSetBox();






    //BOTTOM
    buttonPanel = new JPanel(new GridLayout(2,1));
    add(buttonPanel, BorderLayout.SOUTH);

    buttonPanel1 = new JPanel(new GridLayout());
    buttonPanel2 = new JPanel(new GridLayout());

    buttonPanel.add(buttonPanel1);
    buttonPanel.add(buttonPanel2);

    buttonPanel1.setBackground(Color.ORANGE);
    buttonPanel2.setBackground(Color.GREEN);


    bt30.addActionListener(this);
    bt30.setBackground(Color.ORANGE);
    buttonPanel1.add(bt30);

    bt0.addActionListener(this);
    bt0.setBackground(Color.GREEN);
    buttonPanel2.add(bt0);

    bt10.addActionListener(this);
    bt10.setBackground(Color.GREEN);
    buttonPanel2.add(bt10);

    bt40.addActionListener(this);
    bt40.setBackground(Color.GREEN);
    buttonPanel2.add(bt40);
  }

  public void createSetPanel() {
    //CheckBox
    boxLabel = new JLabel[4];
    setBoxA = new JCheckBox[8];
    setBoxB = new JCheckBox[5];
    checkGroup = new ButtonGroup[4];
    for (int i=0; i<4; i++) {
      boxLabel[i] = new JLabel();
      boxLabel[i].setFont(new Font("Dialog", Font.BOLD, fontsize));
      boxLabel[i].setBackground(Color.pink);
      checkGroup[i] = new ButtonGroup();
    }

    checkBoxPanel1 = new JPanel();
    checkBoxPanel1.setBackground(new Color(0x7f,0xff,0xd4));//green
    checkBoxPanel1.setLayout(new GridLayout(4,3,5,-2));


    for (int i=0; i<8; i++) {
      if (i%2 == 0) {
        checkBoxPanel1.add(boxLabel[(int)(i/2)]);
      }

      setBoxA[i] = new JCheckBox();
      setBoxA[i].addItemListener(this);
      setBoxA[i].setBackground(Color.pink);
      checkBoxPanel1.add(setBoxA[i]);

      checkGroup[(int)(i/2)].add(setBoxA[i]);
    }


    scroll[0] = new JScrollPane(checkBoxPanel1);
    leftPanel.add(scroll[0]);

    //PathPanel
    pathPanel = new JPanel();
    pathPanel.setBackground(new Color(0x7f,0xff,0xd4));//green
    pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.PAGE_AXIS));

    setField = new JTextField[2];
    setFieldLabel = new JLabel[2];

    setFieldLabel[0] = new JLabel();
    setFieldLabel[1] = new JLabel();
    setField[0] = new JTextField();
    setField[1] = new JTextField();

    setFieldLabel[0].setAlignmentX(0.0f);
    setField[0].setAlignmentX(0.0f);
    setFieldLabel[1].setAlignmentX(0.0f);
    setField[1].setAlignmentX(0.0f);

    pathPanel.add(setFieldLabel[0]);
    pathPanel.add(setField[0]);
    pathPanel.add(setFieldLabel[1]);
    pathPanel.add(setField[1]);

    scroll[1] = new JScrollPane(pathPanel);
    leftPanel.add(scroll[1]);

    //preparation for keypanels
    areaLabel = new JLabel[5];
    textField = new JTextField[5];  //[3]:unUsed

    for (int i=0; i<areaLabel.length; i++) {
      areaLabel[i] = new JLabel();
    }
    for (int i=0; i<textField.length; i++) {
      textField[i] = new JTextField();
    }

    for (int i=0; i<pos.length; i++) {
      pos[i] = new JCheckBox();
      posString[i] = settingData.kind_speech[i];
      pos[i].setText(posString[i]);
      pos[i].addItemListener(this);
      pos[i].setBackground(Color.white);
    }

    for (int i=0; i<period.length; i++) {
      period[i] = new JCheckBox();
      periodString[i] = menuInJapanese[28+i].split(" ")[0];
      period[i].setText(menuInJapanese[28+i]);
      period[i].addItemListener(this);
      period[i].setBackground(Color.white);
    }

    suna = new JCheckBox();
    sunaString = menuInJapanese[31];
    suna.setText(sunaString);
    suna.addItemListener(this);
    suna.setBackground(Color.white);

    for (int i=0; i<left.length; i++) {
      left[i] = new JCheckBox();
      left[i].setText(menuInJapanese[32+i]);
      left[i].addItemListener(this);
      left[i].setBackground(Color.white);
    }


    //KEYPANEL1
    keyPanel1 = new JPanel();
    keyPanel1.setBackground(new Color(0xff,0xff,0x66));//yellow
    keyPanel1.setLayout(new BoxLayout(keyPanel1, BoxLayout.PAGE_AXIS));

    keySubPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,-3));
    keySubPanel1.setBackground(new Color(0xff,0xff,0x66));//yellow

    keySubPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,-3));
    keySubPanel2.setBackground(new Color(0xff,0xff,0x66));//yellow






    keySubPanel1.setAlignmentX(0.0f);
    textField[0].setAlignmentX(0.0f);
    keySubPanel2.setAlignmentX(0.0f);
    textField[1].setAlignmentX(0.0f);

    keySubPanel1.add(areaLabel[0]);
    for (int i=0; i<6; i++) {
      keySubPanel1.add(pos[i]);
    }
    keySubPanel2.add(areaLabel[1]);
    for (int i=6; i<9; i++) {
      keySubPanel2.add(pos[i]);
    }



    keyPanel1.add(keySubPanel1);
    keyPanel1.add(textField[0]);
    keyPanel1.add(keySubPanel2);
    keyPanel1.add(textField[1]);


    scroll[2] = new JScrollPane(keyPanel1);
    leftPanel.add(scroll[2]);

    //KeyPanel2
    keyPanel2 = new JPanel();
    keyPanel2.setBackground(new Color(0xff,0xff,0x66));//yellow
    keyPanel2.setLayout(new BoxLayout(keyPanel2, BoxLayout.PAGE_AXIS));

    keySubPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,-3));
    keySubPanel3.setBackground(new Color(0xff,0xff,0x66));//yellow

    keySubPanel4 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,-3));
    keySubPanel4.setBackground(new Color(0xff,0xff,0x66));//yellow



//
    //	JCheckBox period[] = new JCheckBox[3];//periods
//		JCheckBox suna;//sunaribarafuto

    leftGroup = new ButtonGroup();
    leftGroup.add(left[0]);
    leftGroup.add(left[1]);

    keySubPanel3.setAlignmentX(0.0f);
    textField[2].setAlignmentX(0.0f);
    keySubPanel4.setAlignmentX(0.0f);
    textField[4].setAlignmentX(0.0f);

    keySubPanel3.add(areaLabel[2]);
    for (int i=0; i<3; i++) {
      keySubPanel3.add(period[i]);
    }
    keySubPanel4.add(areaLabel[4]);
    keySubPanel4.add(suna);
    keySubPanel4.add(left[0]);
    keySubPanel4.add(left[1]);

    keyPanel2.add(keySubPanel3);
    keyPanel2.add(textField[2]);
    keyPanel2.add(keySubPanel4);
    keyPanel2.add(textField[4]);


    scroll[3] = new JScrollPane(keyPanel2);
    leftPanel.add(scroll[3]);


    //KeyPanel3
    keyPanel3 = new JPanel();
    keyPanel3.setBackground(Color.pink);
    keyPanel3.setLayout(new BoxLayout(keyPanel3, BoxLayout.PAGE_AXIS));

    noisePane = new JTextArea();
    noisePane.setLineWrap(true);
    noisePane.setAlignmentX(0.0f);
    areaLabel[3].setAlignmentX(0.0f);

    keyPanel3.add(areaLabel[3]);
    keyPanel3.add(noisePane);


    scroll[4] = new JScrollPane(keyPanel3);
    leftPanel.add(scroll[4]);



    //BOX continue
    checkBoxPanel2 = new JPanel();
    checkBoxPanel2.setBackground(Color.pink);
    checkBoxPanel2.setLayout(new GridLayout(5,1,5,-2));

    for (int i=0; i<5; i++) {
      setBoxB[i] = new JCheckBox();
      setBoxB[i].addItemListener(this);
      setBoxB[i].setBackground(Color.white);
      checkBoxPanel2.add(setBoxB[i]);
    }

    scroll[5] = new JScrollPane(checkBoxPanel2);
    leftPanel.add(scroll[5]);


    //Names
    setNames();
  }

  public void setTitleNames() {
    if (settingData.inJapanese) {
      message.setText(menuInJapanese[18]);
    } else {
      message.setText("Keyword SETTINGS");
    }
  }



  public void setNames() {
    String boxLabelWords[] = {"Morpheme Analysis","OS","Input Text","Code for ChaSen"};

    String setWordsA[] = {"Igo","Chasen","Windows","Mac","Japanese Text","English Text","SJIS","EUC"};
    String setWordsB[] = {"Exclude HIRAGANA words", "Exclude KATAKANA words", "Exclude One character words","Exclude HANKAKU words","Menu in Japanese (Reconstruct panels)"};

    if (settingData.inJapanese) {

      setFieldLabel[0].setText(menuInJapanese[3]);
      setFieldLabel[1].setText(menuInJapanese[13]);

      for (int i=0; i<4; i++) {
        boxLabel[i].setText(menuInJapanese[24+i]);
      }

      for (int i=0; i<4; i++) {
        setBoxA[i].setText(menuInJapanese[4+i]);
      }
      for (int i=0; i<4; i++) {
        setBoxA[4+i].setText(menuInJapanese[20+i]);
      }
      for (int i=0; i<4; i++) {
        setBoxB[i].setText(menuInJapanese[8+i]);
      }
      setBoxB[4].setText(menuInJapanese[34]);

      bt0.setText(menuInJapanese[14]);
      bt10.setText(menuInJapanese[15]);
//			bt20.setText(menuInJapanese[16]);
      bt30.setText(menuInJapanese[17]);
      bt40.setText(menuInJapanese[19]);
    } else {

      setFieldLabel[0].setText("Morpheme PATH : Please PUSH Return key after modification");
      setFieldLabel[1].setText("Dictionary PATH : Please PUSH Return key after modification");

      for (int i=0; i<4; i++) {
        boxLabel[i].setText(boxLabelWords[i]);
      }

      for (int i=0; i<8; i++) {
        setBoxA[i].setText(setWordsA[i]);
      }
      for (int i=0; i<5; i++) {
        setBoxB[i].setText(setWordsB[i]);
      }

      bt0.setText("Load Original Settings");
      bt10.setText("Save Settings");
//			bt20.setText("Reconstruct Panels");
      bt30.setText("Reset TextData");
      bt40.setText("Quit Settings");
    }
    bt40.setForeground(Color.red);
  }

  public void initializeSetBox() {
    autoSet = true;

    setField[0].setText(settingData.chasenPath);
    setField[1].setText(settingData.dictionaryPath);

    for (int i=0; i<8; i++) {
      setBoxA[i].setFont(new Font("Dialog", Font.BOLD, fontsize));
    }
    for (int i=0; i<5; i++) {
      setBoxB[i].setFont(new Font("Dialog", Font.BOLD, fontsize));
    }


    if (settingData.morpheme.equals("Igo")) {
      setBoxA[0].setSelected(true);
    } else {
      setBoxA[1].setSelected(true);
    }

    setBoxA[2].setSelected(settingData.isWindows);
    setBoxA[2].setEnabled(false);
    setBoxA[3].setSelected(!settingData.isWindows);
    setBoxA[3].setEnabled(false);

    setBoxA[4].setSelected(!settingData.isEnglish);
    setBoxA[4].setEnabled(false);
    setBoxA[5].setSelected(settingData.isEnglish);
    setBoxA[5].setEnabled(false);

    setBoxA[6].setSelected(settingData.defaultCode.equals("SJIS"));
    setBoxA[6].setEnabled(false);
    setBoxA[7].setSelected(settingData.defaultCode.equals("EUC-JP"));
    setBoxA[7].setEnabled(false);

    setBoxB[0].setSelected(settingData.excludeHiragana);
    setBoxB[1].setSelected(settingData.excludeKatakana);
    setBoxB[2].setSelected(settingData.excludeOne);
    setBoxB[3].setSelected(settingData.inJapanese);
    setBoxB[4].setSelected(settingData.excludeHankaku);



    autoCheckKeywordBox();


    autoSet = false;
  }

  public void autoCheckKeywordBox() {
    autoSet = true;

    String setText1[] = textField[0].getText().split(" ");
    for (int i=0; i<6; i++) {
      pos[i].setSelected(false);
      for (int j=0; j<setText1.length; j++)
        if (setText1[j].equals(posString[i])) {
          pos[i].setSelected(true);
          break;
        }
    }

    String setText2[] = textField[1].getText().split(" ");
    for (int i=6; i<9; i++) {
      pos[i].setSelected(false);
      for (int j=0; j<setText2.length; j++)
        if (setText2[j].equals(posString[i])) {
          pos[i].setSelected(true);
          break;
        }
    }

    String setText3[] = textField[2].getText().split(" ");
    for (int i=0; i<3; i++) {
      period[i].setSelected(false);
      for (int j=0; j<setText3.length; j++)
        if (setText3[j].equals(periodString[i])) {
          period[i].setSelected(true);
          break;
        }
    }

    suna.setSelected(false);
    if (textField[4].getText().equals(sunaString)) {
      suna.setSelected(true);
    }

    if (settingData.leftTags == true) {
      left[0].setSelected(true);
    } else {
      left[1].setSelected(true);
    }

    autoSet = false;
  }


  public void setData() {
    if (settingData.inJapanese) {
      areaLabel[0].setText(settingData.guideComments[0]);
      areaLabel[1].setText(settingData.guideComments[1]);
      areaLabel[2].setText(settingData.guideComments[2]);
      areaLabel[3].setText(settingData.guideComments[3]);
      areaLabel[4].setText(settingData.guideComments[5]);
    } else {
      areaLabel[0].setText("##Keywords: No inflection");
      areaLabel[1].setText("##Keywords: With inflection");
      areaLabel[2].setText("##Periods: sentence delimiter");
      areaLabel[3].setText("##Stop words");
      areaLabel[4].setText("##Word: sengment delimiter");
    }

    for (int i=0; i<5; i++) {
      displayText[i] = "";
//            areaLabel[i].setFont(new Font("Dialog", Font.BOLD, fontsize));
    }


    for (int i=0; i<settingData.nouns; i++) {
      displayText[0] += (settingData.kind_nouns[i]+" ");
    }

    for (int i=0; i<settingData.verbs; i++) {
      displayText[1] += (settingData.kind_verbs[i]+" ");
    }

    for (int i=0; i<settingData.touns; i++) {
      displayText[2] += (settingData.kind_touns[i]+" ");
    }

    for (int i=0; i<settingData.noises; i++) {
      displayText[3] += (settingData.kind_noises[i]+" ");
    }

    displayText[4] = settingData.segment_tag;

  }


  public void setText() {
    noisePane.setFont(new Font("Dialog", Font.BOLD, fontsize));
    for (int i=0; i<5; i++) {
      textField[i].setFont(new Font("Dialog", Font.BOLD, fontsize));
      if (i!=3) {
        textField[i].setText(displayText[i]);
      } else {
        noisePane.setText(displayText[i]);
      }
    }
  }



  //Action listener
  public void actionPerformed(ActionEvent e) {
    // ReExecution
    if (e.getSource() == bt30) {
      updateTextSetting();

      control.textDataLoadingWithoutCopy();/////
      control.resetSelectpanelData(control.text);

      if (control.popForSupport != null) {
        control.popForSupport.support.setAnsweredFlagTrue(0,20);
      }
    }

    if (e.getSource() == bt0) { //Load Original Settings
      settingData.readOriginalSettingFile();
      setData();
      setText();
      initializeSetBox();



      if (settingData.inJapanese) {
        message.setText(menuInJapanese[1]);
      } else {
        message.setText("SETTINGS ARE LOADED");
      }

    }

    if (e.getSource() == bt10) { //Save Settings
      updateTextSetting();

      settingData.saveSettingFile();
      if (settingData.inJapanese) {
        message.setText(menuInJapanese[2]);
      } else {
        message.setText("SETTINGS ARE SAVED");
      }
    }

    // Quit Settings
    if (e.getSource() == bt40) {
      updateTextSetting();

      closeWindow();
    }
  }

  public void closeWindow() {
    control.bt0.setSelected(false);
    control.popForSetting.dispose();
  }

  public void updateTextSetting() {
    autoCheckKeywordBox();


    settingData.kind_nouns = textField[0].getText().split(" ");
    if (textField[0].getText().equals("")) {
      settingData.nouns = 0;
    } else {
      settingData.nouns = settingData.kind_nouns.length;
    }

    settingData.kind_verbs = textField[1].getText().split(" ");
    if (textField[1].getText().equals("")) {
      settingData.verbs = 0;
    } else {
      settingData.verbs = settingData.kind_verbs.length;
    }

    settingData.kind_touns = textField[2].getText().split(" ");
    if (textField[2].getText().equals("")) {
      settingData.touns = 0;
    } else {
      settingData.touns = settingData.kind_touns.length;
    }

    settingData.kind_noises = noisePane.getText().split(" ");
    if (noisePane.getText().equals("")) {
      settingData.noises = 0;
    } else {
      settingData.noises = settingData.kind_noises.length;
    }


    settingData.segment_tag = textField[4].getText();

    settingData.chasenPath = setField[0].getText();

    settingData.dictionaryPath = setField[1].getText();
  }


  void changeMorpheme() {
    settingData.chasenPath = "";
    settingData.chasenexe = true;

    //TEXT READ
    String original_file = control.original_file;		//Original text file
    String read_filename = control.original_file+"2";	//Copied text file

    if (settingData.morpheme.equals("Igo")) {
      setField[0].setText(settingData.absolutePath);
      settingData.chasenPath = settingData.absolutePath;
      EXECUTE.filecopy(original_file, read_filename, "SJIS");	//make copy, ALL procedures only for this copy
    } else {
      setField[0].setText(settingData.defaultChasenPath);
      settingData.chasenPath = settingData.defaultChasenPath;
      EXECUTE.filecopy(original_file, read_filename, settingData.defaultCode);	//make copy, ALL procedures only for this copy
    }
  }

  public void itemStateChanged(ItemEvent e) {
    String tempText;
    String currentText[];
    boolean find;

    if (autoSet == true) {
      return;
    }


    // Settings for TextData Creation
    for (int i=0; i<setBoxA.length; i++)
      if (e.getSource() == setBoxA[i]) {
        switch (i) {
          case 0:
            settingData.morpheme = "Igo";
            changeMorpheme();
            break;

          case 1:
            settingData.morpheme = "ChaSen";
            changeMorpheme();
            break;

          case 2:
          case 3:
          case 4:
          case 5:
            break;

          case 6:
            if (setBoxA[6].isSelected()) {
              settingData.defaultCode = "SJIS";
            }
            break;
          case 7:
            if (setBoxA[7].isSelected()) {
              settingData.defaultCode = "EUC-JP";
            }
            break;
        }

        return;
      }

    for (int i=0; i<setBoxB.length; i++)
      if (e.getSource() == setBoxB[i]) {
        switch (i) {
          case 0:
            settingData.excludeHiragana = setBoxB[0].isSelected();
            break;
          case 1:
            settingData.excludeKatakana = setBoxB[1].isSelected();
            break;
          case 2:
            settingData.excludeOne = setBoxB[2].isSelected();
            break;
          case 3:
            settingData.excludeHankaku = setBoxB[3].isSelected();
            break;
          case 4:
            settingData.inJapanese = setBoxB[4].isSelected();
            control.panelsReConstruction();
            control.setButtonLabel();					// for English Menu


            if (settingData.inJapanese) {
              message.setText(menuInJapanese[0]);
            } else {
              message.setText("Keyword SETTINGS");
            }

            setTitleNames();

            setNames();

            setData();

            break;
        }

        return;
      }

    for (int i=0; i<6; i++)
      if (e.getSource() == pos[i]) {
        tempText= "";
        for (int j=0; j<6; j++)
          if (pos[j].isSelected()) {
            tempText += (posString[j]+" ");
          }

        currentText = textField[0].getText().split(" ");
        for (int j=0; j<currentText.length; j++) {
          find = false;
          for (int k=0; k<6; k++)
            if (currentText[j].equals(posString[k])) {
              find = true;
            }

          if (find == false) {
            tempText += (currentText[j]+" ");
          }
        }
        textField[0].setText(tempText);
      }


    for (int i=6; i<9; i++)
      if (e.getSource() == pos[i]) {
        tempText= "";
        for (int j=6; j<9; j++)
          if (pos[j].isSelected()) {
            tempText += (posString[j]+" ");
          }

        currentText = textField[1].getText().split(" ");
        for (int j=0; j<currentText.length; j++) {
          find = false;
          for (int k=6; k<9; k++)
            if (currentText[j].equals(posString[k])) {
              find = true;
            }

          if (find == false) {
            tempText += (currentText[j]+" ");
          }
        }
        textField[1].setText(tempText);
      }



    for (int i=0; i<3; i++)
      if (e.getSource() == period[i]) {
        tempText= "";
        for (int j=0; j<3; j++)
          if (period[j].isSelected()) {
            tempText += (periodString[j]+" ");
          }

        currentText = textField[2].getText().split(" ");
        for (int j=0; j<currentText.length; j++) {
          find = false;
          for (int k=0; k<3; k++)
            if (currentText[j].equals(periodString[k])) {
              find = true;
            }

          if (find == false) {
            tempText += (currentText[j]+" ");
          }
        }
        textField[2].setText(tempText);
      }

    if (e.getSource() == suna) {
      if (suna.isSelected()) {
        textField[4].setText(menuInJapanese[31]);
      } else {
        textField[4].setText("");
      }
    }

    if (e.getSource() == left[0]) {
      if (left[0].isSelected()) {
        settingData.leftTags = true;
      }
      //System.out.println("TRUE");
    }

    if (e.getSource() == left[1]) {
      if (left[1].isSelected()) {
        settingData.leftTags = false;
      }
      //System.out.println("FALSE");
    }

  }
}
