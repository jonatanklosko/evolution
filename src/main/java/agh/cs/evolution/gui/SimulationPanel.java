package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
  private Controller controller;

  public SimulationPanel(SimulationParams simulationParams) {
    super(new BorderLayout());

    this.controller = new Controller(simulationParams);

    MenuBar menuBar = new MenuBar(this.controller);
    InfoBar infoBar = new InfoBar(this.controller);
    AnimalBar animalBar = new AnimalBar(this.controller);

    JPanel toolbars = new JPanel(new GridLayout(2, 1));
    toolbars.add(menuBar);
    toolbars.add(infoBar);
    this.add(toolbars, BorderLayout.PAGE_START);

    WorldMapGrid worldMapGrid = new WorldMapGrid(this.controller);
    this.add(worldMapGrid);

    this.add(animalBar, BorderLayout.PAGE_END);
  }
}
