package agh.cs.evolution;

import java.util.List;

public class MapVisualizer {
  private static final String EMPTY_CELL = " ";
  private static final String FRAME_SEGMENT = "-";
  private static final String CELL_SEGMENT = "|";
  private WorldMap map;

  public MapVisualizer(WorldMap map) {
    this.map = map;
  }

  public String draw(Vector2d lowerLeft, Vector2d upperRight) {
    StringBuilder builder = new StringBuilder();
    int width = upperRight.x - lowerLeft.x + 1;
    String horizontalFrame = " ".repeat(4) + FRAME_SEGMENT.repeat(1 + 2 * width) + System.lineSeparator();
    builder.append(horizontalFrame);
    for (int i = upperRight.y; i >= lowerLeft.y; i--) {
      builder.append(String.format("%3d ", i));
      for (int j = lowerLeft.x; j <= upperRight.x; j++) {
        builder.append(CELL_SEGMENT);
        builder.append(drawMapElement(new Vector2d(j, i)));
      }
      builder.append(CELL_SEGMENT);
      builder.append(System.lineSeparator());
    }
    builder.append(horizontalFrame);
    builder.append(" ".repeat(4));
    for (int i = lowerLeft.x; i <= upperRight.x; i++) {
      builder.append(String.format("%2d", i));
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