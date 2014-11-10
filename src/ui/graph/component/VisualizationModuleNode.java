package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class VisualizationModuleNode extends ModuleNode {

  private static final Color BG_COLOR = new Color(255, 0, 255, 128);


  public VisualizationModuleNode(ModuleData moduleData, Point location) {
    super(moduleData, BG_COLOR, location);
  }

  public VisualizationModuleNode(ModuleData moduleData) {
    super(moduleData, BG_COLOR);
  }


  @Override
  public boolean isConnectableToPrev(Node n) {
    if (!(n instanceof MiningModuleNode)) {
      return false;
    }

    ModuleData d1 = this.moduleData;
    ModuleData d2 = ((ModuleNode)n).moduleData;
    return d1.isConnectableToPrev(d2);
  }

  @Override
  public boolean isConnectableToNext(Node n) {
    return false;
  }

  @Override
  public boolean isConnectableTo(Node n) {
    return this.isConnectableToPrev(n) || this.isConnectableToNext(n);
  }
}