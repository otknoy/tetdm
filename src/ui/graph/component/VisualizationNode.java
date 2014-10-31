package ui.graph.component;

import ui.graph.module.ModuleInformation;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;


public class VisualizationNode extends Node implements Cloneable {

  public VisualizationNode(ModuleInformation mi, Point location) {
    super(mi, location, new Color(255, 0, 255, 92));
  }

  public VisualizationNode(ModuleInformation mi) {
    this(mi, new Point(0, 0));
  }

  @Override
  public VisualizationNode clone() {
    VisualizationNode n;
    n = (VisualizationNode)super.clone();
    // try {
    //   n = (VisualizationNode)super.clone();
    // } catch(CloneNotSupportedException e) {
    //   throw new RuntimeException();
    // }

    return n;      
  }

  /**
   * If a path connecting from PreprocessNode to VisualizationNode (this node) exists, this returns true.
   */
  public boolean hasPathFromPreprocessNode() {
    Node n = this;
    while (n.getPreviousNode() != null) n = n.getPreviousNode();

    // System.out.println(n.getName());
    if (n instanceof PreprocessNode)
      return true;
    return false;
  }
}
