import java.io.*;


class Shape implements Serializable {
    String color;


    Shape(String color) {
        this.color = color;
    }

    double calculateArea() {
        return 0;
    }

    double calculatePerimeter() {
        return 0;
    }

    String getColor() {
        return color;
    }
}


class Triangle extends Shape {
    double base, height, side1, side2;

    Triangle(double base, double height, double side1, double side2, String color) {
        super(color);
        this.base = base;
        this.height = height;
        this.side1 = side1;
        this.side2 = side2;
    }

    double calculateArea() {
        return 0.5 * base * height;
    }

    double calculatePerimeter() {
        return base + side1 + side2;
    }
}


class Rectangle extends Shape {
    double width, height;

    Rectangle(double width, double height, String color) {
        super(color);
        this.width = width;
        this.height = height;
    }

    double calculateArea() {
        return width * height;
    }

    double calculatePerimeter() {
        return 2 * (width + height);
    }
}


class Circle extends Shape {
    double radius;

    Circle(double radius, String color) {
        super(color);
        this.radius = radius;
    }

    double calculateArea() {
        return Math.PI * radius * radius;
    }

    double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}


class Square extends Rectangle {
    Square(double side, String color) {
        super(side, side, color);
    }
}

public class Main {
    public static void main(String[] args) {

        Shape[] shapes = {
                new Triangle(3, 4, 5, 6, "Red"),
                new Rectangle(5, 10, "Blue"),
                new Circle(7, "Green"),
                new Square(4, "Yellow")
        };


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shapes.ser"))) {
            oos.writeObject(shapes);
            System.out.println("Shapes have been serialized (saved) successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("shapes.ser"))) {
            Shape[] deserializedShapes = (Shape[]) ois.readObject();
            System.out.println("\nDeserialized Shapes:");


            for (Shape shape : deserializedShapes) {
                System.out.printf("%s - Area: %.2f, Perimeter: %.2f, Color: %s%n",
                        shape.getClass().getSimpleName(),
                        shape.calculateArea(),
                        shape.calculatePerimeter(),
                        shape.getColor());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
