package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerate {
  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File((args[1]));
    List<ContactData> contacts = generateContact(count);
    save(contacts, file);
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s\n", contact.getFirstName(), contact.getMiddleName(), contact.getLastName(),contact.getNickname()));
    }
    writer.close();
  }
  private static List<ContactData> generateContact(int count) {
    System.out.println(new File(".").getAbsolutePath());
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstName(String.format("Elizaveta %s", i)).withMiddleName(String.format("Prockaya %s", i)).withLastName(String.format("Pavlovna %s", i)).withNickname(String.format("Liza %s", i)));
    }
    return contacts;
  }
}