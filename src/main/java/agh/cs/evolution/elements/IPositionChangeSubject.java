package agh.cs.evolution.elements;

import agh.cs.evolution.elements.IPositionChangeObserver;

public interface IPositionChangeSubject {
  void addPositionChangeObserver(IPositionChangeObserver observer);
  void removePositionChangeObserver(IPositionChangeObserver observer);
}
