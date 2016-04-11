package de.luad4j.lua.func.audio;

import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class GetConnectedVoiceChannels extends ZeroArgFunction
{
	@Override
	public LuaValue call()
	{
		List<IVoiceChannel> channelList = Main.mDiscordClient.getConnectedVoiceChannels();
		
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
