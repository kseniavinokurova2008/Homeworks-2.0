import java.util.Locale;
import java.util.Scanner;

public class Main {
  public static Hero createHero() {
    System.out.println("Введите тип героя");
    String type = scanner.nextLine();
    System.out.println("Введите имя героя");
    String name = scanner.nextLine();
    System.out.println("Введите здоровье");
    int maxHealth = scanner.nextInt();
    System.out.println("Введите атакy");
    int attack = scanner.nextInt();
    System.out.println("Введите ресурсы");
    int resources = scanner.nextInt();
    scanner.nextLine();
    Hero hero;
    if (type.equals("Воин")) {
      hero = new Warrior(name, maxHealth, attack, resources);
    }
    else {
      hero = new Mage(name, maxHealth, attack, resources);
    }
    return hero;
  }
  static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
  public static void main(String[] args) {
    Hero hero1 = createHero();
    Hero hero2 = createHero();
    Arena arena = new Arena(hero1, hero2);
    arena.startTournament();
  }
}
