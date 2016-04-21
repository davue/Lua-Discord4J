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
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.luad4j.lua.func.SetTimer;
import de.luad4j.lua.obj.LuaClient;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Main 
{
	public static IDiscordClient 	mDiscordClient;								// Client instance of current user
	public static LuaClient			mLuaClient;									// Lua Client instance
	public static Globals 			mLuaEnv = JsePlatform.standardGlobals();	// The main lua environment
	private static String 			mLuaPath;
	
	@SuppressWarnings("deprecation") // Testuser needs to be converted to botuser
	public static void main(String[] args) 
	{
		if(args.length >= 4)
		{			
			Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.INFO);
			
			if(args[0].equals("-user"))
			{
				mLuaPath = args[3];
				
				// Login into Discord
				ClientBuilder builder = new ClientBuilder();
				builder.withLogin(args[1], args[2]);
				
				loginDiscord(builder);
			}
			else if(args[0].equals("-bot"))
			{
				mLuaPath = args[2];
				
				// Login into Discord
				ClientBuilder builder = new ClientBuilder();
				builder.withToken(args[1]);
				
				loginDiscord(builder);
			}
			else
			{
				System.out.println("Usage: java -jar Lua-Discord4J.jar -user <email> <password> <luamainfile> [port]");
				System.out.println("Usage: java -jar Lua-Discord4J.jar -bot <bottoken> <luamainfile> [port]");
				System.exit(0);
			}

			
			// Start port listener if desired
			if(!args[args.length-1].equals(mLuaPath))
			{
				PortListener listener = new PortListener(Integer.valueOf(args[args.length-1]).intValue());
				listener.start();
			}
			else
			{
				System.out.println("[INFO] Running without port listener.");
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
	
	public static void loginDiscord(ClientBuilder builder)
	{
		try 
		{
			mDiscordClient = builder.login(); // Builds the IDiscordClient instance and logs it in
		} 
		catch (DiscordException e) 
		{
			System.err.println("[JAVA][Main] Error occurred while logging in!");
			e.printStackTrace();
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
		catch (LuaError err)
		{
			System.err.println("[JAVA][Main] Error occured while loading lua main file!");
			err.printStackTrace();
		}
	}
	
	private static void registerLuaFunctions()
	{
		mLuaClient = new LuaClient();
		mLuaEnv.set("discord", LuaClient.getTable());
		
		// Define non-discord functions
		mLuaEnv.set("setTimer", new SetTimer());	// Used to postpone a given function
	}
}
