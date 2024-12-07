package org.example.demo;

import java.awt.*;
import java.awt.image.BufferedImage;

// Blue color filter strategy
public class BlueFilterStrategy implements ColorFilterStrategy {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        if (original == null) return null;

        BufferedImage filteredImage = new BufferedImage(
                original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = filteredImage.createGraphics();
        g.drawImage(original, 0, 0, null);

        // Blue overlay
        g.setColor(new Color(0, 0, 255, 100));
        g.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.3f));
        g.fillRect(0, 0, original.getWidth(), original.getHeight());

        g.dispose();
        return filteredImage;
    }
}
