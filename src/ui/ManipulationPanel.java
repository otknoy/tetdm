package ui;

import ui.MainPanel;
import ui.history.HistoryTreePanel;
import ui.history.component.History;
import ui.history.data.State;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


class ManipulationPanel extends JPanel implements MouseListener {

  private final MainPanel mainPanel;
  private final HistoryTreePanel historyTreePanel;

  private final JButton addStickyButton;

  private final JButton goodButton;
  private final JButton normalButton;
  private final JButton badButton;


  ManipulationPanel(MainPanel mainPanel, HistoryTreePanel historyTreePanel) {
    this.mainPanel = mainPanel;
    this.historyTreePanel = historyTreePanel;

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

    // Add sticky
    if (b == this.addStickyButton) {
      String text = b.getText();
      this.mainPanel.getGraphInterface().addSticky();
      this.mainPanel.getGraphInterface().repaint();

      return;
    }

    // Save history
    // HistoryTreePanel.addHistory(h)
    int rate = -1;
    if (b == this.goodButton) {
      rate = State.RATE_GOOD;
    } else if (b == this.badButton) {
      rate = State.RATE_BAD;
    } else {
      rate = State.RATE_NORMAL;
    }
    State s = this.mainPanel.getGraphInterface().getCurrentState(rate);
    this.historyTreePanel.addHistory(new History(s));
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
