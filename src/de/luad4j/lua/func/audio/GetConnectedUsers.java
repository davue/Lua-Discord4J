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

package de.luad4j.lua.func.audio;

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
