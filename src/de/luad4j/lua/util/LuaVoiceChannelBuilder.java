package de.luad4j.lua.util;

import org.luaj.vm2.LuaValue;

import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;

public class LuaVoiceChannelBuilder 
{
	public static LuaValue build(IVoiceChannel channel) throws DiscordException
	{
		LuaValue luachannel = LuaValue.tableOf();
		luachannel.set("id", channel.getID());
		luachannel.set("name", channel.getName());
		return luachannel;
	}
}
