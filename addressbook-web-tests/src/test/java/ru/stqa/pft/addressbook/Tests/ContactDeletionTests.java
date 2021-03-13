package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.group().list().size() == 0) {
      app.contact().create(new ContactData("Elizaveta", "Prockaya", "Pavlovna", "Liza", "tester", "CometСat", "Russia, Nizhny Novgorod", "89200101623", "cat@gmail.com", "5", "May", "2000", "test1"), true);
    }
  }

  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    // Сравнение размера списков групп.
    Assert.assertEquals(after.size(), before.size() - 1);
    // Сравнение списков групп целиком.
    before.remove(index);
    Assert.assertEquals(before, after);
  }
}

