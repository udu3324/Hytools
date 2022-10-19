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
            URL obj = new URL("https://api.hypixel.net/friends?key=" + HypixelApiKey.apiKey + "&uuid=" + UUID);

            //request for player's friends using uuid url
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());

            //return only if response is not 200 (ok)
            if (responseCode != 200) {
                Hytools.log.info("FriendsOfUUID.java | 不是一个有效的API密钥!");
                return null;
            }

            //turn response into a string
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();

            //parse json response of uuids
            int occurrencesOfUUIDSender = countOccurrences(raw, "uuidSender");
            int occurrencesOfUUIDReceiver = countOccurrences(raw, "uuidReceiver");
            int temp1 = 0;
            int temp2 = 0;

            //for each friend request that has been sent and accepted
            while (temp1 != occurrencesOfUUIDSender) {
                int startOfUUID = raw.indexOf("\",\"uuidSender\":\"", temp2) + 16;
                int endOfUUID = startOfUUID + 32;

                String uuid = raw.substring(startOfUUID, endOfUUID);

                //only add the uuid if its not the player getting friends of
                if (!uuid.equals(UUID)) {
                    fSL.add(uuid);
                }
                temp1++;
                temp2 = startOfUUID;
            }

            //reset temp values for the uuidReciever
            temp1 = 0;
            temp2 = 0;

            //for each friend request that has been recieved and accepted
            while (temp1 != occurrencesOfUUIDReceiver) {
                int startOfUUID = raw.indexOf("\",\"uuidReceiver\":\"", temp2) + 18;
                int endOfUUID = startOfUUID + 32;

                String uuid = raw.substring(startOfUUID, endOfUUID);

                //only add the uuid if its not the player getting friends of
                if (!uuid.equals(UUID)) {
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
