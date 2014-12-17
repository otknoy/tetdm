package ui.graph.component;

import java.awt.Color;
import java.awt.Point;

public class PreprocessNode extends Node {

  private static final String NAME = "Preprocess";
  private static final Color BG_COLOR = new Color(255, 255, 0, 64);


  public PreprocessNode(Point location) {
    super(NAME, -1, BG_COLOR, location);
  }

  public PreprocessNode() {
    super(NAME, -1, BG_COLOR);
  }


  @Override
  public PreprocessNode clone() {
    PreprocessNode n = new PreprocessNode();
    n.setLocation(this.getLocation());
    n.selected(this.isSelected());
    n.highlighted(this.isHighlighted());
    return n;
  }

  @Override
  public boolean isConnectableToPrev(Node n) { 
    return false;
  }
  
  @Override
  public boolean isConnectableToNext(Node n) { 
    return n instanceof MiningModuleNode;
  }
  
  @Override
  public boolean isConnectableTo(Node n) { 
    return this.isConnectableToNext(n);
  }
}