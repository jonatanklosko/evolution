package agh.cs.evolution;

import java.util.List;

public interface IWorldMap {
  void addElement(IMapElement element);
  void removeElement(IMapElement element);
  boolean isOccupied(Vector2d position);
  List<IMapElement> elementsAt(Vector2d position);
  Vector2d getLowerLeft();
  Vector2d getUpperRight();
}
