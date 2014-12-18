package ui.graph;

import ui.graph.component.Sticky;
import ui.graph.module.ModuleManager;
import ui.history.HistoryTreePanel;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BoxLayout;


public class ModuleSelectPanel extends JPanel implements MouseListener {

  static final String NAME = "Module Select Panel";

  private final ModuleManager moduleManager;

  private final JPanel uiPanel;
  private GraphPanel graphPanel;
  private final HistoryTreePanel<GraphPanel> historyTreePanel;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setLayout(new BorderLayout());

    // graph panel
    this.graphPanel = new GraphPanel(moduleManager);
    this.add(this.graphPanel, BorderLayout.CENTER);

    // ui panel
    this.uiPanel = new JPanel();
    this.uiPanel.setLayout(new BoxLayout(this.uiPanel, BoxLayout.X_AXIS));

    // sticky button
    JButton addStickyButton = new JButton("Add Sticky");
    addStickyButton.addMouseListener(this);
    this.uiPanel.add(addStickyButton);

    
    // history panel
    JFrame hf = new JFrame();
    this.historyTreePanel = new HistoryTreePanel<GraphPanel>(this, this.graphPanel);
    hf.add(historyTreePanel);
    hf.setSize(800, 450);
    hf.setVisible(true);


    // rating buttons
    RatingButtonPanel ratingButtonPanel = new RatingButtonPanel(this.graphPanel, this.historyTreePanel);
    this.uiPanel.add(ratingButtonPanel);

    this.add(this.uiPanel, BorderLayout.SOUTH);
 }


  @Override
  public void mouseClicked(MouseEvent e) {
    Object src = e.getSource();
    if (src instanceof JButton) {
      JButton b = (JButton)src;
      String text = b.getText();
      if (text == "Add Sticky") {
	Sticky sticky = new Sticky();
	sticky.setLocation((this.graphPanel.getWidth()  - Sticky.WIDTH)  / 2,
			   (this.graphPanel.getHeight() - Sticky.HEIGHT) / 2);
	this.graphPanel.addSticky(sticky);
	this.graphPanel.repaint();
      }
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}

  public void changeGraphPanel(GraphPanel gp) {
    this.remove(this.graphPanel);
    this.graphPanel = gp.clone();
    this.add(this.graphPanel, BorderLayout.CENTER);
    this.graphPanel.repaint();
    this.revalidate();
  }
}