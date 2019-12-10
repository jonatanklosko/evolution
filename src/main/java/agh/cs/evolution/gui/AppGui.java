package agh.cs.evolution.gui;

import agh.cs.evolution.SimulationParams;

import javax.swing.*;
import java.awt.*;

public class AppGui extends JFrame implements IChangeListener {
  private Controller controller;
  private WorldMapGrid worldMapGrid;
  private MenuBar menuBar;

  public AppGui(SimulationParams simulationParams) {
    super("Evolution");
    this.controller = new Controller(simulationParams);
    this.controller.addChangeListener(this);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.menuBar = new MenuBar(this.controller);
    this.add(this.menuBar, BorderLayout.PAGE_START);

    this.worldMapGrid = new WorldMapGrid(this.controller);
    this.add(this.worldMapGrid);

    this.setVisible(true);
  }

  public void onChange() {
    this.menuBar.update();
    this.worldMapGrid.update();
  }
}
