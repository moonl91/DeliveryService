package delivery;

/** Текущее состояние курьера. */
public enum CourierStatus {
  FREE,
  BUSY;

  @Override
  public String toString() {
    return this == FREE ? "Свободен" : "Занят";
  }
}
