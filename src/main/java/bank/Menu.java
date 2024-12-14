package bank;

import java.util.Objects;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exception.AmountException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank International");

    Menu menu = new Menu();
    // aprire lo Scanner
    menu.scanner = new Scanner(System.in);

    // autenticazione
    Customer customer = menu.authenticateUser();

    // se l'auteticazione ha successo allora mostri il menu del programma passando i
    // dati dell'utente
    if (Objects.nonNull(customer)) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    // chiudere lo Scanner
    menu.scanner.close();
  }

  // metodo che serve per chiedere le credenziali di accesso
  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;

    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException ecc) {
      System.out.println("There was an error: " + ecc.getMessage());
    }

    return customer;
  }

  // metodo del menu del programma
  private void showMenu(Customer customer, Account account) {

    // definisco una variabile intera per tenere traccia della scelta dell'utente
    int selection = 0;

    // fintanto che la selezione è diversa dal numero 4
    // e il customer non risulta autenticato correttamente
    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("===========================================");
      System.out.println("Please select one of the following options: ");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check Balance");
      System.out.println("4: Exit");
      System.out.println("===========================================");

      // leggi la selezione dell'utente
      selection = scanner.nextInt();
      double amount = 0;

      // casistiche di scelte dell'utente
      switch (selection) {
        case 1:
          System.out.println("How much woould you like to deposit?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;

        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;

        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;

        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking at Globe Bank International!");
          break;

        default:
          System.out.println("Invalid action. Please try again");
      }
    }
  }

}
