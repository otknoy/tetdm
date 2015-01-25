package ui;

import tetdm.TETDM;
import tetdm.PanelState;
import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.history.component.History;
import ui.history.data.State;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.util.List;


public class MainPanel extends JPanel {

  private final TETDM tetdm;
  private final Toolbox toolbox;
  private GraphInterface graphInterface;


  public MainPanel(TETDM tetdm) {
    this.tetdm = tetdm;

    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.toolbox = new Toolbox(this, this.tetdm.getModuleDataList());
    this.graphInterface = new GraphInterface(this, this.tetdm);
    this.graphInterface.initialize();

    this.add(this.toolbox);
    this.add(this.graphInterface);
  }


  public Toolbox getToolbox() { return this.toolbox; }
  public GraphInterface getGraphInterface() { return this.graphInterface; }


  /**
   * Change graph interface
   * @param target graph interface
   */
  public void changeGraphInterface(GraphInterface gi) {
    this.remove(this.graphInterface);
    this.revalidate();

    this.add(gi);
    this.revalidate();

    this.graphInterface = gi;

    this.repaint();
  }


  /**
   * Restore history
   * @param restored history
   */
  public void restoreHistory(History h) {
    this.changeGraphInterface(h.getGraphInterface().clone());

    String filename = h.getInputFilename();
    if (filename != this.tetdm.getInputFilename()) {
      this.tetdm.loadFile(filename);
    }

    for (PanelState ps : h.getPanelStates()) {
      this.tetdm.setToolsToPanel(ps);
    }
  }

  // /**
  //  * Save history of GraphInterface to history tree
  //  */
  // public void saveHistory(History h) {
  //   this.parent.historyTreePanel.addHistory(h);
  // }

  // /**
  //  * Save history of GraphInterface to history tree
  //  * @param rate
  //  */
  // public void saveHistory(int rate) {
  //   GraphInterface gic = this.graphInterface.clone();
  //   String inputFilename = this.tetdm.getInputFilename();
  //   List<PanelState> ps = this.tetdm.getPanelStates();
  //   State s = new State(gic, rate, inputFilename, ps);
  //   History h = new History(s);

  //   this.saveHistory(h);
  // }
}
