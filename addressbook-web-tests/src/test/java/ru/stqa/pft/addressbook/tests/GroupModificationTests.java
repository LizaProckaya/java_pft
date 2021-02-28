package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {
  @Test
  public void testModification() {
    app.getNavigationHelper().gotoGroupPage();
    // Проверка на наличии хотя бы 1ой гуппы на странице.
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().initGroupModification();
    GroupData group = new GroupData(before.get(before.size() - 1).getId(),"test1", "test2", "test3");
    app.getGroupHelper().fillGroupForm(group);
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after= app.getGroupHelper().getGroupList();
    // Сравнение размера списков групп.
    Assert.assertEquals(after.size(), before.size());
    before.remove(before.size()-1);
    before.add(group);
    // Преобразование списков в множества и сравнение множеств
    Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
    app.getSessionHelper().logout();


  }
}
