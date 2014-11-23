package ui.graph.component.util;

import ui.graph.component.Node;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;


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
}
