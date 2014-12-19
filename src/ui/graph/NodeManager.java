package ui.graph;

import ui.Interface;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.MiningModuleNode;
import ui.graph.component.VisualizationModuleNode;
import ui.graph.component.ToolPanelNode;
import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.event.NodeSelectMouseListener;
import ui.graph.event.NodeConnectMouseListener;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JComponent;


public class NodeManager {

  private Interface parent;
  private List<Node> nodes;

  
  public NodeManager(Interface parent) {
    this.parent = parent;
    this.nodes = new ArrayList<Node>();
  }
  

  public void initialize() {
    // Init preprocess node
    // PreprocessNode pNode = new PreprocessNode(new Point(100, this.parent.getGraphInterface().getHeight()/2));
    // this.add(pNode);

    // Init tool panel nodes
    // int n = 3;
    // for (int i = 0; i < n; i++) {
    //   int x = 700;
    //   int y = parent.getHeight()/2 + (n/2 - 3) * 100; // test
    //   ToolPanelNode tpNode = new ToolPanelNode(0, new Point(700, this.parent.getGraphInterface().getHeight()/2 - 100));
    //   this.add(tpNode);
    // }    
  }

  
  public List<Node> getNodes() { return this.nodes; }

  public void add(Node n) {
    this.nodes.add(n);
    this.parent.getGraphInterface().add(n);

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