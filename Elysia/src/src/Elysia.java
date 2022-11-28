package src;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import types.Mob;

/*
 * Elysia class
 * The main class for the game. Includes all methods for the choices.
 * 
 * @author Aidan O'Quinn
 *
 */

public class Elysia {
	public static boolean searchArray(Object[] array, Object object) {
		for(int i = 0; i < array.length; i++) {
			if (array[i].equals(object)) {
				return true;
			}
		}
		
		return false;
	}
	
	private int Health = 25;
    private int MaxHealth = 25;
    private int Armor = 0;
    private int damage = 1;
    
    private int SkillPoints = 0;
    
    private boolean isAttacking = false;
    private boolean firstTimeAttacking = true;
    private boolean hasTorch = true;
    
    private Scanner scan = new Scanner(System.in);
    
    private String PlayerName;
    	
	public Elysia() { }
	
	public void attack(Mob mob, boolean mobAttackedFirst) {
		isAttacking = true;
		System.out.println("--- BATTLE ! ---");
		out("Attacking: " + mob);
		if (firstTimeAttacking = true) {
			firstTimeAttacking = false;
			out("To attack, you must rotate the arrow by using [A] for counter-clockwise and [D] for clockwise!");
			out("The arrow needs to be pointing upwards [↑] and you type [W] to attack!");
			out("Each one of your attacks has a 10% chance to be a critical hit (x2 damage)");
			out("This is turn-based combat, meaning that you and your attackee switches turns to attack.");
		}
		System.out.println();
		
		boolean playersTurn = !mobAttackedFirst;
		
		while(mob.getHealth() != 0 && Health != 0) {
			System.out.println("[ "+PlayerName+" ] - " + Health + "/" + MaxHealth);
			System.out.println("[ "+mob+" ] - " + mob.getHealth() + "/" + mob.getMaxHealth());
			
			if(playersTurn == true) {
				out("It's your turn to attack!");
				out("Type [A] to turn the arrow CCW, [D] to turn CW, and [W] to attack!");
				
				int direction = (int) (Math.random() * 4d) + 1;
				boolean sent = false;
				
				while(sent == false) {
					String arrow;
					switch(direction) {
						case 1:
							arrow = "\\/";
							break;
						case 2:
							arrow = ">";
							break;
						case 3:
							arrow = "^";
							break;
						case 4:
							arrow = "<";
							break;
						default:
							arrow = "";
							break;
					}
					
					System.out.println("-- [ "+arrow+" ] --");
					
					String[] options = {"d", "a", "w"};
					String entry = promptUser(options);
					
					switch(entry) {
					case "d":
						direction -= 1;
						if (direction < 1) {
							direction = 4;
						}
						
						break;
					case "a":
						direction += 1;
						if (direction > 4) {
							direction = 1;
						}
						break;
					case "w":
						sent = true;
						if (direction == 3) {
							boolean crit = ((int) (Math.random() * 100) + 1) <= 10;
							if (crit == false) {
								System.out.println("You hit " + mob + " for " + damage + " damage!");
								mob.takeDamage(damage);
							} else {
								System.out.println("[CRITICAL] You hit " + mob + " for " + damage*2 + " damage!");
								mob.takeDamage(damage*2);
							}
						} else {
							out("You attacked and missed! (The arrow must be pointed upwards)");
						}
						break;
					}
				}
			} else {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(mob+" swings at you! Dealing "+mob.getDamage() + " damage");
				Health -= mob.getDamage();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			playersTurn = !playersTurn;
		}
		
		if (Health <= 0) {
			GameOver("Next time try landing your hits!");
			return;
		}
		
		if (mob.getHealth() == 0) {
			SkillPoints += mob.getReward();
			out("You have won the battle and have been awarded " + mob.getReward() + " skill points!");
		}
		
		isAttacking = false;
	}
	
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
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void out(String str) {
		System.out.println(">> " + str);
	}
	
	public void upgrade() {
		
	}
	
	public void GameOver(String deathMessage) {
		System.out.println("  /$$$$$$   /$$$$$$  /$$      /$$ /$$$$$$$$        /$$$$$$  /$$    /$$ /$$$$$$$$ /$$$$$$$ \r\n"
				+ " /$$__  $$ /$$__  $$| $$$    /$$$| $$_____/       /$$__  $$| $$   | $$| $$_____/| $$__  $$\r\n"
				+ "| $$  \\__/| $$  \\ $$| $$$$  /$$$$| $$            | $$  \\ $$| $$   | $$| $$      | $$  \\ $$\r\n"
				+ "| $$ /$$$$| $$$$$$$$| $$ $$/$$ $$| $$$$$         | $$  | $$|  $$ / $$/| $$$$$   | $$$$$$$/\r\n"
				+ "| $$|_  $$| $$__  $$| $$  $$$| $$| $$__/         | $$  | $$ \\  $$ $$/ | $$__/   | $$__  $$\r\n"
				+ "| $$  \\ $$| $$  | $$| $$\\  $ | $$| $$            | $$  | $$  \\  $$$/  | $$      | $$  \\ $$\r\n"
				+ "|  $$$$$$/| $$  | $$| $$ \\/  | $$| $$$$$$$$      |  $$$$$$/   \\  $/   | $$$$$$$$| $$  | $$\r\n"
				+ " \\______/ |__/  |__/|__/     |__/|________/       \\______/     \\_/    |________/|__/  |__/\r\n"
				+ "                                                                                          \r\n"
				+ "                                                                                          \r\n"
				+ "                                                                                          ");
		
		System.out.println("\n\n"+deathMessage);
		
	}
	
	public String promptUser() {
		System.out.print(">> ");
		String entry = scan.next();
		entry = entry.toLowerCase();
		
		if (entry == "upgrade") {
			if (isAttacking == true) {
				out("You can't upgrade your skills while battling!");
			}
			upgrade();
		}
		
		while (entry.length() != 1) {
			out("Not a valid command!");
			
			entry = scan.next();
			entry = entry.toLowerCase();
			
			if (entry == "upgrade") {
				if (isAttacking == true) {
					out("You can't upgrade your skills while battling!");
				}
				upgrade();
			}
		}
		
		return entry;
	}
	
	public String promptUser(String[] args) {
		System.out.print(">> ");
		String entry = scan.next();
		entry = entry.toLowerCase();
		
		if (entry == "upgrade") {
			upgrade();
		}
		
		while (entry.length() != 1 || !searchArray(args, entry)) {
			out("Not a valid command!");
			
			entry = scan.next();
			entry = entry.toLowerCase();
			
			if (entry == "upgrade") {
				upgrade();
			}
		}
		
		return entry;
	}
	
	public void BeginGame() {
		System.out.println("\r\n"
				+ " ________  _____   ____  ____   ______   _____       _       \r\n"
				+ "|_   __  ||_   _| |_  _||_  _|.' ____ \\ |_   _|     / \\      \r\n"
				+ "  | |_ \\_|  | |     \\ \\  / /  | (___ \\_|  | |      / _ \\     \r\n"
				+ "  |  _| _   | |   _  \\ \\/ /    _.____`.   | |     / ___ \\    \r\n"
				+ " _| |__/ | _| |__/ | _|  |_   | \\____) | _| |_  _/ /   \\ \\_  \r\n"
				+ "|________||________||______|   \\______.'|_____||____| |____| \r\n"
				+ "                                                             \r\n"
				+ "-------------------------------------------------------------\r\n"
				+ "\n\n");
		
		System.out.println("-- WELCOME TO ELYSIA! --\n");
		out("You are a young warrior and it is your birthday today! It is time for your to embark of \n" +
		"your journey to slay the goblin clan! You have just turned 18 and with your trusty SWORD,\n" +
		" you are prepared to take on any enemy!");
		out("Select a name for your character \n>> ");
		PlayerName = scan.next().toUpperCase();
		out("Welcome to Elysia, " + PlayerName + "!");
		
		System.out.println("You are able to upgrade your equipment at any time by typing [upgrade].");
		out(PlayerName + "! You are at the center of town in ELYSIA. It is a heavily populated area with \n" +
		"many districts. The sun is beginning to set in the distance. It is currently 912 CE in the summer.\n" +
		"You see the buildings towering over you, creating shadows on top of you. On your waist, you have \n" +
		"your sword, which deals 1 damage per hit.");
		out("You should probaly get somewhere safe before the sun sets. With that, time to get this journey\n" +
		"started!\n");
		
		Town();
	}
	
	public void Shop() {
		out("You push the wooden door inwards and it scraps across the floor. You hear a bell directly above you and then you hear footsteps walking towards the desk.");
		speak("shopkeeper", "Welcome back " + PlayerName + "! Do you need something, soldier?");
		out("Enter [t] to talk to the shopkeeper");
		String entry = promptUser();
		
		switch(entry) {
			case "t":
				speak(PlayerName, "What's on your mind?");
				speak("shopkeeper", "Hmm, well not much happens these days other then the typical goblin attack. ");
				out("[2] > Goblin attacks? What do you mean?");
				out("[1] > Goodbye.");
				String[] options1 = {"2", "1"};
				String p_1 = promptUser(options1);
				
				switch(p_1) {
				case "2":
					speak(PlayerName, "Goblin attacks? What do you mean");
					speak("shopkeeper", "Oh yes. The goblins come around quite often to steal my things.");
					speak("shopkeeper", "Those pesky things. I really hope something is done about it.");
					
					out("[2] > What do these goblins look like?");
					out("[1] > Oh okay then. I'll see you later.");
					
					String[] options2 = {"2", "1"};
					String p_2 = promptUser(options2);
					
					switch(p_2) {
					case "2":
						speak(PlayerName, "What do these goblins look like?");
						speak("shopkeeper", "Hmm. Last time I saw one they looked like us but they were much shorter. They have pointy ears and a screechy voice.");
						
						out("[2] > Okay, where did you last see them?");
						out("[1] > Okay, I'll keep an eye out!");
						
						String[] options3 = {"2", "1"};
						String p_3 = promptUser(options3);
						
						switch(p_3) {
						case "2":
							speak(PlayerName, "Okay, where did you last see them?");
							speak("shopkeeper", "Well, I last saw them down towards the roofed forest a quite aways from here. It gets pretty dark there at night!");
							if (hasTorch == false) {
								speak("shopkeeper", "Take this torch if you want to head there tonight. It will help a lot!");
								out("You have obtained a torch! You can now travel in dark spaces.");
								hasTorch = true;
							}
							speak(PlayerName, "Okay. I'll see what I can do.");
							speak("shopkeeper", "You would be doing me a HUGE favor! Thank you " + PlayerName + "!");
							speak(PlayerName, "No problem.");
							break;
						case "1":
							speak(PlayerName, "[1] > Okay, I'll keep an eye out!");
							speak("shopkeeper", "Hey! Make sure you show em' whos boss!");
							break;
						}
						
						break;
					case "1":
						break;
					}
						
					
					break;
				case "1":
					break;
				}
			
		}
		
		speak("shopkeeper", "Later gator!");
		out("You shut the door behind you and you hear the bell ring from the inside as you do.");
		Town();
	}
	
	public void Wilderness() {
		out("You walk into the wilderness, straying from your home town. You hear the bushes rustle from the"+ 
			"wind and you feel the cool air blowing on you. You are surrounded by tall trees and before you is"+
			" a pathway that is seemingly endless.");
		
		out("Should you... [b]egin to walk or [g]o back to your town?");
		
		String[] options1 = {"b", "g"};
		String p_1 = promptUser(options1);
		
		switch(p_1) {
			case "b":
				out("The sun sets from behind you and you start seeing less. You really should have went earlier. This is going to be \n"+
			"a longgg trip!");
				System.out.print(">> Hours go by of walking");
				for (int i = 0; i < 6; i++) {
					System.out.print(".");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println();
				
				
				out("You see the path split into two. On one side you see a [d]arkened area with low roofing trees and on the other side\n"+
				" you see a pathway that begins to go downhill towards a [r]iver. Where do you go?");
				out("You also see a sign directly beside the darkened path. \"DO NOT ENTER WHILE DARK!\"");
				
				String[] options2 = {"r", "d"};
				String p_2 = promptUser(options2);
				
				switch(p_2) {
				case "r":
					
					break;
				case "d":
					if (hasTorch == true) {
						out("You remember that you were given a torch by that SHOPKEEPER! You take it out and strike it across the ground. It\n" +
								" makes a loud spark sound and you can easily see now. You walk into the forest slowly.");
						out("The forest looks to have many vines hanging down. The trees on both sides of you look to be maple trees. You hear \n" +
							"the wind blowing above you, making a squeal but you never actually feel the wind.");
						out("Feeling the torch next to you comforts you and improves you confidence for future battles. A point is added to your armor!");
						Armor += 1;
						
						out("You continue to walk down the path.");
						System.out.print(">> You keep walking.");
						for (int i = 0; i < 6; i++) {
							System.out.print(".");
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch(InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println();
						
						out("Suddenly, you see green eyes opposing you. You quickly unsheath you sword and point it towards it.");
						Mob goblin = new Mob(10, 1, 1, "Tarzan", "the Rebel Goblin");
						attack(goblin, false);
						
					} else {
						out("You scoff at the sign and ignore it. You walk into the forest and you can't even see your boots. You begin to think that this may\n"+
					" be a bad idea but you ignore your ideas and keep onward.");
						
						System.out.print(">> You keep walking.");
						for (int i = 0; i < 6; i++) {
							System.out.print(".");
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch(InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println();
						
						out("And all of the sudden, you feel a tap from behind you.");
						out("You look quickly. You can't see a thing");
						speak(PlayerName, "Who's there!?");
						out("You should think carefully of your next action.");
						out("Should you... look [t]owards you, or take out your [s]word and blindly jab around you.");
						
						String[] options3 = {"t", "s"};
						String p_3 = promptUser(options3);
						
						switch(p_3) {
						case "t":
							out("You quickly look in front of you again and nothing is there.");
							out("You hear a high pitched voice.");
							
							speak("???", "Loser!");
							out("You get swung at with a bat and you die instantly");
							break;
						case "s":
							out("You equip your sword and you start swinging at the air. You get pushed from behind you onto the ground.");
							out("You get kicked from all around you and then you get hit with a bat. You die instantly.");
							break;
						}
						
						GameOver("It was a bad idea to disregard signs.");
					}
					break;
				}
				
				break;
			case "g":
				out("You look behind you. Unknown to yourself, the gate is already shut! There is no going back. You must venture "+
						"forth.");
				Wilderness();
				break;
		}
	}
	
	public void Town() {
		out("You look around you. You see a [s]hop, your [h]ouse, and the [w]ilderness. Where should you venture to?");
		out("NOTE: Once you leave your town, you can't come back! You should prepare before you leave.");
		String entry = promptUser();
		
		switch(entry) {
			case "s":
				Shop();
				break;
			case "h":
				out("You decided to take a small nap before you leave. Your armor is now at one point!");
				Armor = 1;
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
