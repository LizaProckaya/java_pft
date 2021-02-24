package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testModification(){
    app.getNavigationHelper().gotoContactPage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Elizaveta", "Prockaya", "Pavlovna", "Liza", "tester", "CometСat", "Russia, Nizhny Novgorod", "89200101623", "cat@gmail.com", "5", "May", "2000", "test1"), true);
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Elizaveta1", "Prockaya1", "Pavlovna1", "Liza1", "tester", "CometСat", "Russia, Nizhny Novgorod", "89200101623", "cat@gmail.com", "5", "May", "2000", "Test"), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHome();
    app.getSessionHelper().logout();;
  }

}
