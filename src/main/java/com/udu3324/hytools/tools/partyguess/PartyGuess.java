package com.udu3324.hytools.tools.partyguess;

import com.udu3324.hytools.HytoolsConfig;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PartyGuess {

    public static boolean doOnceBeforeTimeReset = true;

    // this array list contains the users in the time period
    public static ArrayList<String> tempUserArray = new ArrayList<>();

    // time period for how many users can join
    public static int delay = 10; // ms

    public static void guessMessageParty(String username) {
        //if tool is toggled off, don't run it
        if (!HytoolsConfig.partyGuess) return;

        //run once and start timer 
        if (doOnceBeforeTimeReset) {
            tempUserArray.clear();

            // schedule code
            TimerTask task = new TimerTask() {
                public void run() {
                    // after delay, allow array to clear again and send chat msg (if there's more
                    // than one user stored)
                    if (tempUserArray.size() > 1) {
                        // for each user in array, add it to the checks
                        for (int i = 0; i != tempUserArray.size(); i++) {
                            String ign = tempUserArray.get(i);
                            GuildCheck.store(ign);
                        }

                        // for each user, set the color to their rank
                        try {
                            for (int i = 0; i != tempUserArray.size(); i++) {
                                String uuid = UUID.get(tempUserArray.get(i));
                                tempUserArray.set(i, RankOfUUID.get(uuid) + tempUserArray.get(i));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //create a string of the array and remove []
                        String raw = tempUserArray.toString().replace("[", "").replace("]", "");

                        // user and user string if there's only 2 people
                        if (tempUserArray.size() == 2) {
                            raw = raw.replace(",", "§3 and");
                        } else if (tempUserArray.size() >= 3) {
                            // user, user and user string if there's more than 3 people

                            // get the pos/index of the last comma in msg
                            int lastComma = raw.lastIndexOf(",");

                            // delete comma and replace with and
                            StringBuilder newString = new StringBuilder(raw);
                            newString.deleteCharAt(lastComma);
                            newString.replace(lastComma, lastComma, "§3 and");
                            raw = newString.toString();
                        }
                        raw = "§3" + raw + "§3";
                        Hytools.sendMessage(I18n.format("partyguess.party", raw));

                        // guild check
                        String guildCheck = GuildCheck.reset();
                        if (guildCheck != null) Hytools.sendMessage("§6" + guildCheck);
                    }

                    Hytools.log.info("Reset party guess {}", tempUserArray.toString());

                    doOnceBeforeTimeReset = true;
                }
            };
            Timer timer = new Timer("Timer");

            timer.schedule(task, delay);

            doOnceBeforeTimeReset = false;
        }

        tempUserArray.add(username);
        Hytools.log.info("{}{}", I18n.format("partyguess.username"), tempUserArray.toString());
    }
}
