package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class MiningModuleNode extends ModuleNode {

  private static final Color BG_COLOR = new Color(0, 255, 255, 128);

  public MiningModuleNode(ModuleData moduleData, Point location) {
    super(moduleData, BG_COLOR, location);
  }

  public MiningModuleNode(ModuleData moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }
}