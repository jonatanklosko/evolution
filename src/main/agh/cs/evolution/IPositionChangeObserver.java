package agh.cs.evolution;

public interface IPositionChangeObserver {
  void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition);
}
