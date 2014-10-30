
//
// Visuzalization module for TETDM
// SampleDisplay2.java Version 0.30
// This is one of the sample modules.
// You should read the README file.
//


package module.VisualizationModules.SampleDisplay2;

import source.*;
import source.TextData.*;
//import source.Utility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class SampleDisplay2 extends VisualizationModule {
  String outputInJapanese;
  String outputText;

  public SampleDisplay2() {
    setModuleID(10001);			// Set your module ID after you have got it
    setToolType(1);
  }

  public void initializePanel() {
    outputInJapanese = fileRead();
  }

  public void initializeData() {
    if (isMenuInJapanese()) {
      outputText = outputInJapanese;
    } else {
      outputText = "Sentence Number";
    }
  }

  public void displayOperations(int optionNumber) {
    switch (optionNumber) {
      case 0:
        repaint();
        break;
    }
  }

  //	public void setFont(){}

  //background
  public void drawBackground(Graphics2D g2) {
    g2.setColor(Color.blue);
    g2.fillRect(0,0, sizeX, sizeY);
  }

  public void drawData(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillOval(30, 30, sizeX - 60, sizeY - 60);

    g2.setFont(new Font("Dialog", Font.BOLD, 20));
    g2.setColor(Color.black);
    g2.drawString(text.sentence[0].sentenceText, (sizeX - text.sentence[0].sentenceText.length()*20)/2 ,sizeY/2);

    g2.setColor(Color.yellow);
    g2.drawString(outputText + " = "+text.sentenceNumber, 5 ,sizeY - 30);
  }

  //////////paint
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    getPanelSize();

    drawBackground(g2);		//background
    drawData(g2);
  }

  public void update(Graphics g) {	//avoid from blinking
    paintComponent(g);
  }
}