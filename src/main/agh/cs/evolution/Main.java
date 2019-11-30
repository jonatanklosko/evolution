package agh.cs.evolution;

import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Simulation simulation = new Simulation(60, 25, 30, 2, 8, 0.3);
    simulation.initialize(40);
    long startTime = System.nanoTime();
    for (int i = 0; i < 20000; i++) {
      long animalCount = simulation.livingGenomes$().count();
      if (animalCount == 0) {
        System.out.println("All animals died");
        break;
      }
      if (i % 1000 == 0) {
        System.out.println(String.format("Days passed: %d\t Animals: %d", i, animalCount));
        System.out.println(simulation.getVisualization());
      }
      simulation.nextDay();
    }
    long endTime = System.nanoTime();
    System.out.println(simulation.getVisualization());
    System.out.println("Took: " + (endTime - startTime) / Math.pow(10, 9) + "s");
    System.out.println("Living genomes:");
    simulation.livingGenomes$()
        .collect(Collectors.groupingBy(genome -> genome, Collectors.counting()))
        .forEach((genome, occurrences) -> {
          System.out.println(String.format(
              "%d %s with genome: %s",
              occurrences,
              occurrences == 1 ? "animal" : "animals",
              genome
          ));
        });
  }
}
