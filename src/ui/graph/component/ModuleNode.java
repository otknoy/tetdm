package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public abstract class ModuleNode extends Node {

  protected ModuleData moduleData;


  public ModuleNode(ModuleData moduleData, Color bgColor, Point location) {
    super(moduleData.getName(), moduleData.getId(), bgColor, location);
    this.moduleData = moduleData;
  }

  public ModuleNode(ModuleData moduleData, Color bgColor) {
    this(moduleData, bgColor, new Point(0, 0));
  }
}

