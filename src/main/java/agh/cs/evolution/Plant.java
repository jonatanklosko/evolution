package agh.cs.evolution;

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
