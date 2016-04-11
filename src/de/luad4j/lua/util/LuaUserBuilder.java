package de.luad4j.lua.util;

import org.luaj.vm2.LuaValue;

import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;

public class LuaUserBuilder 
{
	public static LuaValue build(IUser user) throws DiscordException
	{
		LuaValue luauser = LuaValue.tableOf();
		luauser.set("avatarURL", user.getAvatarURL());
		luauser.set("creation", user.getCreationDate().toString());
		luauser.set("discriminator", user.getDiscriminator());
		if(user.getGame().isPresent())
		{
			luauser.set("game", user.getGame().get());
		}
		luauser.set("id", user.getID());
		luauser.set("name", user.getName());
		// roles implementation?
		if(user.getVoiceChannel().isPresent())
		{
			luauser.set("voicechannel", LuaVoiceChannelBuilder.build(user.getVoiceChannel().get()));
		}
		luauser.set("isbot", LuaValue.valueOf(user.isBot()));
		
		return luauser;
	}
}
