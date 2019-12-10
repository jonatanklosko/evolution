package agh.cs.evolution;

import agh.cs.evolution.gui.AppGui;
import agh.cs.evolution.simulation.SimulationParams;

public class Main {
  public static void main(String[] args) {
    try {
      String parametersPath = args.length > 0 ? args[0] : "parameters.json";
      SimulationParams simulationParams = SimulationParams.fromFile(parametersPath);
      new AppGui(simulationParams);
    } catch (Exception error) {
      System.out.println("Failed to start the simulation: " + error.getMessage());
    }
  }
}
