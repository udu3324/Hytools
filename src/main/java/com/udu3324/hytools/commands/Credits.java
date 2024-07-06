package com.udu3324.hytools.commands;

import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import com.udu3324.hytools.Hytools;

@Command(value = Hytools.MOD_ID, description = "About Hytools")
public class Credits {
    @Main
    private void main() {
        Hytools.log.info("Sending Hytools Credits <3");

        UChat.chat("\n&6[+]= Hytools v" + Hytools.VERSION + " =[+]\n" +
                "&7Credits: &2Mod-udu3324 &9I18n-lroj &5Original-wateTina\n" +
                "&8Hytools is a mod that adds useful features to make the experience better on Hypixel. Configure using R-Shift.\n" +
                "/scanfornicks - This scans all the players in the game to check for nicks.");
    }
}