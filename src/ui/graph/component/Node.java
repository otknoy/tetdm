package ui.graph.component;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import javax.swing.JComponent;


public abstract class Node extends JComponent {

  private String name;
  private int id;

  private Color bgColor;

  private List<Node> prevNodes;
  private List<Node> nextNodes;

  private boolean isSelected;
  private boolean isHighlighted;

  public static final int WIDTH  = 64;
  public static final int HEIGHT = 32;


  public Node(String name, int id, Color bgColor, Point location) {
    this.name = name;
    this.id = id;
    this.bgColor = bgColor;

    this.setSize(new Dimension(this.WIDTH, this.HEIGHT));
    this.setLocation(location);

    this.prevNodes = new ArrayList<Node>();
    this.nextNodes = new ArrayList<Node>();

  }

  public Node(String name, int id, Color bgColor) {
    this(name, id, bgColor, new Point(0, 0));
  }


  @Override
  public String toString() {
    return this.getName() + "[" + this.prevNodes.size() + "]" + "[" + this.nextNodes.size() + "]";
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    // background
    if (this.isHighlighted()) {
      Color c = this.bgColor;
      Color cMaxAlpha = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
      g2.setColor(cMaxAlpha);
    } else {
      g2.setColor(this.bgColor);
    }
    g2.fillRect(0, 0, getWidth()-1, getHeight()-1);

    // border
    g2.setColor(Color.black);
    if (this.isSelected()) {
      BasicStroke wideStroke = new BasicStroke(3.0f);
      g2.setStroke(wideStroke);
    } else {
      BasicStroke wideStroke = new BasicStroke(1.0f);
      g2.setStroke(wideStroke);
    }
    g2.drawRect(0, 0, getWidth()-1, getHeight()-1);

    // label
    g2.setColor(Color.black);
    g2.drawString(this.getName(), 2, this.getHeight()/2);
  }

  /**
   * draw edges to next nodes
   * @param targed Graphics
   */
  public void drawEdgesToNextNodes(Graphics g) {
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

  public String getName() { return this.name; }
  public int getId() { return this.id; }

  
  public List<Node> getPrevNodes() { return this.prevNodes; }
  public void addPrevNodes(Node n) { this.prevNodes.add(n); }
  public void removePrevNode(Node n) { this.prevNodes.remove(n); }
  public void removeAllPrevNodes() { this.prevNodes = new ArrayList<Node>(); }
  public boolean isConnectedToPrev(Node n) { return this.prevNodes.contains(n); }
  
  public List<Node> getNextNodes() { return this.nextNodes; }
  public void addNextNodes(Node n) { this.nextNodes.add(n); }
  public void removeNextNode(Node n) { this.nextNodes.remove(n); }
  public void removeAllNextNodes() { this.nextNodes = new ArrayList<Node>(); }
  public boolean isConnectedToNext(Node n) { return this.nextNodes.contains(n); }

  public boolean isConnectedTo(Node n) {
    return this.isConnectedToPrev(n) || this.isConnectedToNext(n);
  }

  public boolean isSelected() { return this.isSelected; }
  public void selected(boolean selected) { this.isSelected = selected; }
  public boolean isHighlighted() { return this.isHighlighted; }
  public void highlighted(boolean highlighted) { this.isHighlighted = highlighted; }

  
  /**
   * get center point of Node
   * @return center point
   */
  public Point getCenterPosition() {
    int x = (int)(getX() + (getWidth()/2));
    int y = (int)(getY() + (getHeight()/2));
    return new Point(x, y);
  }

  /**
   * calculate distance to Node n
   * @param destination node
   * @return distance
   */
  public double distance(Node n) {
    return this.getCenterPosition().distance(n.getCenterPosition());
  }

  public abstract boolean isConnectableToPrev(Node n);
  public abstract boolean isConnectableToNext(Node n);
  public abstract boolean isConnectableTo(Node n);
}