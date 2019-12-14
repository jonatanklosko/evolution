package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel implements IChangeListener {
  private Controller controller;
  private WorldMapGrid worldMapGrid;
  private MenuBar menuBar;
  private InfoBar infoBar;

  public SimulationPanel(SimulationParams simulationParams) {
    super(new BorderLayout());

    this.controller = new Controller(simulationParams);
    this.controller.addChangeListener(this);

    this.menuBar = new MenuBar(this.controller);
    this.infoBar = new InfoBar(this.controller);

    JPanel toolbars = new JPanel(new GridLayout(2, 1));
    toolbars.add(this.menuBar);
    toolbars.add(this.infoBar);
    this.add(toolbars, BorderLayout.PAGE_START);

    this.worldMapGrid = new WorldMapGrid(this.controller);
    this.add(this.worldMapGrid);
  }

  public void onChange() {
    this.infoBar.update();
    this.worldMapGrid.update(this.controller.isRunning());
  }
}
