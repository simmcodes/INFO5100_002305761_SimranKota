package org.example.demo;

import java.awt.image.BufferedImage;


 // Interface for color filter strategies

public interface ColorFilterStrategy {

     // Apply color filter to the image

    BufferedImage applyFilter(BufferedImage image);
}

