package ru.stqa.pft.addressbook.appmanager;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
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
    if (creation) {
//      click(By.name("new_group"));
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroupName() + "");
//      click(By.xpath("(//option[@value='" + contactData.getGroupId() + "'])[3]"));
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void returnToHome() {
    click(By.linkText("home"));
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

  public void createContact(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact,creation);
    submitContactCreation();
    returnToHome();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }
}
