package agh.cs.evolution;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.Plant;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.IWorldMap;
import agh.cs.evolution.simulation.Simulation;
import agh.cs.evolution.simulation.SimulationParams;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
  private static SimulationParams params = new SimulationParams(50, 50, 20, 2, 10, 0.2, 0);
  @Test
  public void removeDeadAnimalsShouldRemoveDeadAnimalsFromMap() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal aliveAnimal = new Animal(new Vector2d(0, 0), 1, 5, 0);
    map.addElement(aliveAnimal);
    map.addElement(new Animal(new Vector2d(0, 0), 0, 5, 0));
    map.addElement(new Animal(new Vector2d(0, 0), -1, 5, 0));
    simulation.removeDeadAnimals();
    assertEquals(List.of(aliveAnimal), map.elementsAt(new Vector2d(0, 0)));
  }

  @Test
  public void eatRemovesPlantAndIncreasesAnimalEnergy() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Plant plant = new Plant(new Vector2d(0, 0), 10);
    map.addElement(animal);
    map.addElement(plant);
    simulation.eat();
    assertEquals(List.of(animal), map.elementsAt(new Vector2d(0, 0)));
    assertEquals(20, animal.getEnergy());
  }

  @Test
  public void eatPrioritizesAnimalWithHighestEnergy() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 5, 5, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 15, 5, 0);
    Animal animal3 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Plant plant = new Plant(new Vector2d(0, 0), 10);
    map.addElement(animal1);
    map.addElement(animal2);
    map.addElement(animal3);
    map.addElement(plant);
    simulation.eat();
    assertEquals(25, animal2.getEnergy());
    assertEquals(5, animal1.getEnergy());
    assertEquals(10, animal3.getEnergy());
  }

  @Test
  public void eatSplitsPlantEnergyIfManyAnimalsHaveMaxEnergy() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Plant plant = new Plant(new Vector2d(0, 0), 10);
    map.addElement(animal1);
    map.addElement(animal2);
    map.addElement(plant);
    simulation.eat();
    assertEquals(15, animal2.getEnergy());
    assertEquals(15, animal1.getEnergy());
  }

  @Test
  public void reproduceConsidersOnlyTwoAnimalsWithMaxEnergyAsParents() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 5, 5, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 15, 5, 0);
    Animal animal3 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    map.addElement(animal1);
    map.addElement(animal2);
    map.addElement(animal3);
    simulation.reproduce();
    assertEquals(4, map.allElements().size());
    assertEquals(5, animal1.getEnergy());
    assertEquals(12, animal2.getEnergy());
    assertEquals(8, animal3.getEnergy());
  }

  @Test
  public void reproduceDoesNotReproduceIfAnimalsDoNotHaveSufficientEnergy() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 10, 20, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    map.addElement(animal1);
    map.addElement(animal2);
    simulation.reproduce();
    assertEquals(List.of(animal1, animal2), map.allElements());
    assertEquals(10, animal1.getEnergy());
    assertEquals(10, animal2.getEnergy());
  }

  @Test
  public void generatePlantsDoesNotAddPlantsIfTheMapIsFull() {
    Simulation simulation = new Simulation(new SimulationParams(2, 2, 20, 2, 10, 0.2, 0));
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Animal animal2 = new Animal(new Vector2d(0, 1), 10, 5, 0);
    Plant plant1 = new Plant(new Vector2d(1, 0), 10);
    Plant plant2 = new Plant(new Vector2d(1, 1), 10);
    map.addElement(animal1);
    map.addElement(animal2);
    map.addElement(plant1);
    map.addElement(plant2);
    simulation.generatePlants();
    assertEquals(List.of(animal1, animal2, plant1, plant2), map.allElements());
  }

  @Test
  public void livingGenomesIgnoresDeadAnimals() {
    Simulation simulation = new Simulation(SimulationTest.params);
    IWorldMap map = simulation.getMap();
    Animal animal1 = new Animal(new Vector2d(0, 0), 0, 5, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 10, 5, 0);
    Animal animal3 = new Animal(new Vector2d(0, 0), -2, 5, 0);
    map.addElement(animal1);
    map.addElement(animal2);
    map.addElement(animal3);
    assertEquals(List.of(animal2.getGenome()), simulation.livingGenomes$().collect(Collectors.toList()));
  }
}
