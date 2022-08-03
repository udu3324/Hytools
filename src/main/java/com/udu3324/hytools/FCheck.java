package com.udu3324.hytools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.udu3324.hytools.hyapi.FriendsOfUUID;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.IGN;
import com.udu3324.hytools.mcapi.UUID;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class FCheck extends CommandBase {

    private HashMap<String, String> collection = new HashMap<String, String>();

    @Override
    public String getCommandName() {
        return "fcheck";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "fcheck <args>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            sender.addChatMessage(new ChatComponentText(
                    "\n" + EnumChatFormatting.RED + "Not enough arguments! You probably have a typo."));
            return;
        }

        if (args.length == 2) {
            final String player1 = args[0];
            final String player2 = args[1];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // check both players to see if they're real
                        String uuid1 = UUID.get(player1);
                        if (uuid1.equals("Not a IGN or UUID!")) {
                            Hytools.sendMessage("\u00A7C" + player1 + " is not a real player.");
                            return;
                        }

                        String uuid2 = UUID.get(player2);
                        if (uuid2.equals("Not a IGN or UUID!")) {
                            Hytools.sendMessage("\u00A7C" + player2 + " is not a real player.");
                            return;
                        }

                        // get the friends of player1
                        ArrayList<String> listOfFriends = FriendsOfUUID.get(uuid1);
                        if (listOfFriends == null) {
                            Hytools.log.info("FCheck.java | Not a valid API key!");
                            Hytools.sendMessage(
                                    "\u00A74\u00A7lERROR! (player data couldn't be fetched) The API key has not been set yet. Please do \u00A7c\u00A7l/api new\u00A74\u00A7l to fix this.");
                            return;
                        }

                        // return result of player2 is found in friends list of player1
                        if (listOfFriends.contains(uuid2)) {
                            Hytools.sendMessage("\u00A7A" + RankOfUUID.get(uuid1) + IGN.get(uuid1) + "\u00A7A is friends with " + RankOfUUID.get(uuid2) + IGN.get(uuid2) + "\u00A7A.");
                        } else {
                            Hytools.sendMessage("\u00A7C" + RankOfUUID.get(uuid1) + IGN.get(uuid1) + "\u00A7C is not friends with " + RankOfUUID.get(uuid2) + IGN.get(uuid2) + "\u00A7C.");
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        if (args.length >= 3) {
            sender.addChatMessage(new ChatComponentText(
                    "\n" + EnumChatFormatting.RED + "Too many arguments! You probably have a typo."));
            return;
        }

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}