package agh.cs.evolution;

import java.util.stream.Stream;

public class Utils {
  public static <T> Stream<T> filterType(Stream stream, Class<T> elementClass) {
    return stream
        .filter(elementClass::isInstance)
        .map(elementClass::cast);
  }
}
