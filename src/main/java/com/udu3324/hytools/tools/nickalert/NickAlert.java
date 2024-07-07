package com.udu3324.hytools.tools.nickalert;

import com.udu3324.hytools.HytoolsConfig;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

public class NickAlert extends Thread {
	public static void run(String username) {
		// if tool is toggled off, don't run it
		if (!HytoolsConfig.nickAlert) return;

		String uuid;

		try {
			// check if the user exists in minecraft's database
			uuid = UUID.get(username);

			if (uuid == null) {
				// checks if username exists in minecraft api
				Hytools.sendMessage("§5" + I18n.format("nickalert.isnicknamed", username));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
