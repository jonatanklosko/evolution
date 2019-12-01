package agh.cs.evolution.gui;

import agh.cs.evolution.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class WorldMapGrid extends JPanel {
  private IWorldMap map;

  public WorldMapGrid(IWorldMap map) {
    this.map = map;
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
    List<IMapElement> elements = this.map.elementsAt(position);
    int elementCount = elements.size();
    label.setIcon(null);
    label.setToolTipText(null);
    label.setText(null);
    if (elementCount == 1) {
      IMapElement element = elements.get(0);
      if (element instanceof Plant) {
        ImageIcon icon = new ImageIcon(getClass().getResource("images/plant.png"));
        label.setIcon(icon);
      }
      if (element instanceof Animal) {
        Animal animal = (Animal) element;
        ImageIcon icon = new ImageIcon(getClass().getResource("images/animal.png"));
        String tooltipText = String.format(
            "<html>Energy: %d<br>Min. reproduction energy: %d</html>",
            animal.getEnergy(),
            animal.getMinReproductionEnergy()
        );
        label.setIcon(icon);
        label.setToolTipText(tooltipText);
      }
    } else if (elementCount > 1) {
      ImageIcon icon = new ImageIcon(getClass().getResource("images/animal.png"));
      String tooltipText = String.format("%d animals", elementCount);
      label.setIcon(icon);
      label.setToolTipText(tooltipText);
      label.setText(String.valueOf(elementCount));
    }
  }
}
