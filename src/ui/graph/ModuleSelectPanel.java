package ui.graph;

import ui.graph.component.Sticky;
import ui.graph.module.ModuleManager;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JButton;


public class ModuleSelectPanel extends JPanel implements MouseListener {

  static final String NAME = "Module Select Panel";

  private final ModuleManager moduleManager;

  private final JPanel uiPanel;
  private final GraphPanel graphPanel;


  public ModuleSelectPanel(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setLayout(new BorderLayout());

    // ui panel
    this.uiPanel = new JPanel();
    this.uiPanel.add(new JButton("test1"));
    this.uiPanel.add(new JButton("test2"));

    // add sticky button
    JButton addStickyButton = new JButton("Add Sticky");
    addStickyButton.addMouseListener(this);
    this.uiPanel.add(addStickyButton);
    this.add(this.uiPanel, BorderLayout.SOUTH);


    // graph panel
    this.graphPanel = new GraphPanel(moduleManager);
    this.add(this.graphPanel, BorderLayout.CENTER);
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    this.graphPanel.addSticky(new Sticky());
    this.graphPanel.repaint();
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
