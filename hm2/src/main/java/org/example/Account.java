package org.example;

public class Account {
  int cardNumber;
  int pinCode;
  double balance;
  BankType bankType;
  Account(int cardNumber, int pinCode, double balance, BankType bankType) {
    if (cardNumber >= 100_000 || cardNumber < 10_000) {
      throw new IllegalArgumentException("Error cardNumber");
    }
    if (pinCode >= 1000 || pinCode < 100) {
      throw new IllegalArgumentException("Error pincode");
    }
    this.cardNumber = cardNumber;
    this.pinCode = pinCode;
    this.balance = balance;
    if (bankType == null) {
      this.bankType = BankType.NEO;
    }
    else {
      this.bankType = bankType;
    }
  }
  int getcardNumber() {
    return cardNumber;
  }
  int getpinCode() {
    return pinCode;
  }
  double getBalance() {
    return balance;
  }
  BankType getBankType() {
    return bankType;
  }
  @Override
  public String toString() {
    return bankType.getNameBank() + " Карта: " + cardNumber + ", Баланс: " + String.format("%.2f", balance) + " руб.";
  }
}