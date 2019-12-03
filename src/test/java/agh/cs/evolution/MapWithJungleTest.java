package agh.cs.evolution;

import agh.cs.evolution.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MapWithJungleTest {
  @Test
  public void isOccupiedReturnsTrueIfThePositionIsOccupied() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    map.addElement(new Plant(new Vector2d(2, 2), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    assertTrue(map.isOccupied(new Vector2d(2, 2)));
    assertTrue(map.isOccupied(new Vector2d(3, 3)));
  }

  @Test
  public void isOccupiedReturnsFalseIfThePositionIsNotOccupied() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    map.addElement(new Plant(new Vector2d(2, 2), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    assertFalse(map.isOccupied(new Vector2d(2, 3)));
  }

  @Test
  public void elementsAtReturnsListOfAllElementsAtTheGivenPosition() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    IMapElement plant = new Plant(new Vector2d(2, 2), 10);
    IMapElement animal = new Animal(new Vector2d(2, 2), 10);
    map.addElement(plant);
    map.addElement(animal);
    assertEquals(List.of(plant, animal), map.elementsAt(new Vector2d(2, 2)));
  }

  @Test
  public void elementsAtReturnsAnEmptyListIfTheGivenPositionIsFree() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    map.addElement(new Plant(new Vector2d(2, 2), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    assertTrue(map.elementsAt(new Vector2d(1, 1)).isEmpty());
  }

  @Test
  public void elements$ReturnsStreamWithElementsFromAllPositions() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    map.addElement(new Plant(new Vector2d(2, 2), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    assertEquals(3, map.elements$().count());
  }

  @Test
  public void freePositions$ReturnsStreamWithAllFreePositions() {
    MapWithJungle map = new MapWithJungle(2, 2, 0.2);
    map.addElement(new Plant(new Vector2d(0, 0), 10));
    map.addElement(new Animal(new Vector2d(1, 0), 10));
    List<Vector2d> freePositions = map.freePositions$().collect(Collectors.toList());
    assertTrue(freePositions.size() == 2);
    assertTrue(freePositions.contains(new Vector2d(0, 1)));
    assertTrue(freePositions.contains(new Vector2d(1, 1)));
  }

  @Test
  public void freeAdjacentPositions$ReturnsStreamWithAllFreeAdjacentPositions() {
    MapWithJungle map = new MapWithJungle(5, 5, 0.2);
    map.addElement(new Plant(new Vector2d(0, 0), 10));
    map.addElement(new Animal(new Vector2d(1, 0), 10));
    map.addElement(new Animal(new Vector2d(1, 2), 10));
    map.addElement(new Animal(new Vector2d(3, 3), 10));
    List<Vector2d> positions = map.freeAdjacentPositions$(new Vector2d(1, 1)).collect(Collectors.toList());
    assertTrue(positions.size() == 5);
    assertTrue(positions.contains(new Vector2d(0, 1)));
    assertTrue(positions.contains(new Vector2d(2, 2)));
  }

  @Test
  public void freeAdjacentPositions$TreatsShiftedPositionsAsAdjacent() {
    MapWithJungle map = new MapWithJungle(5, 5, 0.2);
    List<Vector2d> positions = map.freeAdjacentPositions$(new Vector2d(0, 0)).collect(Collectors.toList());
    assertTrue(positions.size() == 8);
    assertTrue(positions.contains(new Vector2d(4, 4)));
    assertTrue(positions.contains(new Vector2d(4, 0)));
  }

  @Test
  public void shiftIntoBoundsReturnsPositionMovedBackToMap() {
    MapWithJungle map = new MapWithJungle(5, 5, 0.2);
    assertEquals(new Vector2d(2, 0), map.shiftIntoBounds(new Vector2d(2, 5)));
    assertEquals(new Vector2d(0, 3), map.shiftIntoBounds(new Vector2d(5, 3)));
    assertEquals(new Vector2d(0, 0), map.shiftIntoBounds(new Vector2d(5, 5)));
    assertEquals(new Vector2d(4, 4), map.shiftIntoBounds(new Vector2d(-1, -1)));
    assertEquals(new Vector2d(2, 2), map.shiftIntoBounds(new Vector2d(7, 7)));
  }

  @Test
  public void isInJungleReturnsTrueForPositionsInTheMiddle() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    assertTrue(map.isInJungle(new Vector2d(4, 4)));
    assertTrue(map.isInJungle(new Vector2d(4, 5)));
    assertTrue(map.isInJungle(new Vector2d(5, 4)));
    assertTrue(map.isInJungle(new Vector2d(5, 5)));
  }

  @Test
  public void isInJungleReturnsTrueForPositionsOutsideTheJungle() {
    MapWithJungle map = new MapWithJungle(10, 10, 0.2);
    assertFalse(map.isInJungle(new Vector2d(0, 0)));
    assertFalse(map.isInJungle(new Vector2d(3, 3)));
    assertFalse(map.isInJungle(new Vector2d(5, 6)));
  }
}
