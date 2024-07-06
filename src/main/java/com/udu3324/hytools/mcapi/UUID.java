package com.udu3324.hytools.mcapi;

import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Hytools;

public class UUID {
    //UUID.get(str) returns uuid from uuid or ign
    //returns null when str is not uuid or ign
    public static String get(String username, boolean... loop) throws Exception {
        //make default value of loop always true unless if specified by internal function
        if (loop.length == 0) {
            loop = new boolean[]{true};
        }

        //create a http request
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: {} | Response Code: {} | URL Requested {}", con.getRequestMethod(), responseCode, url);

        //check if request was successful
        if (responseCode == 429) {
            Hytools.log.info("This request has been rate-limited by minecraft api.");
            return "!!!RateLimited!!!";
        } else if (responseCode != 200) {
            if (loop[0]) {
                //we were given an uuid, check if that was valid
                Hytools.log.info("Not a username, retrying with uuid.");
                if (IGN.get(username, false) == null) {
                    return null;
                } else {
                    return username;
                }
            } else {
                //not real
                return null;
            }
        }

        return Response.parse(con, "id");
    }
}
