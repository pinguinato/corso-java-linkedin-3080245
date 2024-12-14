package bank;

import java.util.Objects;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

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
    
  }

}
