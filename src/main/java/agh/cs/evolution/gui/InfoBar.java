package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;

import javax.swing.*;

public class InfoBar extends JToolBar {
  Controller controller;
  private JLabel animalCountLabel;
  private JLabel daysPassedLabel;
  private JLabel plantCountLabel;
  private JLabel averageEnergyLabel;
  private JLabel averageLifetimeLabel;
  private JLabel averageChildrenCountLabel;

  public InfoBar(Controller controller) {
    this.controller = controller;
    this.setFloatable(false);
    this.daysPassedLabel = new JLabel("");
    this.animalCountLabel = new JLabel("");
    this.plantCountLabel = new JLabel("");
    this.averageEnergyLabel = new JLabel("");
    this.averageLifetimeLabel = new JLabel("");
    this.averageChildrenCountLabel = new JLabel("");
    this.addSeparator();
    this.add(this.daysPassedLabel);
    this.addSeparator();
    this.add(this.animalCountLabel);
    this.addSeparator();
    this.add(this.plantCountLabel);
    this.addSeparator();
    this.add(this.averageEnergyLabel);
    this.addSeparator();
    this.add(this.averageLifetimeLabel);
    this.addSeparator();
    this.add(this.averageChildrenCountLabel);
    this.update();
  }

  public void update() {
    Simulation simulation = this.controller.getSimulation();
    this.daysPassedLabel.setText("Days passed: " + simulation.getDaysPassed());
    this.animalCountLabel.setText("Animals: " + simulation.getAnimalCount());
    this.plantCountLabel.setText("Plants: " + simulation.getPlantCount());
    this.averageEnergyLabel.setText("Avg. energy: " +
        simulation.getAverageAnimalEnergy()
            .map(avgEnergy -> String.format("%.2f", avgEnergy))
            .orElse("None")
    );
    this.averageLifetimeLabel.setText("Avg. lifetime: " + "TODO");
    this.averageChildrenCountLabel.setText("Avg. children: " +
        simulation.getAverageChildrenCount()
            .map(avgEnergy -> String.format("%.2f", avgEnergy))
            .orElse("None")
    );
  }
}
