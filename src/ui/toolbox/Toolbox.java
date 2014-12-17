package ui.toolbox;

import ui.Interface;
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

  private Interface parent;

  private List<MiningModuleNode> miningModuleNodes;
  private List<VisualizationModuleNode> visualizationModuleNodes;


  public Toolbox(Interface parent) {
    this.parent = parent;

    this.setPreferredSize(new Dimension(Toolbox.WIDTH, Toolbox.HEIGHT));
    this.setLayout(null);


    this.miningModuleNodes = this.createMiningModuleNodes(parent.moduleManager);
    this.visualizationModuleNodes = this.createVisualizationModuleNodes(parent.moduleManager);

    for (Node n : this.miningModuleNodes) {
      this.add(n);
    }
    for (Node n : this.visualizationModuleNodes) {
      this.add(n);
    }

    Nodes.alignNodes(new Rectangle(      0, 0, WIDTH/2, HEIGHT/2), this.miningModuleNodes);
    Nodes.alignNodes(new Rectangle(WIDTH/2, 0, WIDTH/2, HEIGHT/2), this.visualizationModuleNodes);
  }


  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  

  private List<MiningModuleNode> createMiningModuleNodes(ModuleManager moduleManager) {
    List<MiningModuleNode> mmNodes = new ArrayList<MiningModuleNode>();
    List<ModuleData> mmdl = moduleManager.getMiningModuleDataList();
    for (ModuleData md : mmdl) {
      MiningModuleNode n = new MiningModuleNode(md);
      mmNodes.add(n);
    }
    return mmNodes;
  }

  private List<VisualizationModuleNode> createVisualizationModuleNodes(ModuleManager moduleManager) {
    List<VisualizationModuleNode> vmNodes = new ArrayList<VisualizationModuleNode>();
    List<ModuleData> vmdl = moduleManager.getVisualizationModuleDataList();
    for (ModuleData md : vmdl) {
      VisualizationModuleNode n = new VisualizationModuleNode(md);
      vmNodes.add(n);
    }
    return vmNodes;
  }
}