package fr.fms.model;

/**
 * Represents a client.
 *
 * A Client is an immutable object:
 * - once created, it cannot be modified
 *
 */
public class Client {

    /** Database identifier (0 means "not persisted yet") */
    private final int id;

    /** Client first name */
    private final String firstName;

    /** Client last name */
    private final String lastName;

    /** Client email address */
    private final String email;

    /** Client postal address */
    private final String address;

    /** Client phone number */
    private final String phone;

    /**
     * Full constructor.
     * Used when loading a client from db.
     *
     * @param id        client identifier
     * @param firstName client first name
     * @param lastName  client last name
     * @param email     client email address
     * @param address   client postal address
     * @param phone     client phone number
     */
    public Client(int id, String firstName, String lastName, String email, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Lightweight constructor.
     * Used when creating a new client before persistence.
     *
     * @param firstName client first name
     * @param lastName  client last name
     * @param email     client email address
     * @param address   client postal address
     * @param phone     client phone number
     */
    public Client(String firstName, String lastName, String email, String address, String phone) {
        this(0, firstName, lastName, email, address, phone);
    }

    /**
     * @return client identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return client first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return client last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return client email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return client postal address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return client phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Simple string representation.
     *
     * @return string describing client
     */
    @Override
    public String toString() {
        return id + " | "
                + firstName + " | "
                + lastName + " | "
                + email + " | "
                + address + " | "
                + phone;
    }
}
