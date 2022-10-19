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
                Hytools.log.info("HypixelApiKey.java | 不是一个有效的API密钥!");
                if (sendMessageInChat)
                    Hytools.sendMessage("\u00A74\u00A7lERROR! 来自/api new的API密钥不工作.");
                apiKeySet = false;
                return false;
            }

            //since key has been confirmed working, return in chat and set it
            apiKey = key;
            apiKeySet = true;
            
            if (sendMessageInChat)
                Hytools.sendMessage("\u00A72在Hytools中已经成功地设置了Hypixel的API密钥。.");

            Hytools.log.info("HypixelApiKey已被成功设置.");
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
    		Hytools.log.info("由于配置中的api密钥是正确的,/api new被跳过了。. ");
    		return true;
    	} else {
            return false;
        }
    }
}
