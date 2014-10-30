//
// Visuzalization module for TETDM
// SentenceSelect.java Version 0.45
// Copyright(C) 2012-2013 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.KeywordSelect;

import source.*;
import source.TextData.*;
import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class FocusCheckBox extends VisualizationModule implements ItemListener, ActionListener {
  int checkWords[];
  int checkedNumber;

  public int lineNumber;
  public boolean focusSets[];

  public boolean setting;

  public CheckBoxPanel checkBoxPanel[];
  public int boxNumber;
  public String inJapanese[];

  public JButton resetButton;
  public JToggleButton valueButton;
  public JButton setButton1, setButton2;
  JPanel buttonPanel;
  JPanel tablePanel;

  public boolean addSetButton;
  public boolean displayValues;
  public boolean executeAfterCheck;

  public int maxLength=0;
  public FocusCheckBox() {}
  public void initializeData() {}
  public String[] createWords() {
    String words[] = new String[0];

    return words;
  }
  public void createInitialBox() {}

  public void initializePanel() {
    inJapanese = fileReadArray();

    if (isMenuInJapanese()) {
      resetButton = new JButton(inJapanese[0]);
      valueButton = new JToggleButton(inJapanese[1]);
      setButton1 = new JButton(inJapanese[2]);
      setButton2 = new JButton(inJapanese[3]);
    } else {
      resetButton = new JButton("Reset Check");
      valueButton = new JToggleButton("Value");
      setButton1 = new JButton("Part-"+inJapanese[6]);
      setButton2 = new JButton("Part-"+inJapanese[7]);
    }

    resetButton.addActionListener(this);
    resetButton.setBackground(Color.yellow);

    valueButton.addActionListener(this);
    valueButton.setBackground(Color.yellow);

    setButton1.addActionListener(this);
    setButton1.setBackground(Color.yellow);
    setButton2.addActionListener(this);
    setButton2.setBackground(Color.yellow);
  }


  public class CheckBoxPanel extends JPanel {
    JLabel label;
    JPanel select;
    JScrollPane check;
    public JCheckBox ch[];

    double values[];
    String words[];
    String setWords[];
    int maxLength;

    int sortedIndex[];
    int sortedIndexBak[];

    CheckBoxPanel() {
      setLayout(new BorderLayout());

      label = new JLabel();
      add(label, BorderLayout.NORTH);

      select = new JPanel();
      check = new JScrollPane(select);

      select.setBackground(Color.white);
      select.setLayout(new BoxLayout(select, BoxLayout.Y_AXIS));

      add(check);

      values = new double[lineNumber];
    }

    public void setValue(double value[]) {
      for (int i=0; i<lineNumber; i++) {
        values[i] = value[i];
      }
    }

    public void setWords(String word[], int max) {
      words = word;
      maxLength = max;
      setWords = new String[lineNumber];
    }

    void createSetWords() {
      if (maxLength < 0)
        for (int i=0; i<lineNumber; i++) {
          setWords[i] = words[sortedIndex[i]];
        }
      else
        for (int i=0; i<lineNumber; i++) {
          setWords[i] = words[sortedIndex[i]].substring(0,Math.min(maxLength,words[sortedIndex[i]].length()));
        }
    }

    public void setLabel(String labelText) {
      label.setText(labelText);
    }

    void initialize(ItemListener item) {
      ch = new JCheckBox[lineNumber];
      sortedIndex = new int[lineNumber];
      sortedIndexBak = new int[lineNumber];

      createCheckBox(item);
    }

    void createCheckBox(ItemListener item) {
      Qsort.initializeIndex(sortedIndex, lineNumber);
      Qsort.quicksort(values, sortedIndex, lineNumber);

      createSetWords();

      for (int i=0; i<lineNumber; i++) {
        if (displayValues) {
          ch[i] = new JCheckBox(((int)(values[i]))+"("+sortedIndex[i]+") : "+setWords[i]);
        } else {
          ch[i] = new JCheckBox(setWords[i]);
        }
        ch[i].addItemListener(item);
        ch[i].setBackground(Color.white);

        if (values[i] > 0) {
          select.add(ch[i]);
        }

        sortedIndexBak[sortedIndex[i]] = i;
      }
    }

    public void changeLabel() {
      for (int i=0; i<lineNumber; i++) {
        if (displayValues) {
          ch[i].setText(((int)(values[i]))+"("+sortedIndex[i]+") : "+setWords[i]);
        } else {
          ch[i].setText(setWords[i]);
        }
      }
    }

    void findCheck() {
      checkedNumber = 0;
      for (int i=0; i<lineNumber; i++)
        if (ch[i].isSelected() == true) {
          checkWords[checkedNumber++] = sortedIndex[i];
        }
    }

    void setCheck() {
      setting = true;
      for (int i=0; i<lineNumber; i++) {
        ch[i].setSelected(false);
      }

      for (int i=0; i<checkedNumber; i++) {
        ch[sortedIndexBak[checkWords[i]]].setSelected(true);
      }
      setting = false;
    }
  }

  public void initBoxData() {
    removeAll();


    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1,3));
    buttonPanel.setBackground(Color.yellow);
    buttonPanel.add(resetButton);
    /**/
    buttonPanel.add(valueButton);
    if (addSetButton) {
      buttonPanel.add(setButton1);
      buttonPanel.add(setButton2);
    }
    add(buttonPanel, BorderLayout.NORTH);

    tablePanel = new JPanel();
    add(tablePanel);

    tablePanel.setLayout(new GridLayout(1,boxNumber));
    checkBoxPanel = new CheckBoxPanel[boxNumber];

    for (int i=0; i<boxNumber; i++) {
      checkBoxPanel[i] = new CheckBoxPanel();
      tablePanel.add(checkBoxPanel[i]);
    }

    checkWords = new int[lineNumber];

    revalidate();
  }

  public void initBox2() {
    for (int i=0; i<boxNumber; i++) {
      checkBoxPanel[i].initialize((ItemListener)this);
    }

    checkedNumber = 0;
    for (int i=0; i<lineNumber; i++)
      if (focusSets[i]) {
        checkWords[checkedNumber++] = i;
      }

    for (int j=0; j<boxNumber; j++) {
      checkBoxPanel[j].setCheck();
    }
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        initBox2();
        break;
    }
  }

  public boolean setData(int dataID, int data) {
    switch (dataID) {
      case 77:
        boxNumber = data;
        initBoxData();
        return true;
    }
    return false;
  }

  public boolean setData(int dataID, double data[]) {
    switch (dataID) {
      default:
        if (dataID < 10 || dataID > 20) {
          break;
        }
        checkBoxPanel[dataID-10].setValue(data);
        checkBoxPanel[dataID-10].setWords(createWords(), maxLength);
        return true;
    }
    return false;
  }

  public boolean setData(int dataID, String data) {
    switch (dataID) {
      default:
        if (dataID < 10 || dataID > 20) {
          break;
        }
        checkBoxPanel[dataID-10].setLabel(data);
        return true;
    }
    return false;
  }

  public void itemStateChanged(ItemEvent e) {
    int boxID;

    if (setting == false) {
      for (int i=0; i<lineNumber; i++) {
        for (boxID = 0; boxID < boxNumber; boxID++)
          if (e.getItemSelectable() == checkBoxPanel[boxID].ch[i]) {
            checkBoxPanel[boxID].findCheck();
            break;
          }

        if (boxID < boxNumber)
          for (int j=0; j<boxNumber; j++)
            if (j != boxID) {
              checkBoxPanel[j].setCheck();
            }
      }

      for (int i=0; i<lineNumber; i++) {
        focusSets[i] = false;
      }
      for (int i=0; i<checkedNumber; i++) {
        focusSets[checkWords[i]] = true;
      }

      if (executeAfterCheck) {
        executeOthersByClick();
        repaintOthersByClick();
      }
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      setting = true;
      for (int i=0; i<lineNumber; i++)
        for (int j=0; j<boxNumber; j++) {
          checkBoxPanel[j].ch[i].setSelected(false);
        }
      setting = false;
      for (int i=0; i<lineNumber; i++) {
        focusSets[i] = false;
      }
      executeOthersByClick();
      repaintOthersByClick();
    }

    if (e.getSource() == valueButton) {
      if (valueButton.isSelected()) {
        displayValues = true;
      } else {
        displayValues = false;
      }
      for (int i=0; i<boxNumber; i++) {
        checkBoxPanel[i].changeLabel();
      }
    }
  }
}
