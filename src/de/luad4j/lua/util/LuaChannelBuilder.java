package de.luad4j.lua.util;

import org.luaj.vm2.LuaValue;

import sx.blah.discord.handle.obj.IChannel;

public class LuaChannelBuilder 
{
	public static LuaValue build(IChannel channel)
	{
		LuaValue luachannel = LuaValue.tableOf();
		luachannel.set("id", channel.getID());
		luachannel.set("name", channel.getName());
		luachannel.set("creation", channel.getCreationDate().toString());
		luachannel.set("topic", channel.getTopic());
		luachannel.set("isprivate", LuaValue.valueOf(channel.isPrivate()));
		// guild field?
		
		return luachannel;
	}
}
