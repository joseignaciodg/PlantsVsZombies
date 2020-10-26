package tp.p1.logic;

import java.util.Random;

public class ZombieManager {
	private int zombiesLeftToAppear;
	private double frequence;
	private Random r;
	
	public ZombieManager(Level level, Random r) {
		this.r = r;
		
		switch(level) {
		case EASY:
			zombiesLeftToAppear = level.getNumberOfZombies();
			frequence = level.getZombieFrequency();
			break;
		case HARD:
			zombiesLeftToAppear = level.getNumberOfZombies();
			frequence = level.getZombieFrequency();
			break;
		case INSANE:
			zombiesLeftToAppear = level.getNumberOfZombies();
			frequence = level.getZombieFrequency();
			break;
		default:
		}
	}
	
	public boolean isZombieAdded() {
		boolean ok = false;
		if (zombiesLeftToAppear > 0 && r.nextInt(10) < frequence * 10) {
			zombiesLeftToAppear--;
			ok = true;
		}
		return ok;
	}
	
	public int RandomTile() {
		return r.nextInt(4);
	}
	
	public int getZombiesLeftToAppear() {
		return zombiesLeftToAppear;
	}
	
	public void setZombiesLeftToAppear(int zombies) {
		zombiesLeftToAppear = zombies;
	}
}
