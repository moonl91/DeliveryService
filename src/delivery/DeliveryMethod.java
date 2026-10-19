package delivery;

/** Общий контракт для любого способа доставки. */
public interface DeliveryMethod {
  String getName();

  double calculateCost(double itemsCost);

  int getEstimatedDays();

  boolean needsCourier();
}
