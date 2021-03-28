package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.List;

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
    attach(By.name("photo"), contactData.getPhoto());
    click(By.name("title"));
    type(By.name("title"), contactData.getTitle());
    click(By.name("company"));
    type(By.name("company"), contactData.getCompany());
    click(By.name("address"));
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    click(By.name("email"));
    type(By.name("email"), contactData.getEmail());
    click(By.name("email2"));
    type(By.name("email2"), contactData.getEmail2());
    click(By.name("email3"));
    type(By.name("email3"), contactData.getEmail3());
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


  public void deleteFirstSelectedContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void deleteFirstSelectedContactById(int id) {
    wd.findElement(By.cssSelector("input[value ='" + id + "']")).click();
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void initContactModificationById(int id) {
    wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreation();
    contactCache = null;
    returnToHome();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    returnToHome();
  }

  public void delete(ContactData contact) {
    deleteFirstSelectedContactById(contact.getId());
    contactCache = null;
    returnToHome();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("td:nth-child(1) input")).getAttribute("value"));
      String lastName = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstName = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String address = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String allEmails = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
      String allPhones = element.findElement(By.cssSelector("td:nth-child(6)")).getText();

      contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(address)
              .withAllPhones(allPhones).withAllEmails(allEmails));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("home")).getAttribute("value");
    String email2 = wd.findElement(By.name("mobile")).getAttribute("value");
    String email3 = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).withAddress(address)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3);

  }

}

