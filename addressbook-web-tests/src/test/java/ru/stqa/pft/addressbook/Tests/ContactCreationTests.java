package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().gotoContactNewPage();
    app.getContactHelper().createContact(new ContactData("Elizaveta", "Prockaya", "Pavlovna", "Liza", "tester", "CometСat", "Russia, Nizhny Novgorod", "89200101623", "cat@gmail.com", "5", "May", "2000", "test1"),true);
    app.getSessionHelper().logout();
  }

}
