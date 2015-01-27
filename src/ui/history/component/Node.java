package ui.history.component;

import java.util.List;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;


public class Node extends JPanel {

  public static final int WIDTH  = 64;
  public static final int HEIGHT = 48;

  private List<Node> nextNodes;
  private boolean isSelected;


  public Node() {
    this.nextNodes = new ArrayList<Node>();
    this.setSize(Node.WIDTH, Node.HEIGHT);
  }


  @Override public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    // border
    if (this.isSelected) {
      g2.setColor(Color.red);
      BasicStroke wideStroke = new BasicStroke(3.0f);
      g2.setStroke(wideStroke);
    } else {
      g2.setColor(Color.black);
      BasicStroke wideStroke = new BasicStroke(1.0f);
      g2.setStroke(wideStroke);
    }
    g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
  }


  public boolean isSelected() { return this.isSelected; }
  public void selected(boolean selected) { this.isSelected = selected; }
  public List<Node> getNextNodes() { return this.nextNodes; }
  public void addToNext(Node n) { this.nextNodes.add(n); }
 

  /**
   * draw edges to next nodes
   * @param targed Graphics
   */
  public void drawEdgesToNext(Graphics g) {
    Point p1 = this.getLocation();
    int startX = (int)(p1.getX() + this.getWidth());
    int startY = (int)(p1.getY() + this.getHeight()/2);
    for (Node n : this.nextNodes) {
      Point p2 = n.getLocation();
      int endX = (int)p2.getX();
      int endY = (int)(p2.getY() + this.getHeight()/2);

      g.setColor(Color.black);
      g.drawLine(startX, startY, endX, endY);

      n.drawEdgesToNext(g);
    }
  }

  public int align(Point p, int level, int standardRow) {
    // align children
    int i = standardRow;
    int sum = 0;
    for (Node node : this.nextNodes) {
      int n = node.align(p, level+1, i);
      sum += n;
      i += n;
    }

    // align this
    Point location = new Point((int)(p.x + (      level * 1.5*this.WIDTH )),
			       (int)(p.y + (standardRow * 1.5*this.HEIGHT)));
    
    this.setLocation(location);

    if (this.nextNodes.isEmpty()) {
      return 1;
    } else {
      return sum;
    }
  }
  

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
