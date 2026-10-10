public enum ActionType {
  BASE_ATTACK("базовая атака"),
  SPECIAL_SKILL("применение особого умения или магии"),
  REST("пропуск хода ради восстановления сил");
  final String nameRussian;
  ActionType(String nameRussian) {
    this.nameRussian = nameRussian;
  }
  public String getRussianName() {
    return nameRussian;
  }
}
