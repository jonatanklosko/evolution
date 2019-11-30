package agh.cs.evolution;

public class Main {
  public static void main(String[] args) {
//    Genome genome1 = new Genome();
//    Genome genome2 = new Genome();
//    System.out.println(genome1);
//    System.out.println(genome2);
//    System.out.println(genome1.combine(genome2));

    Simulation simulation = new Simulation(100, 100, 40, 1, 10, 0.2);
    simulation.initialize(20);
    long startTime = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
      simulation.nextDay();
    }
    long endTime = System.nanoTime();
    System.out.println("Took: " + (endTime - startTime) / Math.pow(10, 9) + "s");
    simulation.liveGenomes$().forEach(System.out::println);
  }
}
