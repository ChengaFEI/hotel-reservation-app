package ui;

public class MainMenu {
    private final String item1 = "1. Find and reserve a room.";
    private final String item2 = "2. See my reservations";
    private final String item3 = "3. Create an Account";
    private final String item4 = "4. Admin";
    private final String item5 = "5. Exit";

    public String toString() {
        return "Main Menu\n" +
                "-".repeat(30) + "\n" +
                item1 + "\n" +
                item2 + "\n" +
                item3 + "\n" +
                item4 + "\n" +
                item5 + "\n" +
                "-".repeat(30);
    }
}
