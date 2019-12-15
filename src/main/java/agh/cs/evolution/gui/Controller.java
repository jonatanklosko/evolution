package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;
import agh.cs.evolution.simulation.SimulationDaySummary;
import agh.cs.evolution.simulation.SimulationParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class Controller {
  private SimulationParams simulationParams;
  private Simulation simulation;
  private List<IChangeListener> changeListeners;
  private Timer timer;
  private final int INTERVAL_MS = 50;
  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

  public void export(File file) throws IOException {
    List<SimulationDaySummary> simulationDaySummaries = this.simulation.getDaySummaries();
    try (Writer writer = new FileWriter(file)) {
      Controller.gson.toJson(simulationDaySummaries, writer);
    }
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
