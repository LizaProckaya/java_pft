package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoContactNewPage();
    ContactData contact = new ContactData("Elizaveta", "Prockaya", "Pavlovna", "Liza", "tester", "CometСat", "Russia, Nizhny Novgorod", "89200101623", "cat@gmail.com", "5", "May", "2000", "test1");
    app.getContactHelper().createContact(contact, true);
    app.getContactHelper().returnToHome();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
    
    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
    app.getSessionHelper().logout();

  }

}
