package ui;

import tetdm.TETDM;
import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;

import javax.swing.JPanel;
import javax.swing.BoxLayout;


public class MainPanel extends JPanel {

  private final Toolbox toolbox;
  private GraphInterface graphInterface;


  public MainPanel(TETDM tetdm) {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.toolbox = new Toolbox(this, tetdm.getModuleDataList());
    this.graphInterface = new GraphInterface(this, tetdm);
    this.graphInterface.initialize();

    this.add(this.toolbox);
    this.add(this.graphInterface);
  }


  public Toolbox getToolbox() { return this.toolbox; }
  public GraphInterface getGraphInterface() { return this.graphInterface; }

  // public List<? extends Node> getNodesFromToolbox() { return this.toolbox.getNodes(); }
  // public List<Node> getNodesFromGraphInterface() { return this.graphInterface.getNodes(); }
  // public void addNodeToGraphInterface(Node n) { this.graphInterface.addNode(n); }
  // public Node removeNodeFromGraphInterface(Node n) { return this.graphInterface.removeNode(n); }
  

  public void changeGraphInterface(GraphInterface gi) {
    this.remove(this.graphInterface);
    this.revalidate();

    this.add(gi);
    this.revalidate();

    this.graphInterface = gi;

    this.repaint();
  }
}
