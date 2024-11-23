import java.util.ArrayList;
import java.util.List;

// Singleton: OrderManager
class OrderManager {
    private static OrderManager instance;
    private final List<String> orders = new ArrayList<>();
    private final List<Chef> observers = new ArrayList<>();

    private OrderManager() {}

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void placeOrder(String order) {
        orders.add(order);
        notifyChefs(order);
    }

    public List<String> getOrders() {
        return new ArrayList<>(orders);
    }

    public void addObserver(Chef chef) {
        observers.add(chef);
    }

    private void notifyChefs(String order) {
        for (Chef chef : observers) {
            chef.update(order);
        }
    }
}


// Observer: Chef
interface Chef {
    void update(String order);
}

class PizzaChef implements Chef {
    @Override
    public void update(String order) {
        if (order.contains("Pizza")) {
            System.out.println("Pizza Chef: Preparing order - " + order);
        }
    }
}

class GeneralChef implements Chef {
    @Override
    public void update(String order) {
        System.out.println("General Chef: Preparing order - " + order);
    }
}

// Factory: MenuItem and its Types
abstract class MenuItem {
    protected String name;
    protected double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void prepare();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Pizza extends MenuItem {
    public Pizza(String name, double price) {
        super(name, price);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing Pizza: " + name + " ($" + price + ")");
    }
}

class Pasta extends MenuItem {
    public Pasta(String name, double price) {
        super(name, price);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing Pasta: " + name + " ($" + price + ")");
    }
}

class Burger extends MenuItem {
    public Burger(String name, double price) {
        super(name, price);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing Burger: " + name + " ($" + price + ")");
    }
}

class MenuItemFactory {
    public static MenuItem createMenuItem(String type, String name, double price) {
        return switch (type.toLowerCase()) {
            case "pizza" -> new Pizza(name, price);
            case "pasta" -> new Pasta(name, price);
            case "burger" -> new Burger(name, price);
            default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
        };
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        OrderManager orderManager = OrderManager.getInstance();

        PizzaChef pizzaChef = new PizzaChef();
        GeneralChef generalChef = new GeneralChef();
        orderManager.addObserver(pizzaChef);
        orderManager.addObserver(generalChef);

        MenuItem margherita = MenuItemFactory.createMenuItem("pizza", "Margherita", 12.99);
        MenuItem alfredoPasta = MenuItemFactory.createMenuItem("pasta", "Alfredo", 10.99);
        MenuItem cheeseburger = MenuItemFactory.createMenuItem("burger", "Cheeseburger", 8.99);

        margherita.prepare();
        alfredoPasta.prepare();
        cheeseburger.prepare();

        orderManager.placeOrder(margherita.getName());
        orderManager.placeOrder(alfredoPasta.getName());
        orderManager.placeOrder(cheeseburger.getName());

        System.out.println("\nAll orders:");
        for (String order : orderManager.getOrders()) {
            System.out.println(order);
        }
    }
}