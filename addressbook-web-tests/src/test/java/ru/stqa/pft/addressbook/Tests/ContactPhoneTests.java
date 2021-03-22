package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Elizaveta").withMiddleName("Prockaya").withLastName("Pavlovna").withNickname("Liza").withTitle("tester").withCompany("CometСat").withAddress("Russia, Nizhny Novgorod").withHomePhone("4177915").withMobilePhone("89200101623").withWorkPhone("99999999999").withEmail("cat@gmail.com").withDay("5").withMonth("May").withYear("2000").withGroupName("test1"), true);
    }
  }
  @Test
  public void testContactPhones(){
    app.goTo().contactPage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getHome(),equalTo(cleaned(contactInfoFromEditForm.getHome())));
    assertThat(contact.getMobile(),equalTo(cleaned(contactInfoFromEditForm.getMobile())));
    assertThat(contact.getWork(),equalTo(cleaned(contactInfoFromEditForm.getWork())));
  }

  public String cleaned(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }

}
