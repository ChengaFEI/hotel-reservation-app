package ui;

import java.util.Scanner;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import api.*;
import model.*;

public class HotelApplication {
    private static final MainMenu mainMenu = new MainMenu();
    private static final AdminMenu adminMenu = new AdminMenu();

    private static final AdminResource adminResourceAPI = new AdminResource();
    private static final HotelResource hotelResourceAPI = new HotelResource();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            exit = selectMainMenuOption(scanner);
        }
    }

    public static boolean selectMainMenuOption(Scanner scanner) {
        System.out.println("Welcome to the Hotel Reservation Application!");
        int itemNo;
        do
        {
            System.out.println(mainMenu);
            itemNo = validateItemNo(scanner, 1, 5);
            switch (itemNo) {
                case 1 -> findAndReserveARoom(scanner);
                case 2 -> seeMyReservation(scanner);
                case 3 -> createAnAccount(scanner);
                case 4 -> {
                    return selectAdminMenuOption(scanner);
                }
            }
        } while(itemNo!=5);
        return true;
    }

    public static boolean selectAdminMenuOption(Scanner scanner) {
        System.out.println("Welcome to the Administration System!");
        int itemNo;
        do
        {
            System.out.println(adminMenu);
            itemNo = validateItemNo(scanner, 1, 7);
            switch (itemNo) {
                case 1 -> seeAllCustomers();
                case 2 -> seeAllRooms();
                case 3 -> seeAllReservations();
                case 4 -> addARoom(scanner);
                case 5 -> addTestData1();
                case 6 -> addTestData2();
            }
        } while (itemNo!=7);
        return selectMainMenuOption(scanner);
    }

    public static int validateItemNo(Scanner scanner, int minNo, int maxNo) {
        int itemNo = 0;
        while (itemNo<minNo || itemNo>maxNo) {
            System.out.printf("Please select a number for the menu option: (%d-%d)\n", minNo, maxNo);
            String itemStr = scanner.nextLine();
            try {
                itemNo = Integer.parseInt(itemStr);
            }
            catch (NumberFormatException e) {
                itemNo = validateItemNo(scanner, minNo, maxNo);
            }
        }
        return itemNo;
    }

    public static void createAnAccount(Scanner scanner) {
        System.out.println("Email: (Format: name@domain.com)");
        String email = scanner.nextLine().toLowerCase();
        System.out.println("First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Last Name:");
        String lastName = scanner.nextLine();
        try {
            hotelResourceAPI.createACustomer(email, firstName, lastName);
        }
        catch (IllegalArgumentException e) {
            createAnAccount(scanner);
        }
    }

    public static Customer getOrCreateAnAccount(Scanner scanner) {
        String accountOrNot = "";
        System.out.println("Do you have an account with us? (Y-yes, N-no)");
        while (!accountOrNot.equals("Y") && !accountOrNot.equals("N")) {
            System.out.println("(Please enter a valid word.)");
            accountOrNot = scanner.nextLine().toUpperCase();
        }
        if (accountOrNot.equals("N")) createAnAccount(scanner);
        Customer customer = null;
        System.out.println("Email: (Format: name@domain.com)");
        while (customer==null) {
            System.out.println("(Please enter a valid email.)");
            String email = scanner.nextLine().toLowerCase();
            customer = hotelResourceAPI.getCustomer(email);
        }
        return customer;
    }

    public static void findAndReserveARoom(Scanner scanner) {
        // Find rooms.
        Date[] dates = validateDate(scanner);
        ArrayList<IRoom> rooms = findRooms(scanner, dates);
        // Reserve a room.
        reserveARoom(scanner, dates, rooms);
    }

    public static ArrayList<IRoom> findRooms(Scanner scanner, Date[] dates) {
        String freeOrNotStr = "";
        while (!freeOrNotStr.equals("F") && !freeOrNotStr.equals("P") && !freeOrNotStr.equals("A")) {
            System.out.println("Search for paid or free rooms? (F-free, P-paid, A-all)");
            freeOrNotStr = scanner.nextLine().toUpperCase();
        }

        boolean noAvailableRooms = true;
        ArrayList<IRoom> rooms = new ArrayList<>();
        int extendedDays = 0;
        while (noAvailableRooms) {
            switch (freeOrNotStr) {
                case "F" -> rooms = hotelResourceAPI.findARoom(dates[0], dates[1], true, extendedDays);
                case "P" -> rooms = hotelResourceAPI.findARoom(dates[0], dates[1], false, extendedDays);
                case "A" -> rooms = hotelResourceAPI.findARoom(dates[0], dates[1], extendedDays);
            }
            if (rooms.size()==0) {
                extendedDays = Integer.parseInt(validatePositiveInteger(scanner,
                        "No Available Rooms Found!\nPlease enter number of days extension: (Positive integer only)"));
            }
            else {
                noAvailableRooms = false;
            }
        }
        System.out.println("Available Rooms Found!");
        System.out.println("-".repeat(30));

        // Print all rooms.
        System.out.println("Available Rooms:");
        System.out.println("-".repeat(30));
        for (IRoom room : rooms) {
            // Display rooms information.
            System.out.println(room);
            // Display all reserved days for this room.
            System.out.println("Reserved dates:");
            for (Reservation reservation : adminResourceAPI.getAllReservations()) {
                IRoom reservedRoom = reservation.getRoom();
                if (reservedRoom.equals(room)) {
                    Date reservedCheckInDate = reservation.getCheckInDate();
                    Date reservedCheckOutDate = reservation.getCheckOutDate();
                    System.out.println("   Check-in Date: " + reservedCheckInDate);
                    System.out.println("   Check-out Date: " + reservedCheckOutDate);
                }
            }
            System.out.println("-".repeat(30));
        }
        return rooms;
    }

    public static void reserveARoom(Scanner scanner, Date[] dates, ArrayList<IRoom> availableRooms) {
        String bookOrNot = "";
        System.out.println("Would you like to book a room? (Y-yes, N-no)");
        while (!bookOrNot.equals("Y") && !bookOrNot.equals("N")) {
            System.out.println("(Please enter a valid word.)");
            bookOrNot = scanner.nextLine().toUpperCase();
        }

        // Book a room.
        if (bookOrNot.equals("Y")) {
            Customer customer = getOrCreateAnAccount(scanner);
            System.out.println("What room number would you like to reserve:");
            IRoom room = null;
            while (room==null || !availableRooms.contains(room)) {
                System.out.println("(Please enter a valid room number.)");
                String roomNumber = scanner.nextLine();
                room = hotelResourceAPI.getRoom(roomNumber);
            }

            // Scenario: original dates are unavailable, need to enter new dates.
            boolean needNewDates = hotelResourceAPI.reservedOrNot(room, dates[0], dates[1]);
            while (needNewDates) {
                System.out.println("Please specify the revised dates.");
                dates = validateDate(scanner);
                needNewDates = hotelResourceAPI.reservedOrNot(room, dates[0], dates[1]);
            }

            System.out.println(hotelResourceAPI.bookARoom(customer.getEmail(), room, dates[0], dates[1]));
        }
    }

    public static Date[] validateDate(Scanner scanner) {
        Date currentDate = new Date();
        System.out.println("CheckIn Date: (Format: mm/dd/yyyy)\n(Must enter a date after today.)");
        String checkInStr = scanner.nextLine();
        System.out.println("CheckOut Date: (Format: mm/dd/yyyy)\n(Must enter a date after the check-in day.)");
        String checkOutStr = scanner.nextLine();
        try {
            Date checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkInStr);
            Date checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkOutStr);
            if (!(checkInDate.compareTo(currentDate)>=0 && checkOutDate.compareTo(checkInDate)>0)) {
                System.out.println("*".repeat(60));
                System.out.println("Incorrect order! (check-out date > check-in date >= today)");
                System.out.println("*".repeat(60));
                return validateDate(scanner);
            }
            return new Date[]{checkInDate, checkOutDate};
        }
        catch (ParseException e) {
            System.out.println("*".repeat(60));
            System.out.println("Invalid format! (Format: mm/dd/yyyy)");
            System.out.println("*".repeat(60));
            return validateDate(scanner);
        }

    }

    public static void seeMyReservation(Scanner scanner) {
        // Log in to the customer account.
        Customer customer = getOrCreateAnAccount(scanner);
        // Return and display the reservations.
        System.out.println("All your reservations:");
        System.out.println("-".repeat(30));
        for (Reservation reservation : hotelResourceAPI.getCustomersReservations(customer.getEmail())) {
            System.out.println(reservation);
            System.out.println("-".repeat(30));
        }
    }

    public static void seeAllCustomers() {
        System.out.println("All registered customers:");
        System.out.println("-".repeat(30));
        for (Customer customer : adminResourceAPI.getAllCustomers()) {
            System.out.println(customer);
            System.out.println("-".repeat(30));
        }
    }

    public static void seeAllRooms() {
        System.out.println("All rooms:");
        System.out.println("-".repeat(30));
        for (IRoom room : adminResourceAPI.getAllRooms()) {
            System.out.println(room);
            System.out.println("-".repeat(30));
        }
    }

    public static void seeAllReservations() {
        System.out.println("All current reservations:");
        System.out.println("-".repeat(30));
        adminResourceAPI.displayAllReservations();
    }

    public static void addARoom(Scanner scanner) {
        // RoomNumber: Check if it is numeric.
        String captionRoomNumber = "Please enter the room number: (Use digits only)";
        ArrayList<IRoom> allRooms = adminResourceAPI.getAllRooms();
        ArrayList<String> allRoomNumbers = new ArrayList<>();
        for (IRoom room : allRooms) {
            allRoomNumbers.add(room.getRoomNumber());
        }
        String roomNumber = validateRoomNotSetYet(scanner, captionRoomNumber, allRoomNumbers);
        // RoomPrice: free or not?
        String captionRoomPrice = "Please enter the room price: (Use digits only)";
        double roomPrice = Double.parseDouble(validateNumber(scanner, captionRoomPrice));
        // RoomType: SINGLE or DOUBLE
        String roomTypeStr = "";
        while (!roomTypeStr.equals("S") && !roomTypeStr.equals("D")) {
            System.out.println("Please enter the room type: (S-single bed room, D-double bed room)");
            roomTypeStr = scanner.nextLine().toUpperCase();
        }
        RoomType roomType;
        if (roomTypeStr.equals("S")) roomType = RoomType.SINGLE;
        else roomType = RoomType.DOUBLE;
        // Create a room object.
        IRoom room;
        if (roomPrice==0d) room = new FreeRoom(roomNumber, roomType);
        else room = new Room(roomNumber, roomPrice, roomType);
        // Add the room to the roomList.
        ArrayList<IRoom> roomList = new ArrayList<>();
        roomList.add(room);
        adminResourceAPI.addRoom(roomList);
    }

    private static String validateNumber(Scanner scanner, String caption) {
        // Ensure the input string is numeric.
        System.out.println(caption);
        String numberStr = scanner.nextLine();
        try {
            Double.parseDouble(numberStr);
        }
        catch (RuntimeException e) {
            return validateNumber(scanner, caption);
        }
        return numberStr;
    }

    private static String validateRoomNotSetYet(Scanner scanner, String caption, ArrayList<String> allRoomNumbers) {
        String roomNumber = validateNumber(scanner, caption);
        // RoomNumber: Check whether the room has been added before.
        boolean isRoomSetBefore = true;
        while (isRoomSetBefore) {
            for (String existedRoomNumber : allRoomNumbers) {
                if (existedRoomNumber.equals(roomNumber)) {
                    System.out.println("*".repeat(60));
                    System.out.println("The room has been entered before! Enter another room.");
                    System.out.println("List of entered room number:");
                    for (String number : allRoomNumbers) {
                        System.out.print(number + " ");
                    }
                    System.out.println("\n" + "*".repeat(60));
                    return validateRoomNotSetYet(scanner, caption, allRoomNumbers);
                }
            }
            isRoomSetBefore = false;
        }
        return roomNumber;
    }

    public static String validatePositiveInteger(Scanner scanner, String caption) {
        // Ensure the input string is numeric.
        System.out.println(caption);
        String intStr = scanner.nextLine();
        try {
            if (Integer.parseInt(intStr)<=0) return validatePositiveInteger(scanner, caption);
        }
        catch (RuntimeException e) {
            return validatePositiveInteger(scanner, caption);
        }
        return intStr;
    }

    public static void addTestData1() {
        // Add customers.
        for (Customer customer: adminMenu.getTestCustomers()) {
            hotelResourceAPI.createACustomer(customer.getEmail(), customer.getFirstName(), customer.getLastName());
        }

        // Add rooms.
        adminResourceAPI.addRoom(adminMenu.getTestRooms1());

        // Add reservations.
        for (Reservation reservation: adminMenu.getTestReservations1()) {
            Customer customer = reservation.getCustomer();
            hotelResourceAPI.bookARoom(customer.getEmail(), reservation.getRoom(),
                    reservation.getCheckInDate(), reservation.getCheckOutDate());
        }
    }
    public static void addTestData2() {
        // Add customers.
        for (Customer customer: adminMenu.getTestCustomers()) {
            hotelResourceAPI.createACustomer(customer.getEmail(), customer.getFirstName(), customer.getLastName());
        }

        // Add rooms.
        adminResourceAPI.addRoom(adminMenu.getTestRooms2());

        // Add reservations.
        for (Reservation reservation: adminMenu.getTestReservations2()) {
            Customer customer = reservation.getCustomer();
            hotelResourceAPI.bookARoom(customer.getEmail(), reservation.getRoom(),
                    reservation.getCheckInDate(), reservation.getCheckOutDate());
        }
    }
}
