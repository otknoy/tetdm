package ui.graph.component;

import ui.graph.module.ModuleInfo;
import java.awt.Color;
import java.awt.Point;

public class VisualizationNode extends Node {

  private static Color BG_COLOR = Color.MAGENTA;

  public VisualizationNode(ModuleInfo moduleInfo) {
    super(moduleInfo);
  }
}