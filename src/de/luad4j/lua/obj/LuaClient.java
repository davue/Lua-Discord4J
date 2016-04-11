package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;

import sx.blah.discord.api.IDiscordClient;

public class LuaClient 
{
	private IDiscordClient 	mClient;	// Client object inside Java
	private LuaValue		mLuaClient;	// Table: Client object inside Lua
	
	public LuaClient()
	{
		
	}
}
