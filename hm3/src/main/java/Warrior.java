public class Warrior extends Hero implements Castable, Restable{
  private final int armor;
  Warrior(String name, int maxHealth, int baseAttack, int armor) {
    super(name, maxHealth, baseAttack);
    this.armor = armor;
  }
  @Override
  void takeDamage(int damage) {
      int finalDamage = Math.max(1, damage - armor);
      super.takeDamage(finalDamage);
  }
  @Override
  void attack(Hero target) {
    target.takeDamage(getBaseAttack());
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
    if (getHealth() < getMaxHealth() * 0.5) {
      return true;
    }
    return false;
  }
  @Override
  public void castSpecialSkill(Hero target) {
    if (canCast()) {
      target.takeDamage(getBaseAttack() + armor * 2);
    }
    else {
      System.out.println("Воин попытался ударить щитом, но потерял равновесие!");
    }
  }
  @Override
  public boolean needsRest() {
    if (getHealth() < getMaxHealth() * 0.15) {
      return true;
    }
    return false;
  }
  @Override
  public void rest() {
    heal(armor * 2);
    System.out.println("восстановление здоровья");
  }
}