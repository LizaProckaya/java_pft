package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.*;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.group().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withTelephoneMobile("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withGroupName("test1"),true);
    }
  }

  @Test
  public void testModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withTelephoneMobile("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withGroupName("test1");
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());
    // Удаляем старый список.
    before.remove(index);
    // Добавляем измененный.
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    ;
  }
}
