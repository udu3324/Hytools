package com.udu3324.hytools.hyapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;

public class HypixelApiKey {
	public static String apiKey;
	public static Boolean apiKeySet = false;
	
    public static Boolean setKey(String key) {
    	Boolean setCorrectly = null;
        try {
            String url = "https://api.hypixel.net/key?key=" + key;
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);

            if (responseCode != 200) {
                Hytools.log.info("Not a valid API key!");
                setCorrectly = false;
                return false;
            }

            apiKey = key;
            apiKeySet = true;
            
            Hytools.log.info("HypixelApiKey has been successfully set.");
            Config.setStoredAPIKey(key);
            setCorrectly = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return setCorrectly;
    }
    
    public static Boolean setKeyFromConf() {
    	String apiKeyFromConf = Config.getStoredAPIKey();
    	
    	if (setKey(apiKeyFromConf)) {
    		Hytools.log.info("since the api key from config is correct, /api new has been skipped. ");
    		return true;
    	}
    	
		return false;
    }
}
