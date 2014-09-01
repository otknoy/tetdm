
//
// Core Program for TETDM
// PopUpWindow.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class PopUpForSetModule extends JFrame {
  Container pane = getContentPane();
  Control control;

  SetModulePanel setModulePanel;

  PopUpForSetModule(SettingData sp1, int X, int Y, int width, int height, Control control) {
    super("Set Panels");
    pane.setLayout(new BorderLayout());

    setBounds(X, Y, width, height);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    setAlwaysOnTop(true);
    pane.setBackground(Color.white);
//		setUndecorated(true);//TEST

//        pane.add(new SetToolPanel(control));/*FOR TEST*/
    setModulePanel = new SetModulePanel(control);
    pane.add(setModulePanel);/*FOR TEST*/

    setVisible(true);
    this.control = control;
    this.addWindowListener(new MyWindowListener());
  }

  public void setMenu() {
    setModulePanel.setMenu();
  }

  class MyWindowListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW");
    }
  }
}
