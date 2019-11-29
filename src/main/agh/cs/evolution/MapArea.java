package agh.cs.evolution;

public class MapArea {
  private Vector2d lowerLeft;
  private Vector2d upperRight;

  public MapArea(Vector2d lowerLeft, int width, int height) {
    this.lowerLeft = lowerLeft;
    this.upperRight = lowerLeft.add(new Vector2d(width - 1, height - 1));
  }

  public boolean contains(Vector2d position) {
    return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
  }
}
