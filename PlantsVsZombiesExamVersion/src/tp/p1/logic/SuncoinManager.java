package tp.p1.logic;

import tp.p1.control.Exceptions.NotEnoughCoins;

public class SuncoinManager {
	private static final String Suncoins = "not enough suncoins available";
	private int suncoins = 50;
	
	public int getSuncoins() {
		return suncoins;
	}

	public void setSuncoins(int suncoins) {
		this.suncoins += suncoins;
	}
	
	public void equalSuncoins(int suncoins) {
		this.suncoins = suncoins;
	}
	
	public boolean compare(int price) throws NotEnoughCoins {
		boolean ok = suncoins >= price;
		if (!ok)
			throw new NotEnoughCoins(Suncoins);
		return ok;
	}
}
