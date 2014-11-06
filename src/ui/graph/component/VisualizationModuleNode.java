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
}