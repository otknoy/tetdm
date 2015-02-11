package ui.graph;

import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningModuleNode;
import ui.graph.component.VisualizationModuleNode;
import ui.graph.component.ToolPanelNode;

import java.util.List;
import java.util.ArrayList;


public class NodeCombination {

  public final PreprocessNode preprocess;
  public final MiningModuleNode mining;
  public final VisualizationModuleNode visualization;
  public final ToolPanelNode toolPanel;


  public NodeCombination(PreprocessNode preprocess,
			 MiningModuleNode mining,
			 VisualizationModuleNode visualization,
			 ToolPanelNode toolPanel) {
    this.preprocess = preprocess;
    this.mining = mining;
    this.visualization = visualization;
    this.toolPanel = toolPanel;
  }

  public NodeCombination(Node preprocess,
			 Node mining,
			 Node visualization,
			 Node toolPanel) {
    this((PreprocessNode)preprocess,
	 (MiningModuleNode)mining,
	 (VisualizationModuleNode)visualization,
	 (ToolPanelNode)toolPanel);
  }


  private List<Node> getAllNodes() {
    List<Node> nodes = new ArrayList<Node>();
    nodes.add(this.preprocess);
    nodes.add(this.mining);
    nodes.add(this.visualization);
    nodes.add(this.toolPanel);
    return nodes;
  }


  public boolean contains(Node n) {
    return this.getAllNodes().contains(n);
  }


  public void selectAllNodes(boolean selected) {
    for (Node n : this.getAllNodes()) {
      n.selected(selected);
    }
  }

  public boolean isSelected() {
    for (Node n : this.getAllNodes()) {
      if (!n.isSelected()) {
	return false;
      }
    }
    return true;
  }

  /**
   * Count selected node
   * @return number of selected node
   */
  public int countSelectedNode() {
    int count = 0;
    for (Node n : this.getAllNodes()) {
      if (n.isSelected()) {
	count++;
      }
    }
    return count;
  }
}
