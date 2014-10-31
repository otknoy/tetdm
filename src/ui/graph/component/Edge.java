package ui.graph.component;

import ui.graph.ModuleSelectPanel;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Point;
import java.awt.Dimension;

public class Edge extends JComponent {

  static private int OFFSET = 8;
  private Node parent;
  private Node child;


  public Edge(Node parent, Node child) {
    this.parent = parent;
    this.child = child;

    update();
  }

  
  @Override
  public void paintComponent(Graphics g) {
    // g.setColor(Color.gray);
    // g.drawRect(0, 0, getWidth()-1, getHeight()-1);    

    drawConnectedLine(g);

    update();    
  }

  private void update() {
    Point p1 = parent.getCenterPosition();
    Point p2 = child.getCenterPosition();

    int vx = p2.x - p1.x;
    int vy = p2.y - p1.y;
    double rad =  Math.atan2(vy, vx);
    int diameter = (int)Math.sqrt(vx*vx + vy*vy);

    int cx = (p2.x + p1.x) / 2;
    int cy = (p2.y + p1.y) / 2;
    int x = cx - diameter/2;
    int y = cy - diameter/2;

    setBounds(x-OFFSET, y-OFFSET, 2*OFFSET + diameter, 2*OFFSET + diameter);
  }

  private void drawConnectedLine(Graphics g) {
    Point p1 = parent.getCenterPosition();
    Point p2 = child.getCenterPosition();

    int vx = p2.x - p1.x;
    int vy = p2.y - p1.y;
    double rad = rad = Math.atan2(vy, vx);
    int diameter = (int)Math.sqrt(vx*vx + vy*vy);


    Point start = new Point(p1.x - getX(),
			    p1.y - getY());
    Point end = new Point(p2.x - getX(),
			  p2.y - getY());

    g.setColor(Color.black);
    g.drawLine(start.x, start.y, end.x, end.y);
  }

  // public Node[] getNodes() {
  //   return new Node[] {this.parent, this.child};
  // }

  public Node getStartNode() { return this.parent; }
  public Node getEndNode() { return this.child; }  

  public boolean contains(Node n) {
    return (this.parent == n) || (this.child == n);
  }

  public boolean contains(Node n1, Node n2) {
    return
      (this.parent == n1 && this.child == n2) ||
      (this.parent == n2 && this.child == n1);
  }
}
