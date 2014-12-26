package ui.graph.component;

import ui.graph.module.ModuleData;
import ui.graph.component.event.NodeRemoveListener;

import java.awt.Color;
import java.awt.Point;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JButton;


public abstract class ModuleNode extends Node {

  protected ModuleData moduleData;


  public ModuleNode(ModuleData moduleData, Color bgColor, Point location) {
    super(moduleData.getName(), moduleData.getId(), bgColor, location);
    this.moduleData = moduleData;

    this.setLayout(null);
  }

  public ModuleNode(ModuleData moduleData, Color bgColor) {
    this(moduleData, bgColor, new Point(0, 0));
  }

  abstract public ModuleNode clone();

  public void addRemoveListener(NodeRemoveListener nodeRemoveListener) {
    JButton rmButton = new JButton("x");
    rmButton.setSize(12, 12);
    rmButton.setLocation(new Point(this.getWidth()  - rmButton.getWidth(), 0));
    rmButton.addMouseListener(nodeRemoveListener);
    this.add(rmButton);
  }
}
