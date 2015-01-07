package ui.history.component;

import ui.util.ScreenCapture;
import ui.graph.GraphInterface;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import java.awt.AWTException;
import java.awt.image.BufferedImage;


public class History extends JPanel {

  public static final int WIDTH  = 64;
  public static final int HEIGHT = 48;

  private List<History> next;
  private boolean isSelected;

  private GraphInterface gi;
  private int rate;
  private BufferedImage screenCapture;

  public static final int RATE_BAD    = 0;
  public static final int RATE_NORMAL = 1;
  public static final int RATE_GOOD   = 2;


  public History(GraphInterface gi, int rate) {
    this.gi = gi;
    this.rate = rate;

    this.next = new ArrayList<History>();
    this.setSize(History.WIDTH, History.HEIGHT);
    this.setToolTipText("");

    // save screen capture
    try {
      this.screenCapture = ScreenCapture.getImage();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }


  @Override public JToolTip createToolTip() {
    return new ToolTip(this.screenCapture);
  }


  @Override public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    // draw screen capture
    BufferedImage resized = ScreenCapture.resize(this.screenCapture, History.WIDTH, History.HEIGHT);
    g.drawImage(resized, 0, 0, this);

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
  public List<History> getNext() { return this.next; }
  public void addToNext(History n) { this.next.add(n); }

  public GraphInterface getGraphInterface() { return this.gi; }
  public int getRate() { return this.rate; }
 

  /**
   * draw edges to next nodes
   * @param targed Graphics
   */
  public void drawEdgesToNext(Graphics g) {
    Point p1 = this.getLocation();
    int startX = (int)(p1.getX() + this.getWidth());
    int startY = (int)(p1.getY() + this.getHeight()/2);
    for (History n : this.next) {
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
    for (History h : this.next) {
      int n = h.align(p, level+1, i);
      sum += n;
      i += n;
    }

    // align this
    Point location = new Point((int)(p.x + (      level * 1.5*this.WIDTH )),
			       (int)(p.y + (standardRow * 1.5*this.HEIGHT)));
    
    this.setLocation(location);

    if (this.next.isEmpty()) {
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
  public double distance(History h) {
    return this.getCenterPosition().distance(h.getCenterPosition());
  }
}