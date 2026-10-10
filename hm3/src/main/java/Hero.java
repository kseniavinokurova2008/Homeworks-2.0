public abstract class Hero {
  public static final int MIN_STAT_VALUE = 1;

  private final String name;
  private final int maxHealth;
  private final int baseAttack;
  private int health;

  Hero(String name, int maxHealth, int baseAttack) {
    this.maxHealth = maxHealth;
    this.baseAttack = baseAttack;
    this.name = name;
    health = this.maxHealth;
    if (maxHealth < MIN_STAT_VALUE) {
      throw new IllegalArgumentException(
        "incorrect numerical characteristic"
      );
    }
    if (baseAttack < MIN_STAT_VALUE) {
      throw new IllegalArgumentException(
        "incorrect numerical characteristic"
      );
    }
  }
  public String getName() {
    return name;
  }
  public int getBaseAttack() {
    return baseAttack;
  }
  public int getMaxHealth() {
    return maxHealth;
  }
  public int getHealth() {
    return health;
  }
  abstract void attack(Hero target);
  abstract ActionType makeTurn(Hero target);
  void takeDamage(int damage) {
    health = Math.max(0, health - damage);
    if (health == 0) {
      System.out.println(name + " пал в бою!");
    }
  }
  void heal(int amount) {
    health = Math.min(maxHealth, amount + health);
  }
  boolean isAlive() {
    if (health > 0) {
      return true;
    }
    return false;
  }
}
