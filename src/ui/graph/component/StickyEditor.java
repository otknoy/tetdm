package ui.graph.component;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class StickyEditor extends JFrame {

  private Sticky sticky;


  public StickyEditor(Sticky sticky) {
    this.sticky = sticky;

    this.setSize(480, 360);
    this.setLayout(new BorderLayout());

    // scrollable text area
    JTextArea textArea = new JTextArea(this.sticky.getText());
    textArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    this.add(scrollPane, BorderLayout.CENTER);

    // save button
    JButton saveButton = new JButton("save");
    this.add(saveButton, BorderLayout.SOUTH);
  }
}