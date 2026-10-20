package delivery;

/** Хранит данные курьера. */
public class Courier {
  private final int id;
  private final String name;

  public Courier(int id, String name) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID курьера должен быть положительным.");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Имя курьера не должно быть пустым.");
    }
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return id + " — " + name;
  }
}
