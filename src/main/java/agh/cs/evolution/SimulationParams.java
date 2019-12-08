package agh.cs.evolution;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimulationParams {
  public final int width;
  public final int height;
  public final int startEnergy;
  public final int moveEnergy;
  public final int plantEnergy;
  public final double jungleRatio;
  public final int initialNumberOfAnimals;

  private static Gson gson = new Gson();

  public SimulationParams(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int initialNumberOfAnimals) {
    this.width = width;
    this.height = height;
    this.startEnergy = startEnergy;
    this.moveEnergy = moveEnergy;
    this.plantEnergy = plantEnergy;
    this.jungleRatio = jungleRatio;
    this.initialNumberOfAnimals = initialNumberOfAnimals;
  }

  public static SimulationParams fromFile(String path) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
      return SimulationParams.gson.fromJson(bufferedReader, SimulationParams.class);
    } catch (IOException error) {
      throw error;
    }
  }
}
