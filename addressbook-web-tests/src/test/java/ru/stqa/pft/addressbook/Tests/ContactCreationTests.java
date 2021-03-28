package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().contactPage();
    Contacts before = app.contact().all();
    app.goTo().newPage();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withPhoto(photo).withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withGroupName("test1");
    app.contact().create(contact, true);
    app.contact().returnToHome();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
  /*@Test
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/stru.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());

  }
  */

