package ui.toolbox;

import tetdm.module.ModuleData;
import ui.Interface;
import ui.graph.component.ModuleNode;
import ui.graph.component.MiningModuleNode;
import ui.graph.component.VisualizationModuleNode;
import ui.graph.component.util.Nodes;
import ui.toolbox.event.ToolSelectMouseListener;

import java.awt.Rectangle;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;


public class ToolSelectPanel extends JPanel {

  private final Interface parent;
  private final List<ModuleNode> nodes;
  private final ToolSelectMouseListener tsmListener;
  

  public ToolSelectPanel(Interface parent, List<ModuleData> moduleDataList) {
    this.parent = parent;
    this.nodes = this.createNodes(moduleDataList);

    this.setLayout(null);

    // event
    this.tsmListener = new ToolSelectMouseListener(this.parent, this);
    for (ModuleNode n : this.nodes) {
      n.addMouseListener(this.tsmListener);
    }

    // add to this panel
    for (ModuleNode n : this.nodes) {
      this.add(n);
    }

    Nodes.alignNodes(new Point(0, 0), 12, this.nodes);
  }

  private List<ModuleNode> createNodes(List<ModuleData> mdl) {
    List<ModuleNode> nodes = new ArrayList<ModuleNode>();

    for (ModuleData md : mdl) {
      if (md.isMiningModule()) {
	nodes.add(new MiningModuleNode(md));
      } else if (md.isVisualizationModule()) {
	nodes.add(new VisualizationModuleNode(md));
      }
    }

    return nodes;
  }

  public List<ModuleNode> getNodes() { return this.nodes; }
}
