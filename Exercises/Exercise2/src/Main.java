
class Shape {
   static String color = "White";


    double calculateArea() {
        return 0;
    }

    double calculatePerimeter() {
        return 0;
    }


    static String getColor() {
        return color;
    }
}


class Triangle extends Shape {
    double base;
    double height;
    double side1;
    double side2;

    Triangle(double base, double height, double side1, double side2) {
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
    double width;
    double height;

    Rectangle(double width, double height) {
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

    Circle(double radius) {
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
    Square(double side) {
        super(side, side);
    }
}


public class Main {
    public static void main(String[] args) {

        Shape[] shapes = {
                new Triangle(3, 4, 5, 6),
                new Rectangle(5, 10),
                new Circle(7),
                new Square(4)
        };


        for (Shape shape : shapes) {
            System.out.printf("%s - Area: %.2f, Perimeter: %.2f, Color: %s%n",
                    shape.getClass().getSimpleName(),
                    shape.calculateArea(),
                    shape.calculatePerimeter(),
                    Shape.getColor());
        }


       Triangle.color = "Red";
        Rectangle.color = "Blue";
       Circle.color = "Green";
       Square.color = "Yellow";

       System.out.println("\nAfter changing colors:");
       for (Shape shape : shapes) {
           System.out.printf("%s - Area: %.2f, Perimeter: %.2f, Color: %s%n",
                    shape.getClass().getSimpleName(),
                   shape.calculateArea(),
                   shape.calculatePerimeter(),
                   Shape.getColor());
        }
    }
}
