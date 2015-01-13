package tetdm;

public class PanelState {

  public final int panelId;
  public final int miningModuleId;
  public final int visualizationModuleId;


  public PanelState(int panelId, int miningModuleId, int visualizationModuleId) {
    this.panelId = panelId;
    this.miningModuleId = miningModuleId;
    this.visualizationModuleId = visualizationModuleId;
  }

  @Override public String toString() {
    return String.format("{\"pid\": %d, \"mid\": %d, \"vid\": %d}", this.panelId, this.miningModuleId, this.visualizationModuleId);
 }
}
