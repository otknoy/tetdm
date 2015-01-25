package ui;

import tetdm.TETDM;
import ui.history.HistoryTreePanel;
import ui.history.component.History;
import ui.history.data.State;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;


public class Interface extends JFrame {

  public static final String NAME = "Tool Selector";


  public Interface(TETDM tetdm) {
    this.setTitle(Interface.NAME);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());


    // menu bar
    // this.add(hoge, BorderLayout.NORTH);


    // Main panel: toolbox and graph interface
    MainPanel mainPanel = new MainPanel(tetdm);
    this.add(mainPanel, BorderLayout.CENTER);


    // history tree frame
    JFrame f = new JFrame("History tree");
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    State s = mainPanel.getGraphInterface().getCurrentState();
    History h = new History(s);
    HistoryTreePanel historyTreePanel = new HistoryTreePanel(mainPanel, h);
    historyTreePanel.setPreferredSize(new Dimension(800, 250));
    f.add(historyTreePanel);
    f.pack();
    f.setVisible(true);


    // manipulation panel
    ManipulationPanel mp = new ManipulationPanel(mainPanel, historyTreePanel);
    this.add(mp, BorderLayout.SOUTH);

    this.pack();
  }
}
