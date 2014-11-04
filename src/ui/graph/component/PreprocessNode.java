package ui.graph.component;

import ui.graph.module.ModuleInfo;

import java.awt.Color;
import java.awt.Point;


public class PreprocessNode extends Node {

  private static Color BG_COLOR = new Color(255, 255, 0, 128);

  public PreprocessNode(ModuleInfo moduleInfo, Point location) {
    super(moduleInfo, location, BG_COLOR);
  }

  public PreprocessNode(ModuleInfo moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }
}