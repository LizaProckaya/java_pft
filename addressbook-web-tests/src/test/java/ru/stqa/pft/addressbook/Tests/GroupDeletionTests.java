package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    // Проверка на наличии хотя бы 1ой гуппы на странице.
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupDeletion() {
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    app.group().delete(index);
    List<GroupData> after = app.group().list();
    // Сравнение размера списков групп.
    Assert.assertEquals(after.size(), before.size() - 1);
    // Сравнение списков групп целиком.
    before.remove(index);
    Assert.assertEquals(before, after);
  }

}

