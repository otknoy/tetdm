package ui.history.component;

import ui.history.data.State;

import java.awt.Graphics;
import javax.swing.JToolTip;


public class History extends Node {

  private final State state;


  public History(State state) {
    super();

    this.state = state;
    this.setToolTipText("");
  }


  @Override public JToolTip createToolTip() {
    return new ToolTip(this.state.getImage());
  }


  @Override public void paintComponent(Graphics g) {
    // draw screen capture
    g.drawImage(this.state.getThumbnail(), 0, 0, this);

    super.paintComponent(g);
  }


  public State getState() { return this.state; }
}
