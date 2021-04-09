package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeletingContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000"), true);
    }
  }

  @Test
  public void testDeleting() {
    Contacts contacts = app.db().contacts();
    List<ContactData> contactsList = new ArrayList<>(contacts);
    Groups groups = app.db().groups();
    List<GroupData> groupData = new ArrayList<>(groups);
    ContactData contactBefore = null;
    GroupData group = null;

    for (int i = 0; i < contactsList.size(); i++) {
      contactBefore = contactsList.get(i);
      if (contactBefore.getGroups().size() != 0) {
        group = new ArrayList<>(contactBefore.getGroups()).get(0);
        break;
      } else if (contactBefore.getGroups().size() == 0 && i == contactsList.size() - 1 && groups.size() != 0) {
        // добавить в группу
        app.contact().selectContactById(contactBefore.getId());
        group = groupData.get(0);
        app.contact().addContactsToGroupById(group.getId());
      } else if (groups.size() == 0) {
        // создать и добавить
        app.goTo().groupPage();
        String newGroupName = "testAdding";
        app.group().create(new GroupData().withName(newGroupName));
        app.goTo().contactPage();
        app.contact().selectContactById(contactBefore.getId());
        app.contact().addContactsToGroupByVisibleText(newGroupName);
        app.goTo().contactPage();
        group = app.db().groups().stream().filter(g -> g.getName().equals(newGroupName)).collect(Collectors.toList()).get(0);
      }
    }

    Assert.assertNotNull(contactBefore);
    Assert.assertNotNull(group);

    app.contact().removeContactFromGroup(group.getId(), contactBefore.getId());

    ContactData finalContactBefore = contactBefore;
    ContactData contactAfter = app.db().contacts().stream().filter(c -> c.getId() == finalContactBefore.getId()).collect(Collectors.toList()).get(0);

    Assert.assertNotNull(contactAfter);
    GroupData finalGroup = group;
    assertThat((int) contactAfter.getGroups().stream().filter((g) -> g.getId() == finalGroup.getId()).count(), equalTo(0));
    assertThat(contactAfter.getGroups().withAdded(group), equalTo(contactBefore.getGroups()));
  }

}