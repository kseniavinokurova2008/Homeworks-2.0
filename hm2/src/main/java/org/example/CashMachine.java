package org.example;

public class CashMachine implements WithdrawalOperations, DepositOperations{
  public double deposit(double balance, Double sum) {
    if (sum == null || sum <= 0) {
      return balance;
    }
    return balance + sum;
  }
  public double withdraw(double balance, Double sum, BankType bankType) {
    double currentcommission = applyCommission(sum, bankType);
    double debiting = sum + currentcommission;
    if (debiting > balance) {
      System.out.println("Lack of funds");
      return balance;
    }
    else {
      return balance - debiting;
    }
  }
}