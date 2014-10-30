//
// Mining module for TETDM
// TweetExtraction.java Version 0.31
// Copyright(C) 2011 Yoko Nishihara All RIGHTS RESERVED.
// This program is provided under the license.
// You Should read the README file.
//

package module.MiningModules.TweetExtraction;		// Replace ALL [MiningStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TweetExtraction extends MiningModule implements ActionListener {
  JTextField textfield;
  JButton textCheck;

  String japaneseText;
  String query = "";

  public TweetExtraction() {
    setModuleID(20);
    pairingVisualizationID = new int[] {2};
    setToolType(3);
  }
  public void initializePanel() {
    operationPanel.setBackground(Color.GREEN);

    textfield = new JTextField(10);
    japaneseText = fileRead();
    textfield.setText(japaneseText);
    textfield.addActionListener(this);
    operationPanel.add(textfield);

    textCheck = new JButton("Clear/Check");
    textCheck.addActionListener(this);
    operationPanel.add(textCheck);
  }

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
//				System.out.println("Main Focus String0"+text.focus.getMainFocusString());
        break;

      case 23://SEARCH AND SAVE RESULTS
        query = textfield.getText();
        text.focus.setMainFocusString(query);
//				System.out.println("Main Focus String1"+text.focus.getMainFocusString());

        text.fileSave(MyMethod());		// Retry by fileSave
        textfield.setText("");
        break;

      case 1:
      case 24://FOCUS SEARCH KEYWORD
        textfield.setText("");
//				System.out.println("Main Focus String2"+text.focus.getMainFocusString());
        if (!text.focus.getMainFocusString().equals("")) {
          resetData();
          setDataString(text.originalText);
          setDataString(text.focus.getMainFocusString());
          displayOperations(24);
        }
        break;
        /*
        			case 26://DISPLAY SEARCH RESULTS WITHOUT SAVING
        				query = textfield.getText();
        				text.focus.setMainFocusString(query);
        //				setDataString(1,MyMethod());//violation: not created in 0 or 1
         //				setDataString(24,query);
        				displayOperations(24);
        				textfield.setText("");
        				break;*/
    }
  }

  String MyMethod() {

    Pattern patternText = Pattern.compile("<title>(.*?)</title>");
    Pattern patternUnicode = Pattern.compile("x(.+?);");

    String html = "";

    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    try {
      String urlText = "http://search.twitter.com/search.atom?q="+URLEncoder.encode(query, "UTF-8")+"&rpp=50&lang=ja";
      URL url = new URL(urlText);
      HttpURLConnection http = (HttpURLConnection)url.openConnection();
      http.setRequestMethod("GET");
      http.setRequestProperty("User-agent","Mozilla/5.0");
      http.setUseCaches(false);
      http.connect();
      BufferedReader br2 = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));

      String line = "";

      byte[] bytes;

      try {

        while ((line = br2.readLine()) != null) {

          Matcher m2 = patternText.matcher(line);
          while (m2.find()) {
            StringBuffer buf = new StringBuffer();
            String[] str2 = m2.group(1).split("&#");
            for (int i=0; i<str2.length; i++) {
              Matcher m1 = patternUnicode.matcher(str2[i]);
              while (m1.find()) {
                String cc = m1.group(1);
                bytes = new byte[cc.length()/2];
                for (int j=0; j<bytes.length; j++) {
                  bytes[j] = (byte)Integer.parseInt(cc.substring(j*2, (j+1)*2), 16);
                }
                str2[i] = new String(bytes, "UTF-16");
              }
              buf.append(str2[i]);
            }

            bw.write(buf.toString());

            bw.newLine();
//					bw.write(text.settingData.kind_touns[0]);
//					bw.write(text.settingData.segment_tag);
//					bw.write("--------------------");
            bw.write(text.getSegmentTag());

            bw.newLine();
          }

        }
//				bw.write(query);
//				bw.newLine();

        br2.close();
        bw.flush();

      } catch (Exception e) {
        System.out.println("writing ERROR in TweetExtraction");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sw.toString();
  }

  public void actionPerformed(ActionEvent e) {
//		insideOfActionPerformed(e);

    if (e.getSource() == textfield) {
      miningOperations(23);
    }
    if (e.getSource() == textCheck) {
      miningOperations(24);
    }
  }
}
