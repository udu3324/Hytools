package com.udu3324.hytools.tools.nickalert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.mcapi.UUID;

public class NickAlert {
	//hasLoggedOntoHypixel() returns if uuid has logged onto hypixel
	//returns false if api key is not set right
	static Boolean hasLoggedOntoHypixel(String UUID) {
        try {
            URL obj = new URL("https://api.hypixel.net/player?key=" + Config.getStoredAPIKey() + "&uuid=" + UUID);

			//get player's data using uuid
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());
            
            if (responseCode == 403) {
				Hytools.log.info("NickAlert.java | 不是一个有效的API密钥!");
            	Hytools.sendMessage("\u00A74\u00A7lFATAL ERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
            	return false;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String bR = in.readLine();
            in.close();

			//return boolean if response says player has not logged on hypixel yet
            return bR.equals("{\"success\":true,\"player\":null}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public static void checkIfNicked(String message, Boolean hytillities) {
		//disable nick alert if it's disabled in config
		if (!Config.getNickAlert())
			return;
    	
		String username;
		//set username based of if player is using hytilities
        if (hytillities) {
        	username = message.substring(message.indexOf(" ", 3) + 1, message.length());
        } else {
        	if (message.indexOf(" ") != -1)
        		username = message.substring(0, message.indexOf(" "));
        	else
        		username = message;
        }
		
		String uuid = null;
		try {
			//simply check if the user exists in minecraft's database
			uuid = UUID.get(username);
			if (uuid.equals("不是IGN或UUID!")) {
				//checks if username exists in minecraft api
				Hytools.sendMessage("\u00A75" + username + " 是nick用户!");
				return;
			}
			
			//disable nick alert hypixel api below if disabled in config
			if (!Config.getNickAlertHypixelAPI())
				return;
			
			//check if user has logged on before
			if (HypixelApiKey.apiKeySet) {
				if (hasLoggedOntoHypixel(uuid))
					Hytools.sendMessage("\u00A75" + username + " 是nick用户!");
	        } else {
				Hytools.log.info("NickAlert.java | 不是一个有效的API密钥!");
	        	Hytools.sendMessage("\u00A74\u00A7lERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
