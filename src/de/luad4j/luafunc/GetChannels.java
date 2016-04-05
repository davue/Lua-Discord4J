package de.luad4j.luafunc;

import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IChannel;

public class GetChannels extends OneArgFunction{

	@Override
	public LuaValue call(LuaValue guildid) {
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
