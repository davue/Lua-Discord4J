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

package de.luad4j.lua.func;

import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IChannel;

public class GetChannels extends OneArgFunction
{
	@Override
	public LuaValue call(LuaValue guildid) 
	{
		List<IChannel> channelList = Main.mDiscordClient.getGuildByID(guildid.tojstring()).getChannels();
		
		// save name and id of every channel in a table
		LuaValue channelListTable = LuaValue.tableOf();
		for(int i = 0; i < channelList.size(); i++)
		{
			LuaValue channelTable = LuaValue.tableOf();
			channelTable.set("name", channelList.get(i).getName());
			channelTable.set("id", channelList.get(i).getID());
			channelListTable.set(i+1, channelTable);
		}
		
		return channelListTable;
	}
}
