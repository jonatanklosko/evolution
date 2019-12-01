package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;
import agh.cs.evolution.MapWithJungle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppGui extends JFrame {
  private Simulation simulation;
  private JLabel animalCountLabel;
  private JLabel daysPassedLabel;
  private WorldMapGrid worldMapGrid;

  public AppGui(Simulation simulation) {
    super("Evolution");
    this.simulation = simulation;
    MapWithJungle map = this.simulation.getMap();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Toolbar
    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);
    this.add(toolbar, BorderLayout.PAGE_START);
    JButton nextDayButton = new JButton("Next day");
    JButton nextYearButton = new JButton("Next year");
    this.animalCountLabel = new JLabel("");
    this.daysPassedLabel = new JLabel("");
    toolbar.add(nextDayButton);
    toolbar.add(nextYearButton);
    toolbar.addSeparator();
    toolbar.add(this.daysPassedLabel);
    toolbar.addSeparator();
    toolbar.add(this.animalCountLabel);
    nextDayButton.addActionListener(event -> {
      simulation.nextDay();
      this.update();
    });
    nextYearButton.addActionListener(event -> {
      for (int i = 0; i <= 365; i++) {
        simulation.nextDay();
      }
      this.update();
    });

    // Map
    this.worldMapGrid = new WorldMapGrid(map);
    this.add(this.worldMapGrid);

    this.update();

    this.setVisible(true);
  }

  private void update() {
    long animalCount = simulation.livingGenomes$().count();
    this.daysPassedLabel.setText("Days passed: " + simulation.getDaysPassed());
    this.animalCountLabel.setText("Animals: " + animalCount);
    this.worldMapGrid.updateIcons();
    System.out.println(this.simulation.getVisualization());
  }
}
