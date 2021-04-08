package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void resetPassword(String confirmationLink, String userName, String newPassword) {
    wd.get(confirmationLink);
    type(By.name("realname"), userName);
    type(By.name("password"), newPassword);
    type(By.name("password_confirm"), newPassword);
    click(By.xpath("//span[contains(text(),'Изменить пользователя')]"));
  }

}

