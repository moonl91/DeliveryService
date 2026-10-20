package delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Представляет заказ и контролирует изменение его состояния. */
public class Order {
  private final int id;
  private final Customer customer;
  private final String address;
  private final List<OrderItem> items = new ArrayList<>();
  private DeliveryMethod deliveryMethod;
  private Courier courier;
  private OrderStatus status = OrderStatus.NEW;

  public Order(int id, Customer customer, String address) {
    if (id <= 0) {
      throw new IllegalArgumentException("Номер заказа должен быть положительным.");
    }
    if (customer == null) {
      throw new IllegalArgumentException("Клиент обязателен.");
    }
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Адрес не должен быть пустым.");
    }
    this.id = id;
    this.customer = customer;
    this.address = address;
  }

  public void addItem(OrderItem item) {
    if (status != OrderStatus.NEW) {
      throw new IllegalStateException("Добавлять товары можно только в новый заказ.");
    }
    if (item == null) {
      throw new IllegalArgumentException("Позиция заказа не может быть пустой.");
    }
    items.add(item);
  }

  public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
    if (deliveryMethod == null) {
      throw new IllegalArgumentException("Нужно выбрать способ доставки.");
    }
    this.deliveryMethod = deliveryMethod;
    if (!deliveryMethod.needsCourier()) {
      courier = null;
    }
  }

  public void assignCourier(Courier courier) {
    if (deliveryMethod == null || !deliveryMethod.needsCourier()) {
      throw new IllegalStateException("Для этого способа доставки курьер не нужен.");
    }
    if (courier == null) {
      throw new IllegalArgumentException("Курьер не выбран.");
    }
    this.courier = courier;
  }

  public void changeStatus(OrderStatus newStatus) {
    if (newStatus == null) {
      throw new IllegalArgumentException("Статус не выбран.");
    }

    boolean allowed = switch (status) {
      case NEW -> newStatus == OrderStatus.CONFIRMED
          || newStatus == OrderStatus.CANCELLED;
      case CONFIRMED -> newStatus == OrderStatus.IN_DELIVERY
          || newStatus == OrderStatus.CANCELLED;
      case IN_DELIVERY -> newStatus == OrderStatus.DELIVERED
          || newStatus == OrderStatus.CANCELLED;
      case DELIVERED, CANCELLED -> false;
    };

    if (!allowed) {
      throw new IllegalStateException(
          "Нельзя изменить статус «" + status + "» на «" + newStatus + "».");
    }
    if (newStatus == OrderStatus.IN_DELIVERY
        && deliveryMethod != null && deliveryMethod.needsCourier() && courier == null) {
      throw new IllegalStateException("Сначала назначьте курьера.");
    }
    status = newStatus;
  }

  public int getId() {
    return id;
  }

  public double getItemsCost() {
    double total = 0;
    for (OrderItem item : items) {
      total += item.getTotalPrice();
    }
    return total;
  }

  public double getTotalCost() {
    return getItemsCost()
        + (deliveryMethod == null ? 0 : deliveryMethod.calculateCost(getItemsCost()));
  }

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public DeliveryMethod getDeliveryMethod() {
    return deliveryMethod;
  }

  public Courier getCourier() {
    return courier;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public String getSummary() {
    String methodName = deliveryMethod == null ? "не выбран" : deliveryMethod.getName();
    String courierName = courier == null ? "не назначен" : courier.getName();
    String time = deliveryMethod == null ? "неизвестно"
        : deliveryMethod.getEstimatedDays() == 0 ? "сегодня"
        : deliveryMethod.getEstimatedDays() + " дн.";

    return "Заказ #" + id
        + "\nКлиент: " + customer.getName() + ", телефон: " + customer.getPhone()
        + "\nАдрес: " + address
        + "\nТовары: " + (items.isEmpty() ? "нет" : items)
        + "\nСтоимость товаров: " + String.format("%.2f", getItemsCost())
        + "\nДоставка: " + methodName
        + "\nИтого: " + String.format("%.2f", getTotalCost())
        + "\nОриентировочный срок: " + time
        + "\nКурьер: " + courierName
        + "\nСтатус: " + status;
  }
}
