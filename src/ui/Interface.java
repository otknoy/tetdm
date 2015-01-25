package ui;

import tetdm.TETDM;
import tetdm.PanelState;
import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.history.HistoryTreePanel;
import ui.history.component.History;
import ui.history.data.State;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.util.List;
import java.util.ArrayList;


public class Interface extends JFrame implements MouseListener {

  public static final String NAME = "Tool Selector";

  private final TETDM tetdm;

  private final MainPanel mainPanel;
  private final HistoryTreePanel historyTreePanel;


  public Interface(TETDM tetdm) {
    this.tetdm = tetdm;

    this.setTitle(Interface.NAME);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());


    // menu bar
    // this.add(hoge, BorderLayout.NORTH);


    // Main panel: toolbox and graph interface
    this.mainPanel = new MainPanel(tetdm);
    this.add(mainPanel, BorderLayout.CENTER);

    // history tree frame
    State s = new State(this.mainPanel.getGraphInterface().clone(),
			State.RATE_NORMAL,
			this.tetdm.getInputFilename(),
			this.tetdm.getPanelStates());
    History h = new History(s);

    JFrame f = new JFrame("History tree");
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.historyTreePanel = new HistoryTreePanel(this, h);
    this.historyTreePanel.setPreferredSize(new Dimension(800, 250));
    f.add(this.historyTreePanel);
    f.pack();
    f.setVisible(true);


    // manipulation panel
    JPanel mp = new JPanel();
    // add sticky
    JButton addStickyButton = new JButton("Add sticky");
    addStickyButton.addMouseListener(this);
    mp.add(addStickyButton);
    // Rating buttons for saving history
    RatingButtonPanel ratingButtonPanel = new RatingButtonPanel(this, this.historyTreePanel);
    mp.add(ratingButtonPanel);

    this.add(mp, BorderLayout.SOUTH);
    this.pack();
  }


  public void restoreHistory(History h) {
    this.mainPanel.changeGraphInterface(h.getGraphInterface().clone());

    String filename = h.getInputFilename();
    if (filename != this.tetdm.getInputFilename()) {
      this.tetdm.loadFile(filename);
    }

    for (PanelState ps : h.getPanelStates()) {
      this.tetdm.setToolsToPanel(ps);
    }
  }


  /**
   * Save history of GraphInterface to history tree
   * @param rate
   */
  public void saveHistory(int rate) {
    GraphInterface gic = this.mainPanel.getGraphInterface().clone();
    String inputFilename = this.tetdm.getInputFilename();
    List<PanelState> ps = this.tetdm.getPanelStates();
    State s = new State(gic, rate, inputFilename, ps);
    History h = new History(s);

    this.historyTreePanel.addHistory(h);
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    Object src = e.getSource();
    if (src instanceof JButton) {
      JButton b = (JButton)src;
      String text = b.getText();
      if (text == "Add sticky") {
	this.mainPanel.getGraphInterface().addSticky();
		this.mainPanel.getGraphInterface().repaint();
      }
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
