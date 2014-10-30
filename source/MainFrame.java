//
// Core Program for TETDM
// MainFrame.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.*;
import source.TextData.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;


//MAIN FRAME
public class MainFrame extends JFrame {
  public int windowSizeX, windowSizeY;
  int popSizeY = 65;

  Control controlPanel;
  Container pane = getContentPane();

  MainFrame(String title, String filename, String absolutePath) {
    super(title);
    pane.setLayout(new BorderLayout());

    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();

    windowSizeX = displayMode.getWidth();
    windowSizeY = displayMode.getHeight() - 100-popSizeY;

    setBounds(0,popSizeY,windowSizeX,windowSizeY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    controlPanel = new Control(this, filename, absolutePath, windowSizeX, popSizeY);
    pane.add(controlPanel);
//		pane.add(new JPanel());
    setVisible(true);
  }
}
