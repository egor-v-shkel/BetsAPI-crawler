# BetsAPI Crawler v. 0.1.2

![alt tag](https://github.com/Vision666/BetsAPI-crawler/blob/master/src/main/resources/images/icon.png?raw=true)

## Table of content

- [Description](#description)
- [Features](#features)   
- [Release](#release)
- [Screenshots](#screenshots)
- [Usage](#usage)
- [TODO](#TOOD)

## Description

App made to crawl football matches from betsapi.com and notify user about matched events via telegram and desktop GUI.

## Features
* Load/Save settings.
* Load previously saved settings at startup.
* Notify user about events via Telegram bot.
* Stop crawl session via Telegram bot.

## Release
[BetsAPI Crawler Version 0.1.2](https://github.com/Vision666/BetsAPI-crawler/releases)

The Java Runnable requires [Java 8u211]( https://java.com/ru/download/).

## Screenshots

Main window:

![alt tag](https://i.ibb.co/C1xNMMf/image.png)

Telegram settings:

![alt tag](https://i.ibb.co/0QWZ67q/image.png)

Telegram notification example:

![alt tag](https://i.ibb.co/FBbXhyD/image.png)

## Usage

1. You need to register you own telegram bot. Follow this [tutorial](https://core.telegram.org/bots#3-how-do-i-create-a-bot).
1.1. You may implement following commands for your bot via [@BotFather](https://telegram.me/BotFather)'s command "/setcommands <YOUR_BOT_NAME>":
 * /id - will be used for getting current ID of chat with your bot.
 * /exit - will close application.
2. Launch application. At initial start text fields in main window will be filled with default parameters. Fill them as you wish for your own needs. Note: parameter Logic have 2 parameters:
 * "AND" - in this case app will notify user about match, that meet all 3 inputted parameters: possession, target on, target off.  
 * OR - in this case app will notify user about match, that meet any of 3 inputted parameters: possession, target on, target off.
3. Move to "Settings\Telegram bot settings". Here you need to fill 3 text fields: 
 * Chat ID - ID of a chat to which your bot will send messages. You can get it by following this [tutorial](https://stackoverflow.com/a/38388851). Note: Make sure, that Chat ID at Telegram bot settings entered in "123456..."  format for sending private messages and "-123456..." format if bot is in a group.
 * Bot token - your bot token from 1st step
 * Bot name - your bot name from 1st step. Example: Your_cool_bot
 Click apply/ok.
4. Press start to begin crawl session.
 
 ## TODO
 * Add more sources to crawl (e.g. www.marathonbet.by) 
 * Multiple and custom crawl conditions
 * Proxy support
 * English locale
