package agh.cs.evolution;

import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.MapArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapAreaTest {
  @Test
  public void containsReturnsTrueForCornerPosition() {
    MapArea mapArea = new MapArea(new Vector2d(0, 0), 10, 10);
    assertTrue(mapArea.contains(new Vector2d(0, 0)));
    assertTrue(mapArea.contains(new Vector2d(0, 9)));
    assertTrue(mapArea.contains(new Vector2d(9, 9)));
    assertTrue(mapArea.contains(new Vector2d(9, 0)));
  }

  @Test
  public void containsReturnsTrueForPositionsInside() {
    MapArea mapArea = new MapArea(new Vector2d(0, 0), 10, 10);
    assertTrue(mapArea.contains(new Vector2d(5, 5)));
    assertTrue(mapArea.contains(new Vector2d(3, 6)));
  }

  @Test
  public void containsReturnsFalseForPositionsOutside() {
    MapArea mapArea = new MapArea(new Vector2d(0, 0), 10, 10);
    assertFalse(mapArea.contains(new Vector2d(10, 10)));
    assertFalse(mapArea.contains(new Vector2d(-1, 3)));
    assertFalse(mapArea.contains(new Vector2d(5, 10)));
    assertFalse(mapArea.contains(new Vector2d(10, -2)));
  }
}
