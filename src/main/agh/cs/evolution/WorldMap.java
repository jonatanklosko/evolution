package agh.cs.evolution;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorldMap implements IWorldMap, IPositionChangeObserver {
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

  public void addElement(IMapElement element) {
    this.addElementToPosition(element, element.getPosition());
    if (element instanceof IPositionChangeSubject) {
      ((IPositionChangeSubject) element).addPositionChangeObserver(this);
    }
  }

  public void removeElement(IMapElement element) {
    this.removeElementFromPosition(element, element.getPosition());
    if (element instanceof IPositionChangeSubject) {
      ((IPositionChangeSubject) element).removePositionChangeObserver(this);
    }
  }

  public boolean isOccupied(Vector2d position) {
    return this.elementsByPosition.containsKey(position);
  }

  public List<IMapElement> elementsAt(Vector2d position) {
    return this.elementsByPosition.getOrDefault(position, new LinkedList<>());
  }

  public Vector2d getLowerLeft() {
    return new Vector2d(0, 0);
  }

  public Vector2d getUpperRight() {
    return new Vector2d(this.width - 1, this.height - 1);
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

  public Stream<Vector2d> freeAdjacentPositions$(Vector2d position) {
    return Direction.allDirections$()
        .map(Direction::toUnitVector)
        .map(unitVector -> this.shiftIntoBounds(position.add(unitVector)))
        .filter(adjacentPosition -> !this.isOccupied(adjacentPosition));
  }

  public Vector2d shiftIntoBounds(Vector2d position) {
    return new Vector2d(
        Math.floorMod(position.x, this.width),
        Math.floorMod(position.y, this.height)
    );
  }

  public boolean isInJungle(Vector2d position) {
    return this.jungle.contains(position);
  }

  public void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition) {
    this.removeElementFromPosition(element, oldPosition);
    this.addElementToPosition(element, newPosition);
  }

  private void addElementToPosition(IMapElement element, Vector2d position) {
    List<IMapElement> elementsAtPosition = this.elementsByPosition.get(position);
    if (elementsAtPosition == null) {
      this.elementsByPosition.put(position, new LinkedList<>(List.of(element)));
    } else {
      elementsAtPosition.add(element);
    }
  }

  private void removeElementFromPosition(IMapElement element, Vector2d position) {
    List<IMapElement> elementsAtPosition = this.elementsByPosition.get(position);
    elementsAtPosition.remove(element);
    if (elementsAtPosition.isEmpty()) {
      this.elementsByPosition.remove(position);
    }
  }
}
