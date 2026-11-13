import java.util.ArrayList;
import java.util.Iterator;

public class Order {
    private int orderId;
    private String customerName;
    private ArrayList<OrderItem> items;
    private double total;
    private OrderStatus status;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.total = 0;
        this.status = OrderStatus.PENDING;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean isModifiable() {
        return status != OrderStatus.COMPLETED && status != OrderStatus.CANCELLED;
    }

    public boolean addItem(OrderItem orderItem) {
        if (orderItem == null || !isModifiable()) {
            return false;
        }
        items.add(orderItem);
        calculateTotal();
        return true;
    }

    public boolean removeItem(int menuItemId) {
        if (!isModifiable()) {
            return false;
        }
        boolean removed = false;
        Iterator<OrderItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getItem().getId() == menuItemId) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        calculateTotal();
        return removed;
    }

    public void calculateTotal() {
        double sum = 0;
        for (OrderItem oi : items) {
            sum += oi.calculateSubtotal();
        }
        total = sum;
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Status: " + status);
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            for (OrderItem oi : items) {
                System.out.println(oi.getItem().getName() + " x " + oi.getQuantity()
                        + " = " + String.format("%.2f", oi.calculateSubtotal()));
            }
        }
        System.out.println("Total: " + String.format("%.2f", total));
    }
}