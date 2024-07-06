package com.udu3324.hytools.commands;

import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.HytoolsConfig;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;

@Command(value = "scanfornicks", description = "This scans all the players in the game to check for nicks.")
public class ScanForNicks {
    public static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> users;

    public static boolean running = false;

    @Main
    private void main() {
        if (running) {
            UChat.chat("&4Currently scanning through " + users.size() + " users. Please do not run the command.");
            return;
        }

        if (usernames.size() > 20 && !HytoolsConfig.unlimitedScan) {
            UChat.chat("&4You tried to scan a lobby with more than 20 players. (" + usernames.size() + ") This isn't recommended as you could be rate-limited by Mojang. Go to config (R-SHIFT) to remove this limit.");
            return;
        }

        //save the usernames before another person could join and cause ConcurrentModificationException
        users = new ArrayList<>(usernames);

        UChat.chat("&6Scanning " + users.size() + " users if they're nicked. Please wait.");

        new Thread(() -> {
            running = true;

            ArrayList<String> nicks = new ArrayList<>();
            int failedScans = 0;

            //scan through all the stored users
            for (String username : users) {
                try {
                    Thread.sleep(500);
                    String test = UUID.get(username);

                    if (test == null) {
                        nicks.add(username);
                    } else if (test.equals("!!!RateLimited!!!")) {
                        Hytools.log.info("Rate-limited while scanning for nicks. Retrying.");
                        //try one more time, but slower
                        Thread.sleep(1000);

                        test = UUID.get(username);
                        if (test == null) {
                            nicks.add(username);
                        } else {
                            failedScans++;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            running = false;

            //add a string to all messages if there were failed requests
            String failedRequests = "";
            if (failedScans > 1) {
                failedRequests = "&e" + failedScans + " player(s) couldn't be scanned.";
            }

            if (nicks.isEmpty()) {
                UChat.chat("&2No nicked users found. " + failedRequests);
                return;
            }

            //there may be npc usernames passed through. de-highlight them
            for (String nicked : nicks) {
                if (nicked.length() == 10) {
                    nicks.set(nicks.indexOf(nicked), "&8" + nicked);
                } else {
                    nicks.set(nicks.indexOf(nicked), "&5" + nicked);
                }
            }

            if (nicks.size() == 1) {
                UChat.chat(nicks.get(0) + " &5" + I18n.format("nickalert.isnicknamed") + " " + failedRequests);
                return;
            }

            //big list, format it
            String delimiter = ", ";
            String lastDelimiter = " and ";
            int listSize = nicks.size();

            String formatArray = String.join(delimiter, nicks.subList(0, listSize - 1)) + lastDelimiter + nicks.get(listSize - 1);

            UChat.chat(formatArray + "&5 are nicked. " + failedRequests);
        }).start();
    }
}
