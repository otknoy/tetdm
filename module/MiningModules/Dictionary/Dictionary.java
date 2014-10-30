//
// Mining module for TETDM
// Dictionary.java Version 1.02 //2012/3/1
// You should read the README file.
//

package module.MiningModules.Dictionary;

import source.*;
import source.TextData.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.URL;
import java.util.regex.*;


public class Dictionary extends MiningModule implements ActionListener {
  JButton button;

  String searchResults;

  JTextField textfield;

  String[] JP;		//Japanese text

  public Dictionary() {
    setModuleID(21);						// Set your module ID after you have got it
    pairingVisualizationID = new int[] {15};
    focusClickExecute = true;
  }

  public void initializePanel() {
    textfield = new JTextField(10);
    textfield.addActionListener(this);
    operationPanel.add(textfield);

    button = new JButton("List Clear");
    button.addActionListener(this);
    operationPanel.add(button);

    operationPanel.setBackground(Color.RED);



    JP = fileReadArray();

    button.setText(JP[4]);

    searchResults = "";
  }

  public void initializeData() {}

  public void miningOperations(int optionNumber) {
    switch (optionNumber) {
      case 25:
        if (!textfield.getText().equals("")) {
          text.focus.setMainFocusString(textfield.getText());
        }

      case 4502:
      case 0:
        if (!text.focus.getMainFocusString().equals("")) {
          WordSearch(text.focus.getMainFocusString());
          setDataString(1,searchResults);
          displayOperations(102);
          textfield.setText("");
        }
        displayOperations(100);
        break;
    }
  }

  public void actionPerformed(ActionEvent e) {
//		insideOfActionPerformed(e);

    if (e.getSource() == button) {	//list clear
      searchResults = "";
      setDataString(1,searchResults);
      displayOperations(100);
      displayOperations(103);
    }

    if (e.getSource () == textfield) {
      miningOperations(25);
    }
  }

  //Word Search
  void WordSearch(String searchWord) {
    String failedText;
    String ss;
    String result = "";

    failedText = "-"+searchWord+JP[3];

//		System.out.println(selectedText);

    if ((ss = GetQA(0,searchWord)) != null) {	//yahoo
      result += ("-"+searchWord+"\n ["+ss+"...]\n---"+JP[0]);
    } else {
      if ((ss = GetQA(1,searchWord)) != null) {	//Wiktionary
        result += ("-"+searchWord+"\n ["+ss+"...]\n---"+JP[1]);
      } else {
        if ((ss = GetQA(2,searchWord)) != null) {	//goo
          result += ("-"+searchWord+"\n ["+ss+"...]\n---"+JP[2]);
        } else {
          result += failedText;
        }
      }
    }

    searchResults = result+("\n\n")+searchResults;
  }

  String GetQA(int p, String qWord) {
    String path="";

    HttpURLConnection urlconn = null;
    BufferedReader br = null;
    try {
      long count_result;
      qWord = URLEncoder.encode(qWord , "UTF-8");

      if (p==0) {
        path = "http://100.yahoo.co.jp/detail/"+ qWord;			//yahoo hakka jiten
      } else if (p==1) {
        path = "http://dictionary.goo.ne.jp/srch/jn/"+qWord+"/m0u/";	//goo
      } else if (p==2) {
        path = "http://ja.wiktionary.org/wiki/"+ qWord;			//Wiktionary
      }
      URL url = new URL(path);
      urlconn = (HttpURLConnection) url.openConnection();
      urlconn.connect();

      InputStreamReader ir = new InputStreamReader(urlconn.getInputStream(),getCharset(urlconn));
      br = new BufferedReader(ir);

      StringBuilder sb = new StringBuilder();
      String line;

      while ((line=br.readLine())!=null) {
        sb.append(line);
      }

      String count = getHitCount(qWord, sb.toString(),p);
      if (count != null) {
        //System.out.println("bf:"+count.length());
        count = count.replaceAll("<.+?>","");
        //System.out.println("af:"+count.length());
        if (count.length() > 1000) {
          count = count.substring(0,100)+" ......";
        }
        if (count.length() > 100) {
          count = count.substring(0,100)+" ...";
        }
      }

      return count;

    } catch (MalformedURLException e) {
//			System.out.println("MalformedURLException@GETQA@Dictionary_"+p);
      //e.printStackTrace();
      return null;
    } catch (IOException e1) {
//			System.out.println("IOException@GETQA@Dictionary_"+p);
      //e1.printStackTrace();
      return null;
    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException e) {
//				System.out.println("finallyIOException@GETQA@Dictionary_"+p);
        //e.printStackTrace();
      } if (urlconn != null) {
        urlconn.disconnect();
      }
    }
  }

  public String getHitCount(String qWord, String cont,int pp) {
    String preStr="",postStr="";

    if (pp==0) {
      preStr = "summary\">";		//hyakkajiten
      postStr = "</span></p>";
      //postStr = JP[5];		//1st sentence only
    } else if (pp==1) {
      preStr = "allList";
      postStr = "</dl>";		//goo
    } else if (pp==2) {
      preStr = "<li>";
      //postStr = "</li>";
      postStr = JP[5];			//Wiktionary
    }

    int preIndex = cont.indexOf(preStr);
    if (preIndex == -1) {
      return null;
    }
    int postIndex = cont.indexOf(postStr, preIndex);
    if (postIndex == -1) {
      return null;
    }
    return cont.substring(preIndex + preStr.length(), postIndex);
  }

  public String getCharset(HttpURLConnection urlconn) {
    String defaultCharset = "UTF-8";
    String cont = urlconn.getContentType();
    if (cont == null || !cont.contains("charset")) {
      return defaultCharset;
    }
    return cont.substring(cont.indexOf("=") + 1);
  }

  public String TagCut(String st) {
    Pattern pattern = Pattern.compile("[\\s]+", Pattern.DOTALL);

    Matcher matcher = pattern.matcher(st);
    String cut_st = matcher.replaceAll("");

    return cut_st;

  }
}
