package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;

import javax.swing.*;

public class InfoBar extends JToolBar implements IChangeListener {
  Controller controller;
  private JLabel animalCountLabel;
  private JLabel dayNumberLabel;
  private JLabel plantCountLabel;
  private JLabel averageEnergyLabel;
  private JLabel averageAgeLabel;
  private JLabel averageChildrenCountLabel;

  public InfoBar(Controller controller) {
    this.controller = controller;
    this.controller.addChangeListener(this);
    this.setFloatable(false);
    this.dayNumberLabel = new JLabel("");
    this.animalCountLabel = new JLabel("");
    this.plantCountLabel = new JLabel("");
    this.averageEnergyLabel = new JLabel("");
    this.averageAgeLabel = new JLabel("");
    this.averageChildrenCountLabel = new JLabel("");
    this.addSeparator();
    this.add(this.dayNumberLabel);
    this.addSeparator();
    this.add(this.animalCountLabel);
    this.addSeparator();
    this.add(this.plantCountLabel);
    this.addSeparator();
    this.add(this.averageEnergyLabel);
    this.addSeparator();
    this.add(this.averageAgeLabel);
    this.addSeparator();
    this.add(this.averageChildrenCountLabel);
    this.onChange();
  }

  public void onChange() {
    Simulation simulation = this.controller.getSimulation();
    this.dayNumberLabel.setText("Day: " + simulation.getDayNumber());
    this.animalCountLabel.setText("Animals: " + simulation.getAnimalCount());
    this.plantCountLabel.setText("Plants: " + simulation.getPlantCount());
    this.averageEnergyLabel.setText("Avg. energy: " +
        simulation.getAverageAnimalEnergy()
            .map(avgEnergy -> String.format("%.2f", avgEnergy))
            .orElse("None")
    );
    this.averageAgeLabel.setText("Avg. age: " +
        simulation.getAverageAge()
            .map(avgAge -> String.format("%.2f days", avgAge))
            .orElse("None")
    );
    this.averageChildrenCountLabel.setText("Avg. children: " +
        simulation.getAverageChildrenCount()
            .map(avgChildren -> String.format("%.2f", avgChildren))
            .orElse("None")
    );
  }
}
