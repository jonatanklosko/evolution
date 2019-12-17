package agh.cs.evolution.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MenuBar extends JToolBar {
  private Controller controller;

  public MenuBar(Controller controller) {
    this.controller = controller;
    this.setFloatable(false);
    JButton runButton = new JButton("Run");
    JButton nextDayButton = new JButton("Next day");
    JButton nextYearButton = new JButton("Next year");
    JButton exportButton = new JButton("Export");
    JButton resetButton = new JButton("Reset");
    JButton showLivingGenomesButton = new JButton("Show living genomes");
    this.add(runButton);
    this.add(nextDayButton);
    this.add(nextYearButton);
    this.add(showLivingGenomesButton);
    this.add(Box.createHorizontalGlue());
    this.add(exportButton);
    this.add(resetButton);
    runButton.addActionListener(event -> this.controller.toggleRun());
    nextDayButton.addActionListener(event -> this.controller.nextDay());
    nextYearButton.addActionListener(event -> this.controller.nextYear());
    showLivingGenomesButton.addActionListener(this::showLivingGenomes);
    exportButton.addActionListener(this::export);
    resetButton.addActionListener(event -> this.controller.reset());
  }

  private void showLivingGenomes(ActionEvent event) {
    String genomes = this.controller.getSimulation().livingGenomes$()
        .collect(Collectors.groupingBy(genome -> genome, Collectors.counting()))
        .entrySet()
        .stream()
        .sorted(Comparator.comparing(entry -> -entry.getValue()))
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

  private void export(ActionEvent event) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Export simulation summary as JSON");
    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      if (!file.getName().toLowerCase().endsWith(".json")) {
        file = new File(file.toString() + ".json");
      }
      try {
        this.controller.export(file);
      } catch (IOException error) {
        JOptionPane.showMessageDialog(this, "Export failed: " + error.getMessage());
      }
    }
  }
}
