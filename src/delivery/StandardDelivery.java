package delivery;

/** Обычная курьерская доставка. */
public class StandardDelivery implements DeliveryMethod {
  @Override
  public String getName() {
    return "Стандартная доставка";
  }

  @Override
  public double calculateCost(double itemsCost) {
    return 300.0;
  }

  @Override
  public int getEstimatedDays() {
    return 3;
  }

  @Override
  public boolean needsCourier() {
    return true;
  }
}
