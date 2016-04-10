package de.luad4j.luafunc.audio;

import java.util.List;
import java.util.stream.Collectors;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class GetConnectedUsers extends OneArgFunction
{
	@Override
	public LuaValue call(LuaValue voicechannelid) 
	{
		IVoiceChannel voiceChannel = Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring());
		
		List<IUser> connectedUsers = voiceChannel.getGuild().getUsers().stream()
			.filter(u -> voiceChannel.equals(u.getVoiceChannel().orElse(null)))
			.collect(Collectors.toList());
		
		LuaValue userlist = LuaValue.tableOf();
		for(int i = 0; i < connectedUsers.size(); i++)
		{
			LuaValue user = LuaValue.tableOf();
			user.set("id", connectedUsers.get(i).getID());
			user.set("name", connectedUsers.get(i).getName());
		}
		
		return userlist;
	}
}
