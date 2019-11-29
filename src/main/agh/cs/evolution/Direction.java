package agh.cs.evolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Direction {
  NORTH,
  NORTHEAST,
  EAST,
  SOUTHEAST,
  SOUTH,
  SOUTHWEST,
  WEST,
  NORTHWEST;

  public static Direction directionByCode(int code) {
    if (code < 0 || code > 7) {
      throw new IllegalArgumentException("Invalid direction code: " + code);
    }
    return Direction.values()[code];
  }

  public static List<Vector2d> allUnitVectors() {
    return Arrays.asList(Direction.values()).stream()
        .map(Direction::toUnitVector)
        .collect(Collectors.toList());
  }

  public Vector2d toUnitVector() {
    switch (this) {
      case NORTH: return new Vector2d(0, 1);
      case NORTHEAST: return new Vector2d(1, 1);
      case EAST: return new Vector2d(1, 0);
      case SOUTHEAST: return new Vector2d(1, -1);
      case SOUTH: return new Vector2d(0, -1);
      case SOUTHWEST: return new Vector2d(-1, -1);
      case WEST: return new Vector2d(-1, 0);
      case NORTHWEST: return new Vector2d(-1, 1);
    }
    return null;
  }
}
