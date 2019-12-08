package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;
import agh.cs.evolution.SimulationParams;

public class Main {
  public static void main(String[] args) {
    try {
      SimulationParams simulationParams = SimulationParams.fromFile("src/main/resources/parameters.json");
      Simulation simulation = new Simulation(simulationParams);
      new AppGui(simulation);
    } catch (Exception error) {
      System.out.println(error);
    }
  }
}
