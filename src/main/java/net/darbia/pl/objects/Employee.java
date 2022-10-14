package net.darbia.pl.objects;

public class Employee {

  private int id;
  private String firstName;
  private String lastName;

  public Employee(final int id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public int getId() {
    return this.id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return this.firstName + " " + this.lastName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final Employee employee)) {
      return false;
    }
    return this.getId() == employee.getId() && this.getFirstName().equals(employee.getFirstName())
        && this.getLastName().equals(employee.getLastName());
  }

  @Override
  public int hashCode() {
    int result = this.getId();
    result = 31 * result + this.getFirstName().hashCode();
    result = 31 * result + this.getLastName().hashCode();
    return result;
  }
}
