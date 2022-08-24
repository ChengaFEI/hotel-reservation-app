package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }
    public IRoom getRoom() {
        return this.room;
    }
    public Date getCheckInDate() {
        return this.checkInDate;
    }
    public Date getCheckOutDate() {
        return this.checkOutDate;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setRoom(IRoom room) {
        this.room = room;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    @Override
    public String toString() {
        return "Reservation Information:\n" +
                "Customer:\n" +
                "   Name: " + this.customer.getFirstName() + " " + this.customer.getLastName() + "\n" +
                "   Email: " + this.customer.getEmail() + "\n" +
                "Room:\n" +
                "   Number: " + this.room.getRoomNumber() + ".\n" +
                "   Price: " + this.room.getRoomPrice() + "\n" +
                "   Type: " + this.room.getRoomType() + "\n" +
                "Date:\n" +
                "   Check In Date: " + this.checkInDate + ".\n" +
                "   Check Out Date: " + this.checkOutDate + ".";
    }
    @Override
    public boolean equals(Object o) {
        if (o==this) return true;
        if (!(o instanceof Reservation reservationO)) return false;
        boolean customerEquals = (this.customer==null && reservationO.customer==null ||
                this.customer!=null && this.customer.equals(reservationO.customer));
        boolean roomEquals = (this.room==null && reservationO.room==null ||
                this.room!=null && this.room.equals(reservationO.room));
        boolean checkInDateEquals = (this.checkInDate==null && reservationO.checkInDate==null ||
                this.checkInDate!=null && this.checkInDate.equals(reservationO.checkInDate));
        boolean checkOutDateEquals = (this.checkOutDate==null && reservationO.checkOutDate==null ||
                this.checkOutDate!=null && this.checkOutDate.equals(reservationO.checkOutDate));
        return customerEquals && roomEquals && checkInDateEquals && checkOutDateEquals;
    }
    @Override
    public int hashCode() {
        int result = 10;
        if (customer!=null) result = result*19 + customer.hashCode();
        if (room!=null) result = result*19 + room.hashCode();
        if (checkInDate!=null) result = result*19 + checkInDate.hashCode();
        if (checkOutDate!=null) result = result*19 + checkOutDate.hashCode();
        return result;
    }
}
