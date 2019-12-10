package agh.cs.evolution;

import agh.cs.evolution.geometry.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
  @Test
  public void composeReturnsDirectionRotatedAccordingToTheGivenOne() {
    assertEquals(Direction.EAST, Direction.NORTH.compose(Direction.EAST));
    assertEquals(Direction.SOUTHEAST, Direction.EAST.compose(Direction.NORTHEAST));
    assertEquals(Direction.NORTHEAST, Direction.SOUTH.compose(Direction.SOUTHWEST));
    assertEquals(Direction.WEST, Direction.SOUTHEAST.compose(Direction.SOUTHEAST));
    assertEquals(Direction.SOUTHEAST, Direction.WEST.compose(Direction.SOUTHWEST));
  }
}
