public class Person {
  private String firstName;
  private String lastName;
  private String address;
  private String passport;
  private String info = "Some standard info";

  public Person() {}

  public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setPassport(String passport) {
    this.passport = passport;
  }

  public String getPassport() {
    return passport;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getInfo() {
    return info;
  }
}