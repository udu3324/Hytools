package com.udu3324.hytools;

import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class HytoolsConfig extends cc.polyfrost.oneconfig.config.Config {

	@Switch(name = "NickAlert")
	public static boolean nickAlert = true;

	@Switch(name = "PartyGuess")
	public static boolean partyGuess = true;

	@Switch(name = "PartyGuessGuild")
	public static boolean partyGuessGuild = true;

	public HytoolsConfig() {
		super(new Mod(Hytools.MOD_NAME, ModType.UTIL_QOL, "/logo.png"), Hytools.MOD_NAME + ".json");
		initialize();
	}
}