package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {


//  @DataProvider
//  public Iterator<Object[]> validGroupsFromJson() throws IOException  {
//    return validGroupsFromXJson();
//  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(("src/test/resources/groups.xml"))))){
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class);
      List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
      return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }
  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(("src/test/resources/groups.json"))))){
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson =  new Gson();
      List<GroupData> groups = gson.fromJson(json,new TypeToken<List<GroupData>>(){}.getType()); //List<GroupData>.class
      //Для xml^
      //XStream xstream = new XStream();
      //xstream.processAnnotations(GroupData.class);
      //List<GroupData> groups = (List<GroupData>) xstream.fromXML(json);
      return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    GroupData group = new GroupData().withName("test1").withFooter("").withHeader("");
    app.group().create(group);
    // Сравнение размера списков групп.
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.db().groups();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    GroupData group = new GroupData().withName("test2'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before));
  }
}
