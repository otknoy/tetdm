package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class VisualizationModuleNode extends ModuleNode {

  private static final Color BG_COLOR = new Color(255, 0, 255, 128);

  public VisualizationModuleNode(ModuleData moduleData, Point location) {
    super(moduleData, location, BG_COLOR);
  }

  public VisualizationModuleNode(ModuleData moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }
}