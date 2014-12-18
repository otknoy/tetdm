package ui.graph;

import ui.Interface;
import ui.graph.component.*;
import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.event.NodeSelectMouseListener;
import ui.graph.event.NodeConnectMouseListener;

import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import javax.swing.*;


public class GraphInterface extends JPanel {

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 650;

  private final Interface parent;
  private List<Node> nodes;


  public GraphInterface(Interface parent) {
    this.parent = parent;
    this.nodes = new ArrayList<Node>();

    this.setPreferredSize(new Dimension(GraphInterface.WIDTH, GraphInterface.HEIGHT));
    this.setLayout(null);

    

    // Init preprocess node
    PreprocessNode pNode = new PreprocessNode(new Point(100, GraphInterface.HEIGHT/2));
    this.addNode(pNode);

    // Init tool panel nodes
    this.addNode(new ToolPanelNode(0, new Point(700, GraphInterface.HEIGHT/2 - 100)));
    this.addNode(new ToolPanelNode(1, new Point(700, GraphInterface.HEIGHT/2)));
    this.addNode(new ToolPanelNode(2, new Point(700, GraphInterface.HEIGHT/2 + 100)));
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    for (Node n : this.nodes) {
      n.drawEdgesToNextNodes(g);
    }
  }


  private List<PreprocessNode> createPreprocessNodes() {
    List<PreprocessNode> preprocessNodes = new ArrayList<PreprocessNode>();
    PreprocessNode n = new PreprocessNode(new Point(100, 650));
    preprocessNodes.add(n);
    return preprocessNodes;
  }


  public List<Node> getNodes() { return this.nodes; }

  public void addNode(Node n) {
    // Add the node to arraylist
    this.nodes.add(n);

    // Add the node to this panel
    this.add(n);

    // Add mouse listener to the node
    // drag and drop
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(n);
    n.addMouseListener(mddl);
    n.addMouseMotionListener(mddl);
    // select and highlight
    NodeSelectMouseListener nsml = new NodeSelectMouseListener(parent, n);
    n.addMouseListener(nsml);
    // connect
    NodeConnectMouseListener ncml = new NodeConnectMouseListener(parent, n);
    n.addMouseListener(ncml);
    n.addMouseMotionListener(ncml);

    this.repaint();
  }
}