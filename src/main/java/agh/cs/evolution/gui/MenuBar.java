package agh.cs.evolution.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

public class MenuBar extends JToolBar {
  Controller controller;

  public MenuBar(Controller controller) {
    this.controller = controller;
    this.setFloatable(false);
    JButton nextDayButton = new JButton("Next day");
    JButton nextYearButton = new JButton("Next year");
    JButton resetButton = new JButton("Reset");
    JButton showLivingGenomesButton = new JButton("Show living genomes");
    this.add(nextDayButton);
    this.add(nextYearButton);
    this.add(showLivingGenomesButton);
    this.add(Box.createHorizontalGlue());
    this.add(resetButton);
    nextDayButton.addActionListener(event -> this.controller.nextDay());
    nextYearButton.addActionListener(event -> this.controller.nextYear());
    showLivingGenomesButton.addActionListener(this::showLivingGenomes);
    resetButton.addActionListener(event -> this.controller.reset());
  }

  private void showLivingGenomes(ActionEvent event) {
    String genomes = this.controller.getSimulation().livingGenomes$()
        .collect(Collectors.groupingBy(genome -> genome, Collectors.counting()))
        .entrySet()
        .stream()
        .map(entry -> String.format(
            "%d %s with genome: %s",
            entry.getValue(),
            entry.getValue() == 1 ? "animal" : "animals",
            entry.getKey().toGenesString()
        ))
        .collect(Collectors.joining("\n"));
    JTextArea textArea = new JTextArea(30, 60);
    textArea.setText(genomes);
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    JOptionPane.showMessageDialog(null, scrollPane, "Living genomes", JOptionPane.INFORMATION_MESSAGE);
  }
}
