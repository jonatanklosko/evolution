package agh.cs.evolution.gui;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.IMapElement;
import agh.cs.evolution.elements.Plant;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WorldMapGrid extends JPanel {
  private Controller controller;
  private ImageIcon plantIcon;
  private ImageIcon animalIcon;

  public WorldMapGrid(Controller controller) {
    this.controller = controller;
    this.plantIcon = new ImageIcon(getClass().getResource("/images/plant.png"));
    this.animalIcon = new ImageIcon(getClass().getResource("/images/animal.png"));
    IWorldMap map = this.controller.getSimulation().getMap();
    Vector2d lowerLeft = map.getLowerLeft();
    Vector2d upperRight = map.getUpperRight();
    int width = upperRight.x - lowerLeft.x + 1;
    int height = upperRight.y - lowerLeft.y + 1;
    this.setLayout(new GridLayout(height, width));
    for (int i = 1; i <= width * height; i++) {
      JLabel label = new JLabel("", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createDashedBorder(new Color(220, 220, 220)));
      label.setHorizontalTextPosition(JLabel.CENTER);
      label.setVerticalTextPosition(JLabel.CENTER);
      label.setForeground(Color.BLACK);
      this.add(label);
    }
    this.update();
  }

  public void update() {
    IWorldMap map = this.controller.getSimulation().getMap();
    Vector2d lowerLeft = map.getLowerLeft();
    Vector2d upperRight = map.getUpperRight();
    int width = upperRight.x - lowerLeft.x + 1;
    int height = upperRight.y - lowerLeft.y + 1;
    for (int y = upperRight.y; y >= lowerLeft.y; y--) {
      for (int x = lowerLeft.x; x <= upperRight.x; x++) {
        Vector2d position = new Vector2d(x, y);
        JLabel label = (JLabel) this.getComponent((height - y - 1) * width + x);
        this.updateLabel(label, position);
      }
    }
  }

  private void updateLabel(JLabel label, Vector2d position) {
    IWorldMap map = this.controller.getSimulation().getMap();
    List<IMapElement> elements = map.elementsAt(position);
    int elementCount = elements.size();
    label.setIcon(null);
    label.setToolTipText(null);
    label.setText(null);
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        label.setIcon(this.plantIcon);
      }
      if (element instanceof Animal) {
        Animal animal = (Animal) element;
        String tooltipText = String.format(
            "<html>Energy: %d<br>Min. reproduction energy: %d<br>No. children: %d</html>",
            animal.getEnergy(),
            animal.getMinReproductionEnergy(),
            animal.getChildrenCount()
        );
        label.setIcon(this.animalIcon);
        label.setToolTipText(tooltipText);
      }
    } else if (elementCount > 1) {
      String tooltipText = String.format("%d animals", elementCount);
      label.setIcon(this.animalIcon);
      label.setToolTipText(tooltipText);
      label.setText(String.valueOf(elementCount));
    }
  }
}
