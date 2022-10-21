import java.util.ArrayList;

public class DinnerParty {
    public static void startConversation(ArrayList<Guest> guests) {
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            System.out.print(guest.getName() + " : ");
            System.out.println(guest);
        }
    }

    public static void main(String[] args) {
        ArrayList<Guest> guests = new ArrayList<Guest>();
        guests.add(new Guest(
                "Pierre Bézier",
                "Engineer",
                "Automobile Engineer",
                112,
                "I really helped to optimize computer graphics by inventing Bezier curves",
                "I also never even intended to help!"));

        startConversation(guests);
    }

}
