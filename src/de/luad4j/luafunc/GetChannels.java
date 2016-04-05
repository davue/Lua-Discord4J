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
		for(IChannel channel : channelList)
		{
			LuaValue channelTable = LuaValue.tableOf();
			channelTable.set("name", channel.getName());
			channelTable.set("id", channel.getID());
			channelListTable.add(channelTable);
		}
		
		return channelListTable;
	}
}
