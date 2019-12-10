package agh.cs.evolution.elements;

import agh.cs.evolution.geometry.Vector2d;

public interface IPositionChangeObserver {
  void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition);
}
