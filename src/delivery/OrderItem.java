package delivery;

/** Одна позиция в заказе: товар, количество и цена за единицу. */
public class OrderItem {
  private final String productName;
  private final int quantity;
  private final double price;

  public OrderItem(String productName, int quantity, double price) {
    if (productName == null || productName.isBlank()) {
      throw new IllegalArgumentException("Название товара не должно быть пустым.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Количество должно быть больше нуля.");
    }
    if (price < 0) {
      throw new IllegalArgumentException("Цена не может быть отрицательной.");
    }
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
  }

  public double getTotalPrice() {
    return quantity * price;
  }

  @Override
  public String toString() {
    return productName + " x " + quantity + " = "
        + String.format("%.2f", getTotalPrice());
  }
}
