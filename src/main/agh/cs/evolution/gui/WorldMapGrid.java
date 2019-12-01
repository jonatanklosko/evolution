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
      label.setBorder(new LineBorder(Color.LIGHT_GRAY));
      label.setHorizontalTextPosition(JLabel.CENTER);
      label.setVerticalTextPosition(JLabel.CENTER);
      label.setForeground(Color.BLACK);
      this.add(label);
    }
    this.updateIcons();
  }

  public void updateIcons() {
    Vector2d lowerLeft = map.getLowerLeft();
    Vector2d upperRight = map.getUpperRight();
    int width = upperRight.x - lowerLeft.x + 1;
    int height = upperRight.y - lowerLeft.y + 1;
    for (int y = upperRight.y; y >= lowerLeft.y; y--) {
      for (int x = lowerLeft.x; x <= upperRight.x; x++) {
        Vector2d position = new Vector2d(x, y);
        ImageIcon icon = this.elementIcon(position);
        String text = this.elementText(position);
        JLabel label = (JLabel) this.getComponent((height - y - 1) * width + x);
        label.setIcon(icon);
        label.setText(text);
      }
    }
  }

  private ImageIcon elementIcon(Vector2d currentPosition) {
    List<IMapElement> elements = this.map.elementsAt(currentPosition);
    if (elements.size() == 0) return null;
    IMapElement element = elements.get(0);
    if (element instanceof Plant) {
      return new ImageIcon(getClass().getResource("images/plant.png"));
    }
    if (element instanceof Animal) {
      return new ImageIcon(getClass().getResource("images/animal.png"));
    }
    return null;
  }

  private String elementText(Vector2d currentPosition) {
    int numberOfElements = this.map.elementsAt(currentPosition).size();
    return numberOfElements > 1 ? String.valueOf(numberOfElements) : null;
  }
}
