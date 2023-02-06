package com.udu3324.hytools;

import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.tools.nickalert.NickAlert;
import com.udu3324.hytools.tools.partyguess.PartyGuess;

@Mod(modid = Reference.MODID,name = Reference.NAME, version = Reference.VERSION)
public class Hytools {
	
	boolean isOnHypixel = false;
	boolean requestApiKeyOnce = true;
	boolean doOnceOnWorldLoaded = true;
	
	boolean configAPIKeySet = false;
	
	public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	// set logger
    	log = event.getModLog();
    	
    	//create a config on fresh start of mod
    	Config.create();
    }
    
	//sends a message to the user (not to the server)
	public static void sendMessage(String text) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, new ChatComponentText(text));
        MinecraftForge.EVENT_BUS.post(event); // Let other mods pick up the new message
        if (!event.isCanceled())
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message); // Just for logs
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event) {
		log.info("Hytools v" + Reference.VERSION + " has loaded! (by udu3324)");
		
        // register subscribe events
        MinecraftForge.EVENT_BUS.register(this);
        
        // register commands
        ClientCommandHandler.instance.registerCommand(new Command());
		ClientCommandHandler.instance.registerCommand(new FCheck());
    }
	
	@SubscribeEvent
    public void onWorldLoaded(EntityJoinWorldEvent event) {
		if (!doOnceOnWorldLoaded)
			return;

		// if minecraft world is multiplayer & server ip is hypixel.net
        if (!Minecraft.getMinecraft().isSingleplayer()) {
        	String serverIP = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        	if (serverIP.toLowerCase().contains("hypixel.net")) { 
        		configAPIKeySet = HypixelApiKey.setKeyFromConf();
				isOnHypixel = true;
        	} else {
        		isOnHypixel = false;
        	}
        } else {
    		isOnHypixel = false;
    	}
        
        //TEMP!!! REMOVE AFTER DEVELOPMENT
        //configAPIKeySet = HypixelApiKey.setKeyFromConf();
        //isOnHypixel = true;
        //TEMP!!! REMOVE AFTER DEVELOPMENT
        
        doOnceOnWorldLoaded = false;
    }

	void runTools(final String filtered, final Boolean hytillities) {
		String user;
        //parse username depending on if user is using hytilities
        if (hytillities) {
            user = filtered.substring(filtered.indexOf(" ", 3) + 1, filtered.length());
        } else {
            if (filtered.indexOf(" ") != -1)
				user = filtered.substring(0, filtered.indexOf(" "));
            else
				user = filtered;
        }

		final String username = user;

		PartyGuess.guessMessageParty(username);
    	
		new Thread(new Runnable() {
			@Override
			public void run() {
				NickAlert.run(username);
			}
	   }).start();
	}
	
    @SubscribeEvent
    public void onPlayerChat(ClientChatReceivedEvent event) {
		//stop if not on hypixel
    	if (!isOnHypixel) return;
    	
		//return if message contains obfuscation
		if (event.message.getFormattedText().contains("\u00A7k")) return;

    	//request for new api key if allowed
    	if (Config.getAutoFetchAPIKey() && requestApiKeyOnce && !configAPIKeySet) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/api new");
			requestApiKeyOnce = false;
		}
    	
		//get chat without the formatting (color codes)
        String filtered = event.message.getUnformattedText();
        
        //recieve messages from command "/api new" and parse
        Matcher m = Pattern.compile(I18n.format("hytools.api.new")).matcher(filtered);
        if (m.find() && filtered.length() == 56)
        	HypixelApiKey.setKey(filtered.substring(20), true);

		// ^\+ \([0-9]+\/[0-9]+\) [a-zA-Z0-9_]{1,16}$ | "+ (23/24) NintendoOS"
		Matcher hytillitiesJoined = Pattern.compile("^\\+ \\([0-9]+\\/[0-9]+\\) [a-zA-Z0-9_]{1,16}$").matcher(filtered);
		// ^[a-zA-Z0-9_]{1,16} has joined \([0-9]+/[0-9]+\)!$ | "NintendoOS has joined (1/16)!"
        Matcher joined = Pattern.compile("^[a-zA-Z0-9_]{1,16} has joined \\([0-9]+/[0-9]+\\)!$").matcher(filtered);

		//check chat messages for regex
		String filterSpaces = filtered.replaceAll(" ", "");
        Matcher duels = Pattern.compile("^Opponent:").matcher(filterSpaces);
        
        if (joined.find()) {
        	runTools(filtered, false);
        } else if (hytillitiesJoined.find()) {
        	runTools(filtered, true);
        } else if (duels.find()) {
        	//remove opponent
        	filterSpaces = filterSpaces.substring(9);
        	
        	//remove rank prefix (if it has one)
        	if (filterSpaces.indexOf(']') != -1)
        		filterSpaces = filterSpaces.substring(filterSpaces.indexOf(']') + 1);
        	
        	//pass it on to nickalert
        	runTools(filterSpaces, false);
        }
    }
}