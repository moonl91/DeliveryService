package delivery;

import java.util.Scanner;

/** Общие методы безопасного чтения данных из консоли. */
public class ConsoleInput {
  private final Scanner scanner;

  public ConsoleInput(Scanner scanner) { this.scanner = scanner; }

  public String readText(String prompt) {
    while (true) {
      System.out.print(prompt);
      String value = scanner.nextLine().trim();
      if (!value.isEmpty()) return value;
      System.out.println("Поле не должно быть пустым.");
    }
  }

  public int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try { return Integer.parseInt(scanner.nextLine().trim()); }
      catch (NumberFormatException exception) { System.out.println("Введите целое число."); }
    }
  }

  public int readPositiveInt(String prompt) {
    while (true) {
      int value = readInt(prompt);
      if (value > 0) return value;
      System.out.println("Значение должно быть больше нуля.");
    }
  }

  public double readPrice(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        double value = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
        if (Double.isFinite(value) && value >= 0) return value;
      } catch (NumberFormatException ignored) { }
      System.out.println("Введите неотрицательную цену, например 199,90.");
    }
  }

  public boolean readYesNo(String prompt) {
    while (true) {
      String answer = readText(prompt).toLowerCase();
      if (answer.equals("д") || answer.equals("да") || answer.equals("y") || answer.equals("yes")) return true;
      if (answer.equals("н") || answer.equals("нет") || answer.equals("n") || answer.equals("no")) return false;
      System.out.println("Введите «Д» или «Н».");
    }
  }
}
