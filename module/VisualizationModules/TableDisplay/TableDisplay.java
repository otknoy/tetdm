//
// Visuzalization module for TETDM
// TableDisplay.java Version 0.30
// Copyright(C) 2008 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.TableDisplay;

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
import javax.swing.table.*;


public class TableDisplay extends VisualizationModule {
  JScrollPane scrollt1;
  JViewport view;

  DefaultTableModel tablemodel1;
  JTable table1;
  JTableHeader th;

  String column_names1[];
  int column_num;

  double table_data[][];

  int sorted_index[];
  double values[];

  String inJapanese[];

  public TableDisplay() {
    setModuleID(1009);
    setToolType(1);
  }

  public void initializePanel() {
    scrollt1 = new JScrollPane();
    view = scrollt1.getViewport();
    add(scrollt1);
  }

  public void initializeData() {
    inJapanese = fileReadArray();
    String names[] = {"Word","Speech","Freq","SenFreq","SegFreq","SubFreq"};

    if (isMenuInJapanese()) {
      column_names1 = inJapanese;
    } else {
      column_names1 = names;
    }

    column_num = column_names1.length;
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        if (scrollt1 == null) {
          scrollt1 = new JScrollPane();		//::::::Creating panel
          view = scrollt1.getViewport();
          add(scrollt1);
        }
        tablemodel1 = new DefaultTableModel(column_names1, text.keywordNumber);
        table1 = new JTable(tablemodel1);
        view.setView(table1);

        th = table1.getTableHeader();
        th.addMouseListener(listMouseListener);

        sorted_index = new int[text.keywordNumber];
        values = new double[text.keywordNumber];

        for (int i=0; i<text.keywordNumber; i++) {
          sorted_index[i] = i;
        }

        make_contents();
        break;
    }
  }

  public String print_double(double num) {
    DecimalFormat df = new DecimalFormat();
    df.applyLocalizedPattern("#####.##");

    return df.format(num);
  }

  public String print_pattern(double num, String pat) {
    DecimalFormat df = new DecimalFormat();
    df.applyLocalizedPattern(pat);

    return df.format(num);
  }

  public void make_contents() {
    table_data = new double[column_num][text.keywordNumber];
    for (int i=0; i<text.keywordNumber; i++) {
      table_data[1][i] = text.keyword[i].partOfSpeech;
      table_data[2][i] = text.keyword[i].frequency;
      table_data[3][i] = text.keyword[i].sentenceFrequency;
      table_data[4][i] = text.keyword[i].segmentFrequency;
      table_data[5][i] = text.keyword[i].frequencyAsSubject;
    }

    for (int i=0; i<text.keywordNumber; i++) {
      table1.setValueAt(text.keyword[sorted_index[i]].word,i,0);
      for (int j=1; j<column_num; j++) {
        table1.setValueAt(print_pattern(table_data[j][sorted_index[i]], "#####"),i,j);
      }
//			table1.setValueAt(print_double(text.keyword[sorted_index[i]].score[0]),i,5);
    }
  }

  public void sort_column(int n) {
//		System.out.println("n="+n+" keywordNumber = "+text.keywordNumber);

    if (n == 0)
      for (int i=0; i<text.keywordNumber; i++) {
        sorted_index[i] = i;
      }
    else {
      values = table_data[n];

      Qsort.initializeIndex(sorted_index, text.keywordNumber);
      Qsort.quicksort(values, sorted_index, text.keywordNumber);
    }

    make_contents();

    TableColumnModel column = table1.getColumnModel();
    DefaultTableCellRenderer dtr = new DefaultTableCellRenderer();
    dtr.setBackground(Color.yellow);
    for (int i=0; i<column_num; i++) {
      TableColumn tc = column.getColumn(i);
      if (i == n) {
        tc.setHeaderRenderer(dtr);
      } else {
        tc.setHeaderRenderer(null);
      }
    }
    th.repaint();
  }

  MouseAdapter listMouseListener = new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
      TableColumnModel column = table1.getColumnModel();
      int cn = column.getColumnIndexAtX(e.getX());
//			int cn2 = table1.convertColumnIndexToModel(cn);

      if (e.getClickCount() == 1 && cn != -1) {
        sort_column(cn);
      }
    }
  };
}
