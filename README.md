
  

<img  align="right"  src="https://raw.githubusercontent.com/udu3324/Hytools/main/src/main/resources/logo.png"  height="200"  width="200">

  

<img  alt="GitHub"  src="https://img.shields.io/github/license/udu3324/Hytools">

  

<a title="Crowdin" target="_blank" href="https://crowdin.com/project/slimefunaddon"><img src="https://badges.crowdin.net/slimefunaddon/localized.svg"></a>

  

<img  alt="GitHub downloads"  src="https://img.shields.io/github/downloads/udu3324/hytools/total">

  

<img  alt="Latest release"  src="https://img.shields.io/github/v/release/udu3324/Hytools">

  

<img  alt="forge version support"  src="https://img.shields.io/badge/mod%20loader-Forge 1.8.9-e04e14">

  

# Hytools

A mod that has useful tools for Hypixel. Credit to the modding community for helping me make my first mod + Eye_Of_Wither for using /nick so I can create NickAlert.

  

**FriendCheck**

A tool that checks if a player is friends with another player.

![](https://media.discordapp.net/attachments/956773599644090379/1004243687900983406/unknown.png)

  

**PartyGuess**

A tool that looks at chat and guesses players that are in a party. PartyGuess also checks if the first player in that party is friends with anyone else in the party. This tool works about **90%** of the time. This could fail if Hypixel decides to join everyone at the same time.

![](https://media.discordapp.net/attachments/956773599644090379/1004244515642671115/unknown.png)

  

**NickAlert**

A tool that alerts if someone joined is nicked. This tool **will not reveal the person** behind the nick, but will just alert it in chat. This tool will half of the time. (uses mc api to check if username exists) You can enable NickAlertHypixelAPI to get more accuracy. (using hypixel api for nickalert is in the gray zone of being allowed. use at your own risk)

![](https://cdn.discordapp.com/attachments/956773599644090379/1004244058262220880/unknown.png)

![](https://media.discordapp.net/attachments/956773599644090379/964672960873000980/unknown.png)

  
## Translations

We have a [Crowdin project](https://crowdin.com/project/slimefunaddon) for translations. You can also submit translations here on GitHub, but Crowdin is preferred since it makes it much easier to resolve issues.

## Installation

Use the mod at your own risk. (this applies to all mods for hypixel)

Make sure you have [Forge 1.8.9](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) installed. You can install the latest version of Hytools by clicking [here](https://github.com/udu3324/Hytools/releases/latest) and clicking on the jar.

Go to your mods folder (.minecraft/mods) and drag Hytools-#.#.#.jar into it. Tada! Hytools is installed.

  

## Commands

Here are the commands you can use to turn certain tools on and off with:

![](https://cdn.discordapp.com/attachments/919010475679825960/1003739858184974416/unknown.png)

 

## Build From Source

If you want to build your own jar of Hytools, you can!

0, Make sure you have Java Development Kit 8 installed.

1, Clone the repository

2, Extract the zip

3, Open a terminal and cd to the source code

4, Run `gradlew build` or `.\gradlew build` depending on terminal

5, Navigate to build/libs

Done! You should now see 2 jars generated. The sources jar is for devs only and not for end users to put in the mods folder.

  

## Contribute

You can contribute by starring the repo, reporting issues, creating pull requests, and suggesting ideas.
