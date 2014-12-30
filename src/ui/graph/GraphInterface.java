package ui.graph;

import ui.Interface;
import ui.graph.event.NodeSelectMouseListener;
import ui.graph.event.NodeConnectMouseListener;
import ui.graph.component.Node;
import ui.graph.component.PreprocessNode;
import ui.graph.component.ModuleNode;
import ui.graph.component.ToolPanelNode;
import ui.graph.component.Sticky;
import ui.graph.component.event.NodeRemoveListener;
import ui.graph.component.util.Nodes;

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

  private final NodeSelectMouseListener nsmLisneter;
  private final NodeConnectMouseListener ncmListener;


  public GraphInterface(Interface parent) {
    this.parent = parent;

    this.setPreferredSize(new Dimension(GraphInterface.WIDTH, GraphInterface.HEIGHT));
    this.setLayout(null);

    this.nodeManager = new NodeManager(this);
    this.stickyManager = new StickyManager(this);

    this.nsmLisneter = new NodeSelectMouseListener(this.parent, this);
    this.ncmListener = new NodeConnectMouseListener(this.parent, this);
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
    for (Node n: this.getNodes()) {
      gi.addNode(n.clone());
    }
    // reconnect nodes
    final int threshold = 192;
    Nodes.connect(gi.getNodes(), threshold);

    // clone stickies
    for (Sticky s : this.getStickies()) {
      gi.addSticky(s.clone());
    }

    return gi;
  }


  public List<Node> getNodes() { return this.nodeManager.getNodes(); }
  
  public void addNode(Node n) {
    n.addMouseListener(this.nsmLisneter);

    n.addMouseListener(this.ncmListener);
    n.addMouseMotionListener(this.ncmListener);

    if (n instanceof ModuleNode) {
      ((ModuleNode)n).addRemoveListener(new NodeRemoveListener(this, n));
    }

    this.nodeManager.add(n);
  }
  
  public Node removeNode(Node n) { return this.nodeManager.remove(n); }

  public List<Node[]> getNodeCombinations() {
    return this.nodeManager.getNodeCombinations();
  }

  public List<Sticky> getStickies() { return this.stickyManager.getStickies(); }
  public void addSticky() { this.stickyManager.add(); }
  public void addSticky(Sticky s) { this.stickyManager.add(s); }
  public Sticky removeSticky(Sticky s) { return this.stickyManager.remove(s); }
}
