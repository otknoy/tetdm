package ui.graph.component;

import java.awt.Color;
import java.awt.Point;

public class PreprocessNode extends Node {

  private static final String NAME = "Preprocess";
  private static final Color BG_COLOR = new Color(255, 255, 0, 128);


  public PreprocessNode(Point location) {
    super(NAME, -1, BG_COLOR, location);
  }

  public PreprocessNode() {
    super(NAME, -1, BG_COLOR);
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