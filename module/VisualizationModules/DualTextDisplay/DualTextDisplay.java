//
// Visuzalization module for TETDM
// DualTextDisplay.java Version 0.36
// Copyright(C) 2011 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under the license.
// You should read the README file.
//

package module.VisualizationModules.DualTextDisplay;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


public class DualTextDisplay extends VisualizationModule implements MouseListener {
  //Text pane
  JTextPane text1, text2;
  JScrollPane scroll1, scroll2;
  JSplitPane splitPane;

  //for DISPLAY
  String displayText1, displayText2;	//display text

  String nothing;
  String outputInJapanese;

  int fontsize;

  StyleContext sc;
  DefaultStyledDocument doc;
  MutableAttributeSet attr0,attr;

  public DualTextDisplay() {
    setModuleID(15);
    dataNumbers = new int[] {0,0,0,2,   // b,i,d,S
                             0,0,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(3);
  }

  public void initializePanel() {
    text1 = new JTextPane();
    text2 = new JTextPane();

    text1.setContentType("text/plain");
    text2.setContentType("text/plain");

    scroll1 = new JScrollPane(text1);
    scroll2 = new JScrollPane(text2);

    text1.addMouseListener(this);
    text2.addMouseListener(this);

    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setDividerLocation(sizeY/2);

    splitPane.setTopComponent(scroll1);
    splitPane.setBottomComponent(scroll2);


    System.out.println("SIZEY="+sizeY);

    add(splitPane);



    outputInJapanese = fileRead();

    if (isMenuInJapanese()) {
      displayText1 = displayText2 = outputInJapanese;
    } else {
      displayText1 = displayText2 = "Nothing";
    }

    fontsize = 14;

    attr = new SimpleAttributeSet();
    StyleConstants.setForeground(attr, Color.BLUE);			//foreground color
    StyleConstants.setBackground(attr, Color.YELLOW);		//background color

    attr0 = new SimpleAttributeSet();
    StyleConstants.setForeground(attr0, Color.BLACK);		//foreground color
    StyleConstants.setBackground(attr0, Color.WHITE);		//background color

    sc = new StyleContext();
  }

  public void initializeData() {}

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text2.setFont(new Font("Dialog", Font.BOLD, fontsize));
        break;

      case 1:
        text.fileSave(text1.getText());
        break;

      case 9:
        fontsize--;
        displayOperations(0);
        break;

      case 10:
        fontsize++;
        displayOperations(0);
        break;

      case 100:
        displayText1 = text.originalText;
        displayText1 = displayText1.replaceAll("\r\n", "\n");

        setWordColor();
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));	// SHOULD be executed before setText
        text1.setDocument(doc);
        text1.setCaretPosition(0);
        break;

      case 101:
        text1.setText(displayText1);
        break;

      case 102:
        text2.setText(displayText2);
        text2.setCaretPosition(0);

        setWordColor();
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text1.setDocument(doc);
        text1.setCaretPosition(0);
        break;

      case 103:
        text2.setText(displayText2);
        text2.setCaretPosition(0);

        doc.setCharacterAttributes(0, displayText1.length(), attr0, true);
        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));
        text1.setDocument(doc);
        text1.setCaretPosition(0);
        break;
    }
  }

  public void setWordColor() {
    doc = new DefaultStyledDocument(sc);

    try {
      doc.insertString(0, displayText1, attr0);
    } catch (BadLocationException ble) {
      System.err.println("Accessed at illegal position out of the document.");
    }

    String searchword = text.focus.getMainFocusString();

    if (searchword == null || searchword.equals("")) {
      return;
    }

    int position = displayText1.indexOf(searchword);
    int wordLength = searchword.length();

    while (position != -1) {
      doc.setCharacterAttributes(position, wordLength, attr, true);
      position = displayText1.indexOf(searchword, position+wordLength);
    }
  }


  public boolean setData(int dataID, String t) {
    switch (dataID) {
      case 0:
        displayText1 = t;
        return true;

      case 1:
        displayText2 = t;
        return true;
    }

    return false;
  }

  public void setFont() {
    fontsize = text.fontSize;
  }

  //Mouse Event
  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {		//get selectedText
    if (text1.getSelectedText() != null) {
      text.focus.setMainFocusString(text1.getSelectedText());
      executeAllByClick();
    } else if (text2.getSelectedText() != null) {
      text.focus.setMainFocusString(text2.getSelectedText());
      executeAllByClick();
    }
  }
}
