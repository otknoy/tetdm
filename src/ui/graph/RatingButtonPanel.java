package ui.graph;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class RatingButtonPanel extends JPanel implements MouseListener {

  private static GraphPanel graphPanel;

  private static JButton goodButton;
  private static JButton normalButton;
  private static JButton badButton;
  

  public RatingButtonPanel(GraphPanel graphPanel) {
    this.graphPanel = graphPanel;
    
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
    Object src = e.getSource();
    if (src == this.goodButton) {
      GraphPanel gp = this.graphPanel.clone();
      JFrame f = new JFrame();
      f.add(gp);
      f.pack();
      f.setVisible(true);
    } else if (src == this.normalButton) {
      return;
    } else if (src == this.badButton) {
      return;
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}