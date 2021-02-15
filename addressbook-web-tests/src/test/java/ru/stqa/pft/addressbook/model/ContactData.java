package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String telephoneMobile;
  private final String email;
  private final String day;
  private final String month;
  private final String year;
  private final String groupName;
  private final int groupId;

  public ContactData(String firstName, String middleName, String lastName, String nickname, String title, String company, String Address, String telephoneMobile, String email, String day, String month, String year, String groupName, int groupId) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    address = Address;
    this.telephoneMobile = telephoneMobile;
    this.email = email;
    this.day = day;
    this.month = month;
    this.year = year;
    this.groupName = groupName;
    this.groupId = groupId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getTelephoneMobile() {
    return telephoneMobile;
  }

  public String getEmail() {
    return email;
  }

  public String getDay() {
    return day;
  }

  public String getMonth() {
    return month;
  }

  public String getYear() {
    return year;
  }

  public String getGroupName() {
    return groupName;
  }

  public int getGroupId() {
    return groupId;
  }
}
