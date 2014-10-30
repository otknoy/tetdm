package ui.graph.module;

import source.MainFrame;
import source.ModuleData;
import source.MiningModule;
import source.VisualizationModule;

import ui.graph.component.Node;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ModuleManager {

  private MainFrame mainFrame;
  private ModuleInformation preprocess;
  private List<ModuleInformation> minings;
  private List<ModuleInformation> visualizations;


  public ModuleManager(MainFrame mainFrame,
		       MiningModule[] miningModules, String[] miningModuleNames,
		       VisualizationModule[] visualizationModules, String[] visualizationModuleNames) {
    this.mainFrame = mainFrame;
    
    int[][] pairIDsArray = new int[miningModules.length][];
    
    ModuleInformation[] minings = new ModuleInformation[miningModules.length];
    for (int i = 0; i < miningModules.length; i++) {
      String name = miningModuleNames[i];
      int id = miningModules[i].getModuleID();
      int type = ModuleInformation.TYPE_MINING;

      ModuleInformation mi = new ModuleInformation(name, id, type);
      minings[i] = mi;

      int[] pairIDs = miningModules[i].pairingVisualizationID;
      pairIDsArray[i] = pairIDs;
    }
    
    ModuleInformation[] visualizations = new ModuleInformation[visualizationModules.length];
    for (int i = 0; i < visualizationModules.length; i++) {
      String name = visualizationModuleNames[i];
      int id = visualizationModules[i].getModuleID();
      int type = ModuleInformation.TYPE_VISUALIZATION;

      ModuleInformation mi = new ModuleInformation(name, id, type);
      visualizations[i] = mi;
    }

    ModuleInformation preprocess = new ModuleInformation("Preprocess", -1, ModuleInformation.TYPE_PREPROCESS);


    for (int i = 0; i < miningModules.length; i++) {
      ModuleInformation mmi = minings[i];
      int[] pairIDs = pairIDsArray[i];
      for (Integer pid : pairIDs) {
	for (ModuleInformation vmi : visualizations) {
	  if (pid != vmi.getID()) continue;

	  mmi.addChild(vmi);
	  vmi.addParent(mmi);
	}
      }
      mmi.addParent(preprocess);
      preprocess.addChild(mmi);
    }

    this.preprocess = preprocess;
    this.minings = Arrays.asList(minings);
    this.visualizations = Arrays.asList(visualizations);
  }

  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.mainFrame.setPanel(panelIndex, miningModuleID, visualizationModuleID);
  }

  public ModuleInformation getPreprocess() { return this.preprocess; }
  public List<ModuleInformation> getMinings() { return this.minings; }
  public List<ModuleInformation> getVisualizations() { return this.visualizations; }
}
