package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;

public class Main {
  public static void main(String[] args) {
    Simulation simulation = new Simulation(50, 25, 60, 2, 30, 0.3);
    simulation.initialize(40);
    new AppGui(simulation);
  }
}
