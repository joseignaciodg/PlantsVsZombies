package tp.p1.control.Commands;

import java.io.*;

import tp.p1.control.Command;
import tp.p1.util.MyStringUtils;
import tp.p1.control.Exceptions.CommandExecuteException;
import tp.p1.control.Exceptions.CommandParseException;
import tp.p1.control.Exceptions.FileContentsException;
import tp.p1.logic.Game;

public class LoadCommand extends Command{
	private String fileName;
	private Game backup;
	public static final String FoundFile = "File not found.";
	public static final String wrongPrefixMsg = "unknown game attribute: ";
	public static final String lineTooLongMsg = "too many words on line commencing: ";
	public static final String lineTooShortMsg = "missing data on line commencing: ";
	public static final String ErrorLoading = "Error while loading the game.";
	public static final String Contents = " There is a problem with the contents of the file. ";
	public static final String WrongFileName = "The file name must be: filename.dat";

	public LoadCommand() {
		super("load", "[Lo]ad <filename>", "Load the state of the game from a file.");
	}

	public boolean execute(Game game) throws CommandExecuteException, CommandParseException {
		  // This will reference one line at a time
        String[] line = new String[32], aux, prefix = {"cycle", "sunCoins", "level", "remZombies", "plantList", "zombieList"};
        int counter = 0, i = 0;
		boolean isList;
		BufferedReader bufferedReader = null;
        //getFileName() String fileName = user input
		try (FileReader fileReader = new FileReader(fileName)) {
            // FileReader reads text files in the default encoding.
           
            // Always wrap FileReader in BufferedReader.
           bufferedReader = new BufferedReader(fileReader);
           bufferedReader.readLine();
           bufferedReader.readLine();
            while (counter < 6) {
            	if (counter == 4 || counter == 5)
            		isList = true;
            	else 
            		isList = false;
            	aux = loadLine(bufferedReader, prefix[counter], isList);
        		for (int j = 0; j < aux.length; j++) {
            		line[i] = aux[j];
            		i++;
        		}
        		counter++;
            }   
            backup = game.MemoryBackUp();
            game.load(line);
                  
        } catch (NumberFormatException e){
        	game.restoreState(backup);
        	throw new CommandExecuteException(ErrorLoading + Contents + "Unable to parse an int.");
        } catch(IOException ex) {
            throw new CommandExecuteException(FoundFile);
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (FileContentsException e) {
        	game.restoreState(backup);
        	throw new CommandExecuteException(ErrorLoading + Contents);
		} catch (Exception ex) {
			game.restoreState(backup);
			throw ex;
		} 
		System.out.println("Game successfully loaded from file " + fileName + ".\n");//filename.dat
		return true;
    }
	
	public Command parse(String[] commandWords) throws CommandParseException, CommandExecuteException {
		Command command = null;
		if(!commandWords[0].isEmpty()) {
			if (commandWords[0].toLowerCase().equals(commandName) || commandWords[0].toLowerCase().equals(commandName.substring(0, 2))) {
				if (commandWords.length == 2) {
					if (commandWords[1].length() > 4) {
						String aux = commandWords[1].substring(0, commandWords[1].length() - 4);
						if(MyStringUtils.isValidFilename(aux) && MyStringUtils.fileExists(aux) && MyStringUtils.isReadable(aux) && commandWords[1].substring(commandWords[1].length() - 4, commandWords[1].length()).equals(".dat")) {
							command = this;
							fileName = aux;
						}
						else 
							throw new CommandExecuteException(FoundFile);
					}
					else
						throw new CommandParseException(WrongFileName);
				}
				else
					throw new CommandParseException(IncorrectLength1 + commandName + IncorrectLength2 + helpText);
			}
		}
		return command;
	}
			
	public String[] loadLine(BufferedReader inStream, String prefix, boolean isList) throws CommandParseException, IOException {
		
		String[] words;
		String line = inStream.readLine().trim();
		
		// absence of the prefix is invalid
		if (!line.startsWith(prefix + ":") )
			throw new CommandParseException(wrongPrefixMsg + prefix);
		
		// cut the prefix and the following colon off the line then trim it to get attribute contents
		String contentString = line.substring(prefix.length()+1).trim();
			
		// the attribute contents are not empty
		if (!contentString.equals("")) {
			if (!isList ) {
				// split non−list attribute contents into words
				// using 1−or−more−white−spaces as separator
				words = contentString.split ("\\s+");
		
				// a non−list attribute with contents of more than one word is invalid
		
				if (words.length != 1)
					throw new CommandParseException(lineTooLongMsg + prefix);
			} else
				// split list attribute contents into words
				// using comma+0−or−more−white−spaces as separator
				words = contentString.split (",\\s*");
		
			// the attribute contents are empty
		} else {
			// a non−list attribute with empty contents is invalid
			if (!isList)
				throw new CommandParseException(lineTooShortMsg + prefix);
		// a list attribute with empty contents is valid; use a zero−length array to store its words
			words = new String[0];
		}
		return words;
	}
}
