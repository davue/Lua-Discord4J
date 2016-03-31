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
			mDiscordClient = builder.login(); //Builds the IDiscordClient instance and logs it in
		} 
		catch (DiscordException e) 
		{
			System.err.println("Error occurred while logging in!");
			e.printStackTrace();
		}
		
		// Load Lua
		try
		{
			registerLuaFunctions(); // register lua functions
			mLuaEnv.get("dofile").call(args[2]); // execute lua main file
		} 
		catch (LuaError err)
		{
			System.err.println("Error occured while loading lua mine file!");
			err.printStackTrace();
		}
		
		// Start event listener
		EventDispatcher dispatcher = mDiscordClient.getDispatcher();
		dispatcher.registerListener(new EventHandler());
	}
	
	private static void registerLuaFunctions()
	{
		mLuaEnv.set("sendMessage", new sendMessage(mDiscordClient));
	}
}
