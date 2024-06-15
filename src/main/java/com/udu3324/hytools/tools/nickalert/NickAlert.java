package com.udu3324.hytools.tools.nickalert;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;
import com.udu3324.hytools.mcapi.UUID;
import net.minecraft.client.resources.I18n;

public class NickAlert extends Thread {
	public static void run(String username) {
		// if tool is toggled off, dont run it
		if (!Config.getNickAlert()) return;

		String uuid = null;

		try {
			// check if the user exists in minecraft's database
			uuid = UUID.get(username);

			// broken wifi or something
			if (uuid == null) return;

			if (uuid.equals("Not a IGN or UUID!")) {
				// checks if username exists in minecraft api
				Hytools.sendMessage("\u00A75" + username + " " + I18n.format("nickalert.isnicknamed"));
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
