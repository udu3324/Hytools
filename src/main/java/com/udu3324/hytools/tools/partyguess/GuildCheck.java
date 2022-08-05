package com.udu3324.hytools.tools.partyguess;

import java.util.ArrayList;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.GuildOfUUID;
import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.IGN;
import com.udu3324.hytools.mcapi.UUID;

public class GuildCheck {
	static Boolean onlyDoOnce = true;
	static String firstUUID = "";
	
	static ArrayList<String> uuidArray = new ArrayList<String>();
	
	private static ArrayList<String> checkIfAnyStringInArrayMatch(ArrayList<String> current, ArrayList<String> checkWith) {
        ArrayList<String> matched = new ArrayList<String>();

        for (int i=0; i!=checkWith.size(); i++) {  //for each element in checkWith
            String uuidCheckingWith = checkWith.get(i);
            if (current.contains(uuidCheckingWith)) { //if current contains that uuid

                //turn uuid into ign
                try {
                    uuidCheckingWith = IGN.get(uuidCheckingWith);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                matched.add(uuidCheckingWith);
            }
        }
        if (matched.size() == 0) {
            return matched;
        }
        return matched;
    }
	
	public static String reset() {
		if (!Config.getPartyGuessGuild()) {
    		//this is to disable party guess guilds if it's disabled in config
    		return null;
    	}
		
		ArrayList<String> guildInfo;
		if (HypixelApiKey.apiKeySet) {
			 guildInfo = GuildOfUUID.get(firstUUID);
        } else {
			Hytools.log.info("GuildCheck.java | Not a valid API key!");
        	Hytools.sendMessage("\u00A74\u00A7lERROR! (guild info couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
        	uuidArray.clear();
        	return null;
        }
		
		
		//check if guildInfo is not null or empty
		if (guildInfo == null) {
			return null;
		} if (guildInfo.isEmpty()) {
			return null;
		}
		
		String guildName = guildInfo.get(0);
		guildInfo.remove(0);
		
		
		ArrayList<String> uuidOfSameGuild = checkIfAnyStringInArrayMatch(guildInfo, uuidArray);
		
		//convert each element to a username
		for (int i=0; i!=uuidOfSameGuild.size(); i++) {
			try {
				uuidOfSameGuild.set(i, RankOfUUID.get(uuidOfSameGuild.get(i)) + IGN.get(uuidOfSameGuild.get(i)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//convert arraylist to string and replace , with and
		String playerList = uuidOfSameGuild.toString().replace(",", " and").replace("[", "").replace("]", "");
		String returningString = null;
		
		try {
			returningString = RankOfUUID.get(firstUUID) + IGN.get(firstUUID) + "\u00A76 is in the same guild as " + playerList + "\u00A76. (" + guildName + ")";
			
			if (playerList.equals(IGN.get(firstUUID))) returningString = null;
			if (playerList.equals("")) returningString = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		onlyDoOnce = true;
		return returningString;
	}
	public static void store(String username) {
		//convert username to uuid
		if (!Config.getPartyGuessGuild()) {
    		//this is to disable party guess guilds if it's disabled in config
    		return;
    	}
    	
        String uid = null;
        try {
            uid = UUID.get(username);
            uid = uid.replace("-", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //if the uuid is the first to store
        if (onlyDoOnce) {
        	//clear array for new uuids
        	uuidArray.clear();
        	//set first uuid
        	firstUUID = uid;
        	onlyDoOnce = false;
        } else {
        	uuidArray.add(uid);
        }
	}
}
