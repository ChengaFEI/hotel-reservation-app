package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0d, roomType);
    }

    @Override
    public boolean isFree() {return true;}
    @Override
    public String toString() {
        return "Room Information:\nNumber: " + this.getRoomNumber() + "\n" +
                "Price: Free!" + "\n" +
                "Type: " + this.getRoomType();
    }
}
