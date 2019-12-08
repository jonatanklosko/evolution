package agh.cs.evolution;

import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    SimulationParams simulationParams = SimulationParams.fromFile("src/main/resources/parameters.json");
    Simulation simulation = new Simulation(simulationParams);
    long startTime = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
      long animalCount = simulation.livingGenomes$().count();
      if (animalCount == 0) {
        System.out.println("All animals died");
        break;
      }
      if (i % 1000 == 0) {
        System.out.println(String.format("Days passed: %d\t Animals: %d", i, animalCount));
        System.out.println(simulation.textVisualization());
      }
      simulation.nextDay();
    }
    long endTime = System.nanoTime();
    System.out.println(simulation.textVisualization());
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
