package types;

public class King extends Mob{
	public King(int maxHealth, int armor, int dmg, String name, String title, int reward) {
		super(maxHealth, armor, dmg, name, title, reward);
		
	}
	
	@Override
	public boolean takeDamage(int dmg, boolean critical) {
		if (((int) (Math.random() * 100) + 1) <= 30) {
			System.out.println(">> [PARRY] " + this.Name +" deflected your attack!");
		}
		
		int dmgTaken = Math.max(0, dmg - Armor);
		if (critical == true) {
			dmgTaken = dmg;
		}
		Health -= dmgTaken;
		Health = Math.max(0, Health);
		
		return dmgTaken == 0;
	}
	
}
