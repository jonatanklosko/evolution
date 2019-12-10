package agh.cs.evolution.elements;

import agh.cs.evolution.geometry.Vector2d;

public class Plant extends AbstractMapElement {
  private int energy;

  public Plant(Vector2d position, int energy) {
    super(position);
    this.energy = energy;
  }

  public int getEnergy() {
    return this.energy;
  }
}
