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
  private ModuleInfo preprocess;
  private List<ModuleInfo> minings;
  private List<ModuleInfo> visualizations;


  public ModuleManager(MainFrame mainFrame,
		       MiningModule[] miningModules, String[] miningModuleNames,
		       VisualizationModule[] visualizationModules, String[] visualizationModuleNames) {
    this.mainFrame = mainFrame;
    
    int[][] pairIDsArray = new int[miningModules.length][];
    
    ModuleInfo preprocess = new ModuleInfo("Preprocess", -1, ModuleInfo.TYPE_PREPROCESS);
    this.preprocess = preprocess;
    
    ModuleInfo[] minings = new ModuleInfo[miningModules.length];
    for (int i = 0; i < miningModules.length; i++) {
      String name = miningModuleNames[i];
      int id = miningModules[i].getModuleID();
      int type = ModuleInfo.TYPE_MINING;

      ModuleInfo mi = new ModuleInfo(name, id, type);
      minings[i] = mi;

      int[] pairIDs = miningModules[i].pairingVisualizationID;
      pairIDsArray[i] = pairIDs;
    }
    
    ModuleInfo[] visualizations = new ModuleInfo[visualizationModules.length];
    for (int i = 0; i < visualizationModules.length; i++) {
      String name = visualizationModuleNames[i];
      int id = visualizationModules[i].getModuleID();
      int type = ModuleInfo.TYPE_VISUALIZATION;

      ModuleInfo mi = new ModuleInfo(name, id, type);
      visualizations[i] = mi;
    }
    this.minings = Arrays.asList(minings);

    for (int i = 0; i < miningModules.length; i++) {
      ModuleInfo mmi = minings[i];
      int[] pairIDs = pairIDsArray[i];
      for (Integer pid : pairIDs) {
	for (ModuleInfo vmi : visualizations) {
	  if (pid != vmi.getID()) continue;

	  mmi.addToNext(vmi);
	  vmi.addToPrev(mmi);
	}
      }
      mmi.addToPrev(preprocess);
      preprocess.addToNext(mmi);
    }
    this.visualizations = Arrays.asList(visualizations);
  }

  public void setModulesToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.mainFrame.setPanel(panelIndex, miningModuleID, visualizationModuleID);
  }

  public ModuleInfo getPreprocess() { return this.preprocess; }
  public List<ModuleInfo> getMinings() { return this.minings; }
  public List<ModuleInfo> getVisualizations() { return this.visualizations; }

  public List<ModuleInfo> getAllModuleInfo() {
    List<ModuleInfo> all = new ArrayList<ModuleInfo>();
    all.add(this.preprocess);
    all.addAll(this.minings);
    all.addAll(this.visualizations);
    return all;
  }
}
