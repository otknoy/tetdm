package tetdm.module;

import java.util.List;
import java.util.ArrayList;

/**
 * Module data in TETDM.
 */
public class ModuleData {

  private final String name;
  private final int id;

  private final int type;
  public static final int TYPE_MINING        = 0;
  public static final int TYPE_VISUALIZATION = 1;

  private List<ModuleData> prevList;
  private List<ModuleData> nextList;

  
  public ModuleData(String name, int type, int id, List<ModuleData> prevList, List<ModuleData> nextList) {
    this.name = name;
    this.type = type;
    this.id = id;
    this.prevList = prevList;
    this.nextList = nextList;
  }

  public ModuleData(String name, int type, int id) {
    this(name, type, id, new ArrayList<ModuleData>(), new ArrayList<ModuleData>());
  }


  public String toString() {
    return "ModuleData: [" + this.name + ", " + this.id + "]";
  }
  
  public String getName() { return this.name; }
  public int getId() { return this.id; }
  public boolean isMiningModule() { return this.type == TYPE_MINING; }
  public boolean isVisualizationModule() { return this.type == TYPE_VISUALIZATION; }

  List<ModuleData> getPrevList() { return this.prevList; }
  void addToPrevList(ModuleData d) { this.prevList.add(d); }
  List<ModuleData> getNextList() { return this.nextList; }
  void addToNextList(ModuleData d) { this.nextList.add(d); }


  public boolean isConnectableToPrev(ModuleData d) { return this.prevList.contains(d); }
  public boolean isConnectableToNext(ModuleData d) { return this.nextList.contains(d); }
  public boolean isConnectableTo(ModuleData d) {
    return this.isConnectableToPrev(d) || this.isConnectableToNext(d);
  }
}
