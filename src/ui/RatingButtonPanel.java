package ui;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.HistoryTreePanel;
import ui.history.data.State;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class RatingButtonPanel extends JPanel implements MouseListener {

  private final Interface parent;
  private final HistoryTreePanel historyTreePanel;

  private static JButton goodButton;
  private static JButton normalButton;
  private static JButton badButton;


  public RatingButtonPanel(Interface parent, HistoryTreePanel historyTreePanel) {
    this.parent = parent;
    this.historyTreePanel = historyTreePanel;

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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
    JButton b = (JButton)e.getSource();

    int rate;
    if (b == this.goodButton) {
      rate = State.RATE_GOOD;
    } else if (b == this.badButton) {
      rate = State.RATE_BAD;
    } else {
      rate = State.RATE_NORMAL;
    }

    // this.parent.saveHistory(rate);
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
