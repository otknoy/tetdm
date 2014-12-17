package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class VisualizationModuleNode extends ModuleNode {

  private static final Color BG_COLOR = new Color(255, 0, 255, 64);


  public VisualizationModuleNode(ModuleData moduleData, Point location) {
    super(moduleData, BG_COLOR, location);
  }

  public VisualizationModuleNode(ModuleData moduleData) {
    super(moduleData, BG_COLOR);
  }


  @Override
  public VisualizationModuleNode clone() {
    VisualizationModuleNode n = new VisualizationModuleNode(this.moduleData);
    n.setLocation(this.getLocation());
    n.selected(this.isSelected());
    n.highlighted(this.isHighlighted());
    return n;
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
    return n instanceof ToolPanelNode;
  }

  @Override
  public boolean isConnectableTo(Node n) {
    return this.isConnectableToPrev(n) || this.isConnectableToNext(n);
  }
}