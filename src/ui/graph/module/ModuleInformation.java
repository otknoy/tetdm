package ui.graph.module;

import ui.graph.component.Node;

import java.util.List;
import java.util.ArrayList;

public class ModuleInformation {

  private String name;
  private int id;
  private List<ModuleInformation> parents;
  private List<ModuleInformation> children;

  private int type;
  public static final int TYPE_MINING        = 0;
  public static final int TYPE_VISUALIZATION = 1;  
  public static final int TYPE_PREPROCESS    = 2;


  public ModuleInformation(String name, int id, int type) {
    this.name = name;
    this.id = id;
    this.type = type;
    this.parents = new ArrayList<ModuleInformation>();
    this.children = new ArrayList<ModuleInformation>();    
  }

  public String toString() {
    return "ModuleInformation: [" + this.getType() + ", " + this.name + ", " + this.id + "]";
  }

  public int getID() { return this.id; }  
  public String getName() { return this.name; }
  // public int[] getPairModuleIDs() { return this.pairModuleIDs; }
  public int getType() { return this.type; }
  public List<ModuleInformation> getParents() { return this.parents; }
  public void addParent(ModuleInformation parent) { this.parents.add(parent); }
  public List<ModuleInformation> getChildren() { return this.children; }
  public void addChild(ModuleInformation child) { this.children.add(child); }


  public boolean isConnectableTo(ModuleInformation mi) {
    return this.isConnectableToPrevious(mi) || this.isConnectableToNext(mi);
  }
  
  public boolean isConnectableToPrevious(ModuleInformation previous) {
    for (ModuleInformation mi : this.parents)
      if (mi == previous) return true;
    return false;
  }

  public boolean isConnectableToNext(ModuleInformation next) {
    for (ModuleInformation mi : this.children)
      if (mi == next) return true;
    return false;
  }
}