package ui.graph.module;

import java.util.List;
import java.util.ArrayList;

public class ModuleInfo {

  private String name;
  private int id;
  private List<ModuleInfo> nextList;
  private List<ModuleInfo> prevList;

  private int type;
  public static final int TYPE_MINING        = 0;
  public static final int TYPE_VISUALIZATION = 1;  
  public static final int TYPE_PREPROCESS    = 2;


  public ModuleInfo(String name, int id, int type) {
    this.name = name;
    this.id = id;
    this.type = type;
    this.nextList = new ArrayList<ModuleInfo>();
    this.prevList = new ArrayList<ModuleInfo>();    
  }

  public String toString() {
    return "ModuleInfo: [" + this.getType() + ", " + this.name + ", " + this.id + "]";
  }

  public int getID() { return this.id; }  
  public String getName() { return this.name; }
  // public int[] getPairModuleIDs() { return this.pairModuleIDs; }
  public int getType() { return this.type; }
  
  public List<ModuleInfo> getNextList() { return this.nextList; }
  public void addToNext(ModuleInfo next) { this.nextList.add(next); }
  
  public List<ModuleInfo> getPrevList() { return this.prevList; }
  public void addToPrev(ModuleInfo prev) { this.prevList.add(prev); }


  public boolean isConnectableTo(ModuleInfo mi) {
    return this.isConnectableToPrevious(mi) || this.isConnectableToNext(mi);
  }
  
  public boolean isConnectableToPrevious(ModuleInfo info) {
    for (ModuleInfo mi : this.prevList)
      if (mi == info) return true;
    return false;
  }

  public boolean isConnectableToNext(ModuleInfo info) {
    for (ModuleInfo mi : this.nextList)
      if (mi == info) return true;
    return false;
  }
}