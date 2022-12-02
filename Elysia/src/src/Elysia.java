package src;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import types.King;
import types.Mob;

/*
 * Elysia class
 * The main class for the game. Includes all methods for the choices. Switch statements are
 * used for the choice making. But the game allows only one branch of choices to give a winning
 * result. Players are required to talk with NPCs to pick these choices carefully rather then
 * ruthless trial and error.
 * 
 * @author Aidan O'Quinn
 * @date November 20th, 2022
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
    
    private boolean canParry = false;
    private boolean isAttacking = false;
    private boolean firstTimeAttacking = true;
    private boolean hasTorch = false;
    private boolean GameFinished = false;
    
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
			out("The arrow needs to be pointing upwards [^] and you type [W] to attack!");
			out("Each one of your attacks has a 10% chance to be a critical hit (x2 damage)");
			out("This is turn-based combat, meaning that you and your attackee switches turns to attack.");
		}
		System.out.println();
		
		System.out.println(" > "+ mob + " <");
		System.out.println("> Armor : " + mob.getArmor());
		System.out.println("> Damage : " + mob.getDamage());
		
		boolean playersTurn = !mobAttackedFirst;
		
		while(mob.getHealth() >= 0 && Health >= 0) {
			System.out.println();
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
								int dmgTaken = Math.max(0, damage - mob.getArmor());
								System.out.println("You hit " + mob + " for " + dmgTaken + " damage!");
								boolean notDamaged = mob.takeDamage(damage, false);
								if (notDamaged == false) {
									out("You didn't harm "+mob+" because their armor is too high!");
								}
							} else {
								int dmgTaken = Math.max(0, damage - mob.getArmor());
								System.out.println("[CRITICAL] You hit " + mob + " for " + dmgTaken*2 + " damage!");
								mob.takeDamage(damage*2, true);
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
				
				if(canParry == true) {
					if (((int) (Math.random() * 100) + 1) <= 30) {
						out("[PARRY] You deflected "+mob+"!");
						playersTurn = !playersTurn;
						continue;
					}
				}
				
				boolean crit = ((int) (Math.random() * 100) + 1) <= 10;
				int dmg = Math.max(0, mob.getDamage() - Armor);
				if (crit == true) {
					dmg *= 2;
					System.out.print("[CRITICAL]" );
				}
				System.out.println(mob+" swings at you! Dealing " + dmg + " damage.");
				Health -= dmg;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			playersTurn = !playersTurn;
		}
		
		if (Health <= 0) {
			GameOver("You have been slain by "+mob+"! Better luck next time!");
			return;
		}
		
		if (mob.getHealth() == 0) {
			SkillPoints += mob.getReward();
			out("You have won the battle and have been awarded " + mob.getReward() + " skill point(s)!");
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
	
	public static void speak(String name, String text, int speed) {
		String[] split = text.split("");
		
		System.out.print(name.toUpperCase() + " > \"");
		
		
		for (int i = 0; i < split.length; i++) {
			System.out.print(split[i]);
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
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
		boolean close = false;
		
		System.out.println("-- SKILLS --");
		System.out.println("[1] > Upgrade damage to "+ (damage+1)+" - 1 SKILL POINT");
		System.out.println("[2] > Upgrade armor to "+ (Armor+1)+" - 1 SKILL POINT");
		System.out.println("[3] > Upgrade health to "+ (MaxHealth+5)+" - 1 SKILL POINT");

		
		while (close == false) {
			System.out.println("SKILL POINTS : "+ SkillPoints);
			System.out.println("\nType the number of a skill to upgrade. Type [B] to return to the story.");
			
			String[] options = {"1", "2", "3", "b"};
			String entry = promptUser(options);
			
			switch(entry) {
			case "1":
				if (SkillPoints >= 1) {
					SkillPoints--;
					damage++;
					out("You have enhanced your damage to " + damage + "!");
				} else {
					out("You do not have enough skill points!");
				}
				break;
			case "2":
				if (SkillPoints >= 1) {
					SkillPoints--;
					Armor++;
					out("You have enhanced your armor to " + Armor + "!");
				} else {
					out("You do not have enough skill points!");
				}
				break;
			case "3":
				if (SkillPoints >= 1) {
					SkillPoints--;
					MaxHealth += 5;
					out("You have enhanced your max health to " + MaxHealth + "!");
				} else {
					out("You do not have enough skill points!");
				}
				break;
				
			case "b":
				close = true;
				break;
			}
		}
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
		
		if (entry.equals("upgrade")) {
			if (isAttacking == true) {
				out("You can't upgrade your skills while battling!");
			} else { upgrade(); }
		}
		
		while (entry.length() != 1) {
			out("Please select a valid command!");
			
			entry = scan.next();
			entry = entry.toLowerCase();
			
			if (entry.equals("upgrade")) {
				if (isAttacking == true) {
					out("You can't upgrade your skills while battling!");
				} else { upgrade(); }
			}
		}
		
		return entry;
	}
	
	public String promptUser(String[] args) {
		System.out.print(">> ");
		String entry = scan.next();
		entry = entry.toLowerCase();
		
		if (entry.equals("upgrade")) {
			if (isAttacking == true) {
				out("You can't upgrade your skills while battling!");
			} else { upgrade(); }
		}
		
		while (entry.length() != 1 || !searchArray(args, entry)) {
			out("Please select a valid command!");
			
			entry = scan.next();
			entry = entry.toLowerCase();
			
			if (entry.equals("upgrade")) {
				if (isAttacking == true) {
					out("You can't upgrade your skills while battling!");
				} else { upgrade(); }
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
		out("Your mission is to take down the goblin clan that steals and pesters ELYSIA so much.");
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
					out("You start walking downwards towards the river.");
					out("Since you can't see below you, you step into a dip in the ground by mistake and then tumble\n"
							+ "down into the water, knocking you out and drowning you.");
					GameOver("Maybe you should ask some people the whereabouts for your objective?");
					break;
				case "d":
					if (hasTorch == true) {
						// CORRECT PATHWAY
						
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
						Mob tarzan = new Mob(10, 0, 3, "Tarzan", "the Rebel Goblin", 1);
						attack(tarzan, false);
						firstTimeAttacking = false;
						
						out("Your torch fizzles out from so much movement.");
						out("Tarzan looks up at you when you are about to give the final blow.");
						speak("tarzan", "Why...");
						out("You hesitate. Think carefully.");
						out("Do you [k]ill him (+1 SKILL POINT), let him [f]lee, or [q]uestion him?");
						
						String[] options_3 = {"k", "f", "q"};
						String p_3 = promptUser(options_3);
						
						switch(p_3) {
						case "k":
							SkillPoints++;
							out("You killed Tarzan with your sword and gained another skill point!");
							GameOver("Since your torch went out and you have no way of getting back, within the next minute " +
									"you were killed by a group of goblin guards. You should find a way to let him help you.");
							break;
						case "f":
							out("You step back and Tarzan scampers away. Leaving a lime green blood trail.");
							GameOver("Since your torch went out and you have no way of getting back, within the next minute " +
									"you were killed by a group of goblin guards. You should find a way to let him help you.");
							break;
						case "q":
							out("He lies there nearly lifeless. You better make this question count.");
							out("You aim your sword in front of him.");
							out("[1] > Who are you?");
							out("[2] > Who do you work for!?");
							
							String[] options_4 = {"1", "2"};
							String p_4 = promptUser(options_4);
							
							switch(p_4) {
							case "1":
								speak(PlayerName, "Who are you?");
								out("Tarzan looks up at you");
								speak("tarzan", "That doesn't matter...");
							case "2":
								speak(PlayerName, "Who do you work for!?");
								speak("tarzan", "I don't work for anyone... I used to work for the fort.");
								
								out("You are intrigued that he used to work there.");
								
								speak(PlayerName, "Where is the fort!?");
								speak("tarzan", "I can show you! Just don't kill me!");
								
								out("Do you [k]ill him, or do you let him [h]elp you?");
								
								String[] options_5 = {"k", "h"};
								String p_5 = promptUser(options_5);
								
								switch(p_5) {
								case "k":
									out("You ignore him and straight up kill Tarzan.");
									GameOver("Since your torch went out and you have no way of getting back, within the next minute " +
											"you were killed by a group of goblin guards. You should find a way to let him help you.");
									break;
								case "h":
									out("You hold out your hand and you help Tarzan up. You patch him up with the bandages in your pocket.");
									out("You also bandage yourself you both should be fully healed tomorrow.");
									speak(PlayerName, "Okay, fine.");
									speak("Tarzan", "What's you name anyways?");
									speak(PlayerName, PlayerName+".");
									speak("Tarzan", "Hm... " + PlayerName+". I'm Tarzan.");
									
									int CPHealth = Health;
									int CPMaxHealth = MaxHealth;
									int CPDamage = damage;
									int CPArmor = Armor;
									int CPSP = SkillPoints;
									
									while (!GameFinished) { 
										Health = CPHealth;
										MaxHealth = CPMaxHealth;
										damage = CPDamage;
										Armor = CPArmor;
										SkillPoints = CPSP;
										
										Checkpoint1(); 
									};
									break;
								}
								
								break;
							}
							
							break;
						}
						
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
							out("You get kicked from all around you and then you get hit with a sign. You die instantly.");
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
	
	public void Checkpoint1() {
		System.out.println("-- CHECKPOINT REACHED! --");
		out("You will be sent back to this time if you lose all of your health!");
		
		out("You pick up Tazan's sign. You look at it closely.");
		speak(PlayerName, "A sign? Really? I'm getting you a sword when we come across one.");
		speak("tarzan", "Oh. Okay.");
		
		out("Do you [a]sk him to direct you to the fort or do you [s]ettle for the night?");
		
		String[] options_1 = {"a", "s"};
		String p_1 = promptUser(options_1);
		
		switch(p_1) {
		case "a":
			break;
		case "s":
			Health = MaxHealth;
			speak(PlayerName, "Well, do you want to settle for the night? You need some rest... and so do I");
			speak("tarzan", "Alright. We can leave in the morning");
			
			out("You and Tarzan set up a small settlement with stray branches and wood. You sleep for the night\n"+
			"and when you get up, you see Tarzan waiting for you to get up as he looks into the distance. You get\n"+
			"up and prepare for the journey");
			out("You fully heal!");
			break;
		}
		
		speak(PlayerName, "Okay so where is the fort?");
		speak("tarzan", "Just to towards the path. We need to take a detour to get in though.");
		speak("tarzan", "I know a weakness.");
		
		out("You and Tarzan venture to the path until you see a group of goblin warriors! It is a group\n"
				+ "of 4 goblin warriors with 0 armor and 15 health each. They deal one damage per hit.");
		out("You see the warriors practicing their warcry beside their campfire.");
		out("Do you [f]ight the warriors or do you [a]void the warriors?");
		
		String[] options_2 = {"f", "a"};
		String p_2 = promptUser(options_2);
		
		switch (p_2) {
		case "f":
			speak(PlayerName, "I think we should fight these goblins. Who are they?");
			speak("Tarzan", "They are the patrol guards for the goblin clan.");
			out("You and Tarzan approach the goblin warriors, wielding your weapons.");
			out("You look at Tarzan. You two may be under-equipped for the fight. Do you [c]ontinue\n"
					+ "or do you [t]ry to convince the warriors to join you?");
			
			String[] options_3 = {"t", "c"};
			String p_3 = promptUser(options_3);
			
			Mob Warrior1 = new Mob(15, 0, 1, "Herbert", "the Royal Assassin", 1);
			Mob Warrior2 = new Mob(15, 0, 2, "Clete", "the Royal Beserk", 2);
			
			// Tarzan fights the other two mobs.
			
			out("You step closer and they see you.");
			speak("???", "Who are you!?");
			out("They look at Tarzan weirdly and then begin to focus on you again.");
			
			switch(p_3) {
			case "t":
				speak(PlayerName, "Hey, we were wondering if you wanted to join us to take over the fort!");
				out("They look at eachother and are furfilled to crush you harder. [+1 DAMAGE EACH]");
				
				Warrior1.setDamage(Warrior1.getDamage() + 1);
				Warrior2.setDamage(Warrior2.getDamage() + 1);
				
				break;
			case "c":
				speak("Tarzan", "I'll get the other two guards!");
				out("You two begin attacking the guards.");
				break;
			}
			
			attack(Warrior1, false);
			out("You look over at Tarzan. He looks like hes pretty good at fighting.");
			out("Well. Besides the sign.");
			attack(Warrior2, false);
			
			out("You and Tarzan looks injured but you two survived. Good job!");
			out("Remember, you can type \"upgrade\" at any point to upgrade your skills.");
			out("You have " + SkillPoints + " skill points in total!");
			
			out("Do you [l]oot the guards' hideout? Or do you leave the [a]rea?");
			
			String[] options_4 = {"l", "a"};
			String p_4 = promptUser(options_4);
			
			switch(p_4) {
			case "l":
				out("You and Tarzan part to different sides of the hideout. There are two tents beside a big campfire.");
				out("You find a spare weapon holder with a [l]ongsword and a [c]leaver");
				out("Which one should you replace with your sword? The longsword increases your damage by 2 and removes\n"
						+ "1 armor point while the cleaver increases your damage by 1.");
				out("You are able to gain the ability to parry with the cleaver.");
				String[] options_5 = {"l", "c"};
				String p_5 = promptUser(options_5);
				
				switch(p_5) {
				case "l":
					out("You equip the longsword and lean your sword on the tent wall. You swing the longsword onto your shoulder.");
					out("Your damage increased by 2 and you lose 1 armor because of your inability to parry!");
					damage += 2;
					Armor--;
					break;
				case "c":
					out("You equip the cleaver and lean your sword on the tent wall. You try to fit the cleaver into your holder\n"
							+ "but it can't fit. You just take off your holder and shove it into your belt.");
					out("You gain the ability to parry an attack by 30%!");
					out("Your damage increases by 1 point!");
					damage++;
					break;
				}
				
				out("You walk out of the tent and you see Tarzan struggling to put on a chestplate and weilding a shortsword.");
				out("You help him put it on.");
				speak("Tarzan", "Thanks! That was a wild fight!");
				speak(PlayerName, "Where to now, boss?");
				speak("Tarzan", "Follow me!");
			}
			
			break;
		case "a":
			out("You and Tarzan sneak quietly around the outpost and make it to the detouring spot.");
			out("NOTE: That was NOT a good idea. Attacking foes makes you stronger!");
			break;
		}
		
		out("Tarzan directs you through through the detour, through the forest, straying from the path.");
		out("The castle walls are in sight! Tarzan points to the top of castle, far up in the sky.");
		speak("Tarzan", "You see that place up there? Thats the king's lair! Thats where we need to go!");
		out("You walk towards the wall and you see a door and Tarzan pulls out a key.");
		speak("Tarzan", "Well. I hope you the best, " + PlayerName + "!");
		speak(PlayerName, "Wait, why are you leaving?!");
		speak("Tarzan", "Do you really think I want to be here? This place is a death sentence!");
		out("You sigh.");
		speak(PlayerName, "Okay then. You helped me a lot Tarzan. How do I get to the king?");
		out("He begins to walk away.");
		speak("Tarzan", "You'll figure it out.", 50);
		out("Tarzan vanishes into the forest. You look towards the door and the key is in the hole.");
		
		int CPHealth = Health;
		int CPMaxHealth = MaxHealth;
		int CPDamage = damage;
		int CPArmor = Armor;
		int CPSP = SkillPoints;
		
		while (!GameFinished) { 
			Health = CPHealth;
			MaxHealth = CPMaxHealth;
			damage = CPDamage;
			Armor = CPArmor;
			SkillPoints = CPSP;
			
			Checkpoint2(); 
		};
	}
	
	public void Checkpoint2() {
		System.out.println("-- CHECKPOINT REACHED! --");
		out("This is the final stretch.");
		out("You might as well rest for the night. You are pretty wounded from the fight still.");
		out("Should you [r]est or do you keep [o]nward?");
		
		String[] options_1 = {"r", "o"};
		String p_1 = promptUser(options_1);
		switch(p_1) {
		case "r":
			out("You rest for the night and you get up in the morning!");
			out("Your health is fully replenished!");
			Health = MaxHealth;
			break;
		case "o":
			break;
		}
		
		out("You walk towards the door and you turn the key slowly.");
		out("It leads to the inside of the castle wall. There is a long [h]allway to the left and there\n"
				+ "is a [b]rick pathway leading to the central castle.");
		String[] options_2 = {"h", "b"};
		String p_2 = promptUser(options_2);
		
		switch(p_2) {
		case "h":
			out("You walk down the hall. The hall is littered with various crates and barrels.");
			out("Travelling down the hall, you begin to hear two voices! Do you [h]ide behind a barrel,\n"
					+ "or do you [c]onfront the guards?");
			
			String[] options_3 = {"h", "c"};
			String p_3 = promptUser(options_3);
			switch(p_3) {
			case "h":
				out("You quickly hide behind some barrels, making a shuffling sound");
				out("They continue to talk and they walk beside you.");
				speak("Guard 1", "You should have seen what they are planning against Elysia!", 125);
				speak("Guard 2", "Oh yeah. I wish. I can't even imagine what our king can do alone!", 125);
				
				out("The steps get closer.");
				
				speak("Guard 1", "I know right...", 200);
				speak("Guard 2", "What losers they are.", 150);
				speak("Guard 1", "...", 1000);
				
				out("A sword slices through the barrel straight through your chest!");
				
				GameOver("Seems like these guards are prepared for attacks 24/7!");
				break;
			case "c":
				out("You step in front of them.");
				speak(PlayerName, "Face me!");
				out("They look at eachother and laugh.");
				
				Mob guard1 = new Mob(20, 2, 2, "Ying", "the Royal Guard", 2);
				attack(guard1, false);
				out("The other guard gets angry at his brother's defeat!");
				Mob guard2 = new Mob(20, 2, 3, "Yang", "the Royal Guard", 2);
				attack(guard2, false);
				
				out("You continue through the hall. It seems like its coming to an end.");
				out("It looks like it leaded to the central castle!");
			}
			
			break;
		case "b":
			out("You walk towards the brick pathway. You hear a voice from above you.");
			speak("???", "Brahh!", 60);
			out("You dash forward and look behind you. A giant elf beast drops down in front of you making\n"
					+ "the ground shake. The mallet drops onto the ground beside it.");
			out("The gate slams shut behind it!");
			Mob elf = new Mob(35, 0, 4, "Cicero", "the Hellbeast", 5);
			attack(elf, true);
			out("The beast falls onto the ground. You take a breather.");
			out("You look behind you and you see the central castle's gate wide open. It seems like this is a trap\n"
					+ "that didn't quite work. Too bad that you were too strong.");
			break;
		}
		
		out("You walk into the castle and you see a spiral of stairs. The steps seem to\n"
				+ "disolve into the darkness above you.");
		out("You are determined to take your next steps! With that, you replenish your health!");
		Health = MaxHealth;
		out("You walk up the stairs");
		out("You arrive in a training room and you see a goblin kneeling down in the center of the room,\n"
				+ "looking downwards to the floor. It seems like her eyes are closed.");
		out("You might want to upgrade your skills before [c]onfronting her if you haven't already.");
		
		String[] options_3 = {"c"};
		String p_3 = promptUser(options_3);
		
		if (p_3 == "c") {
			out("Do you sneak up behind her and get an [e]asy kill, or do you speak up and \n"
					+ "get her to [n]otice you?");
			
			Mob lily = new Mob(30, 4, 5, "Lily", "the King's Apprentice", 5);
			
			String[] options_4 = {"e", "n"};
			String p_4 = promptUser(options_4);
			
			switch(p_4) {
			case "e":
				out("You sneak up behind her and you lift your sword above her.");
				out("You swing downwards while closing your eyes.");
				out("Suddenly she transforms into gas and teleports behind you! She strikes you from behind!");
				speak("Lily", "Nice try!", 50);
				attack(lily, true);
				break;
			case "n":
				speak(PlayerName, "Hey! You!");
				out("She looks up quickly and stands up.");
				speak("Lily", "Hello, " + PlayerName + ". I saw you coming.", 50);
				speak(PlayerName, "Who are you? How do you know my name!?");
				out("She looks up to you and you instantly feel horrible. You lose a damage point.");
				damage--;
				speak("Lily", "You really think the king lets himself be undefended?", 50);
				speak("Lily", "No!", 100);
				speak("Lily", "He trains the finest swordsman'! Or perhaps... the finest wizard!", 50);
				
				attack(lily, false);
				out("You feel relieved to live. You get your damage point back.");
				out("You fall onto the ground and relax for a bit. You take advatage that you are in a training\n"
						+ "room and you find some bandages and healing potions. You apply them to yourself.");
				out("You replenish you health!");
				Health = MaxHealth;
				damage++;
				
				int CPHealth = Health;
				int CPMaxHealth = MaxHealth;
				int CPDamage = damage;
				int CPArmor = Armor;
				int CPSP = SkillPoints;
				
				while (!GameFinished) { 
					Health = CPHealth;
					MaxHealth = CPMaxHealth;
					damage = CPDamage;
					Armor = CPArmor;
					SkillPoints = CPSP;
					
					FinalCheckpoint(); 
				};
				
				break;
			}
		}
	}
	
	public void FinalCheckpoint() {
		System.out.println("-- CHECKPOINT REACHED! --");
		System.out.println("> The final checkpoint.");
		out("A door slowly opens in front of you.");
		out("It appears to be the entering point of the king's lair.");
		out("You walk into the corridor.");
		speak("???", "Hello, "+PlayerName+"! It's nice to see you.");
		out("Do you ask the voice [w]ho they are, or do you ask them why they are [a]ttacking Elysia.");
		
		String[] options_1 = {"w", "a"};
		String p_1 = promptUser(options_1);
		
		switch(p_1) {
		case "w":
			speak(PlayerName, "Who are you?!");
			speak("???", "Oh that doesn't matter. All that matters is that you are dealt with.");
			break;
		case "a":
			speak(PlayerName, "Why are you attacking Elysia!?");
			speak("???", "To become supreme. Simple as that. Elysia is the only place standing in my way.");
			speak("???", "Elysia is corrupt. Haven't you realised?");
			speak(PlayerName, "You are just making lies now.");
			speak("???", "Oh I wish I was, "+PlayerName+".");
			break;
		}
		
		speak("???", "Besides,", 300);
		out("The king dethrones. He slides his sword on the ground and then he points it up towards you.");
		out("His sword has a wavy design for the blade and seems to have grey particles eminating from it.");
		speak("???", "You...", 300);
		speak("Tarzan", "Shall not stand.", 300);
		
		Mob tarzan = new King(50, 5, 10, "Tarzan", "the Tyrant", 10);
		attack(tarzan, true);
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
