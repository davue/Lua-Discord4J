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
		ClientBuilder builder = new ClientBuilder();
		builder.withLogin("discord.stammbot@gmail.com", "stammgruppe123");
		try 
		{
			mDiscordClient = builder.login(); //Builds the IDiscordClient instance and logs it in
		} 
		catch (DiscordException e) 
		{
			System.err.println("Error occurred while logging in!");
			e.printStackTrace();
		}
		
		EventDispatcher dispatcher = mDiscordClient.getDispatcher();
		dispatcher.registerListener(new EventHandler(mLuaEnv));
		
		registerLuaFunctions(); // register lua functions
		
		try
		{
			mLuaEnv.get("dofile").call("test.lua"); // execute lua main file
		} 
		catch (LuaError err)
		{
			System.err.println("Error occured while loading lua mine file!");
			err.printStackTrace();
		}
	}
	
	private static void registerLuaFunctions()
	{
		mLuaEnv.set("sendMessage", new sendMessage(mDiscordClient));
	}
}
