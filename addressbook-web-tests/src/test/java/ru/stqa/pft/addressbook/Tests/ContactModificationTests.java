package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().contactPage();
      app.contact().create(new ContactData()
              .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000"),true);//.withGroupName("test1"), true);
    }
  }

  @Test
  public void testModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000")
//            .withGroupName("test1");
            .withEmail2("").withEmail3("").withWorkPhone("").withHomePhone("");
    app.goTo().contactPage();
    app.contact().modify(contact);
    Assert.assertEquals(app.contact().count(), before.size());
    Contacts after = app.db().contacts();
    // Удаляем старый список.
    before.remove(modifiedContact);
    // Добавляем измененный.
    before.add(contact);
    Assert.assertEquals(before, after);
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI ();
  }
}