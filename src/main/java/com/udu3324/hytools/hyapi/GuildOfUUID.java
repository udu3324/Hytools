package com.udu3324.hytools.hyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;

public class GuildOfUUID {
    //GuildOfUUID.get(uuid) returns a arraylist of the guild they're in + the people in the guild

    //countOccurrences(String str, String word) counts the occurrences of a word in a string
	static int countOccurrences(String str, String word) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = str.indexOf(word, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += word.length();
            }
        }

        return count;
    }
	
	public static ArrayList<String> get(String UUID) {
    	ArrayList<String> finalList = new ArrayList<String>();
    	
        try {
            URL obj = new URL("https://api.hypixel.net/guild?key=" + HypixelApiKey.apiKey + "&player=" + UUID);

            //request for the guild of player uuid url
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();
            
            //check if player is in a guild
            if (raw.contains("{\"success\":true,\"guild\":null}"))
            	return null;
            
            //first, add the guild name to the arrayList
            int indexOfGuildName = raw.indexOf("\",\"name\":\"") + 10;
            int lastIndexOfGuildName = raw.indexOf("\",\"", indexOfGuildName);
            
            String guildName = raw.substring(indexOfGuildName, lastIndexOfGuildName);
            finalList.add(guildName);
            
            //then add the uuids that are in the guild
            int numberOfMembers = countOccurrences(raw, "{\"uuid\":\"");
            
            int offset = 0;
            
            for (int i=0; i<numberOfMembers; i++) { 
                int indexOfUUID = raw.indexOf("{\"uuid\":\"", offset) + 9;
                int endingIndexOfUUID = raw.indexOf("\",\"rank\":\"", indexOfUUID);
                
                String uuid = raw.substring(indexOfUUID, endingIndexOfUUID);
                finalList.add(uuid);
                
                offset = endingIndexOfUUID;
            }
            
            return finalList;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return finalList;
    }
}
