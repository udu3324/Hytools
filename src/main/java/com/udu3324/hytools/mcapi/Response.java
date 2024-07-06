package com.udu3324.hytools.mcapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Response {
    public static String parse(HttpURLConnection connection, String start) throws IOException {
        String startAt = start + "\":\"";

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        String parse = response.toString().replaceAll("\\s", "");

        parse = parse.substring(parse.indexOf(startAt) + startAt.length());
        parse = parse.substring(0, parse.indexOf("\""));

        connection.disconnect();

        return parse;
    }
}
