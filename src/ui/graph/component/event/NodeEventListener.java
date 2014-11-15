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


public class NodeEventListener implements MouseListener, MouseMotionListener {

  private ModuleSelectPanel parent;

  private Node node;
  private List<Node> connectableNodes;

  
  public NodeEventListener(ModuleSelectPanel parent, Node node) {
    this.parent = parent;
    this.node = node;
    this.connectableNodes = new ArrayList<Node>();
  }
  

  @Override
  public void mouseClicked(MouseEvent e) {
    List<Node> nodes = this.parent.getNodes();

    // Only one node at each node type is selected
    if (!this.node.isSelected()) {
      // All same type nodes become "selected = false."
      for (Node n : nodes) {
	if (n.getClass() == this.node.getClass()) {
	  n.selected(false);
	}
      }
      // Only this node becomes "selected = true."
      this.node.selected(true);
    } else {
      this.node.selected(false);
    }

    node.repaint();
  }

  @Override  
  public void mousePressed(MouseEvent e) {}

  @Override  
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    if (node.isSelected()) return;
    
    node.highlighted(true);
    node.repaint();

    // highlight connectable nodes
    this.connectableNodes = parent.findConnectableNodes(this.node);
    for (Node n : this.connectableNodes) {
      n.highlighted(true);
    }

    this.parent.repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (node.isSelected()) return;

    node.highlighted(false);    
    node.repaint();

    // unhighlight connectable nodes
    for (Node n : this.connectableNodes) {
      n.highlighted(false);
    }
    this.connectableNodes = new ArrayList<Node>();

    this.parent.repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (!this.node.isHighlighted()) {
      parent.repaint();
      return;
    }

    // If the distance between connectable nodes becomes closer/more distant than threshold,
    // these nodes are connected/disconnected.
    final int threshold = 192;
    for (Node n : this.connectableNodes) {
      if (this.node.distance(n) <= threshold) {	// connect
	if (this.node.isConnectedTo(n)) {
	  continue;
	}

        if (this.node.isConnectableToPrev(n)) {
	  this.node.addPrevNodes(n);
	  n.addNextNodes(this.node);
	} else if (this.node.isConnectableToNext(n)){
	  this.node.addNextNodes(n);
	  n.addPrevNodes(this.node);
	}

	this.changeModules();
      } else { // disconnect
        if (this.node.isConnectableToPrev(n)) {
          this.node.removePrevNode(n);
	  n.removeNextNode(this.node);
	} else if (this.node.isConnectableToNext(n)){
	  this.node.removeNextNode(n);
	  n.removePrevNode(this.node);
	}
      }
    }

    parent.repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    parent.repaint();
  }


  private void changeModules() {
    List<Node[]> combinations = parent.getNodeCombinations();
    combinations = parent.extractConnectedCombinations(combinations);

    Node[] selectedCombination = null;
    for (Node[] c : combinations) {
      Node n1 = c[0], n2 = c[1], n3 = c[2];
      if (checkSelected(n1, n2, n3)) {
	selectedCombination = c;
      }
    }
    if (selectedCombination == null) {
      return;
    }

    MiningModuleNode mmn = (MiningModuleNode)selectedCombination[1];
    VisualizationModuleNode vmn = (VisualizationModuleNode)selectedCombination[2];
    parent.setModulesToPanel(0, mmn, vmn);
  }

  private boolean checkSelected(Node n1, Node n2, Node n3) {
    return (n1.isSelected() &&
	    n2.isSelected() &&
	    n3.isSelected());
  }
}