package agh.cs.evolution.utils;

import java.util.stream.Stream;

public class Utils {
  public static <T, S> Stream<T> filterType(Stream<S> stream, Class<T> elementClass) {
    return stream
        .filter(elementClass::isInstance)
        .map(elementClass::cast);
  }
}
