package agh.cs.evolution.gui;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.IMapElement;
import agh.cs.evolution.elements.Plant;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMapGrid extends JPanel {
  private Controller controller;
  private Map<Vector2d, JLabel> labelByPosition;

  public WorldMapGrid(Controller controller) {
    this.controller = controller;
    this.labelByPosition = new HashMap<>();
    IWorldMap map = this.controller.getSimulation().getMap();
    Vector2d lowerLeft = map.getLowerLeft();
    Vector2d upperRight = map.getUpperRight();
    int width = upperRight.x - lowerLeft.x + 1;
    int height = upperRight.y - lowerLeft.y + 1;
    this.setLayout(new GridLayout(height, width));
    for (int y = upperRight.y; y >= lowerLeft.y; y--) {
      for (int x = lowerLeft.x; x <= upperRight.x; x++) {
        Vector2d position = new Vector2d(x, y);
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createDashedBorder(new Color(220, 220, 220)));
        this.add(label);
        this.labelByPosition.put(position, label);
      }
    }
    this.update(false);
  }

  public void update(boolean isRunning) {
    this.labelByPosition.forEach((position, label) -> {
      this.updateLabel(label, position);
      if (!isRunning) {
        this.updateTooltip(label, position);
      }
    });
  }

  private void updateLabel(JLabel label, Vector2d position) {
    IWorldMap map = this.controller.getSimulation().getMap();
    List<IMapElement> elements = map.elementsAt(position);
    int elementCount = elements.size();
    label.setIcon(null);
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        label.setIcon(ElementIcon.PLANT.imageIcon);
      }
      if (element instanceof Animal) {
        label.setIcon(ElementIcon.ANIMAL.imageIcon);
      }
    } else if (elementCount > 1) {
      label.setIcon(ElementIcon.ANIMAL.imageIcon);
    }
  }

  private void updateTooltip(JLabel label, Vector2d position) {
    IWorldMap map = this.controller.getSimulation().getMap();
    List<IMapElement> elements = map.elementsAt(position);
    int elementCount = elements.size();
    label.setToolTipText(null);
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        Plant plant = (Plant) element;
        String tooltipText = String.format("Plant with %d energy", plant.getEnergy());
        label.setToolTipText(tooltipText);
      }
      if (element instanceof Animal) {
        Animal animal = (Animal) element;
        String tooltipText = String.format(
            "<html>Energy: %d<br>Min. reproduction energy: %d<br>No. children: %d<br>Genome: %s</html>",
            animal.getEnergy(),
            animal.getMinReproductionEnergy(),
            animal.getChildrenCount(),
            animal.getGenome().toGenesString()
        );
        label.setToolTipText(tooltipText);
      }
    } else if (elementCount > 1) {
      String tooltipText = String.format("%d animals", elementCount);
      label.setToolTipText(tooltipText);
    }
  }
}
