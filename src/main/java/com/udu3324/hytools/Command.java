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
        collection.put("partyguessfriends", "2");
        collection.put("partyguessguilds", "3");
        collection.put("nickalert", "4");
        collection.put("nickalerthypixelapi", "5");
		collection.put("setapikey", "6");
		collection.put("autofetchapikey", "7");

		// /hytools
        if (args.length < 1) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[+]= Hytools v" + Reference.VERSION + " by udu3324 =[+]\n\n"
					+ EnumChatFormatting.GREEN + "/fcheck (player1) (player2) - checks player's friend\n"
					+ EnumChatFormatting.RED + "/hytools setAPIKey - Sets the api key manually\n"
					+ EnumChatFormatting.RED + "/hytools autoFetchAPIKey [toggled|" + Config.getAutoFetchAPIKey() + "]\n"
            		+ EnumChatFormatting.DARK_AQUA + "/hytools partyguess [toggled|" + Config.getPartyGuess() + "]\n"
            		+ EnumChatFormatting.DARK_GREEN + "/hytools partyguessfriends [toggled|" + Config.getPartyGuessFriend() +"] \n"
            		+ EnumChatFormatting.GOLD + "/hytools partyguessguilds [toggled|" + Config.getPartyGuessGuild() + "]\n"
            		+ EnumChatFormatting.DARK_PURPLE + "/hytools nickalert [toggled|" + Config.getNickAlert() + "]\n"
            		+ EnumChatFormatting.GRAY + "Using Hypixel API for NickAlert is in the grey area of being allowed. " + EnumChatFormatting.BOLD + "Use at your own risk.\n"
            		+ EnumChatFormatting.DARK_PURPLE + "/hytools nickalerthypixelapi [toggled|" + Config.getNickAlertHypixelAPI() + "]"));
            return;
        }
        
		// /hytools ??? (get tool toggled)
        if (args.length == 1) {
        	String command = args[0].toUpperCase();
            String data;
            
            if (command.equals("PARTYGUESS")) {
            	if (Config.getPartyGuess()) 
            		data = "ON";
        		else 
        			data = "OFF";
            } else if (command.equals("PARTYGUESSFRIENDS")) {
            	if (Config.getPartyGuessFriend())
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
            	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("comd.setapikey")));
                return;
            } else {
            	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + command + 
            			" doesn't exist in Hytools! "));
                return;
            }
            
        	sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + command + 
        			" is currently set to " + data + "."));
            return;
        }

		// /hytools ??? ??? (toggle a tool)
        if (args.length == 2) {
            String command = args[0].toLowerCase();
            String data = args[1].toUpperCase();
            String collec = collection.get(command);
			
            if (collec == null) {
                sender.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.RED + command + " is not a valid command. Do /hytools for help!"));
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
                        EnumChatFormatting.RED + "Invalid 2nd argument! Make sure its either toggle, on, or off."));
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
            } else if (command.equals("PARTYGUESSFRIENDS")) {
            	if (data.equals("ON")) {
            		Config.setPartyGuessFriend(true);
            	} else if (data.equals("OFF")) {
            		Config.setPartyGuessFriend(false);
            	} else if (data.equals("TOGGLE")) {
            		if (Config.getPartyGuessFriend()) {
            			Config.setPartyGuessFriend(false);
            			data = "OFF";
            		} else { //would be off
            			Config.setPartyGuessFriend(true);
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
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "The Hypixel API key has been set sucessfully for Hytools."));
				} else {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "The Hypixel API key provided is invalid!"));
				}
			}
            
            //commands that are successful end up here
            sender.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.GREEN + command + " is now set to " + data.toLowerCase() + "."));
        }
        
		// /hytools ??? ??? ??? (invalid command)
        if (args.length >= 3) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Too many arguments! You probably have a typo."));
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