package agh.cs.evolution;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.geometry.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
  @Test
  public void isDeadReturnsFalseIfAnimalHasPositiveEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 10, 5 , 0);
    assertFalse(animal.isDead());
  }

  @Test
  public void isDeadReturnsTrueIfAnimalHasNoEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 0, 5, 0);
    assertTrue(animal.isDead());
  }

  @Test
  public void isDeadReturnsFalseIfAnimalHasNegativeEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), -10, 5, 0);
    assertTrue(animal.isDead());
  }

  @Test
  public void childWithCreatesChildWithQuarterOfParentsEnergy() {
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, 0);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, 0);
    Animal child = parent1.childWith(parent2, List.of(new Vector2d(1, 1)), 0);
    assertEquals(12, child.getEnergy());
  }

  @Test
  public void childWithDecreasesBothParentsEnergy() {
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, 0);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, 0);
    parent1.childWith(parent2, List.of(new Vector2d(1, 1)), 0);
    assertEquals(15, parent1.getEnergy());
    assertEquals(23, parent2.getEnergy());
  }

  @Test
  public void childWithPlacesChildAtOneOfTheGivenPositions() {
    List<Vector2d> possibleChildPositions = List.of(new Vector2d(0, 1), new Vector2d(0, 1));
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, 0);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, 0);
    Animal child = parent1.childWith(parent2, possibleChildPositions, 1);
    assertTrue(possibleChildPositions.contains(child.getPosition()));
  }

  @Test
  public void ableToReproduceReturnsTrueIfAnimalHasEnoughEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 20, 10, 0);
    assertTrue(animal.ableToReproduce());
    animal.addEnergy(-10);
    assertTrue(animal.ableToReproduce());
  }

  @Test
  public void ableToReproduceReturnsFalseIfAnimalHasLessThanRequiredEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 9, 10, 0);
    assertFalse(animal.ableToReproduce());
  }

  @Test
  public void getDescendantCountCountsEveryDescendantOnce() {
    Animal animal1 = new Animal(new Vector2d(0, 0), 20, 1, 0);
    Animal animal2 = new Animal(new Vector2d(0, 0), 20, 1, 0);
    Animal child1 = animal1.childWith(animal2, List.of(new Vector2d(1, 1)), 1);
    Animal child2 = animal1.childWith(child1, List.of(new Vector2d(0, 1)), 2);
    assertEquals(2, animal1.getDescendantCount());
  }
}
