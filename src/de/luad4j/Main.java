/*
 * Lua-Discord4J - Lua wrapper for Discord4J Discord API
 * Copyright (C) 2016
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.luad4j;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.lib.jse.JsePlatform;

import de.luad4j.luafunc.*;
import de.luad4j.luafunc.audio.*;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;


public class Main 
{
	public static IDiscordClient mDiscordClient;					// Client instance of current user
	public static Globals mLuaEnv = JsePlatform.standardGlobals();	// The main lua environment
	private static String mLuaPath;
	
	@SuppressWarnings("deprecation") // Testuser needs to be converted to botuser
	public static void main(String[] args) 
	{
		mLuaPath = args[2];
		
		// Login into Discord
		ClientBuilder builder = new ClientBuilder();
		builder.withLogin(args[0], args[1]);
		try 
		{
			mDiscordClient = builder.login(); // Builds the IDiscordClient instance and logs it in
		} 
		catch (DiscordException e) 
		{
			System.err.println("[JAVA][Main] Error occurred while logging in!");
			e.printStackTrace();
		}
		
		// Start port listener if desired
		if(args.length == 4)
		{
			PortListener listener = new PortListener(Integer.valueOf(args[3]).intValue());
			listener.start();
		}

		// Start event listener
		EventDispatcher dispatcher = mDiscordClient.getDispatcher();
		dispatcher.registerListener(new EventHandler());
	}
	
	public static void initializeLua()
	{
		// load Lua
		try
		{
			registerLuaFunctions(); // Register lua functions
			mLuaEnv.get("dofile").call(mLuaPath); // Execute lua main file
		} 
		catch (LuaError err)
		{
			System.err.println("[JAVA][Main] Error occured while loading lua main file!");
			err.printStackTrace();
		}
	}
	
	private static void registerLuaFunctions()
	{
		mLuaEnv.set("getGuilds", new GetGuilds());
		
		mLuaEnv.set("sendMessage", new SendMessage());
		mLuaEnv.set("updateMessage", new UpdateMessage());
		mLuaEnv.set("deleteMessage", new DeleteMessage());
		
		mLuaEnv.set("getChannels", new GetChannels());
		mLuaEnv.set("getVoiceChannels", new GetVoiceChannels());
		mLuaEnv.set("joinVoiceChannel", new JoinVoiceChannel());
		mLuaEnv.set("leaveVoiceChannel", new LeaveVoiceChannel());
		
		mLuaEnv.set("queueFile", new QueueFile());
		mLuaEnv.set("queueURL", new QueueURL());
		mLuaEnv.set("clearQueue", new ClearQueue());
		mLuaEnv.set("resumeAudio", new ResumeAudio());
		mLuaEnv.set("pauseAudio", new PauseAudio());
		mLuaEnv.set("setAudioVolume", new SetAudioVolume());
	}
}
