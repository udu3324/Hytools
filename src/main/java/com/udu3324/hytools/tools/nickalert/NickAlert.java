package com.udu3324.hytools.tools.nickalert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.FriendsOfUUID;
import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.mcapi.UUID;

public class NickAlert {
	static Boolean hasLoggedOntoHypixel(String UUID) {
        try {
            String url = "https://api.hypixel.net/player?key=" + Config.getStoredAPIKey() + "&uuid=" + UUID;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
            
            if (responseCode == 403) {
            	Hytools.sendMessage("\u00A74\u00A7lERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
            	return false;
            }
            String bR;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            bR = in.readLine();
            in.close();
            return bR.equals("{\"success\":true,\"player\":null}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public static void checkIfNicked(String message, Boolean hytillities) {
		if (!Config.getNickAlert()) {
    		//this is to disable nick alert if it's disabled in config
			return;
    	}
		
		String username;
        if (hytillities) {
        	username = message.substring(message.indexOf(" ", 3) + 1, message.length());
        } else {
        	if (message.indexOf(" ") != -1) {
        		username = message.substring(0, message.indexOf(" "));
        	} else {
        		username = message;
        	}
        }
		
		String uuid = null;
		try {
			
			uuid = UUID.get(username);
			if (uuid.equals("Not a IGN or UUID!")) {
				//checks if username exists in minecraft api
				Hytools.sendMessage("\u00A75" + username + " is a nicked user!");
				return;
			}
			
			if (!Config.getNickAlertHypixelAPI()) {
	    		//this is to disable nick alert hypixel api if it's disabled in config
				return;
	    	}
			
			Boolean playerData;
			
			//return if api key set wrong
			if (HypixelApiKey.apiKeySet) {
				//checks if the player has any stored data
				playerData = hasLoggedOntoHypixel(uuid);
	        } else {
	        	Hytools.sendMessage("\u00A74\u00A7lERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
	        	return;
	        }
			
			if (!playerData) {
				return;
			} else {
				Hytools.sendMessage("\u00A75" + username + " is a nicked user!");
				return;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
