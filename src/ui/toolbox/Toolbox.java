package ui.toolbox;

import tetdm.module.ModuleData;
import ui.MainPanel;
import ui.graph.component.*;
import ui.graph.component.util.*;

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

  private final ToolSelectPanel toolSelectPanel;


  public Toolbox(MainPanel mainPanel, List<ModuleData> moduleDataList) {
    this.setPreferredSize(new Dimension(Toolbox.WIDTH, Toolbox.HEIGHT));
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    this.toolSelectPanel = new ToolSelectPanel(mainPanel, moduleDataList);

    this.add(this.toolSelectPanel);
  }
  

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(new Color(223, 223, 223));
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  public List<? extends Node> getNodes() { return this.toolSelectPanel.getNodes(); }
}
