package agh.cs.evolution;

import java.util.List;

public class Animal extends AbstractMapElement {
  private final Genome genome;
  private int energy;
  private int minReproductionEnergy;
  private Direction direction;

  public Animal(Vector2d position, int energy) {
    this(position, energy, new Genome());
  }

  public Animal(Vector2d position, int energy, Genome genome) {
    super(position);
    this.energy = energy;
    this.genome = genome;
    this.minReproductionEnergy = energy / 2;
    this.direction = Direction.randomDirection();
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

  public void rotate() {
    byte gene = this.genome.getRandomGene();
    Direction geneDirection = Direction.directionByValue(gene);
    this.direction = this.direction.compose(geneDirection);
  }

  public Vector2d moveVector() {
    return this.direction.toUnitVector();
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
