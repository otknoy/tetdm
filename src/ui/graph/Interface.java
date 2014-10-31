package ui.graph;

import ui.graph.module.ModuleManager;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class Interface extends JPanel {

  ModuleManager moduleManager;

  
  public Interface(ModuleManager moduleManager) {
    this.moduleManager = moduleManager;

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    ModuleSelectPanel msPanel = new ModuleSelectPanel(moduleManager);
    add(msPanel);
    
    // HistoryPanel hPanel = new HistoryPanel();
    // add(hPanel);
  }
}
