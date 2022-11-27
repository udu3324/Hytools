package com.udu3324.hytools.tools.partyguess;

import java.util.ArrayList;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.FriendsOfUUID;
import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.IGN;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

public class FriendCheck {
	// this array list contains the uuids scanning for friends that are in the list
    public static ArrayList<String> uuidArray = new ArrayList<String>();

    private static ArrayList<String> checkIfAnyStringInArrayMatch(ArrayList<String> current, ArrayList<String> checkWith) {
        ArrayList<String> matched = new ArrayList<String>();

        for (int i=0; i!=checkWith.size(); i++) {  //for each element in checkWith
            String uuidCheckingWith = checkWith.get(i);
            if (current.contains(uuidCheckingWith)) { //if current contains that uuid

                //turn uuid into ign
                try {
                    uuidCheckingWith = RankOfUUID.get(uuidCheckingWith) + IGN.get(uuidCheckingWith);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                matched.add(uuidCheckingWith);
            }
        }
        if (matched.size() == 0) {
            matched.add("None matching.");
            return matched;
        }
        return matched;
    }

    public static String reset() {
        //disable party guess friend
    	if (!Config.getPartyGuessFriend()) return null;
    	
        int numOfUUIDS = uuidArray.size();
        if (numOfUUIDS == 1) {
        	uuidArray.clear();
            return null;
        }
        ArrayList<String> matchingFriendInParty = new ArrayList<String>();
        String uuidFriendsWith = "";
        
        //get the first person put in the uuid array
        ArrayList<String> fOUUID = new ArrayList<String>();
        
        String currentUUID = uuidArray.get(0);
        //return if hypixel api key not set properly
        if (HypixelApiKey.apiKeySet) {
        	fOUUID = FriendsOfUUID.get(currentUUID);
        } else {
            Hytools.log.info("FriendCheck.java | Not a valid API key!");
        	Hytools.sendMessage(I18n.format("friends.error"));
        	uuidArray.clear();
        	return null;
        }

        //return if friends of uuid is empty/not real
        if (fOUUID == null || fOUUID.size() == 0) {
        	uuidArray.clear();
        	return null;
        }

        matchingFriendInParty = checkIfAnyStringInArrayMatch(fOUUID, uuidArray);
        uuidArray.clear();

        //return if no friends in party
        if (matchingFriendInParty.get(0).contains("None matching.")) return null;
    
        uuidFriendsWith = currentUUID;

        //turn uuids into usernames (colored)
        try {
            uuidFriendsWith = RankOfUUID.get(uuidFriendsWith) + IGN.get(uuidFriendsWith);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return uuidFriendsWith + matchingFriendInParty.toString().replace("[", "\u00A72 " + I18n.format("partyguess.friendcheck") + " ").replace("]", "").replace(",", "\u00A72, ") + "\u00A72.";
    }

    public static void store(String username) {
    	if (!Config.getPartyGuessFriend()) return;
    	
        String uid = null;
        try {
            uid = UUID.get(username);
            uid = uid.replace("-", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        uuidArray.add(uid);
    }
}
