package ui.graph.component;

import ui.graph.GraphPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class StickyEditor extends JFrame implements MouseListener {

  private final Sticky sticky;
  private final JTextArea textArea;
  private final JButton saveButton;
  private final JButton removeButton;


  public StickyEditor(Sticky sticky) {
    this.sticky = sticky;

    this.setSize(480, 360);
    this.setLayout(new BorderLayout());

    // scrollable text area
    this.textArea = new JTextArea(this.sticky.getText());
    textArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(this.textArea);
    this.add(scrollPane, BorderLayout.CENTER);


    // ui panel
    JPanel uiPanel = new JPanel();

    // save button
    this.saveButton = new JButton("save");
    this.saveButton.addMouseListener(this);
    uiPanel.add(this.saveButton);

    // remove button
    this.removeButton = new JButton("remove");
    this.removeButton.addMouseListener(this);
    uiPanel.add(this.removeButton);

    this.add(uiPanel, BorderLayout.SOUTH);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Component c = (Component)e.getSource();
    if (c == this.saveButton) {
      this.sticky.setText(this.textArea.getText());
    } else if (c == this.removeButton) {
      GraphPanel p = (GraphPanel)this.sticky.getParent();
      p.removeSticky(this.sticky);
      p.repaint();
    }

    // Close sticky editor
    this.dispose();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
