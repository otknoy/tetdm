//
// Core Program for TETDM
// MainFrame.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.*;
import source.TextData.*;

import ui.Interface;
import ui.graph.ModuleSelectPanel;
import ui.graph.component.Node;
import ui.graph.module.ModuleManager;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.List;


//MAIN FRAME
public class MainFrame extends JFrame {
  public int windowSizeX, windowSizeY;
  int popSizeY = 65;

  Control controlPanel;
  Container pane = getContentPane();

  private JFrame moduleSelectFrame;
  private ModuleSelectPanel moduleSelectPanel;


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
    setVisible(true);


    // initialize module manager
    ModuleData moduleData = controlPanel.moduleData;
    MiningModule[] miningModules = moduleData.mining;
    String[] miningNames = moduleData.miningModuleNamesInJapanese; // moduleData.module_names;
    VisualizationModule[] visualizationModules = moduleData.visual;
    String[] visualNames = moduleData.visualizationModuleNamesInJapanese; // moduleData.visu_module_names;
    ModuleManager moduleManager = new ModuleManager(this,
						    miningModules, miningNames,
						    visualizationModules, visualNames);

    // // our proposed interface
    // this.moduleSelectPanel = new ModuleSelectPanel(moduleManager);
    // this.moduleSelectFrame = new JFrame("Module Select Panel");
    // this.moduleSelectFrame.add(moduleSelectPanel);
    // this.moduleSelectFrame.pack();
    // this.moduleSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.moduleSelectFrame.setVisible(true);
    Interface inf = new Interface(moduleManager);
    inf.setVisible(true);

    // setPanel(0, 14, 14);
    // setPanel(1, 0, 23);
    // setPanel(2, 14, 14);
    // Control.addNewPanel(Text text);
  }

  /**
   * change module select panel
   */
  public void changeModuleSelectPanel(Object data) {
    this.moduleSelectFrame.remove(this.moduleSelectPanel);
    this.moduleSelectPanel = (ModuleSelectPanel)data;
    this.moduleSelectFrame.add(this.moduleSelectPanel);
    this.moduleSelectFrame.pack();
    this.moduleSelectFrame.repaint();
    // this.moduleSelectPanel.repaint();
  }

  /**
   * Set mining module and visualization module to a panel.
   * @param: panel index
   * @param: mining module id
   * @param: visualization module id
   */
  public void setPanel(int selectPanelIndex, int miningModuleID, int visualizationModuleID) {
    // System.out.println("**************** MainFrame.setPanel ****************");
    String[] miningNames = controlPanel.moduleData.module_names;
    String[] visualNames = controlPanel.moduleData.visu_module_names;

    Control control = this.controlPanel;
    Select select = control.panel[selectPanelIndex];
    ModuleData moduleData = control.moduleData;
    SettingData settingData = control.sp1;
    MiningModule[] minings = moduleData.mining;
    VisualizationModule[] visuals = moduleData.visual;

    // convert id to index
    int[] miningModuleIDs = moduleData.moduleIDs;
    int mIndex = -1;
    for (int i = 0; i < miningModuleIDs.length; i++) {
      if (miningModuleID == miningModuleIDs[i]) {
	mIndex = i;
	break;
      }
    }
    int[] visualizationModuleIDs = moduleData.visuModuleIDs;
    int vIndex = -1;
    for (int i = 0; i < visualizationModuleIDs.length; i++) {
      if (visualizationModuleID == visualizationModuleIDs[i]) {
	vIndex = i;
	break;
      }
    }

    boolean panelsReConstruction = false;
    if (moduleData.miningModuleSelection[mIndex] == false) {
      moduleData.miningModuleSelection[mIndex] = true;
      moduleData.initializeVisualiationModuleSelection();
      panelsReConstruction = true;
    }

    if (moduleData.visualizationModuleSelection[vIndex] == false) {
      moduleData.visualizationModuleSelection[vIndex] = true;
      panelsReConstruction = true;
    }

    //create backup
    control.createBackupIDs();

    if (panelsReConstruction == true) {
      // addPairingVisualization(mIndex, vIndex);
      int[] list = minings[mIndex].pairingVisualizationID;
      int vID = visuals[vIndex].getModuleID();
      int[] temp = new int[list.length+1];
      for (int i = 0; i < temp.length-1; i++) {
	temp[i] = list[i];
      }
      temp[temp.length-1] = vID;
      minings[mIndex].pairingVisualizationID = list;
      //
      list = minings[mIndex].pairingVisualizationIndex;
      temp = new int[list.length+1];
      for (int i = 0; i < temp.length-1; i++) {
	temp[i] = list[i];
      }
      temp[temp.length-1] = vIndex;
      minings[mIndex].pairingVisualizationIndex = list;
      //
      moduleData.createPairingMiningID(vIndex);

      moduleData.initializeModuleSubset();

      settingData.miningID[select.getPanelID()] = minings[mIndex].getModuleID();
      settingData.visualizationID[select.getPanelID()] = visuals[vIndex].getModuleID();
      try {
	control.panelsReConstruction();
      } catch (Exception e) {
	System.out.println("ERROR at setPanel in SetToolPanel2.java A");
	e.printStackTrace();

	control.traceBackupIDs();
	control.panelSet();
      }
    }

    try {
      select.changePanel(minings[mIndex].getModuleID(), visuals[vIndex].getModuleID());
    } catch (Exception e) {
      System.out.println("ERROR at setPanel in SetToolPanel2.java B");
      e.printStackTrace();

      control.traceBackupIDs();
      control.panelSet();
    }

    settingData.miningID[select.getPanelID()] = minings[mIndex].getModuleID();
    settingData.visualizationID[select.getPanelID()] = visuals[vIndex].getModuleID();

    control.writeActionLog("CLOSE-MODULE-SELECT-WINDOW-SINGLE panelID= "+select.getPanelID());

    // if (informationWindow != null) {
    //   informationWindow.dispose();
    // }
    // parentFrame.dispose();
  }
}