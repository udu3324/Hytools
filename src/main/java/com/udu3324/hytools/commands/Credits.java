package com.udu3324.hytools.commands;

import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import com.udu3324.hytools.Hytools;
import net.minecraft.client.resources.I18n;

@Command(value = Hytools.MOD_ID, description = "About Hytools")
public class Credits {
    @Main
    private void main() {
        Hytools.log.info("Sending Hytools Credits <3");

        UChat.chat("\n&6[+]= Hytools v" + Hytools.VERSION + " =[+]\n" +
                "&7Credits: &2Mod-udu3324 &9I18n-lroj &5Original-wateTina\n" +
                "&8" + I18n.format("credits.description") + "\n" +
                "/scanfornicks - " + I18n.format("scanfornicks.description"));
    }
}