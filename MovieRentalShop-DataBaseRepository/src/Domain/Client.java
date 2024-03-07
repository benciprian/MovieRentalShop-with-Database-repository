package Domain;

import java.util.Objects;
public class Client extends BaseEntity<Long>{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String city;

    public Client(long id, String firstName, String lastName, String email, String phoneNumber, String city) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public Client(Long id   ) {
        super(id);
    }
// Getters and setters for the attributes


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client that = (Client) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getCity(), that.getCity());
    }

    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getCity());
    }

    @Override
    public String toString() {
        return "Client ID: " + super.getId()      + ", " +
                "Name: " + firstName + " " + lastName + "," +
                "Email: " + email + "," +
                "Phone Number: " + phoneNumber + ", " +
                "City: " + city;
    }


}

