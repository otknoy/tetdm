package ui.graph.component;

import ui.graph.module.ModuleInfo;
import ui.graph.component.event.MouseDragAndDropListener;

import java.awt.Component;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;
import java.util.EventListener;


public abstract class Node extends JComponent {

  private ModuleInfo moduleInfo;

  private Node previousNode;
  private List<Node> nextNodes;
  
  private Color bgColor;
  private boolean selected;
  private boolean highlighted;

  private MouseDragAndDropListener mddl;
  

  public Node(ModuleInfo moduleInfo, Point location, Color bgColor) {
    this.moduleInfo = moduleInfo;
    this.setLocation(location);
    this.bgColor = bgColor;

    this.setSize(new Dimension(64, 32));

    this.nextNodes = new ArrayList<Node>();

    // for drag and drop
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(this);
    this.addMouseListener(mddl);
    this.addMouseMotionListener(mddl);
  }

  public Node(ModuleInfo moduleInfo, Color bgColor) {
    this(moduleInfo, new Point(0, 0), bgColor);
  }
  
  @Override
  public String toString() {
    return this.getName() + "[" + previousNode + "]" + "[" + this.nextNodes.size() + "]";
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    // background
    if (this.isHighlighted()) {
      Color c = this.bgColor;
      Color cMaxAlpha = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
      g.setColor(cMaxAlpha);
    } else {
      g.setColor(this.bgColor);
    }
    g.fillRect(0, 0, getWidth()-1, getHeight()-1);
    
    // border
    g.setColor(Color.black);
    if (this.isSelected()) {
      BasicStroke wideStroke = new BasicStroke(3.0f);
      g2.setStroke(wideStroke);
    } else {
      BasicStroke wideStroke = new BasicStroke(1.0f);
      g2.setStroke(wideStroke);
    }
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);

    // label
    g.setColor(Color.black);
    g.drawString(this.getName(), 2, this.getHeight()/2);
    // g.drawString(this.getID() + " " + this.getName(), 2, this.getHeight()/2);    
  }

  public void drawEdgesToNextNode(Graphics g) {
    Point p1 = this.getLocation();
    int startX = (int)(p1.getX() + this.getWidth());
    int startY = (int)(p1.getY() + this.getHeight()/2);
    for (Node n : this.nextNodes) {
      Point p2 = n.getLocation();
      int endX = (int)p2.getX();
      int endY = (int)(p2.getY() + this.getHeight()/2);

      g.setColor(Color.black);
      g.drawLine(startX, startY, endX, endY);
    }
  }
    
 
  public ModuleInfo getModuleInfo() { return this.moduleInfo; }
  public String getName() { return this.moduleInfo.getName(); }
  public int getID() { return this.moduleInfo.getID(); }
  public int getType() { return this.moduleInfo.getType(); }

  public Node getPreviousNode() { return this.previousNode; }
  public void setPreviousNode(Node n) { this.previousNode = n; }
  public void removePreviousNode(Node n) { if (this.previousNode == n) this.previousNode = null; }  
  public List<Node> getNextNodes() { return this.nextNodes; }
  public void addNextNode(Node n) { this.nextNodes.add(n); }
  public void addNextNodes(List<Node> nodes) { for (Node n : nodes) this.addNextNode(n); }  
  public void removeNextNode(Node n) { this.nextNodes.remove(n); }
  public void removeAllNextNodes() { this.nextNodes = new ArrayList<Node>(); }

  public boolean isSelected() { return this.selected; }
  public void selected(boolean selected) { this.selected = selected; this.repaint(); }
  public boolean isHighlighted() { return this.highlighted; }
  public void highlighted(boolean highlighted) { this.highlighted = highlighted; this.repaint(); }

  public boolean isConnectableTo(Node n) {
    return this.getModuleInfo().isConnectableTo(n.getModuleInfo());
  }

  public boolean isConnectableToPrevious(Node n) {
    return this.getModuleInfo().isConnectableToPrevious(n.getModuleInfo());
  }

  public boolean isConnectableToNext(Node n) {
    return this.getModuleInfo().isConnectableToNext(n.getModuleInfo());
  }

  public boolean isConnectedTo(Node n1) {
    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.previousNode);
    nodes.addAll(this.nextNodes);
    for (Node n2 : nodes)
      if (n1 == n2) return true;
    return false;
  }


  public Point getCenterPosition() {
    int x = (int)(getX() + (getWidth()/2));
    int y = (int)(getY() + (getHeight()/2));
    return new Point(x, y);
  }

  public double distance(Node n) {
    return this.getCenterPosition().distance(n.getCenterPosition());
  }
}
