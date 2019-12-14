package agh.cs.evolution.gui;

import javax.swing.*;

public enum ElementIcon {
  PLANT("/images/plant.png"),
  ANIMAL("/images/animal.png"),
  MULTIPLE_ANIMALS("/images/multiple-animals.png"),
  DOMINANT_ANIMAL("/images/dominant-animal.png");

  public final ImageIcon imageIcon;

  ElementIcon(String imagePath) {
    this.imageIcon = new ImageIcon(getClass().getResource(imagePath));
  }
}
