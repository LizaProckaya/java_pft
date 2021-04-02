package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(("src/test/resources/contacts.xml"))))){
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        //String[] split = line.split(";");
        //list.add(new Object[] {new ContactData().withFirstName(split[0]).withMiddleName(split[1]).withLastName(split[2]).withNickname(split[3]).withGroupName(split[4])});
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }
  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(("src/test/resources/contacts.json"))))){
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        // Для csv:
        //String[] split = line.split(";");
        //list.add(new Object[] {new ContactData().withFirstName(split[0]).withMiddleName(split[1]).withLastName(split[2]).withNickname(split[3]).withGroupName(split[4])});
        line = reader.readLine();
      }
      Gson gson =  new Gson();
      List<ContactData> contacts = gson.fromJson(json,new TypeToken<List<ContactData>>(){}.getType()); //List<GroupData>.class
      //Для xml:
      //XStream xstream = new XStream();
      //xstream.processAnnotations(ContactData.class);
      //List<ContactData> contacts = (List<ContactData>) xstream.fromXML(json);
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test
  public void testContactCreation() {
    //File photo = new File("src/test/resources/stru.png");
    app.goTo().contactPage();
    Contacts before = app.db().contacts();
    app.goTo().newPage();
    ContactData contact = new ContactData()
            .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withEmail2("").withEmail3("").withWorkPhone("").withHomePhone("");
    app.contact().create(contact, true);
    app.contact().returnToHome();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
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

