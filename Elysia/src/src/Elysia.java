package src;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Elysia {
	private int Health = 100;
    private int MaxHealth = 100;
    private int Armor = 0;
    
    private Scanner scan = new Scanner(System.in);
    
    private String PlayerName;

    private int Gold = 10;

    private HashMap<String, Integer> Skills = new HashMap<String, Integer>();
    private HashMap<String, Integer> Inventory = new HashMap<String, Integer>();
	
	public Elysia() { }
	
	public static void speak(String name, String text) {
		String[] split = text.split("");
		
		System.out.print(name.toUpperCase() + " > \"");
		
		
		for (int i = 0; i < split.length; i++) {
			System.out.print(split[i]);
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\"\n");
	}
	
	public static void out(String str) {
		System.out.println(">> " + str);
	}
	
	public void upgrade() {
		
	}
	
	public String promptUser() {
		System.out.print(">> ");
		String entry = scan.next();
		entry = entry.toLowerCase();
		
		if (entry == "upgrade") {
			upgrade();
		}
		
		while (entry.length() != 1) {
			out("Not a valid command!");
		}
		
		return entry;
	}
	
	public void BeginGame() {
		System.out.println("-- WELCOME TO ELYSIA! --\n");
		out("You are a young warrior and it is your birthday today! It is time for your to embark of \n" +
		"your journey to become the DRAGON SLAYER. You have just turned 18 and with your trusty SWORD,\n" +
		" you are prepared to take on any enemy!");
		out("Select a name for your character \n>> ");
		PlayerName = scan.next().toUpperCase();
		out("Welcome to Elysia, " + PlayerName + "!");
		
		System.out.println("You are able to upgrade your equipment at any time by typing [upgrade].");
		out(PlayerName + "! You are at the center of town in ELYSIA. It is a heavily populated area with \n" +
		"many districts. The sun is beginning to set in the distance. It is currently 912 CE in the summer.\n" +
		"You see the buildings towering over you, creating shadows on top of you. On your waist, you have \n" +
		"your basic sword, which deals 1 damage per hit.");
		out("You should probaly get somewhere safe before the sun sets. With that, time to get this journey\n" +
		"started!\n");
		
		Town();
	}
	
	public void Shop() {
		speak("shopkeeper", "Welcome back, " + PlayerName + ". Do you need something soldier?");
		out("Enter [t] to talk to the shopkeeper or enter [b] to buy things");
		String entry = promptUser();
		
		switch(entry) {
			case "t":
				speak(PlayerName, "What's on your mind?");
				speak("shopkeeper", "Hmm, well not much happens these days other then the typical goblin attack. ")
		}
	}
	
	public void Wilderness() {
		
	}
	
	public void Town() {
		out("You look around you. You see a shop [s], your house [h], and the wilderness [w]. Where should you venture to?");
		String entry = promptUser();
		
		switch(entry) {
			case "s":
				Shop();
				break;
			case "h":
				out("You rest and replenish all of your health!");
				Health = MaxHealth;
				Town();
				break;
			case "w":
				Wilderness();
				break;
			default:
				Town();
				break;
		}
	}
}
