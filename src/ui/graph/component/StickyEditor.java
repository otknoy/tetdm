package ui.graph.component;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class StickyEditor extends JFrame {

  private Sticky sticky;

  public static final int WIDHT  = 480;
  public static final int HEIGHT = 480;


  public StickyEditor(Sticky sticky, int x, int y) {
    this.sticky = sticky;

    this.setBounds(x, y,StickyEditor.WIDHT, StickyEditor.HEIGHT);

    JPanel p = new JPanel();
    
    JTextArea textArea = new JTextArea("test");
    JScrollPane scrollPane = new JScrollPane(textArea);
    p.add(scrollPane);
    
    this.add(p);
  }
}