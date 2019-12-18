package agh.cs.evolution.simulation;

import agh.cs.evolution.elements.*;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.MapWithJungle;
import agh.cs.evolution.utils.RandomUtils;
import agh.cs.evolution.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation implements DateControl {
  public final SimulationParams params;
  private final MapWithJungle map;
  private long dayNumber;
  private List<SimulationDaySummary> daySummaries;
  private List<Long> lifetimes;

  public Simulation(SimulationParams params) {
    this.params = params;
    this.map = new MapWithJungle(params.width, params.height, params.jungleRatio);
    this.dayNumber = 1;
    this.daySummaries = new LinkedList<>();
    this.lifetimes = new LinkedList<>();
    this.initialize(params.initialNumberOfAnimals);
  }

  private void initialize(int numberOfAnimals) {
    this.map.randomPositions$().distinct().limit(numberOfAnimals).forEach(position -> {
      Animal animal = new Animal(position, this.params.startEnergy, this.params.startEnergy / 2, this);
      this.map.addElement(animal);
    });
  }

  public void nextDay() {
    this.daySummaries.add(new SimulationDaySummary(
        this.dayNumber,
        this.getAnimalCount(),
        this.getPlantCount(),
        this.getAverageAnimalEnergy().orElse(0.0),
        this.getAverageAge().orElse(0.0),
        this.getAverageChildrenCount().orElse(0.0),
        this.getAverageLifetime().orElse(0.0)
    ));
     this.removeDeadAnimals();
     this.moveAnimals();
     this.eat();
     this.reproduce();
     this.generatePlants();
     this.dayNumber++;
  }

  public void nextYear() {
    for (int i = 1; i <= 365; i++) {
      this.nextDay();
    }
  }

  public void removeDeadAnimals() {
    this.animals$()
        .filter(Animal::isDead)
        .collect(Collectors.toList())  /* Materialize the stream first as we remove elements. */
        .forEach(animal -> {
          this.map.removeElement(animal);
          this.lifetimes.add(animal.getLifetime());
        });
  }

  public void moveAnimals() {
    this.animals$()
        .collect(Collectors.toList()) /* Materialize the stream first as we remove elements. */
        .forEach(animal -> {
          animal.rotate();
          Vector2d move = animal.getMoveVector();
          Vector2d newPosition = this.map.shiftIntoBounds(animal.getPosition().add(move));
          animal.moveTo(newPosition);
          animal.addEnergy(-this.params.moveEnergy);
        });
  }

  public void eat() {
    this.map.positionsWithElements$().map(Map.Entry::getValue).forEach(elementsAtPosition -> {
      Utils.filterType(elementsAtPosition.stream(), Plant.class)
          .findAny()
          .ifPresent(plant -> {
            TreeMap<Integer, List<Animal>> animalsByEnergy = Utils.filterType(elementsAtPosition.stream(), Animal.class)
                .collect(
                    Collectors.groupingBy(Animal::getEnergy, TreeMap::new, Collectors.toList())
                );
            if (animalsByEnergy.isEmpty()) return;
            List<Animal> animalsWithHighestEnergy = animalsByEnergy.lastEntry().getValue();
            animalsWithHighestEnergy.forEach(animal -> {
              animal.addEnergy(plant.getEnergy() / animalsWithHighestEnergy.size());
              this.map.removeElement(plant);
            });
          });
    });
  }

  public void reproduce() {
    this.map.positionsWithElements$()
        .collect(Collectors.toList()) /* Materialize the stream first as we remove elements. */
        .forEach(positionWithElements -> {
          Vector2d position = positionWithElements.getKey();
          List<IMapElement> elementsAtPosition = positionWithElements.getValue();
          List<Animal> animalPair = Utils.filterType(elementsAtPosition.stream(), Animal.class)
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
      Plant junglePlant = new Plant(junglePlantPosition, this.params.plantEnergy);
      this.map.addElement(junglePlant);
    }
    if (!freeSteppePositions.isEmpty()) {
      Vector2d steppePlantPosition = RandomUtils.randomElement(freeSteppePositions);
      Plant steppePlant = new Plant(steppePlantPosition, this.params.plantEnergy);
      this.map.addElement(steppePlant);
    }
  }

  public Stream<Genome> livingGenomes$() {
    return this.animals$()
        .filter(animal -> !animal.isDead())
        .map(Animal::getGenome);
  }

  public Optional<Genome> dominantGenome() {
     Map<Genome, Long> countByGenome = this.livingGenomes$()
        .collect(Collectors.groupingBy(genome -> genome, Collectors.counting()));
     if (countByGenome.isEmpty()) return Optional.empty();
     Long maxCount = countByGenome.values().stream().max(Long::compareTo).get();
     List<Genome> dominantGenomes = countByGenome.entrySet().stream()
         .filter(entry -> entry.getValue().equals(maxCount))
         .map(Map.Entry::getKey)
         .collect(Collectors.toList());
     if (dominantGenomes.size() > 1) return Optional.empty();
     return Optional.of(dominantGenomes.get(0));
  }

  public long getAnimalCount() {
    return this.livingGenomes$().count();
  }

  public long getPlantCount() {
    return Utils.filterType(this.map.elements$(), Plant.class).count();
  }

  public Optional<Double> getAverageAnimalEnergy() {
    if (this.animals$().count() == 0) return Optional.empty();
    return Optional.of(
        this.animals$()
            .collect(Collectors.averagingDouble(Animal::getEnergy))
    );
  }

  public Optional<Double> getAverageChildrenCount() {
    if (this.animals$().count() == 0) return Optional.empty();
    return Optional.of(
        this.animals$()
            .collect(Collectors.averagingDouble(Animal::getChildrenCount))
    );
  }

  public Optional<Double> getAverageAge() {
    if (this.animals$().count() == 0) return Optional.empty();
    return Optional.of(
        this.animals$()
            .collect(Collectors.averagingDouble(animal -> this.dayNumber - animal.getBirthDay()))
    );
  }

  public Optional<Double> getAverageLifetime() {
    if (this.lifetimes.size() == 0) return Optional.empty();
    return Optional.of(
        this.lifetimes.stream()
            .collect(Collectors.averagingDouble(lifetime -> lifetime))
    );
  }

  private Stream<Animal> animals$() {
    return Utils.filterType(this.map.elements$(), Animal.class);
  }

  public MapWithJungle getMap() {
    return this.map;
  }

  public long getDayNumber() {
    return this.dayNumber;
  }

  public long getDay() {
    return this.dayNumber;
  }

  public List<SimulationDaySummary> getDaySummaries() {
    return this.daySummaries;
  }
}
