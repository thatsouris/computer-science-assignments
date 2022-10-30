/*
 * Aidan O'Quinn
 * October 23rd, 2022
 * 
 * The main class of the dinner party.
 * Initiates all guests and prints their dialogue for the party.
 */

import java.util.ArrayList;

public class DinnerParty {

    /**
     * Begins the conversation with all of the guests
     * within the given arraylist.
     * 
     * @param guests The ArrayList, containing all of the guests for the dinner party.
     */
    public static void startConversation(ArrayList<Guest> guests) {
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            System.out.print(guest.getName() + " : ");
            System.out.println(guest);
            System.out.println();
        }
    }

    /**
     * Inits all of the guest objects and begins a conversation.
     */
    public static void main(String[] args) {
        ArrayList<Guest> guests = new ArrayList<Guest>();
        guests.add(new Guest(
                "Pierre Bézier",
                "Engineer",
                "Automobile Engineer",
                112,
                "I really helped to optimize computer graphics by inventing Bezier curves",
                "I also never even intended to help because I am an engineer!"));
        guests.add(new Guest(
                "Elon Musk",
                "CEO",
                "Entrepreneur",
                51,
                "Rocketry and electric vehicles have been completely revolutionized",
                "I'm also the richest person on Earth from being the CEO of Tesla and SpaceX!"));
        guests.add(new Guest(
                "Barbara Liskov",
                "Computer Scientist",
                "Data Scientist",
                82,
                "I've become one of the most notable computer scientists because I created the language that insired Java syntax",
                "I have also developed the Liskov substitution principle; in which a class can have sub-classes."));
        guests.add(new Guest(
                "Mark Zuckerburg",
                "CEO",
                "Computer Scientist",
                38,
                "I created one of the biggest social media platforms online",
                "I have also used algorithms to collect information about my users for improving advertising!"));
        guests.add(new Guest(
                "Brendan Eich",
                "Computer Scientist",
                "Computer Scientist",
                61,
                "I created the most popular web-based scripting language, JavaScript",
                "JavaScript is one of the most easy-to-use languages and the syntax is easy to learn for beginners."));
        guests.add(new Guest(
                "Larry Page",
                "CEO",
                "Computer Scientist",
                49,
                "I helped to create the most used search engine in the world",
                "It utilzes algorithms to search for what you want, very quickly!"));
        guests.add(new Guest(
                "Guido Van Rossum",
                "Computer Scientist",
                "Computer Scientist",
                66,
                "I'm known for creating the most well-known programming language, Python",
                "millions of people use python for many purposes."));
        guests.add(new Guest(
                "Tim Berners-Lee",
                "Professor",
                "Computer Scientist",
                67,
                "I invented the world wide web (WWW) to help people read information online instead of using books",
                "I currently teach computer science concepts to students in MIT and in the University of Oxford."));

        startConversation(guests);
    }

}
