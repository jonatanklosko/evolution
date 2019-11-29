import agh.cs.evolution.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
  @Test
  public void equalsShouldCompareCoordinates() {
    assertTrue(new Vector2d(2, 2).equals(new Vector2d(2, 2)));
    assertFalse(new Vector2d(2, 2).equals(new Vector2d(2, 1)));
    assertFalse(new Vector2d(2, 2).equals(new Vector2d(1, 2)));
    assertFalse(new Vector2d(2, 2).equals(new Vector2d(1, 1)));
    assertFalse(new Vector2d(2, 2).equals("(2, 2)"));
  }

  @Test
  public void toStringShouldReturnPairRepresentation() {
    assertEquals("(-1, 2)", new Vector2d(-1, 2).toString());
  }

  @Test
  public void addShouldReturnVectorSum() {
    assertEquals(
        new Vector2d(3, 4),
        new Vector2d(-1, 2).add(new Vector2d(4, 2))
    );
  }

  @Test
  public void subtractShouldReturnVectorDifference() {
    assertEquals(
        new Vector2d(-5, 0),
        new Vector2d(-1, 2).subtract(new Vector2d(4, 2))
    );
  }

  @Test
  public void oppositeShouldReturnVectorWithOppositeCoordinates() {
    assertEquals(new Vector2d(-2, 1), new Vector2d(2, -1).opposite());
    assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).opposite());
  }

  @Test
  public void followsShouldReturnWhetherVectorIsUpperRightInRespectToTheGiven() {
    assertTrue(new Vector2d(-1, -2).precedes(new Vector2d(0, 0)));
    assertFalse(new Vector2d(1, 2).precedes(new Vector2d(0, 0)));
  }

  @Test
  public void precedesShouldReturnWhetherVectorIsLowerLeftInRespectToTheGiven() {
    assertTrue(new Vector2d(1, 2).follows(new Vector2d(0, 0)));
    assertFalse(new Vector2d(-1, -2).follows(new Vector2d(0, 0)));
  }
}
