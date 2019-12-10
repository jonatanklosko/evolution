package agh.cs.evolution.map;

import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.elements.IMapElement;

import java.util.List;

public interface IWorldMap {
  void addElement(IMapElement element);
  void removeElement(IMapElement element);
  boolean isOccupied(Vector2d position);
  List<IMapElement> elementsAt(Vector2d position);
  List<IMapElement> allElements();
  Vector2d getLowerLeft();
  Vector2d getUpperRight();
}
