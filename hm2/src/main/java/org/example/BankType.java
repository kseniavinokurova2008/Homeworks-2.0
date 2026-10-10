package org.example;

public enum BankType {
  NEO("НеоКредит Банк", 0.01),
  AUM("Арум Финтех", 0.02),
  VTA("Вектор Альянс Банк", 0.00);
  final String NameBank;
  final double commission;
  BankType(String NameBank, double commission) {
    this.NameBank = NameBank;
    this.commission = commission;
  }
  public String getNameBank() {
    return NameBank;
  }
  public double getcommission() {
    return commission;
  }
}