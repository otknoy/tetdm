package ui.toolbox;

import ui.graph.component.*;
import ui.graph.component.util.*;
import ui.graph.module.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;


public class Toolbox extends JPanel {

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 250;

  private final ModuleManager moduleManager;
  private final List<ModuleNode> nodes;


  public Toolbox(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setPreferredSize(new Dimension(Toolbox.WIDTH, Toolbox.HEIGHT));
    this.setLayout(null);


    this.nodes = new ArrayList<ModuleNode>();
    List<ModuleNode> mNodes = this.createNodes(this.moduleManager.getMiningModuleDataList());
    this.nodes.addAll(mNodes);
    List<ModuleNode> vNodes = this.createNodes(this.moduleManager.getVisualizationModuleDataList());
    this.nodes.addAll(vNodes);

    for (ModuleNode n : this.nodes) {
      this.add(n);
    }

    Nodes.alignNodes(new Rectangle(      0, 0, WIDTH/2, HEIGHT/2), mNodes);
    Nodes.alignNodes(new Rectangle(WIDTH/2, 0, WIDTH/2, HEIGHT/2), vNodes);
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  private List<ModuleNode> createNodes(List<ModuleData> mdl) {
    List<ModuleNode> nodes = new ArrayList<ModuleNode>();

    for (ModuleData md : mdl) {
      if (md.isMiningModule()) {
	nodes.add(new MiningModuleNode(md));
      } else if (md.isVisualizationModule()) {
	nodes.add(new VisualizationModuleNode(md));
      }
    }

    return nodes;
  }

  public List<ModuleNode> getNodes() { return this.nodes; }
}