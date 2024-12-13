package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect() {
    String dbFile = "jdbc:sqlite:resources/bank.db";

    Connection connection = null;

    try {
      connection = DriverManager.getConnection(dbFile);
      System.out.println("We're connected!");
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "SELECT * FROM customers WHERE username = ?";
    Customer customer = null;

    try (Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, username);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return customer;

  }

  public static Account getAccount(int accountId) {
    String sql = "SELECT * FROM accounts WHERE id = ?";
    Account account = null;

    try (Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, accountId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance"));
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return account;
  }

  public static void main(String[] args) {
    // test di connessione
    connect();
    // test di ricerca Customer
    Customer customer = getCustomer("amcerlaineqm@admin.ch");
    System.out.println(customer.getName());
    // test dell'account
    Account account = getAccount(14067);
    System.out.println("Account: " + account.getId() + "," + account.getType() + "," + account.getBalance());
  }
}
