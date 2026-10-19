package delivery;

/** Экспресс-доставка: дороже, но быстрее обычной. */
public class ExpressDelivery implements DeliveryMethod {
  @Override
  public String getName() {
    return "Экспресс-доставка";
  }

  @Override
  public double calculateCost(double itemsCost) {
    return 600.0;
  }

  @Override
  public int getEstimatedDays() {
    return 1;
  }

  @Override
  public boolean needsCourier() {
    return true;
  }
}
