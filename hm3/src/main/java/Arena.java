public class Arena {
  private final Hero fighter1;
  private final Hero fighter2;
  private int roundCounter;

  Arena(Hero fighter1, Hero fighter2) {
    this.fighter1 = fighter1;
    this.fighter2 = fighter2;
  }

  void startTournament() {
    while (fighter1.isAlive() && fighter2.isAlive()) {
      roundCounter += 1;
      System.out.println("Round " + roundCounter);
      int attack = (int) (Math.random() * 2);
      Hero attacker;
      Hero defender;
      if (attack == 0) {
        attacker = fighter1;
        defender = fighter2;
      } else {
        attacker = fighter2;
        defender = fighter1;
      }
      ActionType action = attacker.makeTurn(defender);
      System.out.println("Игрок " + attacker.getName() + " совершил действие: " + action.getRussianName());
      if (!defender.isAlive()) {
        System.out.println("победитель: " + attacker.getName());
        return;
      } else {
        ActionType action1 = defender.makeTurn(attacker);
        System.out.println("Игрок " + defender.getName() + " совершил действие: " + action1.getRussianName());
        if (!attacker.isAlive()) {
          System.out.println("победитель: " + defender.getName());
          return;
        }
      }
      System.out.println(fighter1.getName() + ": " + fighter1.getHealth());
      System.out.println(fighter2.getName() + ": " + fighter2.getHealth());
    }
  }
}
