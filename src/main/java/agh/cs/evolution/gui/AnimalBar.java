package agh.cs.evolution.gui;

import agh.cs.evolution.elements.Animal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AnimalBar extends JPanel implements IChangeListener {
  private Controller controller;
  private JLabel animalLabel;

  public AnimalBar(Controller controller) {
    this.controller = controller;
    this.controller.addChangeListener(this);
    this.setBorder(new EmptyBorder(2, 0, 2, 0));

    this.animalLabel = new JLabel("");
    this.add(this.animalLabel);

    this.onChange();
  }

  public void onChange() {
    Animal animal = this.controller.getSelectedAnimal();
    if (animal == null) {
      this.animalLabel.setText("No animal selected. Click on an animal to keep track of it.");
    } else if (animal.isDead()) {
      this.animalLabel.setText(String.format(
          "Your animal died at the age of %d days.",
          animal.getLifetime()
      ));
    } else {
      this.animalLabel.setText(String.format(
          "Your animal is living and has %s energy. Hover over it for more details.",
          animal.getEnergy()
      ));
    }
  }
}
