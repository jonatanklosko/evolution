package agh.cs.evolution;

public abstract class AbstractMapElement implements IMapElement {
  protected Vector2d position;

  public AbstractMapElement(Vector2d position) {
    this.position = position;
  }

  public Vector2d getPosition() {
    return this.position;
  }
}
