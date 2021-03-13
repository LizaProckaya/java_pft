package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){  app.getNavigationHelper().gotoGroupPage();
    // Проверка на наличии хотя бы 1ой гуппы на странице.
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupDeletion() {
    app.getNavigationHelper().gotoGroupPage();
    // Проверка на наличии хотя бы 1ой гуппы на странице.
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroup();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    // Сравнение размера списков групп.
    Assert.assertEquals(after.size(), before.size() - 1);
    // Сравнение списков групп целиком.
    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }
}

