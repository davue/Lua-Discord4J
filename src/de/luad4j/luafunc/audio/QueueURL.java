package de.luad4j.luafunc.audio;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;

public class QueueURL extends TwoArgFunction
{
	@Override
	public LuaValue call(LuaValue voicechannelid, LuaValue url) 
	{
		try 
		{
			Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).getAudioChannel().queueUrl(url.tojstring());
			return LuaValue.NIL;
		} 
		catch (DiscordException e) 
		{
			e.printStackTrace();
			return LuaValue.valueOf("DiscordException");
		}
	}
}
