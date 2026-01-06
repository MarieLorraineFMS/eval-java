package fr.fms.model;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;

    public Client(int id, String firstName, String lastName, String email, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return id + " | " + firstName + " | " + lastName + " | " + email + " | " + address + " | " + phone;
    }
}