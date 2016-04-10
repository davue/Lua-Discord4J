package de.luad4j.luafunc.audio;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;

public class GetQueueSize extends OneArgFunction
{
	@Override
	public LuaValue call(LuaValue voicechannelid) 
	{
		try 
		{
			int queueSize = Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).getAudioChannel().getQueueSize();
			return LuaValue.valueOf(queueSize);
		} 
		catch (DiscordException e) 
		{
			return LuaValue.valueOf("DiscordException");
		}
	}
}
