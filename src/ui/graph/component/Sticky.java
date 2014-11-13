package ui.graph.component;

import ui.graph.component.event.MouseDragAndDropListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


public class Sticky extends JPanel {

  public static final int WIDTH  = 128;
  public static final int HEIGHT = 128;

  private String text;
  private Color bgColor;
  
  public Sticky(String text, Color bgColor) {
    this.text = text;
    this.bgColor = bgColor;

    this.setSize(new Dimension(this.WIDTH, this.HEIGHT));    

    // JTextArea textArea = new JTextArea(memo);
    // JScrollPane scrollPane = new JScrollPane(textArea);
    // scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

    // this.setLayout(new BorderLayout(8, 8));
    // this.add("Center", scrollPane);

    // drag & drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(this);
    this.addMouseListener(mddl);
    this.addMouseMotionListener(mddl);
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
  }
}