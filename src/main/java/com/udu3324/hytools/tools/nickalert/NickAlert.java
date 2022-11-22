package com.udu3324.hytools.tools.nickalert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.mcapi.UUID;

public class NickAlert extends Thread {
	public static void run(String username) {
		// if tool is toggled off, dont run it
		if (!Config.getNickAlert())
			return;

		String uuid = null;

		try {
			// check if the user exists in minecraft's database
			uuid = UUID.get(username);

			// broken wifi or something
			if (uuid == null) return;

			if (uuid.equals("Not a IGN or UUID!")) {
				// checks if username exists in minecraft api
				Hytools.sendMessage("\u00A75" + username + " is a nicked user!");
				return;
			}

			// dont use hypixel api if it's toggled off
			if (!Config.getNickAlertHypixelAPI()) return;

			// check if user has logged on before
			if (HypixelApiKey.apiKeySet) {
				boolean joinedHypixel = true;

				URL obj = new URL("https://api.hypixel.net/player?key=" + Config.getStoredAPIKey() + "&uuid=" + uuid);

				// get player's data using uuid
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();
				Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode
						+ " | URL Requested " + obj.toString());

				if (responseCode == 403) {
					Hytools.log.info("NickAlert.java | Not a valid API key!");
					Hytools.sendMessage(
							"\u00A74\u00A7lFATAL ERROR! (this shouldn't even be possible, contact me on discord _._#3324) (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
					joinedHypixel = true;
					return;
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String bR = in.readLine();
				in.close();

				// if it contains the string below, the player hasn't joined hypixel before
				joinedHypixel = bR.equals("{\"success\":true,\"player\":null}");
				// if it returns anything else, the player has joined before

				if (!joinedHypixel)
					Hytools.sendMessage("\u00A75" + username + " is a nicked user!");
			} else {
				Hytools.log.info("NickAlert.java | Not a valid API key!");
				Hytools.sendMessage(
						"\u00A74\u00A7lERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
