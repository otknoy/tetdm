package ui.graph.component.event;

import ui.graph.ModuleSelectPanel;
import ui.graph.module.ModuleData;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningModuleNode;
import ui.graph.component.VisualizationModuleNode;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.util.List;
import java.util.ArrayList;


public class NodeEventListener implements MouseListener {

  private Node node;
  private List<Node> connectableNodes;

  
  public NodeEventListener(Node node) {
    this.node = node;
    this.connectableNodes = new ArrayList<Node>();
  }
  

  @Override
  public void mouseClicked(MouseEvent e) {
    node.selected(!node.isSelected());
    node.repaint();
  }

  @Override  
  public void mousePressed(MouseEvent e) {
  }

  @Override  
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (node.isSelected()) return;
    
    node.highlighted(true);
    node.repaint();

    // generate connectable nodes
    List<ModuleInfo> mil = node.getModuleInfo().getNextList();
    List<Node> nodes = new ArrayList<Node>();
    for (ModuleInfo mi : mil) {
      Node node = null;
      if (mi.getType() == ModuleInfo.TYPE_PREPROCESS) {
	node = new PreprocessNode(mi);
      } else if (mi.getType() == ModuleInfo.TYPE_MINING) {
	node = new MiningNode(mi);
      } else if (mi.getType() == ModuleInfo.TYPE_VISUALIZATION) {
	node = new VisualizationNode(mi);
      }
      nodes.add(node);
    }
    this.connectableNodes = nodes;
    this.alignNodes(this.connectableNodes);

    ModuleSelectPanel parent = (ModuleSelectPanel)this.node.getParent();
    parent.addNodes(this.connectableNodes);
    parent.repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (node.isSelected()) return;

    node.highlighted(false);    
    node.repaint();

    // remove connectable nodes
    ModuleSelectPanel parent = (ModuleSelectPanel)this.node.getParent();
    parent.removeNodes(this.connectableNodes);
    this.connectableNodes = new ArrayList<Node>();
    parent.repaint();
  }

  // Align nodes to arch-shaped
  private void alignNodes(List<Node> nodes) {
    final double maxRad = Math.toRadians(150);
    
    double diffRad = Math.toRadians(10);
    double rad = diffRad * nodes.size();
    if (rad > maxRad) {
      rad = maxRad;
      diffRad = rad / nodes.size();
    }
    
    final double startRad = -rad / 2;
    final double endRad   =  rad / 2;

    final Point np = node.getCenterPosition();
    final int r = 192;
    for (int i = 0; i < nodes.size(); i++) {
      double theta = startRad + diffRad * (i+1);
      double x = np.getX() + r * Math.cos(theta);
      double y = np.getY() + r * Math.sin(theta);

      nodes.get(i).setLocation((int)x - node.getWidth()/2,
			       (int)y - node.getHeight()/2);
    }
  }
}