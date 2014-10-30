package tetdm.history;

import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.Point;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;


public class History<T> extends JComponent{

  public static final int WIDTH  = 40;
  public static final int HEIGHT = 30;
  
  private T data;
  private History previous;
  private List<History> next;  
  private BufferedImage screenCapture;
  private boolean selected;

  
  public History(T data, History previous) {
    this.data = data;
    this.previous = previous;
    this.next = new ArrayList<History>();
    this.setSize(new Dimension(History.WIDTH, History.HEIGHT));

    // save screen capture
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
    int width  = displayMode.getWidth();
    int height = displayMode.getHeight();
    Robot r;
    try {
      r = new Robot();
      this.screenCapture = r.createScreenCapture(new Rectangle(width, height));

      this.screenCapture = this.resizeBufferedImage(this.screenCapture, History.WIDTH, History.HEIGHT);
    } catch(AWTException e) {
      e.printStackTrace();
    }
  }


  private BufferedImage resizeBufferedImage(BufferedImage img, int w, int h) {
    BufferedImage resized = new BufferedImage(w, h, img.getType());
    Graphics2D g2 = resized.createGraphics();
    g2.drawImage(img, 0, 0, w, h, null);
    g2.dispose();
    return resized;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;

    g.drawImage(this.screenCapture, 0, 0, this);

    // draw border
    if (this.isSelected()) {
      BasicStroke wideStroke = new BasicStroke(3.0f);
      g2.setColor(Color.red);
      g2.setStroke(wideStroke);
    }
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
  }


  public void drawEdge(Graphics g) {
    Point p1 = this.getLocation();
    int startX = (int)(p1.getX() + this.getWidth());
    int startY = (int)(p1.getY() + this.getHeight()/2);
    for (History h : this.next) {
      Point p2 = h.getLocation();
      int endX = (int)p2.getX();
      int endY = (int)(p2.getY() + this.getHeight()/2);

      g.setColor(Color.black);
      g.drawLine(startX, startY, endX, endY);

      h.drawEdge(g);
    }
  }

  public void moveTo(int x, int y) {
    this.setLocation(x, y);
    int i = 0;
    for (History h : this.next) {
      h.moveTo((int)(x + 1.5*this.WIDTH),
    	       (int)(y + i*1.5*this.HEIGHT));
      i++;
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

    if (this.next.size() == 0) {
      return 1;
    } else {
      return sum;
    }
  }

  public T getData() { return this.data; }

  public void addToNext(History h) { this.next.add(h); }

  public List<History> getAllHistroy() {
    List<History> list = new ArrayList<History>();
    list.add(this);

    for (History h : this.next) {
      list.addAll(h.getAllHistroy());
    }

    return list;
  }

  public void selected(boolean selected) { this.selected = selected; }
  public boolean isSelected() { return this.selected; }

  public Point getCenterPosition() {
    int x = (int)(getX() + (getWidth()/2));
    int y = (int)(getY() + (getHeight()/2));
    return new Point(x, y);
  }
}
