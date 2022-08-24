package api;

import service.*;
import model.*;
import java.util.Date;
import java.util.ArrayList;

public class HotelResource {
    CustomerService customerService = CustomerService.initializeCustomerService();
    ReservationService reservationService = ReservationService.initializeReservationService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName) throws IllegalArgumentException{
        customerService.addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public ArrayList<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomerReservation(customer);
    }
    public ArrayList<IRoom> findARoom(Date checkInDate, Date checkOutDate, int extendedDays) {
        return reservationService.findRooms(checkInDate, checkOutDate, extendedDays);
    }
    public ArrayList<IRoom> findARoom(Date checkInDate, Date checkOutDate, boolean freeOrNot, int extendedDays) {
        return reservationService.findRooms(checkInDate, checkOutDate, freeOrNot, extendedDays);
    }
    public boolean reservedOrNot(IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reservedOrNot(room, checkInDate, checkOutDate);
    }
}
