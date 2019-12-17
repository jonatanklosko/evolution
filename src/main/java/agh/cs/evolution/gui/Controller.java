package agh.cs.evolution.gui;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.IMapElement;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.simulation.Simulation;
import agh.cs.evolution.simulation.SimulationExport;
import agh.cs.evolution.simulation.SimulationParams;
import agh.cs.evolution.utils.Utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {
  private final int INTERVAL_MS = 50;
  private SimulationParams simulationParams;
  private Simulation simulation;
  private List<IChangeListener> changeListeners;
  private Timer timer;
  private Animal selectedAnimal;

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
    this.selectedAnimal = null;
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
    new SimulationExport(this.simulation.getDaySummaries()).saveAsJson(file);
  }

  public boolean isRunning() {
    return this.timer.isRunning();
  }

  public void onPositionSelected(Vector2d position) {
    List<IMapElement> elements = this.simulation.getMap().elementsAt(position);
    this.selectedAnimal = Utils.filterType(elements.stream(), Animal.class).findAny().orElse(null);
    updateView();
  }

  public Animal getSelectedAnimal() {
    return this.selectedAnimal;
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
