package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContactForm(ContactData contactData, boolean fillGroup) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    click(By.name("nickname"));
    type(By.name("nickname"), contactData.getNickname());
    click(By.name("title"));
    type(By.name("title"), contactData.getTitle());
    click(By.name("company"));
    type(By.name("company"), contactData.getCompany());
    click(By.name("address"));
    type(By.name("address"), contactData.getAddress());
    click(By.name("home"));
    click(By.name("home"));
    type(By.name("mobile"), contactData.getTelephoneMobile());
    click(By.name("email"));
    type(By.name("email"), contactData.getEmail());
    click(By.name("bday"));
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDay());
    click(By.xpath("//option[@value='" + contactData.getDay() + "']"));
    click(By.name("bmonth"));
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonth());
    click(By.xpath("//option[@value='" + contactData.getMonth() + "']"));
    type(By.name("byear"), contactData.getYear());
    if (fillGroup) {
      click(By.name("new_group"));
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroupName() + "");
      click(By.xpath("(//option[@value='" + contactData.getGroupId() + "'])[3]"));
    }
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void returnToHomeLogout() {
    click(By.linkText("home"));
    click(By.linkText("Logout"));
  }

  public void deleteFirstSelectedContact() {
    wd.findElements(By.name("selected[]")).get(0).click();
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }
}
