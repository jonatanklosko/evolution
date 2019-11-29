package agh.cs.evolution;

public class Animal extends AbstractMapElement {
  private final Genome genome;
  private int energy;

  public Animal(Vector2d position, int energy) {
    super(position);
    this.genome = new Genome();
    this.energy = energy;
  }
}
