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
    JButton b1 = new JButton("test1");
    b1.addMouseListener(this);
    JButton b2 = new JButton("test2");
    b2.addMouseListener(this);
    this.uiPanel.add(b1);
    this.uiPanel.add(b2);

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
    Object src = e.getSource();
    if (src instanceof JButton) {
      JButton b = (JButton)src;
      String text = b.getText();
      System.out.println(text);
      if (text == "test1") {
	GraphPanel gp = this.graphPanel.clone();

      } else if (text == "test2") {
	GraphPanel gp = this.graphPanel.clone();

      } else if (text == "Add Sticky") {
	this.graphPanel.addSticky(new Sticky());
	this.graphPanel.repaint();
      }
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
