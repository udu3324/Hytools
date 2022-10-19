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

    static String symbol = "\u00a7";

    public static String get(String UUID) {
        String rank = "";

        try {
            URL obj = new URL("https://api.hypixel.net/player?key=" + HypixelApiKey.apiKey + "&uuid=" + UUID);

            // get player's data url
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode
                    + " | URL Requested " + obj.toString());

            // return if response is not 200 (ok)
            if (responseCode != 200) {
                Hytools.log.info("RankOfUUID.java | 不是一个有效的API密钥!");
                return symbol + "7";
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();

            // parse response and return rank color
            if (raw.indexOf("\"rank\":\"ADMIN\"") != -1)
                rank = symbol + "c";
            else if (raw.indexOf("\"rank\":\"GAME_MASTER\"") != -1)
                rank = symbol + "2";
            else if (raw.indexOf("\"rank\":\"YOUTUBER\"") != -1)
                rank = symbol + "c";
            else if (raw.indexOf("\"monthlyPackageRank\":\"SUPERSTAR\"") != -1 || raw.indexOf("\"packageRank\":\"SUPERSTAR\"") != -1)
                rank = symbol + "6";
            else if (raw.indexOf("\"newPackageRank\":\"MVP_PLUS\"") != -1 || raw.indexOf("\"packageRank\":\"MVP_PLUS\"") != -1)
                rank = symbol + "b";
            else if (raw.indexOf("\"newPackageRank\":\"MVP\"") != -1 || raw.indexOf("\"packageRank\":\"MVP\"") != -1)
                rank = symbol + "b";
            else if (raw.indexOf("\"newPackageRank\":\"VIP_PLUS\"") != -1  || raw.indexOf("\"packageRank\":\"VIP_PLUS\"") != -1)
                rank = symbol + "a";
            else if (raw.indexOf("\"newPackageRank\":\"VIP\"") != -1 || raw.indexOf("\"packageRank\":\"VIP\"") != -1)
                rank = symbol + "a";
            else
                rank = symbol + "7";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rank;
    }
}