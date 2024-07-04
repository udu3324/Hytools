package com.udu3324.hytools.commands;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import net.minecraft.client.Minecraft;
@Command(value = "scanfornicks", description = "This scans all the players in the game to check for nicks.")
public class ScanForNicks {
    @Main
    private void main() {
        //todo
        System.out.println("yyyyy");
        //Collection<NetworkPlayerInfo> playersC=Minecraft.getMinecraft().getCurrentServerData().playerList;
    }
}
