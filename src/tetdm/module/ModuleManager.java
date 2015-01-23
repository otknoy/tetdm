package tetdm.module;

import source.MainFrame;
import source.MiningModule;
import source.VisualizationModule;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Module manager and interface to existing TETDM
 */
public class ModuleManager {

  private MainFrame mainFrame;
  private List<ModuleData> minings;
  private List<ModuleData> visualizations;


  public ModuleManager(MiningModule[] miningModules, String[] miningModuleNames,
		       VisualizationModule[] visualizationModules, String[] visualizationModuleNames) {
    this.mainFrame = mainFrame;
    
    // init mining module data
    ModuleData[] minings = new ModuleData[miningModules.length];
    for (int i = 0; i < miningModules.length; i++) {
      String name = miningModuleNames[i];
      int id = miningModules[i].getModuleID();
      int type = ModuleData.TYPE_MINING;

      ModuleData mmd = new ModuleData(name, type, id);
      minings[i] = mmd;
    }
    this.minings = Arrays.asList(minings);
    
    // init visualization module data
    ModuleData[] visualizations = new ModuleData[visualizationModules.length];
    for (int i = 0; i < visualizationModules.length; i++) {
      String name = visualizationModuleNames[i];
      int id = visualizationModules[i].getModuleID();
      int type = ModuleData.TYPE_VISUALIZATION;

      ModuleData vmd = new ModuleData(name, type, id);
      visualizations[i] = vmd;
    }
    this.visualizations = Arrays.asList(visualizations);

    // constructing pairing data
    int[][] pairIDsArray = new int[miningModules.length][];
    for (int i = 0; i < miningModules.length; i++) {
      int[] pairIDs = miningModules[i].pairingVisualizationID;
      pairIDsArray[i] = pairIDs;
    }

    for (int i = 0; i < miningModules.length; i++) {
      ModuleData mmd = minings[i];
      int[] pairIDs = pairIDsArray[i];
      for (Integer pid : pairIDs) {
	for (ModuleData vmd : visualizations) {
	  if (pid != vmd.getId()) continue;

	  mmd.addToNextList(vmd);
	  vmd.addToPrevList(mmd);
	}
      }
    }
  }

  public List<ModuleData> getMiningModuleDataList() { return this.minings; }
  public List<ModuleData> getVisualizationModuleDataList() { return this.visualizations; }

  public List<ModuleData> getModuleDataList() {
    List<ModuleData> modules = new ArrayList<ModuleData>();
    modules.addAll(this.getMiningModuleDataList());
    modules.addAll(this.getVisualizationModuleDataList());
    return modules;
 }
}
