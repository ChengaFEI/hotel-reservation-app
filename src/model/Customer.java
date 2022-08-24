package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;
        Pattern pattern = Pattern.compile("^(.+)@(.+).(.+)$");
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {return this.firstName;};

    public String getLastName() {return this.lastName;};

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Customer Information:\nFirst Name: " + this.firstName + ".\n" +
                "Last Name: " + this.lastName + ".\n" +
                "Email: " + this.email + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (o==this) return true;
        if (!(o instanceof Customer customerO)) return false;
        boolean firstNameEquals = (this.firstName==null && customerO.firstName==null ||
                this.firstName!=null & this.firstName.equals(customerO.firstName));
        boolean lastNameEquals = (this.lastName==null && customerO.lastName==null ||
                this.lastName!=null && this.lastName.equals(customerO.lastName));
        boolean emailEquals = (this.email==null && customerO.email==null ||
                this.email!=null && this.email.equals(customerO.email));
        return firstNameEquals && lastNameEquals & emailEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (firstName!=null) result = result*31 + firstName.hashCode();
        if (lastName!=null) result = result*31 + lastName.hashCode();
        if (email!=null) result = result*31 + email.hashCode();
        return result;
    }
}
