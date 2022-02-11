package com.udu3324.hytools.tools.gamerestyle;

import com.udu3324.hytools.Config;
import com.udu3324.hytools.Hytools;

public class GameRestyle {
	int tempSlotNum = 0;
	static String slot = "";
	
	public static Boolean startsIn(String filteredMSG) {
		if (!Config.getGameRestyle()) {
    		//this is to disable game restyle if it's disabled in config
    		return false;
    	}
		//get the int in the msg
		int indexOfSpace = filteredMSG.indexOf(" ", 19);
		String substringed = filteredMSG.substring(19, indexOfSpace);
		
		int seconds = Integer.valueOf(substringed);
		
		String appendS = "";
		if (seconds > 1) {
			appendS = "s";
		}
		
		String colorCode = "2";
		if (seconds <= 2) { //red if 2 1
			colorCode = "4";
		} else if (seconds <= 5) { //yellow if 5 4 3
			colorCode = "e";
		}
		
		Hytools.sendMessage("\u00A7e\u00A7l* " + "\u00A7r\u00A7aThe game starts in \u00A7" + colorCode + "\u00A7l" + seconds + "\u00A7r\u00A7a second" + appendS + "!");
		return true;
	}
	
	public static Boolean startCanceled() {
		if (!Config.getGameRestyle()) {
    		//this is to disable game restyle if it's disabled in config
    		return false;
    	}
		
		Hytools.sendMessage("\u00A7e\u00A7l* " + "\u00A7r\u00A74Start has been canceled.");
		return true;
	}
	
	static int doubleNegative = 0;
	
	public static Boolean joinMSG(String unfilteredMSG) {
		if (!Config.getGameRestyle()) {
    		//this is to disable game restyle if it's disabled in config
    		return false;
    	}
		//get the slots of msg
		int indexOfJoined = unfilteredMSG.indexOf(" has joined ") + 12;
		int lastIndex = unfilteredMSG.indexOf("!");
		slot = unfilteredMSG.substring(indexOfJoined, lastIndex);
		
		doubleNegative = 0;
		
		//get player of msg
		String player = unfilteredMSG.substring(0, unfilteredMSG.indexOf(" "));
		
		Hytools.sendMessage("\u00A7a\u00A7l+ " + "\u00A7r\u00A7e" + slot + " " + player);
		return true;
	}
	
	public static Boolean leaveMSG(String unfilteredMSG) {
		if (!Config.getGameRestyle()) {
    		//this is to disable game restyle if it's disabled in config
    		return false;
    	}

		//get slot number and -1 from it
		String slotNum = slot.substring(5, slot.indexOf("/") - 8);
		int slotNumber = Integer.valueOf(slotNum) - 1 - doubleNegative;
		doubleNegative++;
		
		//get player of msg
		String player = unfilteredMSG.substring(0, unfilteredMSG.indexOf(" "));
		
		Hytools.sendMessage("\u00A74\u00A7l- " + "\u00A7r\u00A7e" + slot.replaceFirst(slotNum + "", slotNumber + "") + " " + player);
		return true;
	}
}
