package agh.cs.evolution.gui;

import agh.cs.evolution.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class WorldMapGrid extends JPanel {
  private WorldMap map;

  public WorldMapGrid(WorldMap map) {
    this.map = map;
    this.setLayout(new GridLayout(map.height, map.width));
    for (int i = map.height - 1; i >= 0; i--) {
      for (int j = 0; j < map.width; j++) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBorder(new LineBorder(Color.LIGHT_GRAY));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setForeground(Color.BLACK);
        this.add(label);
      }
    }
    this.updateIcons();
  }

  public void updateIcons() {
    for (int i = map.height - 1; i >= 0; i--) {
      for (int j = 0; j < map.width; j++) {
        Vector2d position = new Vector2d(j, i);
        ImageIcon icon = this.elementIcon(position);
        String text = this.elementText(position);
        JLabel label = (JLabel) this.getComponent((map.height - i - 1) * map.width + j);
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
