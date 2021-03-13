package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().contactPage();
    List<ContactData> before = app.contact().list();
    app.goTo().newPage();
    ContactData contact = new ContactData()
            .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withTelephoneMobile("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withGroupName("test1");
    app.contact().create(contact, true);
    app.contact().returnToHome();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}
