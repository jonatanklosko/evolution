package agh.cs.evolution.gui;

import agh.cs.evolution.elements.Animal;
import agh.cs.evolution.elements.Genome;
import agh.cs.evolution.elements.IMapElement;
import agh.cs.evolution.elements.Plant;
import agh.cs.evolution.geometry.Vector2d;
import agh.cs.evolution.map.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMapGrid extends JPanel implements IChangeListener {
  private Controller controller;
  private Map<Vector2d, JLabel> labelByPosition;

  public WorldMapGrid(Controller controller) {
    this.controller = controller;
    this.controller.addChangeListener(this);
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
    this.onChange();
  }

  public void onChange() {
    this.labelByPosition.forEach((position, label) -> {
      Genome dominantGenome = this.controller.getSimulation().dominantGenome().orElse(null);
      label.setIcon(this.getIcon(position, dominantGenome));
      if (!this.controller.isRunning()) {
        label.setToolTipText(this.getTooltipText(position, dominantGenome));
      }
    });
  }

  private ImageIcon getIcon(Vector2d position, Genome dominantGenome) {
    IWorldMap map = this.controller.getSimulation().getMap();
    List<IMapElement> elements = map.elementsAt(position);
    int elementCount = elements.size();
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        return ElementIcon.PLANT.imageIcon;
      }
      if (element instanceof Animal) {
        Animal animal = (Animal) element;
        if (animal.getGenome().equals(dominantGenome)) {
          return ElementIcon.DOMINANT_ANIMAL.imageIcon;
        }
        return ElementIcon.ANIMAL.imageIcon;
      }
    } else if (elementCount > 1) {
      return ElementIcon.MULTIPLE_ANIMALS.imageIcon;
    }
    return null;
  }

  private String getTooltipText(Vector2d position, Genome dominantGenome) {
    IWorldMap map = this.controller.getSimulation().getMap();
    List<IMapElement> elements = map.elementsAt(position);
    int elementCount = elements.size();
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        Plant plant = (Plant) element;
        return String.format("Plant with %d energy", plant.getEnergy());
      }
      if (element instanceof Animal) {
        Animal animal = (Animal) element;
        return String.format(
            "<html>%s<br>Energy: %d<br>Min. reproduction energy: %d<br>Age: %d<br>No. children: %d<br>No. descendants: %d<br>Genome: %s</html>",
            animal.getGenome().equals(dominantGenome) ? "Dominant animal" : "Animal",
            animal.getEnergy(),
            animal.getMinReproductionEnergy(),
            this.controller.getSimulation().getDayNumber() - animal.getBirthDay(),
            animal.getChildrenCount(),
            animal.getDescendantCount(),
            animal.getGenome().toGenesString()
        );
      }
    } else if (elementCount > 1) {
      return String.format("%d animals", elementCount);
    }
    return null;
  }
}
