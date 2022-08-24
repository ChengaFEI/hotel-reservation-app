package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import model.*;

public class ReservationService {
    private static final ReservationService reservationService = new ReservationService();
    private static final ArrayList<IRoom> roomList = new ArrayList<>();
    private static final ArrayList<Reservation> reservationList = new ArrayList<>();

    private ReservationService() {}
    public static ReservationService initializeReservationService() {
        return reservationService;
    }

    public void addRoom(IRoom room) {
        roomList.add(room);
    }
    public IRoom getARoom(String roomId) {
        IRoom result = null;
        for (IRoom room: roomList) {
            if (room.getRoomNumber().equals(roomId)) result = room;
        }
        return result;
    }
    public ArrayList<IRoom> getAllRooms() {
        return roomList;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }
    public ArrayList<IRoom> findRooms(Date checkInDate, Date checkOutDate, int extendedDays) {
        ArrayList<IRoom> result = new ArrayList<>();

        for (int i = 0; i <= extendedDays; i++) {
            Date[] extendedDates1 = minusDates(checkInDate, checkOutDate, i);
            Date[] extendedDates2 = plusDates(checkInDate, checkOutDate, i);

            for (IRoom room : roomList) {
                if (!result.contains(room)) {
                    boolean isReserved = reservedOrNot(room, extendedDates1[0], extendedDates1[1]) &&
                            reservedOrNot(room, extendedDates2[0], extendedDates2[1]);
                    if (!isReserved) result.add(room);
                }
            }
        }

        return result;
    }

    public ArrayList<IRoom> findRooms(Date checkInDate, Date checkOutDate, boolean freeOrNot, int extendedDays) {
        ArrayList<IRoom> result = new ArrayList<>();

        for (int i = 0; i <= extendedDays; i++) {
            Date[] extendedDates1 = minusDates(checkInDate, checkOutDate, i);
            Date[] extendedDates2 = plusDates(checkInDate, checkOutDate, i);

            for (IRoom room : roomList) {
                Double roomPrice = room.getRoomPrice();
                if ((roomPrice==0d) == freeOrNot && !result.contains(room)) {
                    boolean isReserved = reservedOrNot(room, extendedDates1[0], extendedDates1[1]) &&
                            reservedOrNot(room, extendedDates2[0], extendedDates2[1]);
                    if (!isReserved) result.add(room);
                }
            }
        }
        return result;
    }

    //Check whether any reservation conflicts.
    public boolean reservedOrNot(IRoom room, Date checkInDate, Date checkOutDate) {
        String roomNumber = room.getRoomNumber();
        boolean isReserved = false;

        for (Reservation reservation : reservationList) {
            // Check whether the reservation conflicts with the check-in and check-out date.
            IRoom reservedRoom = reservation.getRoom();
            String reservedRoomNumber = reservedRoom.getRoomNumber();
            Date reservedRoomCheckInDate = reservation.getCheckInDate();
            Date reservedRoomCheckOutDate = reservation.getCheckOutDate();
            boolean isDateOverlapped = reservedRoomCheckInDate.compareTo(checkOutDate)<0 &&
                    reservedRoomCheckOutDate.compareTo(checkInDate)>0;
            boolean isNumberEqual = roomNumber.equals(reservedRoomNumber);
            if (isNumberEqual && isDateOverlapped) {
                isReserved = true;
                break;
            }
        }
        return isReserved;
    }

    Date[] minusDates(Date checkInDate, Date checkOutDate, int extendedDays) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(checkInDate);
        cal1.add(Calendar.DATE, -extendedDays);
        checkInDate = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(checkOutDate);
        cal2.add(Calendar.DATE, -extendedDays); //minus number would decrement the days
        checkOutDate = cal2.getTime();

        Date currentDate = new Date();
        if (currentDate.compareTo(checkInDate)>0) {
            long timeDiff = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            int daysDiff = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            checkInDate = currentDate;
            Calendar cal3 = Calendar.getInstance();
            cal3.setTime(checkInDate);
            cal3.add(Calendar.DATE, daysDiff);
            checkOutDate = cal3.getTime();
        }

        return new Date[]{checkInDate, checkOutDate};
    }

    Date[] plusDates(Date checkInDate, Date checkOutDate, int extendedDays) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(checkInDate);
        cal1.add(Calendar.DATE, extendedDays);
        checkInDate = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(checkOutDate);
        cal2.add(Calendar.DATE, extendedDays); //minus number would decrement the days
        checkOutDate = cal2.getTime();

        return new Date[]{checkInDate, checkOutDate};
    }

    public ArrayList<Reservation> getCustomerReservation(Customer customer) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getCustomer().equals(customer)) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }
    public void printAllReservation() {
        for (Reservation reservation: reservationList) {
            System.out.println(reservation);
            System.out.println("-".repeat(30));
        }
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservationList;
    }
}
