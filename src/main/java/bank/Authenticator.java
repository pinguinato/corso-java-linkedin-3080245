package bank;

import java.util.Objects;

import javax.security.auth.login.LoginException;

public class Authenticator {

  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);

    if (Objects.isNull(customer))
      throw new LoginException("Username not found");

    if (password.equals(customer.getPassword())) {
      customer.setAuthenticated(true);

      return customer;
    }

    else
      throw new LoginException("Incorrect passwprd");
  }

  public static void logout(Customer customer) {
    customer.setAuthenticated(false);
  }

}
