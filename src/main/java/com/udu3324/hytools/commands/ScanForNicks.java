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
            UChat.chat("&4" + I18n.format("scanfornicks.currently", users.size()));
            return;
        }

        if (usernames.size() > 20 && !HytoolsConfig.unlimitedScan) {
            UChat.chat("&4" + I18n.format("scanfornicks.limit", usernames.size()));
            return;
        }

        //save the usernames before another person could join and cause ConcurrentModificationException
        users = new ArrayList<>(usernames);

        UChat.chat("&6" + I18n.format("scanfornicks.start", users.size()));

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
                        Hytools.log.info(I18n.format("scanfornicks.logratelimit"));
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
                failedRequests = "&e" + I18n.format("scanfornicks.ratelimit", failedScans);
            }

            if (nicks.isEmpty()) {
                UChat.chat("&2" + I18n.format("scanfornicks.none") + " " + failedRequests);
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
                UChat.chat("&5" + I18n.format("nickalert.isnicknamed", nicks.get(0)) + " " + failedRequests);
                return;
            }

            //big list, format it
            String delimiter = ", ";
            String lastDelimiter = " & ";
            int listSize = nicks.size();

            String formatArray = String.join(delimiter, nicks.subList(0, listSize - 1)) + lastDelimiter + nicks.get(listSize - 1) + " &5";

            UChat.chat(I18n.format("scanfornicks.many", formatArray) + " " + failedRequests);
        }).start();
    }
}
