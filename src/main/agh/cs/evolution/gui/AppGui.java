package agh.cs.evolution.gui;

import agh.cs.evolution.Simulation;
import agh.cs.evolution.MapWithJungle;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

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
    JButton showLivingGenomesButton = new JButton("Show living genomes");
    this.animalCountLabel = new JLabel("");
    this.daysPassedLabel = new JLabel("");
    toolbar.add(nextDayButton);
    toolbar.add(nextYearButton);
    toolbar.add(showLivingGenomesButton);
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
    showLivingGenomesButton.addActionListener(event -> {
      String genomes = this.simulation.livingGenomes$()
          .collect(Collectors.groupingBy(genome -> genome, Collectors.counting()))
          .entrySet()
          .stream()
          .map(entry -> {
            return String.format(
                "%d %s with genome: %s",
                entry.getValue(),
                entry.getValue() == 1 ? "animal" : "animals",
                entry.getKey()
            );
          })
          .collect(Collectors.joining("\n"));
      JTextArea textArea = new JTextArea(30, 60);
      textArea.setText(genomes);
      textArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(textArea);
      JOptionPane.showMessageDialog(null, scrollPane, "Living genomes", JOptionPane.INFORMATION_MESSAGE);
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
    this.worldMapGrid.update();
    System.out.println(this.simulation.getVisualization());
  }
}
