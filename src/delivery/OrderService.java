package delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Управляет заказами, хранящимися в памяти программы. */
public class OrderService {
  private final List<Order> orders = new ArrayList<>();
  private int nextOrderId = 1;

  public Order createOrder(Customer customer, String address) {
    Order order = new Order(nextOrderId, customer, address);
    nextOrderId++;
    orders.add(order);
    return order;
  }

  public List<Order> getOrders() {
    return Collections.unmodifiableList(orders);
  }

  public Order findOrder(int id) {
    for (Order order : orders) {
      if (order.getId() == id) {
        return order;
      }
    }
    return null;
  }
}
