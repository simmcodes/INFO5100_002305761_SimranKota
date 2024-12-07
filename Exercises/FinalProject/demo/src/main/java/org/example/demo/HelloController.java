package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HelloController {
    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());

    @FXML
    private ImageView imageView;

    @FXML
    private Text metadataText;

    @FXML
    private ComboBox<String> imageCombo;

    @FXML
    private ComboBox<String> colorCombo;

    // Color filter strategies
    private ColorFilterStrategy blueFilter;
    private ColorFilterStrategy redFilter;
    private ColorFilterStrategy greenFilter;
    private ColorFilterStrategy noFilter;

    private File selectedFile;
    private BufferedImage originalImage;

    @FXML
    public void initialize() {
        // Initialize color filter strategies
        blueFilter = new BlueFilterStrategy();
        redFilter = new RedFilterStrategy();
        greenFilter = new GreenFilterStrategy();
        noFilter = new NoFilterStrategy();

        // Initialize combo boxes
        imageCombo.getItems().addAll("PNG", "JPG", "JPEG", "GIF");
        colorCombo.getItems().addAll("blue", "red", "green", "none");

        // Set default selections
        colorCombo.setValue("none");
        imageCombo.setValue("PNG");

        // Add listener to apply color filter when selection changes
        colorCombo.setOnAction(event -> applyColorFilter());
    }

    @FXML
    private void uploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Read the original image
                originalImage = ImageIO.read(selectedFile);

                // Create and display thumbnail
                BufferedImage thumbnailImage = createThumbnail(originalImage, 100, 100);
                Image thumbnail = convertToFXImage(thumbnailImage);
                imageView.setImage(thumbnail);

                // Display detailed metadata
                displayImageMetadata(selectedFile);

                // Apply any selected color filter
                applyColorFilter();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to read image", e);
                showAlert("Error", "Failed to read image: " + e.getMessage());
            }
        }
    }

    // Method to create a thumbnail with a specific size
    private BufferedImage createThumbnail(BufferedImage originalImage, int width, int height) {
        BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbnailImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Scale and draw the original image
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        return thumbnailImage;
    }

    // Method to extract and display image metadata
    private void displayImageMetadata(File imageFile) {
        try {
            // Read image basic metadata
            BufferedImage image = ImageIO.read(imageFile);
            int width = image.getWidth();
            int height = image.getHeight();

            // Try to extract camera and location information (if available)
            String cameraInfo = extractCameraInfo(imageFile);
            String locationInfo = extractLocationInfo(imageFile);

            // Prepare metadata text
            StringBuilder metadataBuilder = new StringBuilder();
            metadataBuilder.append("Image Details:\n")
                    .append("Width: ").append(width).append(" px\n")
                    .append("Height: ").append(height).append(" px\n");

            if (!cameraInfo.isEmpty()) {
                metadataBuilder.append("Camera: ").append(cameraInfo).append("\n");
            }

            if (!locationInfo.isEmpty()) {
                metadataBuilder.append("Location: ").append(locationInfo);
            }

            // Update metadata text
            metadataText.setText(metadataBuilder.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to extract image metadata", e);
            showAlert("Error", "Failed to extract image metadata: " + e.getMessage());
        }
    }

    // Method to extract camera information (simplified)
    private String extractCameraInfo(File imageFile) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
            ImageReader reader = ImageIO.getImageReaders(iis).next();
            reader.setInput(iis);

            // Try to get metadata (this is a basic implementation)
            IIOMetadata metadata = reader.getImageMetadata(0);

            // This is a simplified approach and may not work for all image types
            // In a real-world scenario, you'd use more robust metadata extraction libraries
            return "Unknown Camera Model";
        } catch (Exception e) {
            return "";
        }
    }

    // Method to extract location information (simplified)
    private String extractLocationInfo(File imageFile) {
        // Similar to camera info, this would typically use specialized
        // metadata extraction libraries like metadata-extractor
        return "";
    }

    @FXML
    private void applyColorFilter() {
        if (originalImage == null) return;

        // Clear any previous effects
        imageView.setEffect(null);

        // Apply color filter
        BufferedImage filteredImage = originalImage;
        if (colorCombo.getValue() != null) {
            switch (colorCombo.getValue()) {
                case "blue":
                    filteredImage = blueFilter.applyFilter(originalImage);
                    break;
                case "red":
                    filteredImage = redFilter.applyFilter(originalImage);
                    break;
                case "green":
                    filteredImage = greenFilter.applyFilter(originalImage);
                    break;
                default:
                    filteredImage = noFilter.applyFilter(originalImage);
            }
        }

        // Convert filtered image back to JavaFX Image for display
        imageView.setImage(convertToFXImage(filteredImage));
    }

    @FXML
    private void downloadButton(ActionEvent event) {
        if (selectedFile == null) {
            showAlert("Error", "Please upload an image first.");
            return;
        }

        if (imageCombo.getValue() == null) {
            showAlert("Error", "Please select an image format.");
            return;
        }

        try {
            BufferedImage imageToSave = originalImage;

            // Color filter application for saving
            if (colorCombo.getValue() != null && !colorCombo.getValue().equals("none")) {
                switch (colorCombo.getValue()) {
                    case "blue":
                        imageToSave = blueFilter.applyFilter(originalImage);
                        break;
                    case "red":
                        imageToSave = redFilter.applyFilter(originalImage);
                        break;
                    case "green":
                        imageToSave = greenFilter.applyFilter(originalImage);
                        break;
                }
            }

            // Create downloads folder if it doesn't exist
            File downloadFolder = new File(System.getProperty("user.home"), "Downloads/ImageManagementTool");
            if (!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }

            // Generate unique filename
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File outputFile = new File(downloadFolder,
                    "converted_" + timestamp + "." + imageCombo.getValue().toLowerCase());

            // Write the image in the specified format
            ImageIO.write(imageToSave, imageCombo.getValue(), outputFile);

            showAlert("Success", "Image downloaded successfully to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to download image", e);
            showAlert("Error", "Failed to download image: " + e.getMessage());
        }
    }

    // Convert BufferedImage to JavaFX Image
    private Image convertToFXImage(BufferedImage bufferedImage) {
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
            return new Image(bais);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to convert image", e);
            return null;
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}