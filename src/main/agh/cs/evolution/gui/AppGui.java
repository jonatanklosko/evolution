package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;
import agh.cs.evolution.MapWithJungle;

import javax.swing.*;
import java.awt.*;

public class AppGui extends JFrame {
  Simulation simulation;

  public AppGui(Simulation simulation) {
    super("Evolution");
    this.simulation = simulation;
    MapWithJungle map = this.simulation.getMap();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    WorldMapGrid mapGrid = new WorldMapGrid(map);

    // Buttons
    JToolBar tools = new JToolBar();
    tools.setFloatable(false);
    this.add(tools, BorderLayout.PAGE_START);
    JButton nextDayButton = new JButton("Next day");
    JButton nextYearButton = new JButton("Next year");
    tools.add(nextDayButton);
    tools.add(nextYearButton);
    nextDayButton.addActionListener(event -> {
      simulation.nextDay();
      mapGrid.updateIcons();
      System.out.println(this.simulation.getVisualization());
    });
    nextYearButton.addActionListener(event -> {
      for (int i = 0; i <= 365; i++) {
        simulation.nextDay();
      }
      mapGrid.updateIcons();
      System.out.println(this.simulation.getVisualization());
    });

    // Map
    this.add(mapGrid);

    this.setVisible(true);
  }
}
