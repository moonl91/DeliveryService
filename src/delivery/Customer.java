package delivery;

/** Хранит данные клиента. */
public class Customer {
  private final String name;
  private final String phone;

  public Customer(String name, String phone) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Имя клиента не должно быть пустым.");
    }
    if (phone == null || phone.isBlank()) {
      throw new IllegalArgumentException("Телефон не должен быть пустым.");
    }
    this.name = name;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }
}
