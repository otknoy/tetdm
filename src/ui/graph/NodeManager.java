package ui.graph;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.ToolPanelNode;
import ui.graph.component.event.MouseDragAndDropListener;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JComponent;


public class NodeManager {

  private GraphInterface graphInterface;
  private List<Node> nodes;

  
  public NodeManager(GraphInterface graphInterface) {
    this.graphInterface = graphInterface;
    this.nodes = new ArrayList<Node>();
  }

  public List<Node> getNodes() { return this.nodes; }

  public void add(Node n) {
    this.nodes.add(n);
    this.graphInterface.add(n);

    // drag and drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(n);
    n.addMouseListener(mddl);
    n.addMouseMotionListener(mddl);

    // // select and highlight event
    // System.out.println(this.graphInterface.getParent());
    // NodeSelectMouseListener nsml = new NodeSelectMouseListener((Interface)this.graphInterface.getParent(), n);
    // n.addMouseListener(nsml);

    // // connect event
    // NodeConnectMouseListener ncml = new NodeConnectMouseListener((Interface)this.graphInterface.getParent(), n);
    // n.addMouseListener(ncml);
    // n.addMouseMotionListener(ncml);

    this.graphInterface.repaint();
  }

  public Node remove(Node n) {
    this.graphInterface.remove(n);
    this.nodes.remove(n);

    this.graphInterface.repaint();
    return n;
  }
}