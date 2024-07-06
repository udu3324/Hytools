package com.udu3324.hytools;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HytoolsConfig extends Config {

	@Switch(name = "NickAlert")
	public static boolean nickAlert = true;

	@Switch(name = "PartyGuess")
	public static boolean partyGuess = true;

	@Switch(name = "PartyGuessGuild")
	public static boolean partyGuessGuild = true;

	@Switch(name = "Higher /scanfornicks Limit (not recommended)")
	public static boolean unlimitedScan = false;

	@Button(name = "Hytools v" + Hytools.VERSION, text = "Send Feedback")
	Runnable runnable = () -> {
		Hytools.log.info("Sending to browser");
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/udu3324/hytools"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
	};

	public HytoolsConfig() {
		super(new Mod(Hytools.MOD_NAME, ModType.UTIL_QOL, "/logo.png"), Hytools.MOD_NAME + ".json");
		initialize();
	}
}