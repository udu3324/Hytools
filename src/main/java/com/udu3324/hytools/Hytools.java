package com.udu3324.hytools;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.udu3324.hytools.hyapi.HypixelApiKey;
import com.udu3324.hytools.mcapi.UUID;
import com.udu3324.hytools.tools.gamerestyle.GameRestyle;
import com.udu3324.hytools.tools.nickalert.NickAlert;
import com.udu3324.hytools.tools.partyguess.PartyGuess;

@Mod(modid = Reference.MODID,name = Reference.NAME, version = Reference.VERSION)
public class Hytools {
	
	boolean isOnHypixel = false;
	boolean doOnce = true;
	boolean doOnce2 = true;
	boolean doOnceOnWorldLoaded = true;
	
	boolean skipAPINewKeyProcess = false;
	
	public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	// set logger
    	log = event.getModLog();
    	
    	//create a config on fresh start of mod
    	Config.create();
    }
    
	public static void sendMessage(String text) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, new ChatComponentText(text));
        MinecraftForge.EVENT_BUS.post(event); // Let other mods pick up the new message
        if (!event.isCanceled()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message); // Just for logs
        }
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event) {
		log.info("Hytools v" + Reference.VERSION + " has loaded! (by udu3324)");
		
        // register subscribe events
        MinecraftForge.EVENT_BUS.register(this);
        
        // register commands
        ClientCommandHandler.instance.registerCommand(new Command());
    }
	
	@SubscribeEvent
    public void onWorldLoaded(EntityJoinWorldEvent event) {
		if (!doOnceOnWorldLoaded) { //prevents infinite loop
			return;
		}
		// if statements have to be seperate or else serverIP could be null and crash mc
        if (!Minecraft.getMinecraft().isSingleplayer()) { //is multi-player
        	String serverIP = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        	if (serverIP.toLowerCase().contains("hypixel.net")) { //if is on hypixel
        		isOnHypixel = true;
        		
        		skipAPINewKeyProcess = HypixelApiKey.setKeyFromConf();
        	} else {
        		isOnHypixel = false;
        	}
        } else {
    		isOnHypixel = false;
    	}
        
        //TEMP!!! REMOVE AFTER DEVELOPMENT
        //skipAPINewKeyProcess = HypixelApiKey.setKeyFromConf();
        //isOnHypixel = true;
        //TEMP!!! REMOVE AFTER DEVELOPMENT
        
        doOnceOnWorldLoaded = false;
    }
	
	public static void runPaGuNiAl(final String filtered) {
        Matcher noSpace = Pattern.compile("^ ").matcher(filtered);
        
		if (!noSpace.find()) {
        	PartyGuess.guessMessageParty(filtered);
        	
        	new Thread(new Runnable() {
        	     @Override
        	     public void run() {
        	    	 NickAlert.checkIfNicked(filtered); 
        	     }
        	}).start();
        }
	}

    @SubscribeEvent
    public void onPlayerChat(ClientChatReceivedEvent event) {
    	if (!isOnHypixel) { //stop if not on hypixel
    		return;
    	}
    	
    	//request for new api key
    	if (doOnce && !skipAPINewKeyProcess) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/api new");
			doOnce = false;
		}
    	
        final String filtered = event.message.getUnformattedText();
        final String unfiltered = event.message.getFormattedText();
        
        //get the new api key in chat and prevent client from seeing it on the first time
        Matcher m = Pattern.compile("^Your new API key is ").matcher(filtered);
        if (m.find() && filtered.length() == 56) {
        	String key = filtered.substring(20);
        	
        	HypixelApiKey.setKey(key);
        	
        	//allows new api keys to be seen in chat after it was requested by hytools
        	if (doOnce2) {
        		event.setCanceled(true);
        		doOnce2 = false;
        	}
        }
        
        Matcher gameStarts = Pattern.compile("(?=.*^The game starts in )(?=.* seconds!$)").matcher(filtered);
	    Matcher gameStarts2 = Pattern.compile("(?=.*^The game starts in )(?=.* second!$)").matcher(filtered);
	    
	    Matcher playerLeave = Pattern.compile(" has quit!$").matcher(filtered);
        Matcher playerJoin = Pattern.compile("(?=.* has joined \\().*(?=.*\\)!$)").matcher(filtered);
        
        if (gameStarts.find() || gameStarts2.find()) {
        	event.setCanceled(GameRestyle.startsIn(filtered));
        } else if (playerJoin.find()) {
        	runPaGuNiAl(filtered);
        	event.setCanceled(GameRestyle.joinMSG(unfiltered));
        } else if (playerLeave.find()) {
        	event.setCanceled(GameRestyle.leaveMSG(unfiltered));
        } else if (filtered.equals("We don't have enough players! Start cancelled.")) {
        	event.setCanceled(GameRestyle.startCanceled());
        }
    }
}
