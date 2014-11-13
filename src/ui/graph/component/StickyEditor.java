package ui.graph.component;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class StickyEditor extends JFrame {

  private Sticky sticky;
  

  public StickyEditor(Sticky sticky) {
    this.sticky = sticky;

    this.setPreferredSize(new Dimension(480, 480));

    JPanel p = new JPanel();
    
    JTextArea textArea = new JTextArea("test");
    JScrollPane scrollPane = new JScrollPane(textArea);
    p.add(scrollPane);
    
    this.add(p);
  }
}