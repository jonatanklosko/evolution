package agh.cs.evolution;

import java.util.List;

public class Animal extends AbstractMapElement {
  private final Genome genome;
  private int energy;
  private int minReproductionEnergy;

  public Animal(Vector2d position, int energy) {
    super(position);
    this.energy = energy;
    this.genome = new Genome();
    this.minReproductionEnergy = energy / 2;
  }

  public Animal(Vector2d position, int energy, Genome genome) {
    super(position);
    this.energy = energy;
    this.genome = genome;
  }

  public int getEnergy() {
    return this.energy;
  }

  public Genome getGenome() {
    return this.genome;
  }

  public boolean isDead() {
    return this.energy == 0;
  }

  public Vector2d moveVector() {
    Byte gene = this.genome.getRandomGene();
    return Direction.directionByCode(gene).toUnitVector();
  }

  public void moveTo(Vector2d newPosition) {
    this.position = newPosition;
  }

  public void increaseEnergy(int energy) {
    this.energy += energy;
  }

  public boolean ableToReproduce() {
    return this.energy >= this.minReproductionEnergy;
  }

  public Animal childWith(Animal other, List<Vector2d> possibleChildPositions) {
    Genome childGenome = this.genome.combine(other.genome);
    int childEnergy = (int) (0.25 * this.energy + 0.25 * other.energy);
    Vector2d childPosition = RandomUtils.randomElement(possibleChildPositions);
    this.energy *= 0.75;
    other.energy *= 0.75;
    return new Animal(childPosition, childEnergy, childGenome);
  }
}
