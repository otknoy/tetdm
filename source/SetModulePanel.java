//
// Core Program for TETDM
// SetModulePanel.java Version 0.49
// Copyright(C) 2013-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//

package source;

//import source.*;
import source.TextData.*;
import source.Utility.*;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;


public class SetModulePanel extends JPanel implements ActionListener, ComponentListener {
  JPanel informationSelectPanel;
  JSplitPane splitPane;
  JScrollPane userLayer, developerLayer;

  JToggleButton displayInformation;
  JRadioButton forUsers,forDevelopers;
  ButtonGroup bgroup;

  String useJapanese[];

  SetModulePanel2 drawPanel;

  int width;
  int informationWidth = 300;
  int fontSize = 13;

  Control control;

  SetModulePanel(Control control) {
    this.control = control;

    setLayout(new BorderLayout());
    addComponentListener(this);
    width = getWidth();

    useJapanese = FILEIO.TextFileAllReadCodeArray(new File(control.absolutePath + "source" + File.separator + "useJapanese.txt"));

    drawPanel = new SetModulePanel2(control);
    userLayer = new JScrollPane(drawPanel.area);
    developerLayer = new JScrollPane(drawPanel.d_area);

    splitPane = new JSplitPane();
    splitPane.setOpaque(true);
    setDivider();

    //Button
    informationSelectPanel = new JPanel();
    informationSelectPanel.setLayout(new GridLayout(1,3));
    informationSelectPanel.setBackground(new Color(0xcc,0xff,0xff));
    add("North",informationSelectPanel);

    if (control.sp1.inJapanese) {
      displayInformation = new JToggleButton(useJapanese[5],true);
    } else {
      displayInformation = new JToggleButton("Information Display",true);
    }
    displayInformation.addActionListener(this);
    displayInformation.setFont(new Font("Dialog", Font.BOLD, fontSize));
    informationSelectPanel.add(displayInformation);

    bgroup = new ButtonGroup();

    if (control.sp1.inJapanese) {
      forUsers = new JRadioButton(useJapanese[18],true);
    } else {
      forUsers = new JRadioButton("For Users",true);
    }
    forUsers.addActionListener(this);
    forUsers.setFont(new Font("Dialog", Font.BOLD, fontSize));
    bgroup.add(forUsers);
    informationSelectPanel.add(forUsers);

    if (control.sp1.inJapanese) {
      forDevelopers = new JRadioButton(useJapanese[19],false);
    } else {
      forDevelopers = new JRadioButton("For Developers",false);
    }
    forDevelopers.addActionListener(this);
    forDevelopers.setFont(new Font("Dialog", Font.BOLD, fontSize));
    bgroup.add(forDevelopers);
    informationSelectPanel.add(forDevelopers);


    if (displayInformation.isSelected()) {
      splitPane.setLeftComponent(drawPanel);
      splitPane.setRightComponent(userLayer);

      add(splitPane);
    } else {
      add(drawPanel);
    }
  }

  public void setMenu() {
    if (control.sp1.inJapanese) {
      displayInformation.setText(useJapanese[5]);
      forUsers.setText(useJapanese[18]);
      forDevelopers.setText(useJapanese[19]);
    } else {
      displayInformation.setText("Information Display");
      forUsers.setText("For Users");
      forDevelopers.setText("For Developers");
    }

    drawPanel.setMenu();

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == displayInformation) {
      if (displayInformation.isSelected()) {
//				forUsers.setEnabled(true);
//				forDevelopers.setEnabled(true);

        remove(drawPanel);
        splitPane.setLeftComponent(drawPanel);
        if (forUsers.isSelected()) {
          splitPane.setRightComponent(userLayer);
        } else {
          splitPane.setRightComponent(developerLayer);
        }
        setDivider();
        add(splitPane);
        revalidate();
        repaint();
      } else {
//				forUsers.setEnabled(false);
//				forDevelopers.setEnabled(false);

        remove(splitPane);
        add(drawPanel);
        revalidate();
      }
    }

    if (e.getSource() == forUsers)
      if (forUsers.isSelected()) {
        drawPanel.forUser = true;

        if (displayInformation.isSelected()) {
          splitPane.setRightComponent(userLayer);
          setDivider();
        }

        drawPanel.repaint();
      }

    if (e.getSource() == forDevelopers)
      if (forDevelopers.isSelected()) {
        drawPanel.forUser = false;

        if (displayInformation.isSelected()) {
          splitPane.setRightComponent(developerLayer);
          setDivider();
        }

        drawPanel.repaint();
      }
  }

  public void setDivider() {
    width = getWidth();
    if (width < informationWidth * 2) {
      splitPane.setDividerLocation(width);
    } else {
      splitPane.setDividerLocation(width - informationWidth);
    }
  }

  //ComponentListener
  public void componentHidden(ComponentEvent e) {}
  public void componentMoved(ComponentEvent e) {}
  public void componentShown(ComponentEvent e) {}
  public void componentResized(ComponentEvent e) {
    setDivider();
  }
}
