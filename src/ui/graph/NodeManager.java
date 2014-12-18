package ui.graph;

import ui.Interface;
import ui.graph.component.Node;
import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.event.NodeSelectMouseListener;
import ui.graph.event.NodeConnectMouseListener;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JComponent;


public class NodeManager {

  private Interface parent;
  private List<Node> nodes;

  
  public NodeManager(Interface parent) {
    this.parent = parent;
    this.nodes = new ArrayList<Node>();
  }


  public List<Node> getNodes() { return this.nodes; }

  public void add(Node n) {
    this.nodes.add(n);
    this.add(n);

    // drag and drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(n);
    n.addMouseListener(mddl);
    n.addMouseMotionListener(mddl);

    // select and highlight event
    NodeSelectMouseListener nsml = new NodeSelectMouseListener(this.parent, n);
    n.addMouseListener(nsml);

    // connect event
    NodeConnectMouseListener ncml = new NodeConnectMouseListener(this.parent, n);
    n.addMouseListener(ncml);
    n.addMouseMotionListener(ncml);

    this.parent.repaint();
  }

  public Node remove(Node n) {
    this.parent.remove(n);
    this.nodes.remove(n);

    this.parent.repaint();
    return n;
  }
}