package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;
import agh.cs.evolution.simulation.SimulationParams;

import java.util.LinkedList;
import java.util.List;

public class Controller {
  private SimulationParams simulationParams;
  private Simulation simulation;
  private List<IChangeListener> changeListeners;

  public Controller(SimulationParams simulationParams) {
    this.simulationParams = simulationParams;
    this.simulation = new Simulation(this.simulationParams);
    this.changeListeners = new LinkedList<>();
  }

  public Simulation getSimulation() {
    return this.simulation;
  }

  public void nextDay() {
    this.simulation.nextDay();
    this.updateView();
  }

  public void nextYear() {
    this.simulation.nextYear();
    this.updateView();
  }

  public void reset() {
    this.simulation = new Simulation(this.simulationParams);
    this.updateView();
  }

  public void addChangeListener(IChangeListener listener) {
    this.changeListeners.add(listener);
  }

  public void removeChangeListener(IChangeListener listener) {
    this.changeListeners.remove(listener);
  }

  private void updateView() {
    this.changeListeners.forEach(IChangeListener::onChange);
  }
}
