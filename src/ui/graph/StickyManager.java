package ui.graph;

import ui.graph.component.Sticky;
import ui.graph.component.event.MouseDragAndDropListener;
import ui.graph.component.event.StickyEventListener;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;


public class StickyManager {

  private JPanel parent;
  private List<Sticky> stickies;

  private StickyEventListener stickyEventListener;
  
  
  public StickyManager(JPanel parent) {
    this.parent = parent;
    this.stickies = new ArrayList<Sticky>();

    this.stickyEventListener = new StickyEventListener(this);
  }

  public List<Sticky> getStickies() { return this.stickies; }


  public void add() {
	Sticky s = new Sticky();
	s.setLocation((this.parent.getWidth()  - s.getWidth())  / 2,
		      (this.parent.getHeight() - s.getHeight()) / 2);
	this.add(s);
  }

  public void add(Sticky s) {
    // drag & drop event
    MouseDragAndDropListener mddl = new MouseDragAndDropListener(s);
    s.addMouseListener(mddl);
    s.addMouseMotionListener(mddl);

    // mouse event
    s.addMouseListener(this.stickyEventListener);

    this.parent.add(s);
    this.stickies.add(s);
    this.parent.repaint();
  }

  public Sticky remove(Sticky s) {
    this.parent.remove(s);
    this.stickies.remove(s);
    this.parent.repaint();    
    return s;
  }
}
