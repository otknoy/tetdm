package ui.graph.component;

import ui.graph.module.ModuleInfo;

import java.awt.Color;
import java.awt.Point;


public class ToolPanelNode extends Node {

  private static Color BG_COLOR = new Color(128, 128, 128, 128);

  public ToolPanelNode(ModuleInfo moduleInfo, Point location) {
    super(moduleInfo, location, BG_COLOR);
  }

  public ToolPanelNode(ModuleInfo moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }
}