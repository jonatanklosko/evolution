package agh.cs.evolution;

import agh.cs.evolution.gui.App;
import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    try {
      String parametersPath = args.length > 0 ? args[0] : "parameters.json";
      SimulationParams simulationParams = SimulationParams.fromFile(parametersPath);
      SwingUtilities.invokeLater(() -> {
        new App(simulationParams);
      });
    } catch (Exception error) {
      System.out.println("Failed to start the simulation: " + error.getMessage());
    }
  }
}
