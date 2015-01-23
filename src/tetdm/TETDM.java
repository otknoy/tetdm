package tetdm;

import source.MainFrame;

import tetdm.module.ModuleData;
import tetdm.module.ModuleManager;

import java.util.List;


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

  public void setToolsToPanel(int pid, int mid, int vid) {
    this.mainFrame.setPanel(pid, mid, vid);
  }
  
  public void setToolsToPanel(PanelState ps) {
    this.mainFrame.setPanel(ps.panelId, ps.miningModuleId, ps.visualizationModuleId);
  }

  public List<PanelState> getPanelStates() {
    return this.mainFrame.getPanelStates();
  }
}
