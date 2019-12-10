package agh.cs.evolution;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractMapElement implements IPositionChangeSubject {
  private final Genome genome;
  private int energy;
  private final int minReproductionEnergy;
  private Direction direction;
  private List<IPositionChangeObserver> positionChangeObservers;

  public Animal(Vector2d position, int energy, int minReproductionEnergy) {
    this(position, energy, minReproductionEnergy, Genome.randomGenome());
  }

  public Animal(Vector2d position, int energy, int minReproductionEnergy, Genome genome) {
    super(position);
    this.energy = energy;
    this.genome = genome;
    this.minReproductionEnergy = minReproductionEnergy;
    this.direction = Direction.randomDirection();
    this.positionChangeObservers = new LinkedList<>();
  }

  public int getEnergy() {
    return this.energy;
  }

  public Genome getGenome() {
    return this.genome;
  }

  public int getMinReproductionEnergy() {
    return this.minReproductionEnergy;
  }

  public boolean isDead() {
    return this.energy <= 0;
  }

  public void rotate() {
    byte gene = this.genome.getRandomGene();
    Direction geneDirection = Direction.directionByValue(gene);
    this.direction = this.direction.compose(geneDirection);
  }

  public Vector2d getMoveVector() {
    return this.direction.toUnitVector();
  }

  public void moveTo(Vector2d newPosition) {
    this.setPosition(newPosition);
  }

  public void addEnergy(int energyDiff) {
    this.energy += energyDiff;
  }

  public boolean ableToReproduce() {
    return this.energy >= this.minReproductionEnergy;
  }

  public Animal childWith(Animal other, List<Vector2d> possibleChildPositions) {
    Genome childGenome = this.genome.combine(other.genome);
    int childEnergy = this.energy / 4 + other.energy / 4;
    Vector2d childPosition = RandomUtils.randomElement(possibleChildPositions);
    this.addEnergy(-this.energy / 4);
    other.addEnergy(-other.energy / 4);
    return new Animal(childPosition, childEnergy, this.minReproductionEnergy, childGenome);
  }

  public void addPositionChangeObserver(IPositionChangeObserver observer) {
    this.positionChangeObservers.add(observer);
  }

  public void removePositionChangeObserver(IPositionChangeObserver observer) {
    this.positionChangeObservers.remove(observer);
  }

  private void setPosition(Vector2d newPosition) {
    Vector2d oldPosition = this.position;
    this.position = newPosition;
    for (IPositionChangeObserver observer : this.positionChangeObservers) {
      observer.positionChanged(this, oldPosition, newPosition);
    }
  }
}
