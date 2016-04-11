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
