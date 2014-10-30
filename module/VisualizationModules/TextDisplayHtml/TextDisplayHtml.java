//
// Visuzalization module for TETDM
// TextDisplayHtml.java Version 0.30
// Copyright(C) 2011 2012 WATARU SUNAYAMA ALL RIGHTS RESERVED.
//
// This program is provided under licenses.
// You Should read README file.
//

package module.VisualizationModules.TextDisplayHtml;

import source.*;
import source.TextData.*;

import java.awt.*;
import javax.swing.*;

import java.io.*;

public class TextDisplayHtml extends TextDisplay {
  public TextDisplayHtml() {
    setModuleID(3);
    dataNumbers = new int[] {0,0,0,1,   // b,i,d,S
                             0,0,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(2);
  }

  public void initializePanel() {
    text1 = new JTextPane();
    text1.setContentType("text/html");
    scroll1 = new JScrollPane(text1);
    add(scroll1);
  }

  public void displayOperations(int optionNumber) {
    StringWriter sw = new StringWriter();			//For words
    BufferedWriter bw = new BufferedWriter(sw);

    switch (optionNumber) {
      case 0:
        if (checkDataNumbers() == false) {
          return;
        }

        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));

        try {
          bw.write("<style type=text/css> <!-- BODY, TH, TD { font: bold; font-size: ");
          bw.write(Integer.toString(fontsize));
          bw.write("pt; font-family: sans-serif;} -->");
          bw.write(displayText);
          bw.write("</style>");
          bw.flush();
        } catch (Exception e) {
          System.out.println("writing ERROR in TextDisplayHtml-A");
          e.printStackTrace();
        }

        text1.setText(sw.toString());
        text1.setCaretPosition(0);
        break;

      case 1:
        if (checkDataNumbers() == false) {
          return;
        }

        text1.setFont(new Font("Dialog", Font.BOLD, fontsize));

        try {
          bw.write("<style type=text/css> <!-- BODY, TH, TD { font: bold; font-size: ");
          bw.write(Integer.toString(fontsize));
          bw.write("pt; font-family: sans-serif;} -->");
          bw.write(displayText);
          bw.write("</style>");
          bw.flush();
        } catch (Exception e) {
          System.out.println("writing ERROR in TextDisplayHtml-B");
          e.printStackTrace();
        }

        text1.setText(sw.toString());
        break;
    }
  }
}
