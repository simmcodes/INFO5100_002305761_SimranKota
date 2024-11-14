package com.example.exercise9;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BasicCalculator extends Application {

    TextField textField1 = new TextField();
    TextField textField2 = new TextField();
    Label resultDisplay = new Label("");

    @Override
    public void start(Stage primaryStage) {
        Label label1 = new Label("Number 1:");
        Label label2 = new Label("Number 2:");
        Label resultLabel = new Label("Result:");

        Button addButton = new Button("+");
        Button subtractButton = new Button("-");
        Button multiplyButton = new Button("*");
        Button divideButton = new Button("/");

        addButton.setOnAction(this::handleAdd);
        subtractButton.setOnAction(this::handleSubtract);
        multiplyButton.setOnAction(this::handleMultiply);
        divideButton.setOnAction(this::handleDivide);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(label1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(label2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(resultLabel, 0, 2);
        gridPane.add(resultDisplay, 1, 2);
        gridPane.add(addButton, 0, 3);
        gridPane.add(subtractButton, 1, 3);
        gridPane.add(multiplyButton, 0, 4);
        gridPane.add(divideButton, 1, 4);

        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Calculator");
        primaryStage.show();
    }
    //Add operation
    private void handleAdd(javafx.event.ActionEvent e) {
        double num1 = Double.parseDouble(textField1.getText());
        double num2 = Double.parseDouble(textField2.getText());
        double sum = num1 + num2;
        resultDisplay.setText(String.valueOf(sum));
    }

    // Subtract operation
    private void handleSubtract(javafx.event.ActionEvent e) {
        double num1 = Double.parseDouble(textField1.getText());
        double num2 = Double.parseDouble(textField2.getText());
        double difference = num1 - num2;
        resultDisplay.setText(String.valueOf(difference));
    }

    // Multiply operation
    private void handleMultiply(javafx.event.ActionEvent e) {
        double num1 = Double.parseDouble(textField1.getText());
        double num2 = Double.parseDouble(textField2.getText());
        double product = num1 * num2;
        resultDisplay.setText(String.valueOf(product));
    }

    // Divide operation
    private void handleDivide(javafx.event.ActionEvent e) {
        double num1 = Double.parseDouble(textField1.getText());
        double num2 = Double.parseDouble(textField2.getText());
        if (num2 != 0) {
            double quotient = num1 / num2;
            resultDisplay.setText(String.valueOf(quotient));
        } else {
            resultDisplay.setText("Cannot divide by zero");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
