package ui.history.component;

import ui.history.data.State;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import javax.swing.JPanel;


public abstract class Node extends JPanel {

  public static final int WIDTH  = 64;
  public static final int HEIGHT = 48;

  private Node prev;
  private List<Node> nextList;

  private boolean isSelected;


  public Node(Node prev) {
    this.prev = prev;
    this.nextList = new ArrayList<Node>();

    this.setSize(Node.WIDTH, Node.HEIGHT);
  }

  @Override public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    // border
    g2.setColor(Color.black);
    if (this.isSelected) {
      BasicStroke wideStroke = new BasicStroke(3.0f);
      g2.setStroke(wideStroke);
    } else {
      BasicStroke wideStroke = new BasicStroke(1.0f);
      g2.setStroke(wideStroke);
    }
    g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
  }


  public boolean isSelected() { return this.isSelected; }
  public void selected(boolean selected) { this.isSelected = selected; }
  public void addToNextList(Node n) { this.nextList.add(n); }
  

  /**
   * draw edges to next nodes
   * @param targed Graphics
   */
  public void drawEdgesToNextNodes(Graphics g) {
    Point p1 = this.getLocation();
    int startX = (int)(p1.getX() + this.getWidth());
    int startY = (int)(p1.getY() + this.getHeight()/2);
    for (Node n : this.nextList) {
      Point p2 = n.getLocation();
      int endX = (int)p2.getX();
      int endY = (int)(p2.getY() + this.getHeight()/2);

      g.setColor(Color.black);
      g.drawLine(startX, startY, endX, endY);
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
