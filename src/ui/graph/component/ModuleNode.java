package ui.graph.component;

import ui.graph.module.ModuleData;
import ui.graph.component.event.NodeRemoveListener;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JButton;


public abstract class ModuleNode extends Node {

  protected ModuleData moduleData;

  private final JButton removeButton;


  public ModuleNode(ModuleData moduleData, Color bgColor, Point location) {
    super(moduleData.getName(), moduleData.getId(), bgColor, location);
    this.moduleData = moduleData;

    this.removeButton = new JButton("x");
    this.add(this.removeButton);
  }

  public ModuleNode(ModuleData moduleData, Color bgColor) {
    this(moduleData, bgColor, new Point(0, 0));
  }

  abstract public ModuleNode clone();

  public void addRemoveListener(NodeRemoveListener nodeRemoveListener) {
    this.removeButton.addMouseListener(nodeRemoveListener);
  }
}
