package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double roomPrice;
    private RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }@Override
    public RoomType getRoomType() {
        return roomType;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    @Override
    public boolean isFree() {
        return roomPrice==0d;
    }
    @Override
    public String toString() {
        return "Room Information:\n" +
                "-".repeat(30) + "\n" +
                "Number: " + this.roomNumber + "\n" +
                "Price: " + this.roomPrice + "\n" +
                "Type: " + this.roomType;
    }
    @Override
    public boolean equals(Object o) {
        if (o==this) return true;
        if (!(o instanceof Room roomO)) return false;
        boolean roomNumberEquals = (this.roomNumber==null && roomO.roomNumber==null ||
                this.roomNumber!=null && this.roomNumber.equals(roomO.roomNumber));
        boolean roomTypeEquals = (this.roomType==null && roomO.roomType==null ||
                this.roomType!=null && this.roomType.equals(roomO.roomType));
        boolean roomPriceEquals = (this.roomPrice==null && roomO.roomPrice==null ||
                this.roomPrice!=null && this.roomPrice.equals(roomO.roomPrice));
        return roomNumberEquals && roomTypeEquals && roomPriceEquals;
    }
    @Override
    public int hashCode() {
        int result = 10;
        if (roomNumber!=null) result = result*19 + roomNumber.hashCode();
        if (roomType!=null) result = result*19 + roomType.hashCode();
        if (roomPrice!=null) result = result*19 + roomPrice.hashCode();
        return result;
    }
}
