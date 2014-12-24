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


  public Toolbox(Interface parent) {
    this.setPreferredSize(new Dimension(Toolbox.WIDTH, Toolbox.HEIGHT));
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    List<ModuleData> dataList = new ArrayList<ModuleData>();
    dataList.addAll(parent.getModuleManager().getMiningModuleDataList());
    dataList.addAll(parent.getModuleManager().getVisualizationModuleDataList());
    ToolSelectPanel toolSelectPanel = new ToolSelectPanel(parent, dataList);

    this.add(toolSelectPanel);
  }
  

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(new Color(223, 223, 223));
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}