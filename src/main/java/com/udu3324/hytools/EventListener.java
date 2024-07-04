package com.udu3324.hytools;

import com.udu3324.hytools.tools.nickalert.NickAlert;
import com.udu3324.hytools.tools.partyguess.PartyGuess;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class EventListener {

    //stops worldLoaded checks
    boolean stopChecks = false;

    boolean isOnHypixel = false;

    // ^\+ \([0-9]+\/[0-9]+\) [a-zA-Z0-9_]{1,16}$ | "+ (23/24) NintendoOS"
    private static final Pattern hytillitiesJoin = Pattern.compile("^\\+ \\([0-9]+/[0-9]+\\) [a-zA-Z0-9_]{1,16}$");

    // ^[a-zA-Z0-9_]{1,16} has joined \([0-9]+/[0-9]+\)!$ | "NintendoOS has joined (1/16)!"
    private static final Pattern regularJoin = Pattern.compile("^[a-zA-Z0-9_]{1,16} has joined \\([0-9]+/[0-9]+\\)!$");

    //                        Opponent: [MVP+] NintendoOS
    private static final Pattern duelsJoin = Pattern.compile("^[ \\t]+Opponent:");

    @SubscribeEvent
    public void onWorldLoaded(EntityJoinWorldEvent event) {
        if (stopChecks) return;

        //add this check in case you are on replay mod, where you are not on a single-player world, but also not multiplayer (GitHub/EmeraldWither)
        if (Minecraft.getMinecraft().getCurrentServerData() == null) {
            isOnHypixel = false;
        } else if (Minecraft.getMinecraft().isSingleplayer()) {
            isOnHypixel = false;
        } else {
            String serverIP = Minecraft.getMinecraft().getCurrentServerData().serverIP;
            if (serverIP.toLowerCase().contains("hypixel.net")) {
                isOnHypixel = true;
                stopChecks = true;
            } else {
                isOnHypixel = false;
            }
        }

        //TEMP!!! REMOVE AFTER DEVELOPMENT
        //isOnHypixel = true;
    }

    @SubscribeEvent
    public void onPlayerChat(ClientChatReceivedEvent event) {
        if (!isOnHypixel) return;

        //return if message contains obfuscation
        if (event.message.getFormattedText().contains("§k")) return;

        //get chat without the formatting (color codes)
        String chat = event.message.getUnformattedText();

        //parse chat with regex to get username
        String username;

        if (regularJoin.matcher(chat).find()) {
            username = chat.substring(0, chat.indexOf(" "));
        } else if (hytillitiesJoin.matcher(chat).find()) {
            username = chat.substring(chat.indexOf(" ", 3) + 1);
        } else if (duelsJoin.matcher(chat).find()) {
            username = chat.substring(chat.lastIndexOf(" ") + 1);
        } else {
            //return since there's no username present
            return;
        }

        PartyGuess.guessMessageParty(username);

        new Thread(() -> NickAlert.run(username)).start();
    }
}
