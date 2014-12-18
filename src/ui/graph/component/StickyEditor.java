package ui.graph.component;

import ui.graph.StickyManager;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JColorChooser;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class StickyEditor extends JFrame implements MouseListener {

  private final StickyManager stickyManager;
  private final Sticky sticky;
  private final JTextArea textArea;
  private final JButton saveButton;
  private final JButton selectColorButton;
  private final JButton removeButton;


  public StickyEditor(StickyManager stickyManager, Sticky sticky) {
    this.stickyManager = stickyManager;
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

    // change color button
    this.selectColorButton = new JButton("change color");
    this.selectColorButton.addMouseListener(this);
    uiPanel.add(this.selectColorButton);

    // remove button
    this.removeButton = new JButton("remove");
    this.removeButton.addMouseListener(this);
    uiPanel.add(this.removeButton);

    this.add(uiPanel, BorderLayout.SOUTH);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Component src = (Component)e.getSource();
    if (src == this.saveButton) {
      this.sticky.setText(this.textArea.getText());
    } else if (src == this.removeButton) {
      this.stickyManager.remove(this.sticky);
    } else if (src == this.selectColorButton) {
      Color c = JColorChooser.showDialog(this, "Select color", sticky.getBgColor());
      if (c == null) return;
      sticky.setBgColor(c);
    }

    // Close sticky editor
    this.dispose();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
