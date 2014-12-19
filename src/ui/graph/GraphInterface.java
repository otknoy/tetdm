package ui.graph;

import ui.Interface;
import ui.graph.component.Node;
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

    this.nodeManager = new NodeManager(this.parent);
    this.stickyManager = new StickyManager(this);
    
    this.nodeManager.initialize();
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
  
  public NodeManager getNodeManager() { return this.nodeManager; }
  public StickyManager getStickyManager() { return this.stickyManager; }
}