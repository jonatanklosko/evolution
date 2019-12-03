package agh.cs.evolution;

public class Plant extends AbstractMapElement {
  private int energy;

  public Plant(Vector2d position, int energy) {
    super(position);
    this.energy = energy;
  }

  public String symbolRepresentation() {
    return "⚘";
  }

  public int getEnergy() {
    return this.energy;
  }
}
