package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;
import agh.cs.evolution.SimulationParams;

public class Main {
  public static void main(String[] args) {
    try {
      String parametersPath = args.length > 0 ? args[0] : "parameters.json";
      SimulationParams simulationParams = SimulationParams.fromFile(parametersPath);
      Simulation simulation = new Simulation(simulationParams);
      new AppGui(simulation);
    } catch (Exception error) {
      System.out.println(error);
    }
  }
}
