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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddingContactToGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withMobilePhone("89200101623").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000"), true);
    }
  }

  @Test
  public void testAdding() {
    Contacts contacts = app.db().contacts();
    List<ContactData> contactsList = new ArrayList<>(contacts);
    Groups groups = app.db().groups();
    ContactData contactBefore = null;

    for (int i = 0; i < contactsList.size(); i++) {
      contactBefore = contactsList.get(i);
      if (contactBefore.getGroups().size() < groups.size()) {
        break;
      } else if (contactBefore.getGroups().size() == groups.size() && i == contactsList.size() - 1) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("testAdding"));
        app.goTo().contactPage();
        groups = app.db().groups();
      }
    }

    Assert.assertNotNull(contactBefore);

    /*
    Удаляем из всех загруженных групп те, в которых состоит контакт.
    В итоге, в groups получаем доступные для записи группы. Можем взять оттуда любой, но для простоты берем первый
     */
    groups.removeAll(contactBefore.getGroups());

    GroupData availableGroup = groups.stream().iterator().next();

    app.contact().selectContactById(contactBefore.getId());

    app.contact().addContactsToGroupById(availableGroup.getId());

    ContactData finalContactBefore = contactBefore;
    ContactData contactAfter = app.db().contacts().stream().filter(contactData -> contactData.getId() == finalContactBefore.getId()).iterator().next();

    Assert.assertNotNull(contactAfter);
    assertThat(contactBefore.getGroups().size() + 1, equalTo(contactAfter.getGroups().size()));
    assertThat((int) contactAfter.getGroups().stream().filter(g -> g.getId() == availableGroup.getId()).count(), equalTo(1));
    assertThat(contactAfter.getGroups(), equalTo(contactBefore.getGroups().withAdded(availableGroup)));
  }

}
