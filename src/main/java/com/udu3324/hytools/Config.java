package com.udu3324.hytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.Loader;

public class Config {
	public static File configFile = new File(Loader.instance().getConfigDir(), "hytools.cfg");
    
	//returns if config is not the current version
	public static Boolean isNotCurrentVersion() throws IOException {
		BufferedReader brTest = new BufferedReader(new FileReader(configFile));
		String text = brTest.readLine();
		brTest.close();
		
		if (!text.contains(Reference.VERSION)) return true;
		return false;
	}

	//example: getValueFromConfig("val1") returns "this is val1"
	public static String getValueFromConfig(String value) {
		String data = null;
		try {
			BufferedReader file = new BufferedReader(new FileReader(configFile));
	        String line;

	        while ((line = file.readLine()) != null) {
	            if (line.contains(value)) {
	            	//take the value line and parse it for its data
			    	// value: data -> data
	            	data = line.replace(value + ": ", "");
	            	file.close();
	            	return data;
		        }
	        }
	        
	        file.close();
		} catch (Exception e) {
			Hytools.log.info("Problem reading file.");
		}
		
		return data;
	}

	//example: setValueFromConfig("val1", "this is val1") sets val1 to be "this is val1" in config
	public static void setValueFromConfig(String value, String data) {
		try {
			// get the lines in the config
			BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));

            ArrayList<String> lines = new ArrayList<String>();

            String l;
            while ((l = bufferedReader.readLine()) != null) {
				lines.add(l);
            }
            bufferedReader.close();

			// modify the values from the config
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);

				// dont parse comments
				if (!line.contains("#") && line.length() > 1 && line.contains(":")) {
					// if line (value: data) -> substring to : (value) -> equals value
					if (value.equals(line.substring(0, line.indexOf(":")))) {
						// its the line being modified
						lines.set(i, value + ": " + data);
					}
				}
			}

	        //overwrite the rest
            FileWriter writer = new FileWriter(configFile);

            //write array back to new file
            for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i) + System.lineSeparator());
			}

            writer.close();
	    } catch (Exception e) {
	    	Hytools.log.info("Problem writing file. " + e);
	    }
	}

	//delete the config file
	public static void delete() {
		if (configFile.delete())
			Hytools.log.info("Config file has been succesfully deleted.");
		else
			Hytools.log.info("Error! Config file couldn't be deleted!");
	}

	//create a config file
	public static void create() {
		try {
			if (configFile.createNewFile()) {
				FileWriter w = new FileWriter(configFile, true);
				w.write("# Hytools v" + Reference.VERSION + " Config" + System.lineSeparator());
				w.write("# Hey! I suggest you use the ingame commands "
						+ "instead of editing the config directly instead." + System.lineSeparator());
				w.write(System.lineSeparator());
				w.write("# Party Guess Config" + System.lineSeparator());
				w.write("party-guess_toggled: true" + System.lineSeparator());
				w.write("party-guess-guildcheck_toggled: true" + System.lineSeparator());
				w.write(System.lineSeparator());
				w.write("# Nick Alert Config" + System.lineSeparator());
				w.write("nick-alert_toggled: true" + System.lineSeparator());
				w.close();
				
				Hytools.log.info(I18n.format("config.new"));
			  } else {
				//dont do anything if the config has alr been made
			    Hytools.log.info(I18n.format("config.exist"));
			    
			    if (isNotCurrentVersion()) {
			    	Hytools.log.info(I18n.format("config.bed"));
			    	//delete and create the new one with the right version
					
			    	delete();
			    	create();
			    }
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// party guess
	public static Boolean getPartyGuess() {
		String str = getValueFromConfig("party-guess_toggled");
		return str.equals("true");
	}
	
	public static void setPartyGuess(Boolean bool) {
		setValueFromConfig("party-guess_toggled", String.valueOf(bool));
	}
	
	// party guess guild
	public static Boolean getPartyGuessGuild() {
		String str = getValueFromConfig("party-guess-guildcheck_toggled");
		return str.equals("true");
	}
	
	public static void setPartyGuessGuild(Boolean bool) {
		setValueFromConfig("party-guess-guildcheck_toggled", String.valueOf(bool));
	}

	// nick alert
	public static Boolean getNickAlert() {
		String str = getValueFromConfig("nick-alert_toggled");
		return str.equals("true");
	}
	
	public static void setNickAlert(Boolean bool) {
		setValueFromConfig("nick-alert_toggled", String.valueOf(bool));
	}
}