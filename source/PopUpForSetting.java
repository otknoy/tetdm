

//
// Core Program for TETDM
// PopUpWindow.java Version 0.44
// Copyright(C) 2012-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class PopUpForSetting extends JFrame {
  SettingPanel settingPanel;
  Container pane = getContentPane();

  PopUpForSetting(SettingData sp1, int X, int Y, int width, int height, Control control) {
    super("Settings");
    pane.setLayout(new BorderLayout());

    setBounds(X, Y, width, height);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    setAlwaysOnTop(true);
    pane.setBackground(Color.white);
//		setUndecorated(true);

    settingPanel = new SettingPanel(sp1, control);
    this.addWindowListener(new MyWindowListener());
    pane.add(settingPanel);
    setVisible(true);
  }

  class MyWindowListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      //System.out.println("windowClosing");

      settingPanel.closeWindow();
    }
  }
}
