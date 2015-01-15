package tetdm;

/**
 * State of result panel in TETDM.
 * The result panel shows processing result by combination of mining module and visualization module.
 */
public class PanelState {

  /**
   * Result panel ID
   */
  public final int panelId;

  /**
   * ID of selected mining module
   */
  public final int miningModuleId;

  /**
   * ID of selected visualization module
   */
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
