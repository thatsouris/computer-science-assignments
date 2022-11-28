package types;

public class Mob {
	private int Health = 100;
	private int MaxHealth = 100;
	private int Armor = 0;
	private int Damage = 0;
	private int Reward = 0;
	
	private String Name;
	private String Title;
	
	public Mob(int maxHealth, int armor, int dmg, String name, String title, int reward) {
		MaxHealth = maxHealth;
		Health = MaxHealth;
		Armor = armor;
		Damage = dmg;
		Name = name;
		Title = title;
		Reward = reward;
	}
	
	public int getReward() {
		return Reward;
	}
	
	public int getDamage() {
		return Damage;
	}
	
	public int getHealth() {
		return Health;
	}
	
	public int getMaxHealth() {
		return MaxHealth;
	}
	
	public int getArmor() {
		return Armor;
	}
	
	
	public boolean takeDamage(int dmg) {
		int dmgTaken = Math.max(0, dmg - Armor);
		Health -= dmgTaken;
		Health = Math.max(0, Health);
		
		return dmgTaken == 0;
	}
	
	public void heal(int amount) {
		Health += amount;
		Health = Math.min(100, Health);
	}
	
	public String toString() {
		return Name + ", " + Title;
	}
}
