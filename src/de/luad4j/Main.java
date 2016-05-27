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

import org.slf4j.LoggerFactory;

import de.luad4j.lua.func.SetTimer;
import de.luad4j.lua.obj.LuaClient;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Main 
{
	public static IDiscordClient 			mDiscordClient;										// Client instance of current user
	public static LuaClient					mLuaClient;											// Lua client instance
	public static Globals 					mLuaEnv = JsePlatform.standardGlobals();			// The main lua environment
	public static boolean					mAlreadyInitialized;								// If lua has already been initialized
	
	private static String 					mLuaPath;											// The path to the lua main file
	private static final org.slf4j.Logger 	mLogger = LoggerFactory.getLogger(Main.class);		// Logger of this class
	
	@SuppressWarnings("deprecation")	// Support user login as long as Discord4J supports it
	public static void main(String[] args) 
	{
		if(args.length >= 4)
		{	
			// Lua isn't initialized yet
			mAlreadyInitialized = false;
			
			// Check for parameters
			if(args[0].equals("-user")) // Login into Discord with user
			{
				mLuaPath = args[3];
				
				try
				{
					ClientBuilder builder = new ClientBuilder();
					mDiscordClient = builder.withLogin(args[1], args[2]).login();
				}
				catch (DiscordException e)
				{
					mLogger.error("Failed to login: " + e.getErrorMessage());
				}
			}
			else if(args[0].equals("-bot")) // Login into Discord with bottoken
			{
				mLuaPath = args[2];
				
				try
				{
					ClientBuilder builder = new ClientBuilder();
					mDiscordClient = builder.withToken(args[1]).login();
				}
				catch (DiscordException e)
				{
					mLogger.error("Failed to login: " + e.getErrorMessage());
				}
			}
			else // Print usage and exit
			{
				mLogger.info("Usage: java -jar Lua-Discord4J.jar -user <email> <password> <luamainfile> [port]");
				mLogger.info("Usage: java -jar Lua-Discord4J.jar -bot <bottoken> <luamainfile> [port]");
				System.exit(0);
			}
			
			// Init message
			mLogger.info("Lua-Discord4J v1.0.1");
			mLogger.info("A Lua wrapper for Discord4J. Copyright (c) 2016, Licensed under GNU GPLv3");
			
			// Start port listener if desired
			if(!args[args.length-1].equals(mLuaPath))
			{
				PortListener listener = new PortListener(Integer.valueOf(args[args.length-1]).intValue());
				listener.start();
			}
			else
			{
				mLogger.info("Running without port listener.");
			}

			// Start event listener
			EventDispatcher dispatcher = mDiscordClient.getDispatcher();
			dispatcher.registerListener(new EventHandler());
		}
		else
		{
			System.out.println("Usage: java -jar Lua-Discord4J.jar -user <email> <password> <luamainfile> [port]");
			System.out.println("Usage: java -jar Lua-Discord4J.jar -bot <bottoken> <luamainfile> [port]");
			System.exit(0);
		}
	}
	
	public static void initializeLua()
	{
		// load Lua
		try
		{
			registerLuaFunctions(); // Register lua functions
			mLuaEnv.get("dofile").call(mLuaPath); // Execute lua main file
		} 
		catch (LuaError e)
		{
			mLogger.error("Failed to load lua main file: " + e.getMessage());
		}
	}
	
	private static void registerLuaFunctions()
	{
		// Create new lua client and set it global
		mLuaClient = new LuaClient();
		mLuaEnv.set("discord", LuaClient.getTable());
		
		// Define non-discord functions
		mLuaEnv.set("setTimer", new SetTimer());	// Used to postpone a given function
	}
}
