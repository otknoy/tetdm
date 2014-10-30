//
// Visuzalization module for TETDM
// TagCloudView.java Version 0.30
// Copyright(C) 2011 YASUFUMI TAKAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.TagCloudView;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class TagCloudView extends VisualizationModule {
  JEditorPane text1;
  JScrollPane scroll1;

  String display_text;
  String[] sentences;
  HashMap<String,Integer> keyphrases;
  String[] words;//////////////////
  int[] freqs;
  StyleSheet sheet;
  HTMLEditorKit htmlEditorKit;

  public TagCloudView() {
    setModuleID(14);
    dataNumbers = new int[] {0,0,0,0,   // b,i,d,S
                             0,1,0,2,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
  }

  public void initializePanel() {
    sheet = new StyleSheet();
    sheet.addRule(".tgc .lv1 {font-size:100%; color:#897c9d;}");
    sheet.addRule(".tgc .lv2 {font-size:150%; color:#a57c9d;}");
    sheet.addRule(".tgc .lv3 {font-size:250%; color:#e771b4; font-weight:bold;}");
    sheet.addRule(".tgc .lv4 {font-size:400%; color:#db00aa; font-weight:bold;}");
    text1 = new JEditorPane();
    text1.setContentType("text/html");
    htmlEditorKit = new HTMLEditorKit();
    htmlEditorKit.setStyleSheet(sheet);
    text1.setEditorKit(htmlEditorKit);
    scroll1 = new JScrollPane(text1);
    add(scroll1);
  }

  public void initializeData() {}

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        display_text = createDisplayText();
        text1.setText(display_text);
        text1.setCaretPosition(0);
        break;
    }
  }


  public String createDisplayText() {
    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    try {

      bw.write("<html><head></head><body>\n");
      if (keyphrases!=null) {
        for (int i=0; i<sentences.length; i++) {
          bw.write("<div class=\"tgc\">\n");
          bw.write(i+": ");
          for (String st: sentences[i].split("\t")) {
            bw.write(get_tag(st));
          }
          bw.write("</div>\n");
        }
      }
      bw.write("</body></html>\n");

      bw.flush();
    } catch (Exception e) {
      System.out.println("writing ERROR in TagCloudView");
    }
    return sw.toString();
  }

  /* for keyphrases or sentences */
  public boolean setData(int dataID, String[] t) {
    switch (dataID) {
      case 0:// keyphrases
        words = new String[t.length];
        keyphrases = new HashMap<String,Integer>();
        for (int i=0; i<t.length; i++) {
          keyphrases.put(t[i],i);
          words[i] = t[i];
        }
        return true;

      case 2:// sentences
        sentences = new String[t.length];
        for (int i=0; i<t.length; i++) {
          sentences[i] = t[i];
        }
        return true;
    }
    return false;
  }

  /* for frequencies of keyphrases */
  public boolean setData(int dataID, int[] v) {
    switch (dataID) {
      case 1:
        freqs = new int[v.length];
        for (int i=0; i<v.length; i++) {
          freqs[i] = v[i];
        }
        return true;
    }
    return false;
  }

  String get_tag(String wd) {
    if (wd.length()==0) {
      return "";
    }
//		int i = keyphrases.get(wd);
    int i;
    for (i=0; i<words.length; i++)
      if (wd.equals(words[i])) {
        break;
      }

    String str ="<span class=\"lv"+calc_lv(freqs[i])+"\">  "+wd+"  </span>";
    return str;
  }

  String calc_lv(int f) {
    if (f>=10) {
      return "4";
    } else if (f>=5) {
      return "3";
    } else if (f>=2) {
      return "2";
    } else {
      return "1";
    }
  }
}
