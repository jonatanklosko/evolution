package agh.cs.evolution.simulation;

public class SimulationDaySummary {
  public final long dayNumber;
  public final long animalCount;
  public final long plantCount;
  public final double averageAnimalEnergy;
  public final double averageAnimalAge;
  public final double averageChildrenCount;

  public SimulationDaySummary(long dayNumber, long animalCount, long plantCount, double averageAnimalEnergy, double averageAnimalAge, double averageChildrenCount) {
    this.dayNumber = dayNumber;
    this.animalCount = animalCount;
    this.plantCount = plantCount;
    this.averageAnimalEnergy = averageAnimalEnergy;
    this.averageAnimalAge = averageAnimalAge;
    this.averageChildrenCount = averageChildrenCount;
  }
}
