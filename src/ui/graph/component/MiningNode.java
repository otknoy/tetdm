package ui.graph.component;

import ui.graph.module.ModuleInfo;

import java.awt.Color;
import java.awt.Point;


public class MiningNode extends Node {

  private static Color BG_COLOR = new Color(0, 255, 255, 128);

  public MiningNode(ModuleInfo moduleInfo, Point location) {
    super(moduleInfo, location, BG_COLOR);
  }

  public MiningNode(ModuleInfo moduleInfo) {
    super(moduleInfo, BG_COLOR);
  }
}