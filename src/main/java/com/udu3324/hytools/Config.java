package com.udu3324.hytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraftforge.fml.common.Loader;

public class Config {
	public static File configFile = new File(Loader.instance().getConfigDir(), "hytools.cfg");
    
	public static Boolean isNotCurrentVersion() throws IOException {
		BufferedReader brTest = new BufferedReader(new FileReader(configFile));
		String text = brTest.readLine();
		brTest.close();
		
		if (!text.contains(Reference.VERSION)) return true;
		return false;
	}
	
	public static String getValueFromConfig(String value) {
		//example:
		//getValueFromConfig("api-key") returns qergijoiwequjfboqwiuefnweiofnwe
		
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
	
	public static void setValueFromConfig(String value, String data) {
		//example:
		//setValueFromConfig("api-key", "qergijoiwequjfboqwiuefnweiofnwe")
		
		try {
			// input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader(configFile));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;

	        while ((line = file.readLine()) != null) {
	            if (line.contains(value)) {
	            	line = value + ": " + data;
		        }
	            inputBuffer.append(line);
	            inputBuffer.append('\n');
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(configFile);
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	    } catch (Exception e) {
	    	Hytools.log.info("Problem writing file.");
	    }
	}
	
	public static void delete() {
		//deletes the config file
		if (configFile.delete()) {
			Hytools.log.info("Config file has been succesfully deleted.");
		} else {
			Hytools.log.info("Error! Config file couldn't be deleted!");
		}
	}

	public static void create() {
		try {
			if (configFile.createNewFile()) {
				FileWriter w = new FileWriter(configFile, true);
				w.write("# Hytools v" + Reference.VERSION + " Config" + System.lineSeparator());
				w.write("# Hey! I suggest you use the ingame commands "
						+ "instead of editing the config directly instead." + System.lineSeparator());
				w.write(System.lineSeparator());
				w.write("# Hypixel API Key" + System.lineSeparator());
				w.write("api-key: empty" + System.lineSeparator());
				w.write(System.lineSeparator());
				w.write("# Party Guess Config" + System.lineSeparator());
				w.write("party-guess_toggled: true" + System.lineSeparator());
				w.write("party-guess-friendmsg_toggled: true" + System.lineSeparator());
				w.write("party-guess-guildcheck_toggled: true" + System.lineSeparator());
				w.write(System.lineSeparator());
				w.write("# Nick Alert Config" + System.lineSeparator());
				w.write("nick-alert_toggled: true" + System.lineSeparator());
				w.write("nick-alert-hypixel-api_toggled: false" + System.lineSeparator());
				w.close();
				
				Hytools.log.info("new config has been created (good)");
			  } else {
				//dont do anything if the config has alr been made
			    Hytools.log.info("config already exists (good)");
			    
			    if (isNotCurrentVersion()) {
			    	Hytools.log.info("config file is at the wrong version (bad)");
			    	//delete and create the new one with the right version
			    	String apiKey = getStoredAPIKey();
			    	
			    	delete();
			    	create();
			    	
			    	setStoredAPIKey(apiKey);
			    }
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// premade methods to do the ones above automatically
	public static String getStoredAPIKey() {
		return getValueFromConfig("api-key");
	}
	
	public static void setStoredAPIKey(String key) {
		setValueFromConfig("api-key", key);
	}
	
	public static Boolean getPartyGuess() {
		String str = getValueFromConfig("party-guess_toggled");
		if (str.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setPartyGuess(Boolean bool) {
		setValueFromConfig("party-guess_toggled", String.valueOf(bool));
	}
	
	public static Boolean getPartyGuessFriend() {
		String str = getValueFromConfig("party-guess-friendmsg_toggled");
		if (str.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setPartyGuessFriend(Boolean bool) {
		setValueFromConfig("party-guess-friendmsg_toggled", String.valueOf(bool));
	}
	
	public static Boolean getPartyGuessGuild() {
		String str = getValueFromConfig("party-guess-guildcheck_toggled");
		if (str.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setPartyGuessGuild(Boolean bool) {
		setValueFromConfig("party-guess-guildcheck_toggled", String.valueOf(bool));
	}
	
	public static Boolean getNickAlert() {
		String str = getValueFromConfig("nick-alert_toggled");
		if (str.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setNickAlert(Boolean bool) {
		setValueFromConfig("nick-alert_toggled", String.valueOf(bool));
	}
	
	public static Boolean getNickAlertHypixelAPI() {
		String str = getValueFromConfig("nick-alert-hypixel-api_toggled");
		if (str.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setNickAlertHypixelAPI(Boolean bool) {
		setValueFromConfig("nick-alert-hypixel-api_toggled", String.valueOf(bool));
	}
}