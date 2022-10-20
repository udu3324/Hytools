package com.udu3324.hytools.mcapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Hytools;

public class UUID {
    //UUID.get(str) returns uuid from uuid or ign
    //returns "Not a IGN or UUID!" when str is not uuid or ign
    public static String get(String str) throws Exception {
        if (str.contains(" ") || str.contains(">"))
            return "Not a IGN or UUID!";

        URL obj = new URL("https://api.mojang.com/users/profiles/minecraft/" + str);
        
        //request for url
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());

        //only return if response is not 200 (ok)
        if (responseCode != 200) {
            Hytools.log.info("Not a IGN! Now trying UUID.");
            return uuidToIGN(str);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response2 = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }
        in.close();

        //parse json response for uuid
        int idIndex = response2.indexOf("\",\"id\":\"") + 8;
        return new StringBuilder(response2.substring(idIndex, idIndex + 32)).toString();
    }

    private static String uuidToIGN(String str) throws Exception {
        URL obj = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + str);
        //request for url
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: " + con.getRequestMethod() + " | Response Code: " + responseCode + " | URL Requested " + obj.toString());
        //if str returns 200, it is a correct uuid
        if (responseCode != 200)
            return "Not a IGN or UUID!";
        else
            return str.replace("-", "");
    }
}
