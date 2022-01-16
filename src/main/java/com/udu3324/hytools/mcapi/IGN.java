package com.udu3324.hytools.mcapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Hytools;

public class IGN {
    public static String get(String str) throws Exception {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + str;
        URL obj = new URL(url);
        if (url.contains(" ") || url.contains(">")) {
            return "Not a IGN or UUID!";
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
        if (responseCode != 200) {
            Hytools.log.info("Not a IGN! Now trying UUID.");
            return uuidToIGN(str);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response2 = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response2.append(inputLine);
            }
            int idIndex = response2.indexOf("\"name\":\"") + 8;
            int id2Index = response2.indexOf("\",\"id\":\"");
            response2 = new StringBuilder(response2.substring(idIndex, id2Index));

            return response2.toString();
        }
    }

    private static String uuidToIGN(String str) throws Exception {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + str;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + url);
        if (responseCode != 200) {
            return "Not a IGN or UUID!";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response2 = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }

        int idIndex = response2.indexOf("\"name\" : \"") + 10;
        int id2Index = response2.indexOf("\",  \"", idIndex + 1);
        response2 = new StringBuilder(response2.substring(idIndex, id2Index));


        in.close();
        return response2.toString();
    }
}