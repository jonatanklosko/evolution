package agh.cs.evolution.elements;

import agh.cs.evolution.geometry.Direction;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.utils.RandomUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Animal extends AbstractMapElement implements IPositionChangeSubject {
  private final Genome genome;
  private int energy;
  private final int minReproductionEnergy;
  private Direction direction;
  private List<IPositionChangeObserver> positionChangeObservers;
  private List<Animal> children;
  private final long birthDay;

  public Animal(Vector2d position, int energy, int minReproductionEnergy, long birthDay) {
    this(position, energy, minReproductionEnergy, Genome.randomGenome(), birthDay);
  }

  public Animal(Vector2d position, int energy, int minReproductionEnergy, Genome genome, long birthDay) {
    super(position);
    this.energy = energy;
    this.genome = genome;
    this.minReproductionEnergy = minReproductionEnergy;
    this.direction = Direction.randomDirection();
    this.positionChangeObservers = new LinkedList<>();
    this.children = new LinkedList<>();
    this.birthDay = birthDay;
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

  public long getBirthDay() {
    return this.birthDay;
  }

  public long getChildrenCount() {
    return this.children.size();
  }

  public long getDescendantCount() {
    return this.descendants$().count();
  }

  private Stream<Animal> descendants$() {
    return Stream.concat(
        this.children.stream(),
        this.children.stream().flatMap(Animal::descendants$)
    ).distinct();
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

  public Animal childWith(Animal other, List<Vector2d> possibleChildPositions, long birthDay) {
    Genome childGenome = this.genome.combine(other.genome);
    int childEnergy = this.energy / 4 + other.energy / 4;
    Vector2d childPosition = RandomUtils.randomElement(possibleChildPositions);
    Animal child = new Animal(childPosition, childEnergy, this.minReproductionEnergy, childGenome, birthDay);
    this.addEnergy(-this.energy / 4);
    other.addEnergy(-other.energy / 4);
    this.children.add(child);
    other.children.add(child);
    return child;
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
