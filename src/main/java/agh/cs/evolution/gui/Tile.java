package agh.cs.evolution.gui;

import javax.swing.*;
import java.awt.*;

public class Tile extends JComponent {
  private Image image;

  public Tile(Image image) {
    this.image = image;
  }

  public final void setImage(Image image) {
    if (image != this.image) {
      this.image = image;
      this.repaint();
    }
  }

  @Override
  public final void paintComponent(final Graphics g) {
    super.paintComponent(g);
    if (this.image != null) {
      int tileWidth = this.getWidth();
      int tileHeight = this.getHeight();
      int imageWidth = this.image.getWidth(null);
      int imageHeight = this.image.getHeight(null);
      double widthRatio = (double) tileWidth / imageWidth;
      double heightRatio = (double) tileHeight / imageHeight;
      double ratio = Math.min(widthRatio, heightRatio) * 0.9;
      int newWidth = (int) (imageWidth * ratio);
      int newHeight = (int) (imageHeight * ratio);
      Image scaledImage = this.image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
      g.drawImage(scaledImage, (tileWidth - newWidth) / 2,(tileHeight - newHeight) / 2,this);
    }
  }
}
