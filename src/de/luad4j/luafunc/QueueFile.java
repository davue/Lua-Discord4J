package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;

public class QueueFile extends TwoArgFunction {

	@Override
	public LuaValue call(LuaValue voicechannelid, LuaValue filepath) 
	{
		try 
		{
			Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).getAudioChannel().queueFile(filepath.tojstring());
			return LuaValue.NIL;
		} 
		catch (DiscordException e) 
		{
			e.printStackTrace();
			return LuaValue.valueOf("DiscordException");
		}
	}
}
