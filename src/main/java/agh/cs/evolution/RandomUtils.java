package agh.cs.evolution;

import java.util.List;
import java.util.Random;

public class RandomUtils {
  public static final Random random = new Random();

  public static <T> T randomElement(List<T> elements) {
    if (elements.size() == 0) {
      throw new IllegalArgumentException("Cannot get random element of an empty list.");
    }
    int randomIndex = random.nextInt(elements.size());
    return elements.get(randomIndex);
  }

  public static <T> T randomElement(T[] elements) {
    if (elements.length == 0) {
      throw new IllegalArgumentException("Cannot get random element of an empty array.");
    }
    int randomIndex = random.nextInt(elements.length);
    return elements[randomIndex];
  }
}
