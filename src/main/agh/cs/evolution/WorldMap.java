package agh.cs.evolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorldMap {
  public final int width;
  public final int height;
  private Map<Vector2d, List<IMapElement>> elementsByPosition;
  private MapArea jungle;

  public WorldMap(int width, int height, double jungleRatio) {
    this.width = width;
    this.height = height;
    this.elementsByPosition = new LinkedHashMap<>();
    this.jungle = calculateJungle(width, height, jungleRatio);
  }

  private static MapArea calculateJungle(int width, int height, double jungleRatio) {
    int jungleWidth = (int) (width * jungleRatio);
    int jungleHeight = (int) (height * jungleRatio);
    int jungleX = (width - jungleWidth) / 2;
    int jungleY = (height - jungleHeight) / 2;
    Vector2d jungleLowerLeft = new Vector2d(jungleX, jungleY);
    return new MapArea(jungleLowerLeft, jungleWidth, jungleHeight);
  }

  public boolean isOccupied(Vector2d position) {
    return this.elementsByPosition.containsKey(position);
  }

  public Stream<IMapElement> elements$() {
    return this.elementsByPosition.values().stream()
        .flatMap(Collection::stream);
  }

  public Stream<Map.Entry<Vector2d, List<IMapElement>>> positionsWithElements$() {
    return this.elementsByPosition.entrySet().stream();
  }

  public Stream<Vector2d> freePositions$() {
    return IntStream.range(0, this.width).boxed()
        .flatMap(x -> IntStream.range(0, this.height).boxed().map(y -> new Vector2d(x, y)))
        .filter(position -> !this.isOccupied(position));
  }

  public Stream<Vector2d> randomPositions$() {
    return Stream.generate(() -> new Vector2d(
        RandomUtils.random.nextInt(this.width),
        RandomUtils.random.nextInt(this.height)
    ));
  }

  public boolean isInJungle(Vector2d position) {
    return this.jungle.contains(position);
  }

  public Stream<Vector2d> emptyAdjacentPositions$(Vector2d position) {
    return Direction.allUnitVectors$()
        .map(unitVector -> this.shiftIntoBounds(position.add(unitVector)))
        .filter(adjacentPosition -> !this.isOccupied(adjacentPosition));
  }

  public Vector2d shiftIntoBounds(Vector2d position) {
    return new Vector2d(
        ((position.x + this.width) % this.width),
        ((position.y + this.height) % this.height)
    );
  }

  public void addElement(IMapElement element) {
    Vector2d position = element.getPosition();
    List<IMapElement> elementsAtPosition = this.elementsByPosition.get(position);
    if (elementsAtPosition == null) {
      this.elementsByPosition.put(position, new LinkedList<>(List.of(element)));
    } else {
      elementsAtPosition.add(element);
    }
  }

  public void removeElement(IMapElement element) {
    Vector2d position = element.getPosition();
    List<IMapElement> elementsAtPosition = this.elementsByPosition.get(position);
    elementsAtPosition.remove(element);
    if (elementsAtPosition.isEmpty()) {
      this.elementsByPosition.remove(position);
    }
  }
}
