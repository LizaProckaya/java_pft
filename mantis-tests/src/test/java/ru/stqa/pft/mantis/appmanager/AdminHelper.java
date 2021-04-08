package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class AdminHelper extends HelperBase {

  public AdminHelper(ApplicationManager app) {
    super(app);
  }

  public void login() {
    type(By.name("username"), "administrator");
    click(By.cssSelector("input[type='Submit']"));
    type(By.name("password"), "root");
    click(By.cssSelector("input[type='Submit']"));
  }

  public void resetUserPassword(String userName) {
    click(By.xpath("//a[contains(@href,'manage_overview_page.php')]"));
    click(By.xpath("//a[contains(@href,'manage_user_page.php')]"));
    click(By.xpath(String.format("//a[contains(text(),'%s')]", userName)));
    click(By.xpath("//input[@type='submit'][@value='Сбросить пароль']"));
  }

}
