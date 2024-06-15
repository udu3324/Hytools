<img align="right" src="https://raw.githubusercontent.com/udu3324/Hytools/main/src/main/resources/logo.png" height="200"  width="200">

![Mod Loader: Forge](https://img.shields.io/badge/mod%20loader-Forge%201.8.9-e04e14)
[![Latest Release](https://img.shields.io/github/v/release/udu3324/Hytools)](https://github.com/udu3324/Hytools/releases/latest)
![License](https://img.shields.io/github/license/udu3324/hytools)
[![Localization](https://badges.crowdin.net/slimefunaddon/localized.svg)](https://crowdin.com/project/slimefunaddon)
![Github Clones](https://img.shields.io/github/downloads/udu3324/hytools/total)
[![Modrinth Downloads](https://img.shields.io/badge/dynamic/json?color=1bd96a&label=modrinth&query=downloads&suffix=%20downloads&url=https%3A%2F%2Fapi.modrinth.com%2Fv2%2Fproject%2Fhytools)

# Hytools
Hytools is a forge mod that adds useful client sided features to make the experience better on Hypixel. 

### PartyGuess

A tool that looks at chat and guesses players that are in a party. PartyGuess also checks if the first player in that party is in a guild with anyone else in the party. This tool works about **73%** of the time. This could fail if Hypixel decides to join everyone at the same time.

![](https://cdn.modrinth.com/data/rZiwXEaU/images/e9f8f5f64e52005d750cb5a027153ee9f48d374b.png)

  

**NickAlert**

A tool that alerts if someone joined is nicked. This tool **will not reveal the person** behind the nick, but will just send it in chat. This tool will work half the time. (uses mc api) NickAlertHypixelAPI has been removed due to the api rework.

![](https://cdn.modrinth.com/data/rZiwXEaU/images/f5c311380dc62310d54e8c606d3f4c4f318b3b36.png)

![](https://cdn.modrinth.com/data/rZiwXEaU/images/6e247ac4e93bafe5e0bb3d451a28cb315e098f62.png)

## ⌨️ Commands

These are the commands to toggle certain tools in Hytools.

![](https://cdn.modrinth.com/data/rZiwXEaU/images/fcd0d95faeb663472687dffc9b4e93f921689460.png)

 
## 📜 Translations

We have a [Crowdin project](https://crowdin.com/project/slimefunaddon) for translations. You can also submit translations here on GitHub, but Crowdin is preferred since it makes it much easier to resolve issues.

## 💾 Installation

Use the mod at your own risk. (this applies to all mods for hypixel) All chat messages from this mod is up to the player's interpretation of it.

 1. Download [Forge 1.8.9](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) or have a client like Feather/Lunar/etc. that supports forge mods.
 2. Download the latest version of Hytools [here](https://github.com/udu3324/Hytools/releases/latest).
 3. Place the jar file into your mods folder. (.minecraft/mods)
 4. Tada! Hytools is installed.

## 🛠️ Build From Source

If you want to build your own jar of Hytools, you can!

 1. Make sure you have Java Development Kit 8 Installed.
 2. [Clone](https://github.com/udu3324/Hytools/archive/refs/heads/main.zip) the repository.
 3. Extract the zip.
 4. Open a terminal and navigate to all of the mod's files. (`cd` on windows)
 5. Run `gradlew build` or `.\gradlew build` depending on your OS.
 6. The artifacts should now be in the `/build/libs` folder
 7. (optional) Set up a dev workflow by opening the repo in your favorite IDE (vsc recommended) and run `gradlew runClient` to start minecraft with the mod. Add [ReAuth](https://www.curseforge.com/minecraft/mc-mods/reauth/files/4407996) to `/run/mods` to allow logging into Hypixel.

## ❤️ Contribute

You can contribute by starring the repo, reporting issues, creating pull requests, and suggesting ideas.

