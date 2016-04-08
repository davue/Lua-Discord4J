package de.luad4j.luafunc;

import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IGuild;

public class GetGuilds extends ZeroArgFunction
{
	@Override
	public LuaValue call() 
	{
		List<IGuild> guildList = Main.mDiscordClient.getGuilds();
		
		// save name and id of every channel in a table
		LuaValue guildListTable = LuaValue.tableOf();
		for(int i = 0; i < guildList.size(); i++)
		{
			LuaValue guildTable = LuaValue.tableOf();
			guildTable.set("name", guildList.get(i).getName());
			guildTable.set("id", guildList.get(i).getID());
			guildListTable.set(i+1, guildTable);
		}
		
		return guildListTable;
	}
}
