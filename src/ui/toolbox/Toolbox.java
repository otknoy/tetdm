package ui.toolbox;

import java.awt.*;
import javax.swing.*;


public class Toolbox extends JPanel {

  public Toolbox() {
    JPanel p = new JPanel();
    
    p.setLayout(new FlowLayout());
    for (int i = 0; i < 100; i++) {
      p.add(new JButton("test" + (i+1)));
    }


    JScrollPane scrollpane = new JScrollPane(p,
					     JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					     JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollpane);
  }
}