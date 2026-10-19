package delivery;

/** Самовывоз: доставка бесплатная, курьер не нужен. */
public class PickupDelivery implements DeliveryMethod {
  @Override
  public String getName() {
    return "Самовывоз";
  }

  @Override
  public double calculateCost(double itemsCost) {
    return 0.0;
  }

  @Override
  public int getEstimatedDays() {
    return 0;
  }

  @Override
  public boolean needsCourier() {
    return false;
  }
}
