package agh.cs.evolution;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.DateControl;
import agh.cs.evolution.geometry.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
  private DateControl dateControl = new DateControl() {
    @Override
    public long getDayNumber() {
      return 0;
    }
  };

  @Test
  public void isDeadReturnsFalseIfAnimalHasPositiveEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 10, 5 , dateControl);
    assertFalse(animal.isDead());
  }

  @Test
  public void isDeadReturnsTrueIfAnimalHasNoEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 0, 5, dateControl);
    assertTrue(animal.isDead());
  }

  @Test
  public void isDeadReturnsFalseIfAnimalHasNegativeEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), -10, 5, dateControl);
    assertTrue(animal.isDead());
  }

  @Test
  public void childWithCreatesChildWithQuarterOfParentsEnergy() {
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, dateControl);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, dateControl);
    Animal child = parent1.childWith(parent2, List.of(new Vector2d(1, 1)));
    assertEquals(12, child.getEnergy());
  }

  @Test
  public void childWithDecreasesBothParentsEnergy() {
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, dateControl);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, dateControl);
    parent1.childWith(parent2, List.of(new Vector2d(1, 1)));
    assertEquals(15, parent1.getEnergy());
    assertEquals(23, parent2.getEnergy());
  }

  @Test
  public void childWithPlacesChildAtOneOfTheGivenPositions() {
    List<Vector2d> possibleChildPositions = List.of(new Vector2d(0, 1), new Vector2d(0, 1));
    Animal parent1 = new Animal(new Vector2d(0, 0), 20, 5, dateControl);
    Animal parent2 = new Animal(new Vector2d(0, 0), 30, 5, dateControl);
    Animal child = parent1.childWith(parent2, possibleChildPositions);
    assertTrue(possibleChildPositions.contains(child.getPosition()));
  }

  @Test
  public void ableToReproduceReturnsTrueIfAnimalHasEnoughEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 20, 10, dateControl);
    assertTrue(animal.ableToReproduce());
    animal.subtractEnergy(10);
    assertTrue(animal.ableToReproduce());
  }

  @Test
  public void ableToReproduceReturnsFalseIfAnimalHasLessThanRequiredEnergy() {
    Animal animal = new Animal(new Vector2d(0, 0), 9, 10, dateControl);
    assertFalse(animal.ableToReproduce());
  }

  @Test
  public void getDescendantCountCountsEveryDescendantOnce() {
    Animal animal1 = new Animal(new Vector2d(0, 0), 20, 1, dateControl);
    Animal animal2 = new Animal(new Vector2d(0, 0), 20, 1, dateControl);
    Animal child1 = animal1.childWith(animal2, List.of(new Vector2d(1, 1)));
    Animal child2 = animal1.childWith(child1, List.of(new Vector2d(0, 1)));
    assertEquals(2, animal1.getDescendantCount());
  }
}
