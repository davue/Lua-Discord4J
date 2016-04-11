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
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IRole;

public class GetRoles extends TwoArgFunction
{
	@Override
	public LuaValue call(LuaValue userid, LuaValue guildid) 
	{
		List<IRole> roleList = Main.mDiscordClient.getUserByID(userid.tojstring()).getRolesForGuild(Main.mDiscordClient.getGuildByID(guildid.tojstring()));
		
		// save name and id of every channel in a table
		LuaValue roleListTable = LuaValue.tableOf();
		for(int i = 0; i < roleList.size(); i++)
		{
			LuaValue roleTable = LuaValue.tableOf();
			roleTable.set("name", roleList.get(i).getName());
			roleTable.set("id", roleList.get(i).getID());
			roleListTable.set(i+1, roleTable);
		}
		
		return roleListTable;
	}
}
