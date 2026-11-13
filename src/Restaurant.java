import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Restaurant {
    private ArrayList<MenuItem> menu = new ArrayList<>();
    private LinkedList<Order> kitchenQueue = new LinkedList<>();
    private HashMap<Integer, Order> orders = new HashMap<>();
    private LinkedHashMap<Integer, Order> completedOrders = new LinkedHashMap<>();

    private MenuItem findMenuItem(int id) {
        for (MenuItem m : menu) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public void addMenuItem(int id, String name, double price, String category) {
        if (findMenuItem(id) != null) {
            System.out.println("A menu item with this ID already exists.");
            return;
        }
        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }
        menu.add(new MenuItem(id, name, price, category));
        System.out.println("Menu item added.");
    }

    public void removeMenuItem(int id) {
        Iterator<MenuItem> iterator = menu.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                System.out.println("Menu item removed.");
                return;
            }
        }
        System.out.println("Menu item not found.");
    }

    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("The menu is empty.");
            return;
        }
        for (MenuItem m : menu) {
            System.out.println(m);
        }
    }

    public void searchMenuItem(int id) {
        MenuItem m = findMenuItem(id);
        if (m == null) {
            System.out.println("Menu item not found.");
        } else {
            System.out.println(m);
        }
    }

    public void createOrder(int orderId, String customerName) {
        if (orders.containsKey(orderId)) {
            System.out.println("An order with this ID already exists.");
            return;
        }
        orders.put(orderId, new Order(orderId, customerName));
        System.out.println("Order created with status PENDING.");
    }

    public void addItemToOrder(int orderId, int menuItemId, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (!order.isModifiable()) {
            System.out.println("This order is " + order.getStatus() + " and cannot be modified.");
            return;
        }
        MenuItem item = findMenuItem(menuItemId);
        if (item == null) {
            System.out.println("Menu item not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        order.addItem(new OrderItem(item, quantity));
        System.out.println("Item added. Current total: " + String.format("%.2f", order.getTotal()));
    }

    public void removeItemFromOrder(int orderId, int menuItemId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (!order.isModifiable()) {
            System.out.println("This order is " + order.getStatus() + " and cannot be modified.");
            return;
        }
        if (order.removeItem(menuItemId)) {
            System.out.println("Item removed. Current total: " + String.format("%.2f", order.getTotal()));
        } else {
            System.out.println("That item is not in this order.");
        }
    }

    public void displayOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.displayOrder();
    }

    public void addOrderToKitchenQueue(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            System.out.println("Only PENDING orders can be added to the kitchen queue. Current status: "
                    + order.getStatus());
            return;
        }
        kitchenQueue.add(order);
        order.updateStatus(OrderStatus.IN_KITCHEN);
        System.out.println("Order added to the kitchen queue.");
    }

    public void processNextOrder() {
        if (kitchenQueue.isEmpty()) {
            System.out.println("The kitchen queue is empty.");
            return;
        }
        Order order = kitchenQueue.peek();
        if (order.getItems().isEmpty()) {
            System.out.println("This order has no items and cannot be processed.");
            return;
        }
        kitchenQueue.poll();
        order.updateStatus(OrderStatus.COMPLETED);
        completedOrders.put(order.getOrderId(), order);
        System.out.println("Order " + order.getOrderId() + " has been completed.");
    }

    public void searchOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
        } else {
            order.displayOrder();
        }
    }

    public void checkOrderStatus(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
        } else {
            System.out.println("Order " + orderId + " status: " + order.getStatus());
        }
    }

    public void displayCompletedOrders() {
        if (completedOrders.isEmpty()) {
            System.out.println("No completed orders yet.");
            return;
        }
        for (Order order : completedOrders.values()) {
            order.displayOrder();
            System.out.println("------------------------");
        }
    }

    public void cancelOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getStatus() == OrderStatus.COMPLETED) {
            System.out.println("A completed order cannot be cancelled.");
            return;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("This order is already cancelled.");
            return;
        }
        if (order.getStatus() == OrderStatus.IN_KITCHEN) {
            kitchenQueue.remove(order);
        }
        order.updateStatus(OrderStatus.CANCELLED);
        System.out.println("Order " + orderId + " has been cancelled.");
    }
}