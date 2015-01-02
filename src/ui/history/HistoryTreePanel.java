package ui.history;

import ui.Interface;
import ui.graph.GraphInterface;
import ui.history.component.History;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;


public class HistoryTreePanel extends JPanel implements MouseListener {

  static final String NAME = "History Tree";

  private Interface parent;

  private History root;
  private HistoryTree historyTree;


  public HistoryTreePanel(Interface parent) {
    this.parent = parent;
    this.historyTree = new HistoryTree(this);
    
    this.setLayout(null);
  }


  @Override public void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    // draw edges
    this.root.drawEdgesToNextNodes(g);
  }
  
  
  @Override public void mouseClicked(MouseEvent e) {
    // History h = (History)e.getSource();
    // this.current.selected(false);
    // this.current = h;
    // this.current.selected(true);
    // this.repaint();

    // // change graph panel
    // GraphPanel gp = ((GraphPanel)this.current.getData());
    // this.moduleSelectPanel.changeGraphPanel(gp);
  }
  
  @Override public void mousePressed(MouseEvent e) {}
  @Override public void mouseReleased(MouseEvent e) {}
  @Override public void mouseEntered(MouseEvent e) {}
  @Override public void mouseExited(MouseEvent e) {}
}
