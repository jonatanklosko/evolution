package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.SimulationParams;

import javax.swing.*;

public class App extends JFrame {
  public App(SimulationParams simulationParams) {
    super("Evolution");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.setIconImage(
        new ImageIcon(getClass().getClassLoader().getResource("images/favicon.png")).getImage()
    );

    SimulationPanel simulationPanel = new SimulationPanel(simulationParams);
    this.add(simulationPanel);

    this.setVisible(true);
  }
}
