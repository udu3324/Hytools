package com.udu3324.hytools.tools.partyguess;

import java.util.ArrayList;

import com.udu3324.hytools.HytoolsConfig;
import com.udu3324.hytools.hyapi.GuildOfUUID;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.IGN;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

public class GuildCheck {
	static Boolean onlyDoOnce = true;
	static String firstUUID = "";
	
	static ArrayList<String> uuidArray = new ArrayList<>();
	
	private static ArrayList<String> checkIfAnyStringInArrayMatch(ArrayList<String> current, ArrayList<String> checkWith) {
        ArrayList<String> matched = new ArrayList<>();

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
        return matched;
    }
	
	public static String reset() {
		//disable party guess guild if toggled
		if (!HytoolsConfig.partyGuessGuild) return null;
    	
		ArrayList<String> guildInfo = GuildOfUUID.get(firstUUID);
		
		//check if guildInfo is not null or empty
		if (guildInfo == null) {
			return null;
		} else if (guildInfo.isEmpty()) {
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
			String person1 = RankOfUUID.get(firstUUID) + IGN.get(firstUUID) + "§6";
			String person2 = playerList + "§6. (" + guildName + ")";
			returningString = I18n.format("partyguess.guildcheck", person1, person2);
			
			if (playerList.equals(IGN.get(firstUUID))) returningString = null;
			if (playerList.isEmpty()) returningString = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		onlyDoOnce = true;
		return returningString;
	}
	
	public static void store(String username) {
		if (!HytoolsConfig.partyGuessGuild) return;
    	
		//convert username to uuid
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
