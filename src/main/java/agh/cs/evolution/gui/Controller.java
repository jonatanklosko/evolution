package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;
import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Controller {
  private SimulationParams simulationParams;
  private Simulation simulation;
  private List<IChangeListener> changeListeners;
  private Timer timer;
  private final int INTERVAL_MS = 50;

  public Controller(SimulationParams simulationParams) {
    this.simulationParams = simulationParams;
    this.simulation = new Simulation(this.simulationParams);
    this.changeListeners = new LinkedList<>();
    this.timer = new Timer(INTERVAL_MS, event -> this.nextDay());
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

  public void toggleRun() {
    if (this.timer.isRunning()) {
      this.timer.stop();
    } else {
      this.timer.start();
    }
    this.updateView();
  }

  public boolean isRunning() {
    return this.timer.isRunning();
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
