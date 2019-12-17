package agh.cs.evolution.simulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationExport {
  public final double averageAnimalCount;
  public final double averagePlantCount;
  public final double averageAnimalEnergy;
  public final double averageAnimalAge;
  public final double averageChildrenCount;
  public final double averageAnimalLifetime;
  public final List<SimulationDaySummary> history;

  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public SimulationExport(List<SimulationDaySummary> simulationDaySummaries) {
    this.history = simulationDaySummaries;
    this.averageAnimalCount = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.animalCount));
    this.averagePlantCount = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.plantCount));
    this.averageAnimalEnergy = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.averageAnimalEnergy));
    this.averageAnimalAge = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.averageAnimalAge));
    this.averageChildrenCount = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.averageChildrenCount));
    this.averageAnimalLifetime = simulationDaySummaries.stream()
        .collect(Collectors.averagingDouble(summary -> summary.averageAnimalLifetime));
  }

  public void saveAsJson(File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      SimulationExport.gson.toJson(this, writer);
    }
  }
}
