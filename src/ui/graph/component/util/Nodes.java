package ui.graph.component.util;

import ui.graph.component.Node;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;


public class Nodes {
  
  /**
   * Align nodes
   * @param region where nodes aligned
   * @param aligned nodes
   */
  public static void alignNodes(Rectangle rect, List<? extends Node> nodes) {
    final int mergin = 8;

    final int wn = 5;
    for (int i = 0; i < nodes.size(); i++) {
      int wi = i % wn;
      int hi = i / wn;

      int x = rect.x + mergin + wi * (nodes.get(i).getWidth() + mergin);
      int y = rect.y + mergin + hi * (nodes.get(i).getHeight() + mergin);
      nodes.get(i).setLocation(x, y);
    }
  }
  
  /**
   * Align nodes to arch-shaped
   * @param base node
   * @param aligned nodes
   */
  public static void alignNodesToArchShaped(Node node, List<Node> nodes) {
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

  /**
   * Search same type nodes from node list.
   * @param target node list for searching
   * @param key node
   */
  public static List<Node> searchSameTypeNodes(List<Node> nodes, Node key) {
    List<Node> sameTypeNodes = new ArrayList<Node>();
    for (Node n : nodes) {
      if (n.getClass() == key.getClass()) {
	sameTypeNodes.add(n);
      }
    }
    return sameTypeNodes;
  }


  /**
   * Select all nodes.
   * @param target node list
   * @param selected or unselected
   */
  public static void selectedAll(List<Node> nodes, boolean selected) {
    for (Node n : nodes) { n.selected(selected); }
  }
}
