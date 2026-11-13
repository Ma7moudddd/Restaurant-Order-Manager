import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Restaurant Order Manager =====");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. Display Menu");
        System.out.println("4. Search Menu Item");
        System.out.println("5. Create Order");
        System.out.println("6. Add Item to Order");
        System.out.println("7. Remove Item from Order");
        System.out.println("8. Display Order");
        System.out.println("9. Add Order to Kitchen Queue");
        System.out.println("10. Process Next Order");
        System.out.println("11. Search Order");
        System.out.println("12. Check Order Status");
        System.out.println("13. Display Completed Orders");
        System.out.println("14. Cancel Order");
        System.out.println("15. Exit");
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: {
                    int id = readInt("Item ID: ");
                    String name = readString("Name: ");
                    double price = readDouble("Price: ");
                    String category = readString("Category: ");
                    restaurant.addMenuItem(id, name, price, category);
                    break;
                }
                case 2:
                    restaurant.removeMenuItem(readInt("Item ID: "));
                    break;
                case 3:
                    restaurant.displayMenu();
                    break;
                case 4:
                    restaurant.searchMenuItem(readInt("Item ID: "));
                    break;
                case 5: {
                    int id = readInt("Order ID: ");
                    String customer = readString("Customer name: ");
                    restaurant.createOrder(id, customer);
                    break;
                }
                case 6: {
                    int orderId = readInt("Order ID: ");
                    int itemId = readInt("Menu item ID: ");
                    int qty = readInt("Quantity: ");
                    restaurant.addItemToOrder(orderId, itemId, qty);
                    break;
                }
                case 7: {
                    int orderId = readInt("Order ID: ");
                    int itemId = readInt("Menu item ID: ");
                    restaurant.removeItemFromOrder(orderId, itemId);
                    break;
                }
                case 8:
                    restaurant.displayOrder(readInt("Order ID: "));
                    break;
                case 9:
                    restaurant.addOrderToKitchenQueue(readInt("Order ID: "));
                    break;
                case 10:
                    restaurant.processNextOrder();
                    break;
                case 11:
                    restaurant.searchOrder(readInt("Order ID: "));
                    break;
                case 12:
                    restaurant.checkOrderStatus(readInt("Order ID: "));
                    break;
                case 13:
                    restaurant.displayCompletedOrders();
                    break;
                case 14:
                    restaurant.cancelOrder(readInt("Order ID: "));
                    break;
                case 15:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 15.");
            }
        }
        scanner.close();
    }
}