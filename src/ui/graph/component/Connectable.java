package ui.graph.component;

public interface Connectable<T> {
  boolean isConnectableToPrev(T o);
  boolean isConnectableToNext(T o);
  boolean isConnectableTo(T o);
}