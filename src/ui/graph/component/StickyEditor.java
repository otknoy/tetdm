package ui.graph.component;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class StickyEditor extends JFrame implements MouseListener {

  private final Sticky sticky;
  private final JTextArea textArea;


  public StickyEditor(Sticky sticky) {
    this.sticky = sticky;

    this.setSize(480, 360);
    this.setLayout(new BorderLayout());

    // scrollable text area
    this.textArea = new JTextArea(this.sticky.getText());
    textArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(this.textArea);
    this.add(scrollPane, BorderLayout.CENTER);

    // save button
    JButton saveButton = new JButton("save");
    saveButton.addMouseListener(this);
    this.add(saveButton, BorderLayout.SOUTH);
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    sticky.setText(textArea.getText());
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
