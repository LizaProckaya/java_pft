package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerate {
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;


  public static void main(String[] args) throws IOException {
    ContactDataGenerate generator = new ContactDataGenerate();
    JCommander jCommander = new JCommander(generator);
    try{
      jCommander.parse(args);
    } catch (ParameterException ex){
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContact(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if(format.equals("xml")){
      saveAsXml(contacts, new File(file));
    } else if(format.equals("json")){
      saveAsJson(contacts, new File(file));
    }else{
      System.out.println("Unrecognized format" + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try(Writer writer = new FileWriter(file)){
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try(Writer writer = new FileWriter(file)){
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)){
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getMiddleName(), contact.getLastName(), contact.getNickname()));//, contact.getGroupName()));
      }
    }
  }

  private List<ContactData> generateContact(int count) {
    System.out.println(new File(".").getAbsolutePath());
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstName(String.format("Elizaveta %s", i)).withMiddleName(String.format("Prockaya %s", i)).withLastName(String.format("Pavlovna %s", i)).withNickname(String.format("Liza %s", i)));//.withGroupName("test"));
    }
    return contacts;
  }
}