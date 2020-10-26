package tp.p1;

import java.util.Scanner;
import tp.p1.control.Controller;
import tp.p1.control.Exceptions.WrongArgumentsException;
import tp.p1.logic.GamePrinter;
import tp.p1.logic.Game;
import tp.p1.logic.Level;
import tp.p1.logic.ReleasePrinter;

public class PlantsVsZombies {
	private static final String Usage = "Usage: plantsVsZombies <EASY|HARD|INSANE> [seed]";
	private static final String LevelStr = ": level must be one of: EASY, HARD, INSANE";
	private static final String Seed = ": the seed must be a number";
	
	public static void main(String[] args) {
		Scanner scann = new Scanner(System.in);
		GamePrinter printer = new ReleasePrinter();
		boolean reset = false;
		Level level;
		
		try {
			if (args.length > 0 && args.length < 3) {
				level = Level.parse(args[0]);
				if (level == null) 
					throw new WrongArgumentsException(Usage + LevelStr);
			}
			else
				throw new WrongArgumentsException(Usage);
			
			if (args.length == 1)  {
				do {
				Game game = new Game(level, printer);
				Controller controller = new Controller(game, scann);
				reset = controller.run();
				} while (reset);
			}
			else if (args.length == 2) {
				do {
				Game game = new Game(level, Integer.parseInt(args[1]), printer);
				Controller controller = new Controller(game, scann);
				reset = controller.run();
				} while (reset);
			}
		} catch (NumberFormatException e){
			System.out.format(Usage + Seed);
	    } catch (WrongArgumentsException ex) {
			System.out.format(ex.getMessage() + "%n%n");
		} 
	}
}
