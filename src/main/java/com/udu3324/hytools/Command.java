package com.udu3324.hytools;

import java.util.HashMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.udu3324.hytools.hyapi.HypixelApiKey;

public class Command extends CommandBase {

    private HashMap<String, String> collection = new HashMap<String, String>();

    @Override
    public String getCommandName() {
        return "hytools";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "hytools <args>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		//put the commands in the collection
        collection.put("partyguess", "1");
        collection.put("partyguessguilds", "2");
        collection.put("nickalert", "3");
        collection.put("nickalerthypixelapi", "4");
		collection.put("setapikey", "5");
		collection.put("autofetchapikey", "6");

		// /hytools
        if (args.length < 1) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[+]= Hytools v" + Reference.VERSION + " =[+]\n"
					+ EnumChatFormatting.GOLD + "Credits: Mod-udu3324 I18n-lroj Original-wateTina\n\n"
					+ EnumChatFormatting.GREEN + "/fcheck (player1) (player2) - "+I18n.format("command.help1")+"\n"
					+ EnumChatFormatting.RED + "/hytools setAPIKey - "+I18n.format("command.help2")+"\n"
					+ EnumChatFormatting.RED + "/hytools autoFetchAPIKey [toggled|" + Config.getAutoFetchAPIKey() + "]\n"
            		+ EnumChatFormatting.DARK_AQUA + "/hytools partyguess [toggled|" + Config.getPartyGuess() + "]\n"
            		+ EnumChatFormatting.GOLD + "/hytools partyguessguilds [toggled|" + Config.getPartyGuessGuild() + "]\n"
            		+ EnumChatFormatting.DARK_PURPLE + "/hytools nickalert [toggled|" + Config.getNickAlert() + "]\n"
            		+ EnumChatFormatting.GRAY + I18n.format("command.help8.1") + " " + EnumChatFormatting.BOLD + I18n.format("command.help8.2")+"\n"
            		+ EnumChatFormatting.DARK_PURPLE + "/hytools nickalerthypixelapi [toggled|" + Config.getNickAlertHypixelAPI() + "]"));
            return;
        }
        
		// /hytools ??? (get if the tool is toggled)
        if (args.length == 1) {
        	String command = args[0].toUpperCase();
            String data;
            
            if (command.equals("PARTYGUESS")) {
            	if (Config.getPartyGuess()) 
            		data = "ON";
        		else 
        			data = "OFF";
            } else if (command.equals("PARTYGUESSGUILDS")) {
            	if (Config.getPartyGuessGuild())
            		data = "ON";
        		else
        			data = "OFF";
            } else if (command.equals("NICKALERT")) { 
            	if (Config.getNickAlert())
            		data = "ON";
        		else 
        			data = "OFF";
            } else if (command.equals("NICKALERTHYPIXELAPI")) { 
            	if (Config.getNickAlertHypixelAPI())
            		data = "ON";
        		else
        			data = "OFF";
            } else if (command.equals("AUTOFETCHAPIKEY")) { 
            	if (Config.getAutoFetchAPIKey())
            		data = "ON";
        		else
        			data = "OFF";
            } else if (command.equals("SETAPIKEY")) { 
            	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("command.setapikey")));
                return;
            } else {
            	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + command + " " + 
            			I18n.format("command.doesn")));
                return;
            }
            
        	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + command.toUpperCase() + " " + 
        				I18n.format("command.currently") + " " + data.toLowerCase() + "."));
            return;
        }

		// /hytools ??? ??? (toggle a tool)
        if (args.length == 2) {
            String command = args[0].toLowerCase();
            String data = args[1].toUpperCase();
            String collec = collection.get(command);
			
            if (collec == null) {
                sender.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.RED + command + I18n.format("command.ncmd")));
                return;
            }

			command = command.toUpperCase();

			// handle true or false as toggling
			if (data.equals("TRUE")) {
				data = "ON";
			} else if (data.equals("FALSE"))
				data = "OFF";
            
            if (!data.equals("TOGGLE") && !data.equals("ON") && !data.equals("OFF") && !command.equals("SETAPIKEY")) {
            	sender.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.RED + I18n.format("command.i2a")));
            	return;
            }
            
            if (command.equals("PARTYGUESS")) {
            	if (data.equals("ON")) {
            		Config.setPartyGuess(true);
            	} else if (data.equals("OFF")) {
            		Config.setPartyGuess(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getPartyGuess()) {
            			Config.setPartyGuess(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setPartyGuess(true);
            			data = "ON";
            		}
            	}
            } else if (command.equals("PARTYGUESSGUILDS")) {
            	if (data.equals("ON")) {
            		Config.setPartyGuessGuild(true);
            	} else if (data.equals("OFF")) {
            		Config.setPartyGuessGuild(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getPartyGuessGuild()) {
            			Config.setPartyGuessGuild(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setPartyGuessGuild(true);
            			data = "ON";
            		}
            	}
            } else if (command.equals("NICKALERT")) { 
            	if (data.equals("ON")) {
            		Config.setNickAlert(true);
            	} else if (data.equals("OFF")) {
            		Config.setNickAlert(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getNickAlert()) {
            			Config.setNickAlert(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setNickAlert(true);
            			data = "ON";
            		}
            	}
            } else if (command.equals("NICKALERTHYPIXELAPI")) { 
            	if (data.equals("ON")) {
            		Config.setNickAlertHypixelAPI(true);
            	} else if (data.equals("OFF")) {
            		Config.setNickAlertHypixelAPI(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getNickAlertHypixelAPI()) {
            			Config.setNickAlertHypixelAPI(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setNickAlertHypixelAPI(true);
            			data = "ON";
            		}
            	}
            } else if (command.equals("AUTOFETCHAPIKEY")) { 
            	if (data.equals("ON")) {
            		Config.setAutoFetchAPIKey(true);
            	} else if (data.equals("OFF")) {
            		Config.setAutoFetchAPIKey(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getAutoFetchAPIKey()) {
            			Config.setAutoFetchAPIKey(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setAutoFetchAPIKey(true);
            			data = "ON";
            		}
            	}
            } else if (command.equals("SETAPIKEY")) {
				if (HypixelApiKey.setKey(data, false)) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + I18n.format("command.apiset")));
				} else {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("command.apiad")));
				}
			}
            
            //commands that are successful end up here
            sender.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.GREEN + command + " " + I18n.format("command.setn") + " " + data.toLowerCase() + "."));
        }
        
		// /hytools ??? ??? ??? (invalid command)
        if (args.length >= 3) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("command.mas")));
            return;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}