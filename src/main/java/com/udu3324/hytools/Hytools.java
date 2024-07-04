package com.udu3324.hytools;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import com.udu3324.hytools.commands.Credits;
import com.udu3324.hytools.commands.ScanForNicks;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Hytools.MOD_ID, name = Hytools.MOD_NAME, version = Hytools.VERSION)
public class Hytools {
	public static final String MOD_ID = "@ID@";
	public static final String MOD_NAME = "@NAME@";
	public static final String VERSION = "@VER@";

	public static HytoolsConfig config;
	
	public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	// set logger
    	log = event.getModLog();
    }
    
	//sends a message to the user (not to the server)
	public static void sendMessage(String text) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, new ChatComponentText(text));
        MinecraftForge.EVENT_BUS.post(event); // Let other mods pick up the new message
        if (!event.isCanceled())
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message); // Just for logs
    }

	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		log.info("Hytools v" + Hytools.VERSION + " has loaded! (by udu3324)");

		//start the config
		config = new HytoolsConfig();

        //register subscribe events
		MinecraftForge.EVENT_BUS.register(new EventListener());
        
        //register commands
		CommandManager.register(new Credits());
		CommandManager.register(new ScanForNicks());
    }
}
