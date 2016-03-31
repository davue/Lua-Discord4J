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
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;


public class Main 
{
	public static IDiscordClient mDiscordClient;
	public static Globals mLuaEnv = JsePlatform.standardGlobals();
	
	@SuppressWarnings("deprecation") // Testuser needs to be converted to botuser
	public static void main(String[] args) 
	{
		// Login into Discord
		ClientBuilder builder = new ClientBuilder();
		builder.withLogin(args[0], args[1]);
		try 
		{
			mDiscordClient = builder.login(); // Builds the IDiscordClient instance and logs it in
		} 
		catch (DiscordException e) 
		{
			System.err.println("Error occurred while logging in!");
			e.printStackTrace();
		}
		
		// load Lua
		try
		{
			registerLuaFunctions(); // Register lua functions
			mLuaEnv.get("dofile").call(args[2]); // Execute lua main file
		} 
		catch (LuaError err)
		{
			System.err.println("Error occured while loading lua main file!");
			err.printStackTrace();
		}
		
		// Start event listener
		EventDispatcher dispatcher = mDiscordClient.getDispatcher();
		dispatcher.registerListener(new EventHandler());
	}
	
	private static void registerLuaFunctions()
	{
		mLuaEnv.set("sendMessage", new sendMessage());
	}
}
