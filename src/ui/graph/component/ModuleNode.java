package ui.graph.component;

import ui.graph.module.ModuleData;

import java.awt.Color;
import java.awt.Point;


public class ModuleNode extends Node {

  protected ModuleData moduleData;


  public ModuleNode(ModuleData moduleData, Color bgColor, Point location) {
    super(moduleData.getName(), moduleData.getId(), bgColor, location);
    this.moduleData = moduleData;
  }

  public ModuleNode(ModuleData moduleData, Color bgColor) {
    super(moduleData.getName(), moduleData.getId(), bgColor);
    this.moduleData = moduleData;
  }
  

  @Override
  public boolean isConnectableToPrev(Node n) {
    ModuleData d1 = this.moduleData;
    ModuleData d2 = ((ModuleNode)n).moduleData;
    return d1.isConnectableToPrev(d2);
  }
  
  @Override
  public boolean isConnectableToNext(Node n) {
    ModuleData d1 = this.moduleData;
    ModuleData d2 = ((ModuleNode)n).moduleData;
    return d1.isConnectableToNext(d2);
  }

  @Override
  public boolean isConnectableTo(Node n) {
    return this.isConnectableToPrev(n) || this.isConnectableToNext(n);
  }
}

