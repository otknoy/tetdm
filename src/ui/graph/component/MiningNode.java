package ui.graph.component;

import ui.graph.module.ModuleInformation;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class MiningNode extends Node {

  public MiningNode(ModuleInformation mi, Point location) {
    super(mi, location, new Color(0, 255, 255, 92));
  }

  public MiningNode(ModuleInformation mi) {
    this(mi, new Point(0, 0));
  }
}