package com.udu3324.hytools;

import java.util.ArrayList;

import com.udu3324.hytools.hyapi.FriendsOfUUID;
import com.udu3324.hytools.hyapi.RankOfUUID;
import com.udu3324.hytools.mcapi.IGN;
import com.udu3324.hytools.mcapi.UUID;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class FCheck extends CommandBase {

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
        // /fcheck (invalid)
        if (args.length < 2) {
            sender.addChatMessage(new ChatComponentText(
                    "\n" + EnumChatFormatting.RED + I18n.format("fc.Tanearq")));
            return;
        }

        // /fcheck ???? ????
        if (args.length == 2) {
            final String player1 = args[0];
            final String player2 = args[1];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 检查两个玩家，看看他们是不是真的
                        String uuid1 = UUID.get(player1);
                        if (uuid1.equals(I18n.format("fc.niu"))) {
                            Hytools.sendMessage("\u00A7C" + player1 + I18n.format("fc.nop"));
                            return;
                        }

                        String uuid2 = UUID.get(player2);
                        if (uuid2.equals(I18n.format("fc.niu"))) {
                            Hytools.sendMessage("\u00A7C" + player2 + I18n.format("fc.nop"));
                            return;
                        }

                        // 获取玩家1的好友
                        ArrayList<String> listOfFriends = FriendsOfUUID.get(uuid1);
                        if (listOfFriends == null) {
                            Hytools.log.info(I18n.format("fc.api"));
                            Hytools.sendMessage(
                                    I18n.format("fc.sta"));
                            return;
                        }

                        // return result of player2 is found in friends list of player1
                        if (listOfFriends.contains(uuid2))
                            Hytools.sendMessage("\u00A7A" + RankOfUUID.get(uuid1) + IGN.get(uuid1) + I18n.format("fcheck.yes") + RankOfUUID.get(uuid2) + IGN.get(uuid2) + "\u00A7A.");
                        else
                            Hytools.sendMessage("\u00A7C" + RankOfUUID.get(uuid1) + IGN.get(uuid1) + I18n.format("fcheck.not") + RankOfUUID.get(uuid2) + IGN.get(uuid2) + "\u00A7C.");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        if (args.length >= 3) {
            sender.addChatMessage(new ChatComponentText(
                    "\n" + EnumChatFormatting.RED + I18n.format("fc.Tanearw")));
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