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

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Simulation 1", new SimulationPanel(simulationParams));
    tabbedPane.addTab("Simulation 2", new SimulationPanel(simulationParams));
    this.add(tabbedPane);

    this.setVisible(true);
  }
}
