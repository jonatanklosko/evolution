package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;
import java.awt.*;

public class AppGui extends JFrame implements IChangeListener {
  private Controller controller;
  private WorldMapGrid worldMapGrid;
  private MenuBar menuBar;
  private InfoBar infoBar;

  public AppGui(SimulationParams simulationParams) {
    super("Evolution");
    this.controller = new Controller(simulationParams);
    this.controller.addChangeListener(this);

    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.menuBar = new MenuBar(this.controller);
    this.infoBar = new InfoBar(this.controller);

    JPanel toolbars = new JPanel(new GridLayout(2, 1));
    toolbars.add(this.menuBar);
    toolbars.add(this.infoBar);
    this.add(toolbars, BorderLayout.PAGE_START);

    this.worldMapGrid = new WorldMapGrid(this.controller);
    this.add(this.worldMapGrid);

    this.setVisible(true);
  }

  public void onChange() {
    this.infoBar.update();
    this.worldMapGrid.update();
  }
}
