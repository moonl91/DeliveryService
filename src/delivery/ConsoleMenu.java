package delivery;

import java.util.Scanner;

import java.nio.charset.StandardCharsets;

/** Отвечает за меню и консольный ввод пользователя. */
public class ConsoleMenu {
  private final Scanner scanner = 
          new Scanner(System.in, StandardCharsets.UTF_8);
  private final OrderService orderService = new OrderService();

  public void start() {
    boolean running = true;
    while (running) {
      printMenu();
      int choice = readInt("Выберите пункт: ");
      try {
        switch (choice) {
          case 1 -> createOrder();
          case 2 -> showOrders();
          case 3 -> chooseDelivery();
          case 4 -> assignCourier();
          case 5 -> changeStatus();
          case 0 -> {
            running = false;
            System.out.println("Программа завершена.");
          }
          default -> System.out.println("Нет такого пункта меню.");
        }
      } catch (IllegalArgumentException | IllegalStateException exception) {
        System.out.println("Ошибка: " + exception.getMessage());
      }
      System.out.println();
    }
  }

  private void printMenu() {
    System.out.println("VezuKudaHochu: Служба доставки");
    System.out.println("1. Создать заказ");
    System.out.println("2. Показать заказы");
    System.out.println("3. Выбрать способ доставки");
    System.out.println("4. Назначить курьера");
    System.out.println("5. Изменить статус заказа");
    System.out.println("0. Выход");
  }

  private void createOrder() {
    System.out.println("VezuKudaHochu: Новый заказ");
    String name = readText("Имя клиента: ");
    String phone = readText("Телефон клиента: ");
    String address = readText("Адрес доставки: ");

    Customer customer = new Customer(name, phone);
    Order order = orderService.createOrder(customer, address);

    boolean addMore = true;
    while (addMore) {
      String productName = readText("Название товара: ");
      int quantity = readInt("Количество: ");
      double price = readDouble("Цена за единицу: ");
      order.addItem(new OrderItem(productName, quantity, price));
      addMore = readYesNo("Добавить ещё товар? (д/н): ");
    }
    System.out.println("Заказ создан!");
    System.out.println(order.getSummary());
  }

  private void showOrders() {
    if (orderService.getOrders().isEmpty()) {
      System.out.println("Заказов пока нет.");
      return;
    }
    for (Order order : orderService.getOrders()) {
      System.out.println("--------------------");
      System.out.println(order.getSummary());
    }
  }

  private void chooseDelivery() {
    Order order = askForOrder();
    if (order == null) {
      return;
    }

    System.out.println("1. Стандартная доставка");
    System.out.println("2. Экспресс-доставка");
    System.out.println("3. Самовывоз");
    int choice = readInt("Способ доставки: ");

    DeliveryMethod method = switch (choice) {
      case 1 -> new StandardDelivery();
      case 2 -> new ExpressDelivery();
      case 3 -> new PickupDelivery();
      default -> null;
    };

    if (method == null) {
      System.out.println("Неизвестный способ доставки.");
      return;
    }

    order.setDeliveryMethod(method);
    System.out.println("Способ: " + method.getName());
    System.out.println("Стоимость доставки: "
        + String.format("%.2f", method.calculateCost(order.getItemsCost())));
    System.out.println("Ориентировочный срок: "
        + (method.getEstimatedDays() == 0
        ? "сегодня" : method.getEstimatedDays() + " дн."));
    System.out.println("Итоговая стоимость: " + String.format("%.2f", order.getTotalCost()));
  }

  private void assignCourier() {
    Order order = askForOrder();
    if (order == null) {
      return;
    }
    System.out.println("Доступные курьеры:");
    System.out.println("1. Иван");
    System.out.println("2. Мария");
    System.out.println("3. Алексей");
    int choice = readInt("Выберите курьера: ");

    Courier courier = switch (choice) {
      case 1 -> new Courier(1, "Иван");
      case 2 -> new Courier(2, "Мария");
      case 3 -> new Courier(3, "Алексей");
      default -> null;
    };

    if (courier == null) {
      System.out.println("Такого курьера нет.");
      return;
    }
    order.assignCourier(courier);
    System.out.println("Назначен курьер: " + courier);
  }

  private void changeStatus() {
    Order order = askForOrder();
    if (order == null) {
      return;
    }

    System.out.println("1. Подтвердить");
    System.out.println("2. Передать в доставку");
    System.out.println("3. Отметить доставленным");
    System.out.println("4. Отменить");
    int choice = readInt("Новое действие: ");

    OrderStatus newStatus = switch (choice) {
      case 1 -> OrderStatus.CONFIRMED;
      case 2 -> OrderStatus.IN_DELIVERY;
      case 3 -> OrderStatus.DELIVERED;
      case 4 -> OrderStatus.CANCELLED;
      default -> null;
    };

    if (newStatus == null) {
      System.out.println("Неизвестное действие.");
      return;
    }
    order.changeStatus(newStatus);
    System.out.println("Статус обновлён.");
    System.out.println(order.getSummary());
  }

  private Order askForOrder() {
    if (orderService.getOrders().isEmpty()) {
      System.out.println("Сначала создайте заказ.");
      return null;
    }
    int id = readInt("Номер заказа: ");
    Order order = orderService.findOrder(id);
    if (order == null) {
      System.out.println("Заказ с таким номером не найден.");
    }
    return order;
  }

  private String readText(String prompt) {
    while (true) {
      System.out.print(prompt);
      String value = scanner.nextLine().trim();
      if (!value.isEmpty()) {
        return value;
      }
      System.out.println("Поле не должно быть пустым.");
    }
  }

  private int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException exception) {
        System.out.println("Введите целое число.");
      }
    }
  }

  private double readDouble(String prompt) {
    while (true) {
      System.out.print(prompt);
      String value = scanner.nextLine().trim().replace(',', '.');
      try {
        double number = Double.parseDouble(value);
        if (Double.isFinite(number) && number >= 0) {
          return number;
        }
      } catch (NumberFormatException exception) {
      }
      System.out.println("Введите неотрицательное число, например 199.90.");
    }
  }

  private boolean readYesNo(String prompt) {
    while (true) {
        System.out.print(prompt);

        String answer = scanner.nextLine()
                .trim()
                .toLowerCase();

        if (answer.equals("д") || answer.equals("да")
                || answer.equals("y") || answer.equals("yes")) {
            return true;
        }

        if (answer.equals("н") || answer.equals("нет")
                || answer.equals("n") || answer.equals("no")) {
            return false;
        }

        System.out.println("Введите «Д» или «Н».");
    }
  }
}
