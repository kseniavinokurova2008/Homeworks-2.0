public class Mage extends Hero implements Castable, Restable {
  private final int maxMana;
  private int mana;
  Mage(String name, int maxHealth, int baseAttack, int maxMana) {
    super(name, maxHealth, baseAttack);
    this.maxMana = maxMana;
    this.mana = maxMana;
  }
  @Override
  void attack(Hero target) {
    if (mana >= 10) {
      target.takeDamage(getBaseAttack() * 2);
      mana -= 10;
      System.out.println("Магический выстрел");
    }
    else {
      target.takeDamage(getBaseAttack() / 2);
      mana = Math.min(maxMana, mana + 5);
      System.out.println("Удар посохом");
    }
  }
  @Override
  ActionType makeTurn(Hero target) {
    if (canCast()) {
      castSpecialSkill(target);
      return ActionType.SPECIAL_SKILL;
    }
    else if (needsRest()) {
      rest();
      return ActionType.REST;
    }
    else {
      attack(target);
      return ActionType.BASE_ATTACK;
    }
  }
  @Override
  public boolean canCast() {
    if (mana >= 25) {
      return true;
    }
    return false;
  }
  @Override
  public void castSpecialSkill(Hero target) {
    if (canCast()) {
      target.takeDamage(getBaseAttack() * 3);
      mana -= 25;
    }
    else {
      System.out.println("ошибка заклинания");
    }
  }
  @Override
  public boolean needsRest() {
    if (mana == 0 || getHealth() < 0.3 * getMaxHealth()) {
      return true;
    }
    return false;
  }
  @Override
  public void rest() {
    mana = maxMana;
    heal(getBaseAttack());
    System.out.println("Исцеляющая медитация");
  }
}
