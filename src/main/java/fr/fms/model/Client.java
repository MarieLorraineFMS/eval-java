package fr.fms.model;

public class Client {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String address;
    private final String phone;

    public Client(int id, String firstName, String lastName, String email, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Client(String firstName, String lastName, String email, String address, String phone) {
        this(0, firstName, lastName, email, address, phone);
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