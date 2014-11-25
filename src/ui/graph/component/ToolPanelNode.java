package ui.graph.component;

import java.awt.Color;
import java.awt.Point;


public class ToolPanelNode extends Node {

  private static final String NAME_PREFIX = "Panel";
  private static final Color BG_COLOR = new Color(255, 255, 255, 128);

  private final int panelNumber;
  

  public ToolPanelNode(int panelNumber, Point location) {
    super(ToolPanelNode.NAME_PREFIX + " " + panelNumber, -1, BG_COLOR, location);
    this.panelNumber = panelNumber;
  }

  public ToolPanelNode(int panelNumber) {
    this(panelNumber, new Point(0, 0));
  }

  public int getPanelNumber() { return this.panelNumber; }


  @Override
  public ToolPanelNode clone() {
    ToolPanelNode n = new ToolPanelNode(this.panelNumber);
    n.setLocation(this.getLocation());
    n.selected(this.isSelected());
    n.highlighted(this.isHighlighted());
    return n;
  }

  @Override
  public boolean isConnectableToPrev(Node n) {
    return n instanceof VisualizationModuleNode;
  }

  @Override
  public boolean isConnectableToNext(Node n) {
    return false;
  }

  @Override
  public boolean isConnectableTo(Node n) {
    return this.isConnectableToPrev(n) || this.isConnectableToNext(n);
  }
}