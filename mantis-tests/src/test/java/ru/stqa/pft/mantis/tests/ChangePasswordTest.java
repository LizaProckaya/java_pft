package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.mail().start();

    long now = System.currentTimeMillis();
    if (app.db().users().size() == 0) {
      String email = String.format("user%s@localhost", now);
      String username = String.format("user1%s", now);
      app.registration().start(username, email);
    }
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {
    app.admin().login();
    Users users = app.db().users();
    UserData userData = users.stream().iterator().next();

    app.admin().resetUserPassword(userData.getUsername());
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 60000);
    String newPassword = "newPassword";
    String confirmationLink = findConfirmationLink(mailMessages, userData.getEmail());
    app.user().resetPassword(confirmationLink, userData.getUsername(), newPassword);

    assertTrue(app.newSession().login(userData.getUsername(), newPassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    List<MailMessage> filteredMailMessages = mailMessages.stream().filter((m) -> m.to.equals(email)).collect(Collectors.toList());
    MailMessage mailMessage = filteredMailMessages.get(mailMessages.size() - 1);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

}
