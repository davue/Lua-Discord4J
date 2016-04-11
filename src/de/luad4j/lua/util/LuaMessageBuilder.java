package de.luad4j.lua.util;

import java.util.List;

import org.luaj.vm2.LuaValue;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;

public class LuaMessageBuilder 
{
	public static LuaValue build(IMessage message) throws DiscordException
	{
		LuaValue luamessage = LuaValue.tableOf();
		luamessage.set("author", LuaUserBuilder.build(message.getAuthor()));
		//luamessage.set("channel", LuaChannelBuilder.build(message.getChannel()));
		luamessage.set("text", message.getContent());
		luamessage.set("creation", message.getCreationDate().toString());
		//luamessage.set("guild", LuaGuildBuilder.build(message.getGuild()));
		luamessage.set("id", message.getID());
		
		List<IUser> mentions = message.getMentions();
		LuaValue mentionList = LuaValue.tableOf();
		for(int i = 0; i < mentions.size(); i++)
		{
			mentionList.set(i+1, LuaUserBuilder.build(mentions.get(i)));
		}
		if(mentions.size() > 0)
		{
			luamessage.set("mentions", mentionList);
		}
		luamessage.set("mentionsall", LuaValue.valueOf(message.mentionsEveryone()));
		
		return luamessage;
	}
}
