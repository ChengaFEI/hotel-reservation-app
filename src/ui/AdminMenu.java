package ui;

import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminMenu {
    private final String item1 = "1. See all Customers";
    private final String item2 = "2. See all Rooms";
    private final String item3 = "3. See all Reservations";
    private final String item4 = "4. Add a Room";
    private final String item5 = "5. Add First Test Data";
    private final String item6 = "6. Add Second Test Data";
    private final String item7 = "7. Back to Main Menu";

    private final Customer testCustomer1 = new Customer("Meimei", "Wong", "mmWong@gmail.com");
    private final Customer testCustomer2 = new Customer("Lei", "Li", "leili@outlook.com");
    private final ArrayList<Customer> testCustomers = new ArrayList<>();

    private final IRoom testRoom1 = new Room("100", 0d, RoomType.SINGLE);
    private final IRoom testRoom2 = new Room("200", 100d, RoomType.SINGLE);
    private final IRoom testRoom3 = new Room("300", 150d, RoomType.DOUBLE);
    private final IRoom testRoom4 = new Room("400", 1000d, RoomType.DOUBLE);
    private final ArrayList<IRoom> testRooms1 = new ArrayList<>();
    private final ArrayList<IRoom> testRooms2 = new ArrayList<>();

    private final Reservation testReservation1 = new Reservation(testCustomer1, testRoom1,
            new Date(122, Calendar.SEPTEMBER, 15),
            new Date(122, Calendar.SEPTEMBER, 20));
    private final Reservation testReservation2 = new Reservation(testCustomer2, testRoom3,
            new Date(122, Calendar.OCTOBER, 1),
            new Date(122, Calendar.OCTOBER, 7));
    private final Reservation testReservation3 = new Reservation(testCustomer1, testRoom1,
            new Date(122, Calendar.SEPTEMBER, 20),
            new Date(122, Calendar.SEPTEMBER, 25));
    private final Reservation testReservation4 = new Reservation(testCustomer2, testRoom1,
            new Date(122, Calendar.OCTOBER, 1),
            new Date(122, Calendar.OCTOBER, 7));
    private final ArrayList<Reservation> testReservations1 = new ArrayList<>();
    private final ArrayList<Reservation> testReservations2 = new ArrayList<>();

    public AdminMenu() {
        this.testCustomers.add(testCustomer1);
        this.testCustomers.add(testCustomer2);

        this.testRooms1.add(testRoom1);
        this.testRooms1.add(testRoom2);
        this.testRooms1.add(testRoom3);
        this.testRooms1.add(testRoom4);

        this.testRooms2.add(testRoom1);

        this.testReservations1.add(testReservation1);
        this.testReservations1.add(testReservation2);

        this.testReservations2.add(testReservation3);
        this.testReservations2.add(testReservation4);
    }

    public ArrayList<Customer> getTestCustomers() {
        return this.testCustomers;
    }

    public ArrayList<IRoom> getTestRooms1() {
        return this.testRooms1;
    }

    public ArrayList<IRoom> getTestRooms2() {
        return this.testRooms2;
    }

    public ArrayList<Reservation> getTestReservations1() {
        return this.testReservations1;
    }

    public ArrayList<Reservation> getTestReservations2() {
        return this.testReservations2;
    }

    public String toString() {
        return "Admin Menu\n" +
                "-".repeat(30) + "\n" +
                item1 + "\n" +
                item2 + "\n" +
                item3 + "\n" +
                item4 + "\n" +
                item5 + "\n" +
                item6 + "\n" +
                item7 + "\n" +
                "-".repeat(30);
    }
}
