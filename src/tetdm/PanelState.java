package tetdm;

public class PanelState {

  private final int panelId;
  private final int miningModuleId;
  private final int visualizationModuleId;


  public PanelState(int panelId, int miningModuleId, int visualizationModuleId) {
    this.panelId = panelId;
    this.miningModuleId = miningModuleId;
    this.visualizationModuleId = visualizationModuleId;
  }

  @Override public String toString() {
    return String.format("{\"pid\": %d, \"mid\": %d, \"vid\": %d}", this.panelId, this.miningModuleId, this.visualizationModuleId);
 }
}
