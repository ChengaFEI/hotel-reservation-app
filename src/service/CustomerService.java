package service;

import model.Customer;
import java.util.ArrayList;

public class CustomerService {
    private static final CustomerService customerService = new CustomerService();
    private static final ArrayList<Customer> customerList = new ArrayList<>();

    private CustomerService() {}
    public static CustomerService initializeCustomerService() {return customerService;}

    public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException{
        customerList.add(new Customer(firstName, lastName, email));
    }
    public Customer getCustomer(String customerEmail) {
        Customer result = null;
        for (Customer customer: customerList) {
            if (customer.getEmail().equals(customerEmail)) {
                result = customer;
            }
        }
        return result;
    }
    public ArrayList<Customer> getAllCustomers() {
        return customerList;
    }
}
