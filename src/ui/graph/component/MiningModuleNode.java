package ui.graph.component;

import tetdm.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class MiningModuleNode extends ModuleNode {

  private static final Color BG_COLOR = new Color(0, 255, 255, 64);


  public MiningModuleNode(ModuleData moduleData, Point location) {
    super(moduleData, BG_COLOR, location);
  }

  public MiningModuleNode(ModuleData moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }


  @Override
  public MiningModuleNode clone() {
    MiningModuleNode n = new MiningModuleNode(this.moduleData);
    n.setLocation(this.getLocation());
    n.selected(this.isSelected());
    n.highlighted(this.isHighlighted());
    return n;
  }

  @Override
  public boolean isConnectableToPrev(Node n) {
    if (n instanceof PreprocessNode) {
      return true;
    } else if (!(n instanceof VisualizationModuleNode)) {
      return false;
    }

    ModuleData d1 = this.moduleData;
    ModuleData d2 = ((ModuleNode)n).moduleData;
    return d1.isConnectableToPrev(d2);
  }

  @Override
  public boolean isConnectableToNext(Node n) {
    if (!(n instanceof VisualizationModuleNode)) {
      return false;
    }

    ModuleData d1 = this.moduleData;
    ModuleData d2 = ((ModuleNode)n).moduleData;
    return d1.isConnectableToNext(d2);
  }

  @Override
  public boolean isConnectableTo(Node n) {
    return this.isConnectableToPrev(n) || this.isConnectableToNext(n);
  }
}
