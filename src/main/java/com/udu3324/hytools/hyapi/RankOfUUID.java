package com.udu3324.hytools.hyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.udu3324.hytools.Hytools;

public class RankOfUUID {
    // RankOfUUID.get(uuid) returns the UUID's rank color
    // returns default color even if api key is not valid

    public static String get(String UUID) {
        String rank = "";

        try {
            URL obj = new URL("https://api.hypixel.net/player?key=" + HypixelApiKey.apiKey + "&uuid=" + UUID);

            // get player's data url
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: {} | Response Code: {} | URL Requested {}", con.getRequestMethod(), responseCode, obj);

            // return if response is not 200 (ok)
            if (responseCode != 200) {
                Hytools.log.info("RankOfUUID.java | Internal developer key bad. Please report an issue to udu3324.");
                return "§7";
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();

            // parse response and return rank color
            if (raw.contains("\"rank\":\"ADMIN\""))
                rank = "§c";
            else if (raw.contains("\"rank\":\"GAME_MASTER\""))
                rank = "§2";
            else if (raw.contains("\"rank\":\"YOUTUBER\""))
                rank = "§c";
            else if (raw.contains("\"monthlyPackageRank\":\"SUPERSTAR\"") || raw.contains("\"packageRank\":\"SUPERSTAR\""))
                rank = "§6";
            else if (raw.contains("\"newPackageRank\":\"MVP_PLUS\"") || raw.contains("\"packageRank\":\"MVP_PLUS\""))
                rank = "§b";
            else if (raw.contains("\"newPackageRank\":\"MVP\"") || raw.contains("\"packageRank\":\"MVP\""))
                rank = "§b";
            else if (raw.contains("\"newPackageRank\":\"VIP_PLUS\"") || raw.contains("\"packageRank\":\"VIP_PLUS\""))
                rank = "§a";
            else if (raw.contains("\"newPackageRank\":\"VIP\"") || raw.contains("\"packageRank\":\"VIP\""))
                rank = "§a";
            else
                rank = "§7";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rank;
    }
}