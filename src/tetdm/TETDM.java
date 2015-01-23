package tetdm;

import source.MainFrame;

import tetdm.module.ModuleData;
import tetdm.module.ModuleManager;

import java.util.List;


/**
 * Wrapper class of original TETDM
 */
public class TETDM {

  private final MainFrame mainFrame;
  private final ModuleManager moduleManager;


  public TETDM(MainFrame mainFrame, ModuleManager moduleManager) {
    this.mainFrame = mainFrame;
    this.moduleManager = moduleManager;
  }


  public List<ModuleData> getModuleDataList() {
    return this.moduleManager.getModuleDataList();
  }


  public void setToolsToPanel(PanelState ps) {
    this.mainFrame.setPanel(ps.panelId, ps.miningModuleId, ps.visualizationModuleId);
  }

  public List<PanelState> getPanelStates() {
    return this.mainFrame.getPanelStates();
  }


  public String getReadFilename() {
    return "";
  }

  public void loadFile(String filename) {

  }
}
