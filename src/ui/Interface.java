package ui;

import ui.toolbox.Toolbox;
import ui.graph.GraphInterface;
import ui.graph.component.Node;
import ui.history.HistoryTreePanel;
import ui.graph.module.ModuleManager;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.util.List;
import java.util.ArrayList;


public class Interface extends JFrame implements MouseListener {

  public static final String NAME = "Tool Selector";

  private final ModuleManager moduleManager;

  private final Toolbox toolbox;
  private final GraphInterface graphInterface;
  
  
  public Interface(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    this.setTitle(Interface.NAME);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());


    // menu bar
    // this.add(hoge, BorderLayout.NORTH);


    // toolbox and graph interface
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

    this.graphInterface = new GraphInterface(this);
    this.toolbox = new Toolbox(this);

    this.graphInterface.initialize();

    p.add(this.toolbox);
    p.add(this.graphInterface);
    this.add(p, BorderLayout.CENTER);


    // manipulation panel
    JPanel mp = new JPanel();
    // add sticky
    JButton addStickyButton = new JButton("Add sticky");
    addStickyButton.addMouseListener(this);
    mp.add(addStickyButton);
    // save history (test)
    JButton saveHistoryButton = new JButton("Save history");
    saveHistoryButton.addMouseListener(this);
    mp.add(saveHistoryButton);

    this.add(mp, BorderLayout.SOUTH);
    this.pack();


    // history tree frame
    JFrame f = new JFrame("History tree");
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    HistoryTreePanel htPanel = new HistoryTreePanel(this);
    htPanel.setPreferredSize(new Dimension(800, 250));
    f.add(htPanel);
    f.pack();
    f.setVisible(true);
  }


  public ModuleManager getModuleManager() { return this.moduleManager; }
  public Toolbox getToolbox() { return this.toolbox; }

  public List<Node> getNodesFromGraphInterface() { return this.graphInterface.getNodes(); }
  public void addNodeToGraphInterface(Node n) { this.graphInterface.addNode(n); }
  public Node removeNodeFromGraphInterface(Node n) { return this.graphInterface.removeNode(n); }

  public GraphInterface cloneGraphInterface() { return this.graphInterface.clone(); }

  public void setToolsToPanel(int panelIndex, int miningModuleID, int visualizationModuleID) {
    this.moduleManager.setModulesToPanel(panelIndex, miningModuleID, visualizationModuleID);
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    Object src = e.getSource();
    if (src instanceof JButton) {
      JButton b = (JButton)src;
      String text = b.getText();
      if (text == "Add sticky") {
	this.graphInterface.addSticky();
	this.graphInterface.repaint();
      } else if (text == "Save history") {
	JFrame p = new JFrame();
	p.setPreferredSize(new Dimension(800, 800));
	p.add(this.graphInterface.clone());
	p.pack();
	p.setVisible(true);
      }
    }
  }

  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
