package ui;

import ui.Interface;
import ui.history.data.State;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


class ManipulationPanel extends JPanel implements MouseListener {

  private final Interface parent;

  private final JButton addStickyButton;

  private final JButton goodButton;
  private final JButton normalButton;
  private final JButton badButton;


  ManipulationPanel(Interface parent) {
    this.parent = parent;

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    // Sticky
    this.addStickyButton = new JButton("Add sticky");
    this.addStickyButton.addMouseListener(this);
    this.add(addStickyButton);


    // Rating buttons for savin history
    this.goodButton = new JButton("Good");
    this.goodButton.addMouseListener(this);
    this.add(this.goodButton);

    this.normalButton = new JButton("Normal");
    this.normalButton.addMouseListener(this);
    this.add(this.normalButton);

    this.badButton = new JButton("Bad");
    this.badButton.addMouseListener(this);
    this.add(this.badButton);
  }




  @Override
  public void mouseClicked(MouseEvent e) {
    Object src = e.getSource();
    if (!(src instanceof JButton)) {
      return;
    }

    JButton b = (JButton)src;
    if (b == this.addStickyButton) {
      String text = b.getText();
      this.parent.mainPanel.getGraphInterface().addSticky();
      this.parent.mainPanel.getGraphInterface().repaint();
    } else if (b == this.goodButton) {
      this.parent.saveHistory(State.RATE_GOOD);
    } else if (b == this.badButton) {
      this.parent.saveHistory(State.RATE_BAD);
    } else {
      this.parent.saveHistory(State.RATE_NORMAL);
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
