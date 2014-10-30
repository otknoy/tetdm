package ui.graph.component;

import ui.graph.module.ModuleInformation;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


public class PreprocessNode extends Node {

  public PreprocessNode(ModuleInformation mi) {
    super(mi, new Point(32, 640), new Color(255, 255, 0, 92));
  }
}