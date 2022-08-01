package com.udu3324.hytools.hyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.udu3324.hytools.Hytools;

public class FriendsOfUUID {
    //FriendsOfUUID.get(uuid) returns a arraylist of uuids of the friends of uuid provided
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
        ArrayList<String> fSL = new ArrayList<String>();

        try {
            String url = "https://api.hypixel.net/friends?key=" + HypixelApiKey.apiKey + "&uuid=" + UUID;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
            if (responseCode != 200) {
                Hytools.log.info("Not a valid API key!");
                return null;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();
            int occurrencesOfUUIDSender = countOccurrences(raw, "uuidSender");
            int occurrencesOfUUIDReceiver = countOccurrences(raw, "uuidReceiver");
            int temp1 = 0;
            int temp2 = 0;

            while (temp1 != occurrencesOfUUIDSender) {
                int startOfUUID = raw.indexOf("\",\"uuidSender\":\"", temp2) + 16;
                int endOfUUID = startOfUUID + 32;

                String uuid = raw.substring(startOfUUID, endOfUUID);

                if (!uuid.equals(UUID)) { //if uuid does not equal the starting uuid
                    fSL.add(uuid);
                }
                temp1++;
                temp2 = startOfUUID;
            }

            //reset temp values for the uuidReciever
            temp1 = 0;
            temp2 = 0;

            while (temp1 != occurrencesOfUUIDReceiver) {
                int startOfUUID = raw.indexOf("\",\"uuidReceiver\":\"", temp2) + 18;
                int endOfUUID = startOfUUID + 32;

                String uuid = raw.substring(startOfUUID, endOfUUID);

                if (!uuid.equals(UUID)) { //if uuid does not equal the starting uuid
                    fSL.add(uuid);
                }
                temp1++;
                temp2 = startOfUUID;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fSL;
    }
}
