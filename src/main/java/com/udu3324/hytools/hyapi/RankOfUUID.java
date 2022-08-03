package com.udu3324.hytools.hyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.udu3324.hytools.Hytools;

public class RankOfUUID {
    public static String get(String UUID) {
        String rank = "";

        try {
            String url = "https://api.hypixel.net/player?key=" + HypixelApiKey.apiKey + "&uuid=" + UUID;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
            if (responseCode != 200) {
                Hytools.log.info("Not a valid API key!");
                return "\u00a77";
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            ArrayList<String> response = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String raw = response.toString();
            
            if (raw.indexOf("\"rank\":\"ADMIN\"") != -1) {
                rank = "\u00a7c";
            } else if (raw.indexOf("\"rank\":\"GAME_MASTER\"") != -1) {
                rank = "\u00A72";
            } else if (raw.indexOf("\"rank\":\"YOUTUBER\"") != -1) {
                rank = "\u00a7c";
            } else if (raw.indexOf("\"monthlyPackageRank\":\"SUPERSTAR\"") != -1) {
                rank = "\u00A76";
            } else if (raw.indexOf("\"newPackageRank\":\"MVP_PLUS\"") != -1) {
                rank = "\u00a7b";
            } else if (raw.indexOf("\"newPackageRank\":\"MVP\"") != -1) {
                rank = "\u00a7b";
            } else if (raw.indexOf("\"newPackageRank\":\"VIP_PLUS\"") != -1) {
                rank = "\u00a7a";
            } else if (raw.indexOf("\"newPackageRank\":\"VIP\"") != -1) {
                rank = "\u00a7a";
            } else if (raw.indexOf("\"packageRank\":\"SUPERSTAR\"") != -1) {
                rank = "\u00A76";
            } else if (raw.indexOf("\"packageRank\":\"MVP_PLUS\"") != -1) {
                rank = "\u00a7b";
            } else if (raw.indexOf("\"packageRank\":\"MVP\"") != -1) {
                rank = "\u00a7b";
            } else if (raw.indexOf("\"packageRank\":\"VIP_PLUS\"") != -1) {
                rank = "\u00a7a";
            } else if (raw.indexOf("\"packageRank\":\"VIP\"") != -1) {
                rank = "\u00a7a";
            } else {
                rank = "\u00a77";
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rank;
    }
}