//
// Visuzalization module for TETDM
// SampleDisplay.java Version 0.30
// This is one of the sample modules.
// You should read the README file.
//

package module.VisualizationModules.SampleDisplay;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class SampleDisplay extends VisualizationModule {
  JTextPane text1;
  JScrollPane scroll1;

  String displayText;

  public SampleDisplay() {
    setModuleID(10000);			// Set your module ID after you have got it
    dataNumbers = new int[] {0,0,0,1,   // b,i,d,S
                             0,0,0,0,    // bA,iA,dA,SA
                             0,0,0
                            };     // bA2,iA2,dA2
    setToolType(2);
  }

  public void initializePanel() {
    text1 = new JTextPane();
    text1.setContentType("text/plain");
    scroll1 = new JScrollPane(text1);
    add(scroll1);
  }

  public void initializeData() {
    displayText = "Nothing";
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        text1.setText(displayText);
        text1.setCaretPosition(0);
        break;
    }
  }

  public boolean setData(int dataID, String t) {
    switch (dataID) {
      case 0:
        displayText = t;
        return true;
    }

    return false;

  }

  //	public void setFont(){}

}