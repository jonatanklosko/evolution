package agh.cs.evolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Direction {
  NORTH(0),
  NORTHEAST(1),
  EAST(2),
  SOUTHEAST(3),
  SOUTH(4),
  SOUTHWEST(5),
  WEST(6),
  NORTHWEST(7);

  private final int value;
  private static Map<Integer, Direction> directionByValue = new HashMap<>();

  Direction(int value) {
    this.value = value;
  }

  static {
    Arrays.stream(values()).forEach(direction -> directionByValue.put(direction.value, direction));
  }

  public static Direction directionByValue(int value) {
    if (value < 0 || value > 7) {
      throw new IllegalArgumentException("Invalid direction value: " + value);
    }
    return Direction.directionByValue.get(value);
  }

  public static Stream<Vector2d> allUnitVectors$() {
    return Arrays.stream(Direction.values()).map(Direction::toUnitVector);
  }

  public static Direction randomDirection() {
    return RandomUtils.randomElement(Direction.values());
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

  public Direction compose(Direction other) {
    return Direction.directionByValue.get((this.value + other.value) % 8);
  }

  public String symbolRepresentation() {
    switch (this) {
      case NORTH: return "↑";
      case NORTHEAST: return "↗";
      case EAST: return "→";
      case SOUTHEAST: return "↘";
      case SOUTH: return "↓";
      case SOUTHWEST: return "↙";
      case WEST: return "←";
      case NORTHWEST: return "↖";
    }
    return null;
  }
}
