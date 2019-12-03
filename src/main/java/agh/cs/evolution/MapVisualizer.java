package agh.cs.evolution;

import java.util.List;

public class MapVisualizer {
  private static final String EMPTY_CELL = " ";
  private static final String FRAME_SEGMENT = "-";
  private static final String CELL_SEGMENT = "|";
  private IWorldMap map;

  public MapVisualizer(IWorldMap map) {
    this.map = map;
  }

  public String draw() {
    Vector2d lowerLeft = this.map.getLowerLeft();
    Vector2d upperRight = this.map.getUpperRight();
    StringBuilder builder = new StringBuilder();
    int width = upperRight.x - lowerLeft.x + 1;
    String horizontalFrame = " ".repeat(4) + FRAME_SEGMENT.repeat(1 + 2 * width) + System.lineSeparator();
    builder.append(horizontalFrame);
    for (int y = upperRight.y; y >= lowerLeft.y; y--) {
      builder.append(String.format("%3d ", y));
      for (int x = lowerLeft.x; x <= upperRight.x; x++) {
        builder.append(CELL_SEGMENT);
        builder.append(drawMapElement(new Vector2d(x, y)));
      }
      builder.append(CELL_SEGMENT);
      builder.append(System.lineSeparator());
    }
    builder.append(horizontalFrame);
    builder.append(" ".repeat(4));
    for (int x = lowerLeft.x; x <= upperRight.x; x++) {
      builder.append(String.format("%2d", x));
    }
    builder.append(System.lineSeparator());
    return builder.toString();
  }

  private String drawMapElement(Vector2d currentPosition) {
    List<IMapElement> elements = this.map.elementsAt(currentPosition);
    switch (elements.size()) {
      case 0: return EMPTY_CELL;
      case 1: return elements.get(0).symbolRepresentation();
      default: return "⥁";
    }
  }
}