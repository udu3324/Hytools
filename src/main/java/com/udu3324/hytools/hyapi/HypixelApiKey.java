package com.udu3324.hytools.hyapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;

public class HypixelApiKey {
	public static String apiKey;
	public static Boolean apiKeySet = false;
	
    // setKey() sets the hypixel api key in config and updates it realtime for the other mods
    public static Boolean setKey(String key, boolean sendMessageInChat) {
        boolean result = false;
        try {
            URL obj = new URL("https://api.hypixel.net/key?key=" + key);

            //request for url to check if the key setting is working
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());

            //if response code is not 200 (ok) then the api key is not set correctly
            if (responseCode != 200) {
                Hytools.log.info("HypixelApiKey.java | Not a valid API key!");
                if (sendMessageInChat)
                    Hytools.sendMessage("\u00A74\u00A7lERROR! API key from /api new did not work.");
                apiKeySet = false;
                return false;
            }

            //since key has been confirmed working, return in chat and set it
            apiKey = key;
            apiKeySet = true;
            
            if (sendMessageInChat)
                Hytools.sendMessage("\u00A72Hypixel API Key has been succesfully set in Hytools.");

            Hytools.log.info("HypixelApiKey has been successfully set.");
            Config.setStoredAPIKey(key);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    // setKeyFromConf() sets key from config only if the key in config is functional
    public static Boolean setKeyFromConf() {
    	if (setKey(Config.getStoredAPIKey(), false)) {
    		Hytools.log.info("since the api key from config is correct, /api new has been skipped. ");
    		return true;
    	} else {
            return false;
        }
    }
}
