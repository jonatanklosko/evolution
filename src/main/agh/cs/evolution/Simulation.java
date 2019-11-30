package agh.cs.evolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {
  private final int startEnergy;
  private final int moveEnergy;
  private final int plantEnergy;
  private final WorldMap map;
  private MapVisualizer mapVisualizer;

  public Simulation(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio) {
    this.map = new WorldMap(width, height, jungleRatio);
    this.startEnergy = startEnergy;
    this.moveEnergy = moveEnergy;
    this.plantEnergy = plantEnergy;
    this.mapVisualizer = new MapVisualizer(this.map);
  }

  public void initialize(int numberOfAnimals) {
    this.map.randomPositions$().distinct().limit(numberOfAnimals).forEach(position -> {
      Animal animal = new Animal(position, this.startEnergy);
      this.map.addElement(animal);
    });
  }

  public void nextDay() {
     this.removeDeadAnimals();
     this.moveAnimals();
     this.eat();
     this.reproduce();
     this.generatePlants();
  }

  public void removeDeadAnimals() {
    this.animals$()
        .filter(Animal::isDead)
        .collect(Collectors.toList())  /* Materialize the stream first as we remove elements. */
        .forEach(this.map::removeElement);
  }

  public void moveAnimals() {
    this.animals$()
        .collect(Collectors.toList()) /* Materialize the stream first as we remove elements. */
        .forEach(animal -> {
          animal.rotate();
          Vector2d move = animal.moveVector();
          Vector2d newPosition = this.map.shiftIntoBounds(animal.getPosition().add(move));
          this.map.removeElement(animal);
          animal.moveTo(newPosition);
          this.map.addElement(animal);
          animal.addEnergy(-this.moveEnergy);
        });
  }

  public void eat() {
    this.map.positionsWithElements$().map(Map.Entry::getValue).forEach(elementsAtPosition -> {
      Plant plant = (Plant) elementsAtPosition.stream()
          .filter(Plant.class::isInstance)
          .findAny()
          .orElse(null);
      if (plant == null) return;
      TreeMap<Integer, List<Animal>> animalsByEnergy = elementsAtPosition.stream()
          .filter(Animal.class::isInstance)
          .map(Animal.class::cast)
          .collect(
              Collectors.groupingBy(Animal::getEnergy, TreeMap::new, Collectors.toList())
          );
      if (animalsByEnergy.isEmpty()) return;
      animalsByEnergy.lastEntry().getValue().forEach(animal -> {
        animal.addEnergy(plant.getEnergy() / animalsByEnergy.size());
        this.map.removeElement(plant);
      });
    });
  }

  public void reproduce() {
    this.map.positionsWithElements$()
        .collect(Collectors.toList()) /* Materialize the stream first as we remove elements. */
        .forEach(positionWithElements -> {
          Vector2d position = positionWithElements.getKey();
          List<IMapElement> elementsAtPosition = positionWithElements.getValue();
          List<Animal> animalPair = elementsAtPosition.stream()
              .filter(Animal.class::isInstance)
              .map(Animal.class::cast)
              .filter(Animal::ableToReproduce)
              .sorted(Comparator.comparing(Animal::getEnergy).reversed())
              .limit(2)
              .collect(Collectors.toList());
          List<Vector2d> possibleChildPositions = this.map.freeAdjacentPositions$(position).collect(Collectors.toList());
          if (animalPair.size() != 2 || possibleChildPositions.isEmpty()) return;
          Animal child = animalPair.get(0).childWith(animalPair.get(1), possibleChildPositions);
          this.map.addElement(child);
        });
  }

  public void generatePlants() {
    Map<Boolean, List<Vector2d>> freePositionsByJungle = this.map.freePositions$()
        .collect(Collectors.partitioningBy(this.map::isInJungle));
    List<Vector2d> freeJunglePositions = freePositionsByJungle.get(true);
    List<Vector2d> freeSteppePositions = freePositionsByJungle.get(false);
    if (!freeJunglePositions.isEmpty()) {
      Vector2d junglePlantPosition = RandomUtils.randomElement(freeJunglePositions);
      Plant junglePlant = new Plant(junglePlantPosition, this.plantEnergy);
      this.map.addElement(junglePlant);
    }
    if (!freeSteppePositions.isEmpty()) {
      Vector2d steppePlantPosition = RandomUtils.randomElement(freeSteppePositions);
      Plant steppePlant = new Plant(steppePlantPosition, this.plantEnergy);
      this.map.addElement(steppePlant);
    }
  }

  public Stream<Genome> livingGenomes$() {
    return this.animals$()
        .filter(animal -> !animal.isDead())
        .map(Animal::getGenome);
  }

  public String getVisualization() {
    return this.mapVisualizer.draw(
        new Vector2d(0, 0),
        new Vector2d(this.map.width - 1, this.map.height - 1)
    );
  }

  private Stream<Animal> animals$() {
    return this.map.elements$()
        .filter(Animal.class::isInstance)
        .map(Animal.class::cast);
  }

  public WorldMap getMap() {
    return this.map;
  }
}
