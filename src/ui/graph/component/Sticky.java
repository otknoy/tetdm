package ui.graph.component;

import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.event.StickyEventListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


public class Sticky extends JPanel {

  public static final int WIDTH  = 128;
  public static final int HEIGHT = 128;

  private String text;
  private Color bgColor;
  
  public Sticky(String text, Color bgColor) {
    this.text = text;
    this.bgColor = bgColor;

    this.setSize(new Dimension(this.WIDTH, this.HEIGHT));    

    // drag & drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(this);
    this.addMouseListener(mddl);
    this.addMouseMotionListener(mddl);

    // mouse event
    StickyEventListener sel = new StickyEventListener(this);
    this.addMouseListener(sel);
  }

  public Sticky(String text) {
    this(text, Color.white);
  }

  public Sticky(Color bgColor) {
    this("", bgColor);
  }

  public Sticky() {
    this(Color.white);
  }


  @Override
  public void paintComponent(Graphics g) {
    // background
    g.setColor(this.bgColor);
    g.fillRect(0, 0, getWidth()-1, getHeight()-1);

    // border
    g.setColor(Color.black);
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);

    // text
    int padding = 12;
    this.drawText(g, padding);
  }


  /**
   * Draw auto-formatted text with padding into this panel
   * @param Graphics
   * @param padding
   */
  private void drawText(Graphics g, int padding) {
    Rectangle r = new Rectangle(padding, padding, getWidth()-2*padding, getHeight()-2*padding);

    FontMetrics fm = g.getFontMetrics();
    int fmAscent = fm.getAscent();
    int fmHeight = fm.getHeight();

    List<String> lines = new ArrayList<String>();
    String line = "";
    for (int i = 0; i < this.text.length(); i++) {
      char c = this.text.charAt(i);
      String nl = line + c;
      if (!(fm.stringWidth(nl) <= r.width)) {
	lines.add(line);
	line = "";
      }
      line += c;
    }

    g.setColor(Color.black);
    for (int i = 0; i < lines.size(); i++) {
      int y = r.y + fmAscent + i*fmHeight;
      g.drawString(lines.get(i), r.x, y);
    }
  }
}