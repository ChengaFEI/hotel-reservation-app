package api;

import service.*;
import model.*;
import java.util.List;
import java.util.ArrayList;

public class AdminResource {
    CustomerService customerService = CustomerService.initializeCustomerService();
    ReservationService reservationService = ReservationService.initializeReservationService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }
    public ArrayList<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }
    public ArrayList<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
    public ArrayList<Reservation> getAllReservations() {return reservationService.getAllReservations();}
}
