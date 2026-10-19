package delivery;

/** Возможные статусы заказа. */
public enum OrderStatus {
  NEW,
  CONFIRMED,
  IN_DELIVERY,
  DELIVERED,
  CANCELLED;

  @Override
  public String toString() {
    return switch (this) {
      case NEW -> "Новый";
      case CONFIRMED -> "Подтверждён";
      case IN_DELIVERY -> "В доставке";
      case DELIVERED -> "Доставлен";
      case CANCELLED -> "Отменён";
    };
  }
}
