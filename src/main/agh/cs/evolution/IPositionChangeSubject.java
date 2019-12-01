package agh.cs.evolution;

public interface IPositionChangeSubject {
  void addPositionChangeObserver(IPositionChangeObserver observer);
  void removePositionChangeObserver(IPositionChangeObserver observer);
}
