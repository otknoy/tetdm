package ui.graph;

import ui.Interface;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.ToolPanelNode;

import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import javax.swing.*;
import java.lang.Cloneable;


public class GraphInterface extends JPanel implements Cloneable {

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 650;

  private final Interface parent;
  private final NodeManager nodeManager;
  private final StickyManager stickyManager;


  public GraphInterface(Interface parent) {
    this.parent = parent;

    this.setPreferredSize(new Dimension(GraphInterface.WIDTH, GraphInterface.HEIGHT));
    this.setLayout(null);

    this.nodeManager = new NodeManager(this);
    this.stickyManager = new StickyManager(this);
  }


  public void initialize() {
    // Init preprocess node
    PreprocessNode pNode = new PreprocessNode(new Point(100, GraphInterface.HEIGHT/2));
    this.parent.addNodeToGraphInterface(pNode);

    // Init tool panel nodes
    int n = 3;
    int w = 100;
    for (int i = 0; i < n; i++) {
      int x = 700;
      int y = GraphInterface.HEIGHT - (n-i)*w;
      ToolPanelNode tpNode = new ToolPanelNode(i, new Point(x, y));
      this.parent.addNodeToGraphInterface(tpNode);
    }
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    for (Node n : this.nodeManager.getNodes()) {
      n.drawEdgesToNextNodes(g);
    }
  }

  @Override public GraphInterface clone() {
    // clone this
    GraphInterface gi = new GraphInterface(this.parent);

    // clone nodes

    // clone stickies


    return gi;
  }


  public List<Node> getNodes() { return this.nodeManager.getNodes(); }
  public void addNode(Node n) { this.nodeManager.add(n); }
  public Node removeNode(Node n) { return this.nodeManager.remove(n); }

  public List<Node[]> getNodeCombinations() {
    return this.nodeManager.getNodeCombinations();
  }

  public StickyManager getStickyManager() { return this.stickyManager; }
}
