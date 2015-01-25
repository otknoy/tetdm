package ui;

import tetdm.TETDM;
import tetdm.PanelState;
import ui.graph.GraphInterface;
import ui.history.HistoryTreePanel;
import ui.history.component.History;
import ui.history.data.State;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.List;


public class Interface extends JFrame {

  public static final String NAME = "Tool Selector";

  private final TETDM tetdm;

  final MainPanel mainPanel;
  final HistoryTreePanel historyTreePanel;


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
    ManipulationPanel manipulationPanel = new ManipulationPanel(this);
    this.add(manipulationPanel, BorderLayout.SOUTH);

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
}
