package org.example;

public interface WithdrawalOperations {
  double withdraw(double balance, Double sum, BankType bankType);
  default double applyCommission(Double sum, BankType bankType) {
    if (sum == null || bankType == null ) {
      return 0;
    }
    return Math.round(sum * bankType.commission * 100) / 100.0;
  }
}