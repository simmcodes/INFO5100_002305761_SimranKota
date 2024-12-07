package org.example.demo;

import java.awt.image.BufferedImage;

// No filter strategy (returns original image)

public class NoFilterStrategy implements ColorFilterStrategy {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        return original;
    }
}
