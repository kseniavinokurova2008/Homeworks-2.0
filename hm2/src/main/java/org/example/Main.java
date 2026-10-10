package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    System.out.println("Hello");
    System.out.println("Enter the card number");
    int cardNumber = scanner.nextInt();
    System.out.println("Enter the pincode");
    int pinCode = scanner.nextInt();
    Account standard = new Account(12345, 999, 10000.00, BankType.AUM);
    if (cardNumber != standard.getcardNumber() || pinCode != standard.getpinCode()) {
      System.out.println("Access error");
      return;
    }
    System.out.println("Successful access");

    CashMachine newCashMachine = new CashMachine();

    System.out.println("Enter your deposit");
    Double deposit = scanner.nextDouble();
    double newBalance = newCashMachine.deposit(standard.getBalance(), deposit);
    standard.balance = newBalance;
    System.out.println(newBalance);

    System.out.println("Enter your sum");
    Double sum = scanner.nextDouble();
    double newBalanceAfterWithdraw =  newCashMachine.withdraw(standard.getBalance(), sum, standard.getBankType());
    standard.balance = newBalanceAfterWithdraw;
    System.out.println(newBalanceAfterWithdraw);

  }
}