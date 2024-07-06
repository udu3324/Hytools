package com.udu3324.hytools.mcapi;

import java.net.HttpURLConnection;
import java.net.URL;

import com.udu3324.hytools.Hytools;

public class IGN {
    //IGN.get(str) returns ign from uuid or ign
    //returns null when str is not uuid or ign
    public static String get(String uuid, boolean... loop) throws Exception {
        //make default value of loop always true unless if specified by internal function
        if (loop.length == 0) {
            loop = new boolean[]{true};
        }

        //create a http request
        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Hytools.log.info("Request Type: {} | Response Code: {} | URL Requested {}", con.getRequestMethod(), responseCode, url);

        if (responseCode == 429) {
            Hytools.log.info("This request has been rate-limited by minecraft api.");
            return "!!!RateLimited!!!";
        } else if (responseCode != 200) {
            if (loop[0]) {
                Hytools.log.info("Not a uuid, retrying with username.");
                if (UUID.get(uuid, false) == null) {
                    return null;
                } else {
                    return uuid;
                }
            } else {
                return null;
            }
        }

        return Response.parse(con, "name");
    }
}