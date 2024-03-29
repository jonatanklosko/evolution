package agh.cs.evolution.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public enum ElementImage {
  PLANT("/images/plant.png"),
  ANIMAL("/images/animal.png"),
  MULTIPLE_ANIMALS("/images/multiple-animals.png"),
  DOMINANT_ANIMAL("/images/dominant-animal.png"),
  SELECTED_ANIMAL("/images/selected-animal.png");

  public Image image;

  ElementImage(String imagePath) {
    try {
      this.image = ImageIO.read(getClass().getResource(imagePath));
    } catch (IOException exception) {
      throw new RuntimeException("Failed to load static resource.");
    }
  }
}
