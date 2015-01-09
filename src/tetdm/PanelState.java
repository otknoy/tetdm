package tetdm;

public class PanelState {

  private final int panelId;
  private final int miningModuleId;
  private final int visualizationModuleId;


  private PanelState(int panelId, int miningModuleId, int visualizationModuleId) {
    this.panelId = panelId;
    this.miningModuleId = miningModuleId;
    this.visualizationModuleId = visualizationModuleId;
  }
}
