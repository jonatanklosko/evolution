package agh.cs.evolution.gui;

import agh.cs.evolution.simulation.Simulation;

import javax.swing.*;

public class InfoBar extends JToolBar {
  Controller controller;
  private JLabel animalCountLabel;
  private JLabel daysPassedLabel;
  private JLabel plantCountLabel;

  public InfoBar(Controller controller) {
    this.controller = controller;
    this.setFloatable(false);
    this.daysPassedLabel = new JLabel("");
    this.animalCountLabel = new JLabel("");
    this.plantCountLabel = new JLabel("");
    this.addSeparator();
    this.add(this.daysPassedLabel);
    this.addSeparator();
    this.add(this.animalCountLabel);
    this.addSeparator();
    this.add(this.plantCountLabel);
    this.add(Box.createHorizontalGlue());
    this.update();
  }

  public void update() {
    Simulation simulation = this.controller.getSimulation();
    this.daysPassedLabel.setText("Days passed: " + simulation.getDaysPassed());
    this.animalCountLabel.setText("Animals: " + simulation.getAnimalCount());
    this.plantCountLabel.setText("Plants: " + simulation.getPlantCount());
  }
}
