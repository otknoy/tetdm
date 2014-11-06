package ui.graph.component;

import ui.graph.component.event.MouseDragAndDropListener;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JComponent;


public class Node extends JComponent {

  private String name;
  private int id;

  private Color bgColor;

  private List<Node> prevNodes;
  private List<Node> nextNodes;

  private boolean isSelected;


  public Node(String name, int id, Color bgColor, Point location) {
    this.name = name;
    this.id = id;
    this.bgColor = bgColor;
    this.prevNodes = new ArrayList<Node>();
    this.nextNodes = new ArrayList<Node>();
    this.setLocation(location);

    // select

    // drag & drop 
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(this);
    this.addMouseListener(mddl);
    this.addMouseMotionListener(mddl);
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
    // background
    g.setColor(this.bgColor);
    g.fillRect(0, 0, getWidth()-1, getHeight()-1);

    // border
    if (this.isSelected()) {
      g.setColor(Color.black);
    } else {
      g.setColor(Color.gray);
    }
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);

    // label
    g.setColor(Color.black);
    g.drawString(this.getName(), 2, this.getHeight()/2);
  }


  public String getName() { return this.name; }
  public int getId() { return this.id; }

  
  public List<Node> getPrevNodes() { return this.prevNodes; }
  public void addPrevNodes(Node n) { this.prevNodes.add(n); }
  public void removePrevNode(Node n) { this.prevNodes.remove(n); }
  public void removeAllPrevNodes() { this.prevNodes = new ArrayList<Node>(); }
  
  public List<Node> getNextNodes() { return this.nextNodes; }
  public void addNextNodes(Node n) { this.nextNodes.add(n); }
  public void removeNextNode(Node n) { this.nextNodes.remove(n); }
  public void removeAllNextNodes() { this.nextNodes = new ArrayList<Node>(); }


  public boolean isSelected() { return this.isSelected; }
  public void selected(boolean selected) { this.isSelected = selected; }

  
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
}