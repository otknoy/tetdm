package ui.graph;

import ui.graph.module.ModuleManager;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;


public class ModuleSelectPanel extends JPanel {

  static final String NAME = "Module Select Panel";

  private final ModuleManager moduleManager;

  private final JPanel upperPanel;
  private final GraphPanel graphPanel;

  public static final int WIDTH  = 800;
  public static final int HEIGHT = 800;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setLayout(new BorderLayout());

    this.upperPanel = new JPanel();
    this.upperPanel.add(new JButton("test1"));
    this.upperPanel.add(new JButton("test2"));
    this.upperPanel.add(new JButton("test3"));
    this.add(this.upperPanel, BorderLayout.NORTH);


    this.graphPanel = new GraphPanel(moduleManager);
    this.add(this.graphPanel);
  }
}